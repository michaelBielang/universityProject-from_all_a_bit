| Felix Bühler, <felix.buehler@hs-augsburg.de>, IN6, #2008336
| Tobias Drüeke, <tobias.drueeke@hs-augsburg.de>, IN6, #2004114

Elasticsearch
=============

Grundlegende Konzepte
---------------------

Near Realtime
'''''''''''''
Elasticsearch ist eine near-realtime Suchplattform. Das bedeutet, dass es eine leichte Latenzzeit (normalerweise eine Sekunde) von der Indizierung eines Dokuments bis zur Durchsuchbarkeit gibt.

Cluster
'''''''
Ein Cluster ist eine Sammlung von einem oder mehreren Knoten (Servern), die gemeinsame die gesamten Daten halten und Indexierungs- und Suchfunktionen über alle Knoten hinweg bieten. Ein Cluster wird durch einen eindeutigen Namen identifiziert, der standardmäßig "elasticsearch" lautet. Knoten können dann in ihrer Konfiguration über diesen Namen zu dem Cluster hinzu gefügt werden.

Node
''''
Ein Knoten ist ein einzelner Server der die Daten speichert und Teil der Indexierungs- und Suchfunktionen des Clusters ist. Genau wie ein Cluster wird ein Knoten durch einen Namen identifiziert. Standardmäßig wird ein zufälliger Universally Unique IDentifier (UUID) dem Knoten beim Start zugewiesen. Dieser Name ist wichtig für Verwaltungszwecke, bei denen man feststellen möchten, welche Server in dem Netzwerk welchen Knoten in dem Elasticsearch-Cluster entsprechen.

Index
'''''
Ein Index ist eine Sammlung von Dokumenten, die ähnliche Eigenschaften aufweisen. Beispielsweise können Sie einen Index für Kundendaten, einen weiteren Index für einen Produktkatalog und einen weiteren Index für Auftragsdaten haben. Ein Index wird durch einen Namen identifiziert (der in Kleinbuchstaben geschrieben werden muss). Dieser Name wird verwendet, um auf den Index zu verweisen, wenn Indexierungs-, Such-, Aktualisierungs- und Löschvorgänge gegen die darin enthaltenen Dokumente durchgeführt werden.

Document
''''''''
Ein Dokument ist eine grundlegende Informationseinheit, die indiziert werden kann. Beispielsweise können Sie ein Dokument für einen einzelnen Kunden, ein weiteres Dokument für ein einzelnes Produkt und ein weiteres für einen einzelnen Auftrag haben. Dieses Dokument ist im JSON Format. Innerhalb eines Index kann man beliebig viele Dokumente ablegen.

Shards & Replicas
'''''''''''''''''
Ein Index kann potenziell eine Datenmenge speichern, die die Hardware eines einzelnen Nodes übersteigt. Es könnte nicht genügend Speicher zur Verfügung stehen oder auch dauert eine Suchanfrange an einen einzelnen Node ab ein gewissen Speichermenge zu lange.
Für dieses Problem bietet Elasticsearch die Möglichkeit den Index in mehrere Shards aufzuteilen. Bei der Erstellung eines Indexes wird einfach die Anzahl an Shards mit angegeben. Jeder Shard ist prinzipiell ein eigenständiger Index der auf jedem Node in einem Cluster gehostet werden kann. Wie die Dokumente zwischen den Shards aufgeteilt und abgefragt werden, wird von Elasticsearch gemanaged.

Für die Ausfallsicherheit von Shards & Nodes bietet Elasticsearch zusätzlich die Möglichkeit diese in Replicas zu kopieren. Zudem kann über die Replicas parallel Suchen ausgeführt werden.

Erste Schritte
--------------

Nachdem man sich Elasticsearch heruntergeladen und entpackt hat, kann man den Server direkt über das "elasticsearch" Script im bin Verzeichnis starten.

::

	elasticsearch
	
In die Konsole wir anschließend ausgeloggt mit welchen Parametern und Konfigurationen der Node gestartet wird. Der Node ist demnach standartmäßig unter http://localhost:9200/ erreichbar. Ruft man diese Adresse auf erhält man einen JSON response mit der Node Konfiguration.

..  code-block:: json

	{
	  "name" : "DESKTOP-1NOQ6B4",
	  "cluster_name" : "elasticsearch",
	  "cluster_uuid" : "7PoTqsWRQaaMtX3KFTxSDQ",
	  "version" : {
		"number" : "7.1.1",
		"build_flavor" : "default",
		"build_type" : "zip",
		"build_hash" : "7a013de",
		"build_date" : "2019-05-23T14:04:00.380842Z",
		"build_snapshot" : false,
		"lucene_version" : "8.0.0",
		"minimum_wire_compatibility_version" : "6.8.0",
		"minimum_index_compatibility_version" : "6.0.0-beta1"
	  },
	  "tagline" : "You Know, for Search"
	}

Quellen
-------

elastic search
	https://www.elastic.co