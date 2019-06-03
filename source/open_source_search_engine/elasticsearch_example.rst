| Tobias Drüeke, <tobias.drueeke@hs-augsburg.de>, IN6, #2004114

Elasticsearch Beispiel
======================

Programm Konzept
----------------

Das Programm "elastic_file_indexer" ist in Java geschrieben und dient zur Indexierung und Abfrage des Dateisystems. Das Programm wird gestartet und anschließend kann man zwischen mehreren Optionen wählen:
1.  eine Indexierung starten
2.  alle indexierten Dateien ausgeben
3.  eine spezielle Suche absenden
4.  den aktuellen Status des Clusters abfragen
5.  das Programm beenden

Die initiale Verbindung mit dem Elasticsearch Server aufzubauen ist sehr einfach gemacht. Die mitgegebenen Parameter sind die IP, der Port und das Protokoll wie der Server erreichbar ist.

..  code-block:: java

	 new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
	 
Mit der Instanz des RestHighLevelClient können alle wichtigen Aktionen auf dem Server ausgeführt werden. Der Client ist dabei ein Wrapper der HTTP Anfragen an den Server sendet.

status
------

Die einfachste Abfragen nach einem simplen Ping ist die Statusabfrage. 

..  code-block:: java

	client.info(RequestOptions.DEFAULT);
	
Die Abfrage liefert alle Informationen zurück, die man auch sieht wenn man den Server unter seiner IP und Port im Browser aufruft.

indexing
--------

Zum indexieren werden zuerst vom übergebenen Pfad rekursiv mit Unterordnern alle Dateien gesammelt. Anschließend werden von diesen Dateien die grundlegenden Informationen ausgelesen und in ein JSON Dokument gesammelt. Dieses Dokument wird anschließend in einen Index Request gespeichert. Dieser Index request wird dann final in den Client indexiert.

..  code-block:: java

	XContentBuilder builder = XContentFactory.jsonBuilder();
	BasicFileAttributes attr = null;

	attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
	builder.startObject();
	{
		builder.field("path", file.getAbsolutePath());
		builder.timeField("creationTime",
				LocalDateTime.ofInstant(attr.creationTime().toInstant(), ZoneOffset.systemDefault()));
		...
		builder.field("size", attr.size());
	}
	builder.endObject();
	IndexRequest indexRequest = new IndexRequest("simple_files").id(String.valueOf(id++)).source(builder);
	this.client.index(indexRequest, RequestOptions.DEFAULT);


querying
--------

Suchanfragen zu versenden ist ähnlich einfach wie die Daten zu indexieren. Man erstell als erstes einen SearchSourceBuilder. Diesem wird dann auch über einen QueryBuilder die Query gesetzt. Hier im Beispiel sieht man die Verwendung einer String Query. Über den QueryBuilder kann man auch jegliche anderen (mehr programmatisch) Querys erzeugen lassen. Diesen SearchSourceBuilder setzt man dann einem SearchRequest und kann mit diesem auf dem Client dann die Suche ausführen. Von der Suche bekommt man einen SearchResponse zurück. Dieser enthält neben den Suchergebnissen zusätzliche Informationen über die ausgeführte Suche wie die Ausführdauer, auf welchen Shards gesucht wurde, die gesamte Trefferanzahl oder eine ScrollId zum Blockweise auslesen der Treffer.

..  code-block:: java

	SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
	searchSourceBuilder.query(QueryBuilders.queryStringQuery(query));
	SearchRequest searchRequest = new SearchRequest();
	searchRequest.indices("*");
	searchRequest.source(searchSourceBuilder);
	SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

Zum abfragen aller Dokumente bietet der QueryBuilder eine direkte Funktion an.

..  code-block:: java

	QueryBuilders.matchAllQuery()
	
Stellt man keine maximale Treffermenge ein werden als standart die ersten 10 Treffer zurückgegeben. Möchte man hier alle Dokumente bekommen kann man einen kleinen Trick anwenden. Zuerst sendet man eine Suche bei der beim SearchSourceBuilder die size auf 0 gesetzt ist. Aus dieser Suche bekommt man dann mit einer minimalen Übertragung die gesamte Trefferanzahl und kann diese als neue size in den SearchSourceBuilder setzen. Führt man jetzt die Suche erneut aus bekommt man alle Dokumente gesammelt zurückgegeben. Das ist Weg alle Ergebnisse mit nur 2 Requests zu bekommen. Hat man eine erheblich größere Anzahl an Dateien würde man eher über die scrollId blockweise die Ergebnisse verarbeiten. 



..  code-block:: java 

	searchSourceBuilder.size(0);
	searchRequest.source(searchSourceBuilder);
	SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
	searchSourceBuilder.size((int) searchResponse.getHits().getTotalHits().value);
	searchRequest.source(searchSourceBuilder);
	searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);








