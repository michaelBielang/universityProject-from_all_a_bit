| Mirjam Lawall, <Mirjam.Lawall@hs-augsburg.de>, IN6, #2003849


physikalische Realisierung (Hardware)
=====================================

Probleme beim Bau eines Quantencomputers:
-----------------------------------------

1. "Festhalten" 
'''''''''''''''
Das "Festhalten" einer ausreichenenden Menge von Quantenbits.


2. Relaxation:
'''''''''''''''
Relaxation bedeutet, dass ein System, welches sich selbst überlassen wird, sich ins thermische Gleichgewicht mit seiner Umgebung bringen wird. Dies geschieht beispielsweise über einen Energieaustausch mit der Umgebung. Durch diese Interaktion zwischen den Qubits und der Umwelt werden aber Berechnungen verfälscht, da die Qubits ihren Zustand ändern.


3. Dekohärenz
''''''''''''''
Das Dekohärenzprinzip wurde 1970 seitens des Physikers Dieter Zeh beschrieben. Bei diesem Phänomen wechselwirkt ein bislang abgeschlossenes System mit seiner Umgebung. Dadurch wird der Zustand des Systems verändert. Bei Quantencomputern bedeutet Dekohärenz, dass ein Qubit seine Superpositionseigenschaft verliert und in den Zustand 0 oder 1 wechselt. Dies ist ein rein quantenmechanisches Problem.


Probleme beheben
----------------

1. abgeschlossenes physikalisches System
''''''''''''''''''''''''''''''''''''''''
Die oben genannten Probleme sollen durch ein abgeschlossenes physikalisches System verhindert werden, welches alle äußeren Einflüsse abschirmt. Der Energie- und Platzaufwand für solch ein physikalisches System ist jedoch noch zu groß, als dass man einen Quantencomputer wirtschaftlich herstellen könnte.

2. Fehlerkorrektur
''''''''''''''''''
Eine weitere Notwendigkeit ist die Fehlerkorrektur. Diese wird auch bei Digitalrechnern benötigt. Es ist mathematisch bewiesen, dass es auch bei Quantencomputern möglich ist.

2.1. Fehlererkennungsmaßnahmen
...............................
Fehlererkennungsmaßnahmen können fehlerhafte Quantenzustände und eliminieren. Dadurch gehen alle fehlerbehafteten Qubits verloren. Deshalb ist dieses Verfahren nur für die Quantenkryptographie einsetzbar.


2.2. Fehlerkorrekturmaßnahmen
..............................
Fehlerkorrekturmaßnahmen erkennen und beseitigen Fehler während des Rechenprozesses. Dabei dürfen aber keine Messungen an den betroffenden Qubits durchgeführt werden.


2.3. Fehlertoleranzverfahren
.............................
Dieses Verfahren greift nicht während des Rechenprozesses ein. Stattdessen basiert es auf Coderedundanzen, die fehlerhafte Ergebnisse auf fehlerfreie Ergebnisse abbilden. Allerdings darf die Anzahl der Fehler nicht zu groß sein.



Umsetzung Quantencomputer
-------------------------
Für die physikalische Realisierung gibt es verschiedene Ansätze, wie Qubits erzeugt werden können.

1. Nuclear Magnetic Resonanz (NMR)
''''''''''''''''''''''''''''''''''
Bei dieser Methode wird der Kernspin einzelner Athome genutzt, um Informationen zu speichern. Das hat den Vorteil, dass der Kernspin in großen Molekülen von der Umgebung abgeschirmt ist und somit weniger störanfällig ist. 

1.1 QC auf Diamantbasis: Kernspinresonanz 
..........................................
Ein Diamant besteht aus Kohlenstoffkristallen. In manchen Fällen tritt ein sogenannter NV-Deffekt (Nitrogen-Vacancy-Fehlstelle) auf. Dabei befindet sich an der Stelle eines Kohlenstoffatoms ein Stickstoffatom. Der Nachbarplatz des Stickstoffatoms ist dann quasi unbesetzt. Dort befindet sich jedoch ein einzelnes Elektron. Das Qubit ist dann der sogenannte Spin dieses Elektrons. Der Spin des Kerns repräsentiert die Zustände. Die Zustände können verändert werden, indem man den Kern mit einem genau eingestellten Laser beleuchtet.

 Dieser Aufbau hat den Vorteil, dass der NV-Deffekt bei Zimmertemperaturen auftritt. Im Gegensatz zu anderen Technologien, wie beispielsweise supraleitende Qubits, benötigt man hier keine tiefen Temperaturen. 

Im Quantencomputer müsste man an mehreren Stellen hauchdünne Diamantplättchen verbauen und an mehreren Stellen den NV-Deffekt gezielt einbauen. 

So konnten die Qubits bis zu 8 Stunden stabil gehalten werden. Ein Grund dafür ist das steife Kristallgitter, welches im Gegensatz zu anderen Materialien kaum Gitterschwankungen aufweist. Zum Vergleich: im Arbeitsspeicher eines gewöhnlichen Computers geht die Energie innerhalb von einigen hundert Millisekunden verloren und die Informationen müssen neu aufgefrischt werden.


2.Ionenfallen
'''''''''''''''
Ein vielversprechender Ansatz für Quantencomputer ist die Verwendung von Ionen in Ionenfallen. Die Ionen werden in sogenannten Ionenfallen zusammengehalten. Die Ionenfallen sind elektromagnetische Felder im Vakuum. Die einzelnen Ionen werden darin wie an einer Perlenkette aufgereiht. Theoretisch könnte man hier auch Photonen nehmen. Allerdings können diese keine Informationen untereinander austauschen. Jedes Ion entspricht einem Qubit.

 Es gibt verschiedene Umsetzungen der Ionenfalle. Die bekannteste ist die "Paul-Falle". Die Ionen werden dabei durch ein zeitlich veränderliches elektromagnetisches Feld zusammengehalten. Dieses elektromagnetische Feld entsteht durch gegenüberliegende Metallstäbe mit einem zeitabhängigen Radiofrequenzpotential. Dann werden die Ionen zu einer linearen Kette aufgereiht und über eine Laserkühlung auf eine Temperatur gebracht, bei der kaum noch Schwingungen vorhanden sind. 

Die Qubit-Manipulation erfolgt durch Laser. Diese können mit den einzelnen Ionen wechselwirken. Durch die Bewegung der Ionen in der Falle können die Qubits verschränkt und miteinander gekoppelt werden.


3. Festkörperphysik
'''''''''''''''''''
In der Festkörperphysik versucht man, Quantenregister und Quantengatter mit Josephson-Kontakten und Quantenpunkten zu realisieren. Ein Beispiel hierfür ist das CNOT Gatter. Dieses ist aus zwei Quantenpunkten aufgebaut.

3.1 Josephson-Effekt
....................
Der Josephson-Effekt ist ein physikalischer Effekt, der den Tunnelstrom zwischen zwei Supraleitern beschreibt. Er wurde 1962 von Brian Josephson beschrieben und noch im selben Jahr von John Rowell im Labor bestätigt. Josephson erhielt für diese Entdeckung 1973 den Nobelpreis der Physik. 

Quellen
-------

http://www.chemie.de/lexikon/Qubit.html

https://www.scinexx.de/news/technik/quantenbits-ueber-stunden-gespeichert/

https://www.golem.de/news/quantencomputer-quantenrechnen-mit-diamanten-1408-108371.html

Gilbert Ross, "Einführung in die Quanteninformatik", Springer

http://www.theorie.physik.uni-goettingen.de/lehre/Uebungen/QM2-Seminar/0506/Quantencomputer.pdf


