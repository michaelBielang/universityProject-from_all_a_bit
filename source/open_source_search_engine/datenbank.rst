| Mirjam Lawall, <Mirjam.Lawall@hs-augsburg.de>, IN6, #2003849


Einführung - Datenbanken
========================

Datenbanken
-----------

Technisch gesehen gibt es verschiedene Ansätze mit Daten umzugehen. Sind diese 
Digitalisiert können sie klassisch in den vom Betriebssystem bereitgestellten 
Dateisystem gespeichert und organisiert werden. Dateisystem sind relativ einfach, 
intuitiv und meist eine Grundlage die jeder Benutzer eines Betriebssystems 
beherrscht. Da sie eine Grundlage von Betriebssystemen bildet, ist sie inkludiert und 
damit kostenfrei (mit Linux komplett kostenfrei). Durch diesen allgemeinen Ansatz ist 
ein Dateisystem auch leicht für die Speicherung in der Cloud bzw. auf andere Systeme 
migrier bar. Mit diesen Punkten eignet sich das Dateisystem für das anfängliche 
Arbeiten mit Daten. 

Möchte man seine Daten (performant) durchsuchbar haben oder hat man "Big Data" kommt 
man seltenst um eine Datenbank herum. Datenbanken bieten auch nicht die magische 
Lösung für alles können aber für den jeweiligen Anwendungsfall den entsprechenden 
Vorteil bringen. 


SQL vs NoSQL
------------

Spricht man von SQL redet man meist von relationalen Datenbanken. SQL bietet bei 
diesen nur die Sprache, mit der die Daten abgefragt werden. Bei relationalen 
Datenbanken werden die Daten in Tabellen abgelegt welche fest definierte Felder 
besitzen. Jeder Eintrag einer Tabelle besitzt einen einzigartigen Schlüssel. Über 
diesen Schlüssel können Einträge aus einer Tabelle in anderen Tabellen referenziert 
werden. Diese Referenzierungen kombiniert mit einem hohem Normalisierungsgrad 
bieten strukturiert abgelegte Daten mit geringem Speicherverbrauch. 

NoSQL hingegen ist eher als Sammelbegriff für Datenbankmodelle zu verstehen welche 
sich durch ein nicht-relationales Datenmodell, verteilte Speicherung, 
Quelloffenheit, Schemaflexibilität, die Unterstützung für Datenreplikation oder 
eine einfache API und Flexibilität bezüglich der ACID Einhaltung. NoSQL Datenbanken 
gibt es in jeder Ausführung, allerdings klassifiziert man meist die 4 Kategorien: 
-  Key-Value-Stores
-  Wide-Column-Stores
-  Dokumentendatenbanken
-  Graphdatenbanken

Key-Value-Stores
````````````````
Key-Value-Stores sind sehr simple Datenbanken, die wie eine Map funktionieren. Zu jedem Key gibt es einen Wert. Die meisten Datenbanken unterstützen hier unterschiedlichste Datentypen.
Bsp.: Riak, Redis

Wide-Column-Stores
``````````````````
Setzt man bei einer Key-Value-Store Datenbank als Wert eine HashMap gleicht es einer Wide-Column-Store Datenbank. Bei Wide-Column-Store Datenbanken redet man genau wie bei relationalen Datenbanken von Tabellen, wobei diese im Gegenzug Schemafrei sind. In einer Zeile können also beliebige Spalten mit unterschiedlichsten Datentypen gespeichert werden. 
Bsp.: HBase 

Dokumentendatenbanken
`````````````````````
Dokumentendatenbanken bilden auf den ersten Blick auch ein einfaches Datenbankmodell mit Kollektionen welche Dokumente beinhalten. Die Komplexität dieser Datenbanken kommt durch den Aufbau der Dokumente mit ihren Feldern. Diese besitzen auch jeweils Schlüssel id's, wobei diese allerdings teil des Dokuments sind. Die anderen Felder können den unterschiedlichsten Inhalt haben. Von grundlegenden Datentypen wie Nummern oder Strings, über Listen hinzu anderen Dokumenten welche als Kinder eingetragen sind. Mit Listen und Dokumenten können dann auch weitere Verschachtelungen folgen. Typischer weise werden die Daten im Json oder XML Format dargestellt. Ähnlich wie bei SQL können Zugriffe nicht nur über Id`s folgen, sondern auch über jedes Feld der Dokumente. 
Bsp.: MongoDB, CouchDB, SolrServer, Elasticsearch

Graphdatenbanken
````````````````
Im Gegensatz zu den anderen 3 genannten NoSQL Kategorien sind Graphdatenbanken auf navigierende Zugriffe (ähnlich zu joins bei SQL) optimiert. Das Datenbankmodell besteht aus Knoten und Kanten. Die Knoten können ähnlich zu Dokumenten beliebige Properties besitzen. Die Kanten verbinden dabei jeweils einen Start- und einen Zielknoten miteinander. Die Kanten können dabei auch Properties besitzen, wobei das Label angibt in welcher Beziehung die Knoten stehen und immer mit angegeben werden muss.
Bsp.: Neo4J


Die meisten NoSQL Datenbanken haben ihre eigenen Querysyntax. Dies wurde über die Zeit ein immer größerer Overhead für die Entwickler und daher ist in den letzten Jahren ein Wandel zurück zur SQL Syntax wahr zu nehmen. Die meisten NoSQL Datenbanken bieten Schnittstellen bzw. unterstützen SQL abfragen.

Quellen
-------	
..  Datentransformationen in NoSQL-Datenbanken
	https://kluedo.ub.uni-kl.de/frontdoor/deliver/index/docId/4829/file/_Dissertation_Johannes_Schildgen.pdf
	
..  Big Data, SQL und NoSQL – eine kurze Übersicht
	https://www.bigdata-insider.de/big-data-sql-und-nosql-eine-kurze-uebersicht-a-602249/
	
..  Why SQL is beating NoSQL, and what this means for the future of data
	https://blog.timescale.com/why-sql-beating-nosql-what-this-means-for-future-of-data-time-series-database-348b777b847a/

..  Skript Business Intelligence - Prof. Dr. Nikolaus Nüssigmann
