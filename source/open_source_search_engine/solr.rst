| Michael Bielang, <Michael.Bielang@hs-augsburg.de>, IN6, #2036823
| Felix Bühler, <felix.buehler@hs-augsburg.de>, IN6, #2008336


Solr
====

Die erste Search Engine die hier betrachtet wird ist Solr. Solr ist eine open source Search Plattform aufgebaut auf Apache Lucene. Es bietet skalierbare Indexierung und Suche, Facettierung, Suchtreffer Highlighting und erweiterte Analyse-/Tokenisierungsfunktionen. Sowohl Lucene als auch Apache werden von der Apache Software Foundation gemanaged.

Server starten und initiale Konfiguration
-----------------------------------------

Der Start mit Solr ist relativ einfach. Nachdem man Solr runter geladen hat starten man den Server über die entsprechende Executeable unter \bin.

::

	solr start

Danach kann man die Webview des Serves unter standart Einstellungen unter "localhost:8983/solr" erreichen. Jede weiteren Befehle und Kommunikation mit dem Server würd über HTTP Request gehandelt. Für uns als Benutzer bietet sich die komfortablere Webview an bzw. als Programmierer haben wir in den meisten Sprachen Frameworks, welche uns wiederkehrenden Overhead ersparen.

.. _figlabel:
.. figure:: ./img/solr_main.PNG
	
Der initiale Solr Server ist komplett leer und besitzt keine Konfigurationen. Bevor wir Daten Indexieren können müssen wir einen Core erstellen. Ein Core entspricht einem Index welcher spezifisch konfiguriert werden kann. Cores befinden sich bei dem normalen (nicht Cloud Modus) unter \server\solr. Ein Core besteht grundlegend aus folgenden Dateien 

::

	solr/server/solr
				|---- core.properties
				|---- conf
						|---- solrconfig.xml
						|---- managed-schema
					
Die core.properties Datei gibt Hauptsächlich den Corenamen an. In der solrconfig.xml kann der Solr Core selbst konfiguriert werden z.B: wo die Daten gespeichert werden sollen, Caching oder die verschiedenen request Handler. Die managed-schema gibt die Felder der zu erwartenden Dokumente an. Hier sind die Felder mit ihren Typen angegeben, auf welche später spezielle Suchen durchgeführt werden können. Seit der Solr Version 6.6 wird empfohlen die Schema nur noch über die Weboberfläche anzupassen. Die klassische Konfiguration über die schema.xml kann weiterhin über die Anpassung der SchemaFactory in der der solrconfig.xml vorgenommen werden.  Verwendet ein Server einen leeren Core wird beim Initialisieren des Cores der Data Ordner erstellt. 
Ein kann ähnlich einfach erstellt werden wie der Server gestartet wird. Dabei wir eine default Schema verwendet, welche versucht den richtigen Feldtypen zu erraten, wenn man ein Dokument dem Index hinzufügt.

::

	solr create -c example_core
	
Dokumente hinzufügen
--------------------
	
Ist der Core erstellt kann man über die den Tab "Documents" einfach Dokumente hinzufügen. Beispielsweise fügen wir 3 Bücher hinzu.

..  code-block:: json

	{"id":"0060248025","name":"Falling Up","inStock": true,"author": "Shel Silverstein"},
	{"id":"0679805273","name":"Oh, all the Places You'll Go","inStock": true,"author": "Dr. Seuss"},
	{"id":"0679805586","name":"Open source it all","inStock": false,"author": "Community"}
	
.. figure:: ./img/solr_add_document.PNG
	
Die Webview sendet hier einfach einen HTTP Request an den Solr Server mit der URL http://localhost:8983/solr/example_core/update?_=1559197241468&commitWithin=1000&overwrite=true&wt=json. Unsere 3 Dokumente stehen dabei als Request Payload in dem Request mit dabei. 

Suchen
------

Nachdem wir nun Dokumente unserem Server hinzugefügt haben, können wir erste Suchen unter dem Tab "Query" durchführen. Initial ist die default Query "*:*" vorausgefüllt welche alle Felder mit jedem Inhalt sucht. Führen wir diese Suche aus bekommen wir unsere 3 Dokumente zu sehen.

.. _figlabel:
.. figure:: ./img/solr_query.PNG

Möchten wir beispielsweise nur die Dokumente angezeigt bekommen welche derzeit verfügbar sind können wir als Query "inStock:true" eintragen, die Suche ausführen und unsere Treffer werden anschließend eingeschränkt.


Schema
------

Als kleinen weiteren Einblick in Solr ist es wichtig mit der Schema zu befassen. Die Typen der Felder sind dabei ein ganz besonders interessanter Punkt. Diese können Analyzer als Konfiguartion gesetzt haben. Die Analyzer bearbeiten entweder vor der Indexierung in das Feld den Inhalt oder vor der Suche den Suchterm. Hierbei gibt es für Textfelder standardmäßg TokenizeFilter oder LowercaseFilter welche bei einer Suche beispielsweise den Suchterm lowercasen und bei Leerzeichen den Suchterm splitten und damit dann mehrere Suchen auf dem ensprechenden Feld durchführen. Führt man in der Webview eine Query auf einem Feld mit Analyzern durch, kann man seine Query debuggen und sieht dabei das vorher beschriebene Vorgehen. Sehr interessant ist dabei auch, dass man seine eigenen Filter schreiben kann. Dadurch kann man seine Suche spezifisch für den Anwendungsfall anpassen.


Weiters
-------

Dieser relativ kurz gehaltene Einblick zeigt nur die Spitze des Eisbergs. Solr kann noch deutlich mehr wie zum Beispiel Trefferranking, Synonymsuchen, "meinten Sie" Vorschläge oder aber auch die Daten verteilt auf mehreren Servern zu managen. 

Quellen
-------

..  solr quick overview
	https://lucene.apache.org/solr/guide/7_7/