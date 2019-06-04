| Michael Bielang, <Michael.Bielang@hs-augsburg.de>, IN6, #2036823


Searchengines Allgemein
=======================

In den letzten Jahren haben sich vor allem Dokumenten Datenbanken durchgesetzt. Diese Datenbanken können sehr schnell Daten speichen, verwalten und große Mengen handeln. Das Problem ist dann meist in diesen großen Datenmengen Einträge wieder zu finden. Daher  werden Searchengines verwendet um hochperformant Suchabfragen durchzuführen. Die Abfragen sind dann im Millisekundenbereich anstelle von mehreren Sekunden, Minuten oder gar Stunden. Die Searchengines haben im meistens auch eine Dokumentendatenbank zu der zusätzlich noch ein Index hinzu kommt. Über diese erszeugten Indexe können die Suchen mit der entsprechenden Performance durchgeführt werden. 

Index
-----

Ein Index ist eher als invertierter Index zu betrachten. Sehr vereinfacht erklärt wird jedes Wort eines Dokuments in den Index geschrieben und in welchem Dokument dieses Wort zu finden ist. Die weiteren Dokumente werden nach demselben Prinzip indexiert, wobei mehrfach auftretende Wörter einmal eingetragen werden mit mehreren Dokumentreferenzen. 

Zum Beispiel gibt es zwei Dokumente mit Fließtext.

Dokument1:
	The free sharing and teaching of open source is incompatible with the notion of the solitary genius.

Dokument2:
	The open source nature of the Internet is both a blessing and a curse, because just as much as we can watch what's happening around the world, we can also be watched.

Der Index würde jetzt wie folgend aussehen:

+------------+------------+
| the 		 | 1,2		  |
+------------+------------+
| open 		 | 1,2		  |
+------------+------------+
| sharing	 |  1   	  |
+------------+------------+
| Internet	 |  2     	  |
+------------+------------+
| ...		 |  ...    	  |
+------------+------------+

Der inverted Index ist dabei wird dabei für jedes Feld erstellt. Die echte Implementiert ist um einiges komplexer und beinhaltet noch zusätzliche Informationen, wie die Position im Dokument oder die Anzahl.

Suchranking
-----------

Bei einer Suche spielen andere Faktoren zusätzlich eine Rolle. Sucht man in unserem oberen Beispiel nach "the world" sollte das zweite Dokument vor dem ersten angezeigt werden, da das Wort "the" eine eher geringe Rolle in der Suche spielt und "world" der ausschlaggebende Begriff ist. Dieses Ranking erreicht man über TF-IDF (Term Frequency * Inverse Dokument Frequency). Das Bedeutet man nimmt die Anzahl wie oft ein Wort in einem Dokument vorkommt und dividiert sie durch Gesamtanzahl in allen Dokumenten. Dadurch würde das Wort "world" stärker gewichtet werden als das Wort "the". Auch hier gibt es noch weitere Mechanismen wie Treffer geranked werden.


Quellen
-------

Elasticsearch Tutorial for Beginners
	https://www.youtube.com/watch?v=C3tlMqaNSaI