| Tobias Drüeke, <tobias.drueeke@hs-augsburg.de>, IN6, #2004114


Beispiel - Indexer
==================

Als letztes Thema hatten wir uns mit open source search engines befasst und wie diese intern funktionieren. Als Beispielprogramm haben wir eine Klasse geschrieben die parallel mittels eines Threadpools über mehrere Dokumente einen Index erstellen können. 


Argumente & Rückgabe
--------------------

..  code-block:: rust
	pub fn frequency(input: &HashMap<&str, &'static str>, worker_count: usize) -> HashMap<String, HashMap<String, u32>> {

Als erster Parameter wird an die Funktion die zu verarbeitenden Dokumente als HashMap mit <docName, text> übergeben. Der zweite Parameter gibt an mit wie vielen Threadpoolworkern die Dokumente verarbeitet werden soll. 

Zurückgegeben wird der erzeugte Index. In der ersten HashMap ist als Key jeweils ein Wort eines Textes. Der Value ist dabei eine weitere HashMap die beinhaltet in welchen Dokumenten das Wort vorhanden ist und wie oft.


Threadpool
----------

Für eine möglichst performante "Indexierung" der übergebenen Dokumente wird jedes einzeln verarbeitet und anschließend zusammengeführt. 

..  code-block:: rust
    let pool = ThreadPool::new(worker_count);
    let (tx, rx) = channel();

    for (&name, &text) in input {
        let thread_tx = tx.to_owned();
        pool.execute(move || thread_tx.send(word_count(&text)).ok().unwrap());
    }

Als erstes wird ein ThreadPool mit der übergebenen Anzahl an Workern erstellt. Anschließend wir ein Sender und ein Empfänger für den Threadpool erstellt. Für jedes Dokument wird dann dem Sender als Struct der Funktionsaufruf zur Verarbeitung des Dokuments mit dem Dokument als Parameter übergeben.


Ein Dokument Index
------------------

Nachdem das Struct übergeben wurde, wird im Threadpool die Funktion zu Verarbeitung aufgerufen. Die Funktion erstellt einen simplen Index, der eine HashMap mit den Wörtern und ihrer Anzahl zurückgibt.

..  code-block:: rust
	fn word_count(words: &str) -> HashMap<String, u32> {
		let mut counts: HashMap<String, u32> = HashMap::new();
		for word in words
			.split(|c: char| c.is_whitespace() || c == ',' || c == ';' || c == '.')
			.map(|s| s.trim_matches(|c: char| !c.is_alphanumeric()))
			.filter(|s| !s.is_empty())
			.map(|s| s.to_lowercase().to_string())
		{
			counts.entry(word).and_modify(|v| *v += 1).or_insert(1);
		}
		counts
	}
	
Der übergebene Text wird zuerst an den gängigen Worttrennungen gesplittet. Anschließend werden die Sonderzeichen ausgemappt, leere Trennungen gefiltert und die Werte klein geschrieben. So wie der Text dann auf einzelne Wörter aufbereitet wurde, wird über jedes einzelne iteriert und entsprechend in die HashMap eingefügt.


Dokument Indexe zusammenführen
------------------------------

Die Indexe der einzelnen Dokumente werden als letztes zu einem Gesamtindex zusammengeführt und zurückgegeben.

..  code-block:: rust
    let mut result = HashMap::new();
    for (&name, text) in input {
        let thread_result = rx.recv().ok().unwrap();
        for(word, count) in thread_result {
            result.entry(word).or_insert(HashMap::new()).insert(name.to_string(), count);
        }
    }
    result

Zum Auslesen der einzelnen Indexe wird über den Empfänger des Threadpools einzeln die Rückgabewerte abgerufen und danach zu einem Gesamtindex zusammengeführt.

	
Testen
------

Der erste Test ist ruf die Funktion ohne Dokumente auf und erwarte daraufhin eine leere Map.

..  code-block:: rust
	#[test]
    fn test_no_texts() {
        assert_eq!(frequency(&HashMap::new(), 4), HashMap::new());
    }
	
Als zweites wird ein simples Dokument mit einem Wort indexiert. Das eine indexierte Wort kann dann "gesucht" werden.

..  code-block:: rust
    #[test]
    fn test_one_word_indexing() {
        let input = [("doc1", "test")].iter().cloned().collect();
        let value = frequency(&input, 4);

        assert!(value.contains_key("test"));
        assert_eq!(value["test"]["doc1"], 1);
    }

Es gibt noch weitere Tests die verschiedene Art und Weisen der Indexerstellung abdecken.


Das komplette Beispiel mit weiteren Tests ist in der indexing.rs zu finden.

Fazit
=====

Rust bietet als Programmiersprache viele Vorzüge und ist dabei sehr performant. So wie bei dem Team von npm war auch für uns der Einstieg allerdings nicht einfach. Neben neuer Syntax mussten wir uns (ausnahmsweise) um die Speicherverwaltung Gedanken machen, was bei den meisten anderen Programmiersprachen automatisch erledigt wird. Zudem bietet Rust viele Befehle und umfangreiche Bibliotheken, mit welchen man sich für einen qualitativ hochwertigen Code ausgiebig auseinandersetzen muss. 
Investiert man also die anfängliche Zeit und setzt sich intensiv mit Rust auseinander, bietet sich danach eine performante, sichere und zugleich schnelle und zufriedenstellende Entwicklung zukünftiger Programme.