| Michael Bielang, <Michael.Bielang@hs-augsburg.de>, IN6, #2036823

Rust in der Praxis
==================

Rust kommt ursprünglich von Mozilla ist aber auch bei zahlreichen Firmen wie Samsung Smart Home, Threema, Zeplin oder Yelp in Verwendung.

Dropbox
-------

Dropbox, der Filehosting-Dienstanbieter, hat 2016 mit Rust eine der wohl entscheidendsten  Veränderungen ihrer Firmengeschichte geschafft. Dropbox bietet Usern ein in der Cloud gespeichertes und zu den Benutzer Computern synchronisiertes Dateisystem. Dabei wurde die Amazon Cloud im Hintergrund als Datencenter verwendet. 2014 wurde dann begonnen ein lokales Datencenter zu entwerfen, um die über 500 Petabyte zukünftig komplett selbst verwalten zu können. Zur Speicherung der Daten hat Dropbox ein neues System namens Diskotech entwickelt. Der geschlossene Block kann dabei ca. 1 Petabyte an Daten speichern, ist extrem kompakt und lässt dabei einzelne HDD's bei einem Partitionsausfall einfach und schnell austauschen. Das zuvor für die Amazon Cloud entwickelte System Magic Pocket musste dann für die neuen Discotechs neu geschrieben werden. Zuerst hatte das Dropboxteam mit Go begonnen. Nach einem Jahr haben sie allerdings schmerzlich feststellen müssen, dass sie mit Go erhebliche Speicherprobleme haben und diese nicht in den Griff bekommen. Gerade da Dropbox einen sehr hohen Durchsatz an Daten hat, wird der Speicher für den Transfer benötigt. Mit Rust wurde dann ein neuer Versucht gestartet. Dieser konnte erfolgreich fertig gestellt werden und ist seither im produktiven Einsatz. Seit 2016 sind rund 90% der Daten von Dropbox erfolgreich in das hausinterne Datahouse mit der neuen Software umgezogen.

.. figure:: img/dropbox_discotech.jpg


npm
---

Mit 826 Tausend Javascript Paketen und 1.3 Milliarde Paketdownloads pro Tag ist npm das größte Software-Register. Die Herausforderungen bei npm liegen vor allem in der Effizienz bei Anfragen und das Ganze noch weiter zu skalieren. Da npm nahezu exponentiell wächst, suchen die Entwickler immer nach Möglichkeiten und Code-Stellen um die Performance zu verbessern. Bei der Autorisierung um zu überprüfen ob Nutzer berechtigt sind Pakete hoch zu laden, sahen die Entwickler einen CPU lastigen Vorgang der in Zukunft zu einem Bottelneck führen könnte. 

Auf der Suche nach einer alternativen Programmiersprache zur Überarbeitung der Autorisierung legten die Entwickler besonders Wert auf:


-  Speichersicherheit
-  Compilierung zu einem standalone und einfach deployfähigem Binary
-  dauerhaft bessere Perfomance als JavaScript

Unter diesen Kriterien blieben Go und Rust übrig. Zur Evaluierung der verschiedenen Programmiersprachen programmierten sie ihre Autorisierung erneut sowohl in Node.js als auch in Go und Rust. 
Mit den JavaScript Experten bei npm konnten sie die Autorisierung in einer Stunde neu schreiben und eine nahezu gleiche Performance bekommen.
Die Programmierung in Go hat 2 Tage gedauert, allerdings war das Team mit dem Dependency Management sehr unzufrieden, sodass Go kein Favorit wurde.
Der Einstieg in Rust und die darauffolgende Umsetzung hat die Entwickler eine Woche gekostet. Allerdings hat das gute Dependency Management und die hilfreiche und freundliche Community dann das Team dazu überzeugt Rust für die endgültige Umsetzung zu verwenden. Die neue Implement ist jetzt seit über 1,5 Jahren im produktiven Einsatz und läuft seitdem performant und fehlerfrei. Den einzigen Nachteil sehen die Entwickler bei npm in der neuheit der Sprache, wodurch viele Standartlibraries zum Loggen, Monitoren und Fehleralamierung noch keine Rust Unterstützung anbieten.


writing code vs writing rust
----------------------------

Man kann Rust code ähnlich wie anderen Programmiersprachen mit den typischen Bedingungen und Schleifen schreiben.

..  code-block:: rust

	let mut result: u64 = 0;
    let mut i: u64 = 0;
    loop {
        if i >= max {
            break;
        }
        if i % 6 == 0 || i % 9 == 0 {
            result += i;
        }
        i += 1;
    }
    return result;
	
Allerdings ist das nicht der performanteste und eleganteste Weg. Die 10 Zeilen Code lassen sich auf eine Zeile reduzieren. Dies wird wohl einer der Gründe gewesen sein weshalb das npm team 1 Woche gebraucht hat ihre Autorisierung umzusetzten. 

..  code-block:: rust

	pub fn euler_six_nine_div_sum(max: u64) -> u64 {
		(0..max).filter(|n| n % 6 == 0 || n % 9 == 0).sum()
	}
	
(0..max) erzeugt ein Range Objekt über das sich mit einer Schrittweite von 1 von 0 bis max-1 iterieren lässt. Diese Range wird anschließend mittels eines closures gefiltert. Als letztes werden alle gefilterten Werte aufsummiert.
	
Beginnt man mit Rust ist es ratsam sich in die Rust spezifische Syntax und for allem die Rust standard Befehle einzuarbeiten.
	
testing
-------

Schreibt man Code der eine gewisse Robustheit haben soll (und das nachweisbar), kommt man selten über Tests hinaus. Für Tests kann man einfach eine normale Funktion schrieben und diese als Test annotieren. Standartmäßig sind dann auch alle gängigen Assertions aufrufbar. Mit "cargo test" werden alle Tests ausgeführt.

..  code-block:: rust

	#[test]
	fn test_normal_euler() {
		let value = euler_six_nine_div_sum(13);
		assert_eq!(value, 27);
	}

Hat man ein Modul oder einen Test der nur zur Testlaufzeit compiliert werden soll kann man das "#[cfg(test)]" einfügen.
	
documenting
-----------

Ein weiterer wichtiger Baustein bei lebendigem und wachsendem Code ist eine Dokumentation. Mit drei Slashes definiert man generelle Kommentare die in die Dokumentation gefasst werden. Mit "//!" werden Module dokumentiert. Über "cargo doc" wird dann die Dokumentation erstellt. Bei der erstellung der Dokumentation kann noch als Parameter angegeben werden, dass Dependency Dokumentation nicht mit übernommen wird und sich das erstellte Dokument öffnen soll ("cargo doc --no-deps --open").

..  code-block:: rust

	/// a typical euler problem to sum up all number to a given max where the value is devidable by 6 or by 9
	/// implemented in rust style
	pub fn euler_six_nine_div_sum(max: u64) -> u64 {
	 ...
	 
Zur Dokumentation von Submodulen welche keine API zur Verfügung stellen ist die standard Rust-Dokumentation nicht ohne Umstände geeignet. Hierfür können Libraries wie mdbook verwendet werden.

.. figure:: ./img/rust_documentation.PNG


benchmarking
------------

Rust bietet laut Dokumentation benchmarking Tests für Funktionen an. Um diese auszuprobieren haben wir zwei standard Sortieralgorithmen implementiert. Nach einigen Versuchen mussten wir feststellen, dass benchmarking derzeit nur im Nightly build und nicht in der stable Version verfügbar ist. Die Sortieralgorithmen sind weiterhin in der sorting.rs mit normalen Tests zu finden.

Quellen
--------

Dropbox löst sich von der Amazon Cloud 
	https://www.wired.com/2016/03/epic-story-dropboxs-exodus-amazon-cloud-empire/
	
npm 
	https://www.rust-lang.org/static/pdfs/Rust-npm-Whitepaper.pdf
	
sammlung rust in der Produktion
	https://www.rust-lang.org/production/users