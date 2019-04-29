| Michael Bielang, <Michael.Bielang@hs-augsburg.de>, IN6, #2036823


Wie rechnet der Quantencomputer?
================================

Quantengatter
-------------
Statt den Logikgattern der Digitalrechner führt der Quantencomputer physikalische Manipulationen direkt am Qubit durch. So sind die gleichen Rechenoperationen wie mit Klassischen Logikgattern möglich, aber alle möglichen Berechnungen werden gleichzeitig durchgeführt.
 
Die Quantengatter lassen sich in 2 Kategorien unterteilen:

1. Single-qubit-gate
''''''''''''''''''''
Das Single-qubit-gate ist ein Quantengatter, das auf einem einzelnen Qubit mit den Zuständen 0 und 1 arbeitet.

1.1. Hadamard-Gatter:
.....................
Das Hadamard-Gatter ist ein Single-qubit-gate. Es dient dazu, den Zustand eines Qubits zu verändern und eine Superposition zu erzeugen.

1.2 Phase-Shift Gatter:
....................... 
Das Phase-Shift Gatter verändert Wahrscheinlichkeiten in einem Qubit mit dem es bei Messung entweder in Zustand 1 oder 0 verfällt. Es ist auch ein Single-qubit-gate.

2. Two-qubit-gate
''''''''''''''''''
Das Two-qubit-gate ist ein Quantengatter, dass auf zwei Qubits arbeitet. Dazu ist eine Wechselwirkung zwischen diesen 2 Qubits nötig. 

2.1. Controlled NOT Gatter: 
...........................
Das Controlled NOT oder auch CNOT-Gatter ist ein Two-qubit-gate. Es verschränkt 2 Qubits und invertiert das Ziel-Qubit, wenn das Kontrollqubit 1 ist. 


3. Gatter mit mehr als 2 Einträgen
'''''''''''''''''''''''''''''''''''
Theoretisch ist so ein Quantengatter denkbar.  Allerdings sind dazu Mehrteilcheneffekte notwendig. Diese sind deutlich komplexer in der Umsetzung. Deshalb beschränkt man sich bei Quantencomputer bis jetzt nur auf Single-qubit-gates und Two-qubit-gates. 



Vorteil:
--------
Quantencomputer können alles so berechnen, wie herkömmliche Computer, plus:

- abhörsichere Nachrichtenübertragung

- Teleportation von Informationen

- Erzeugung echter Zufallszahlen

- Knacken von heutigen Verschlüsselungen (sh. Shor's Algorithmus)


Algorithmen für Quantencomputer:
--------------------------------


Probalistischer Algorithmus (Randomisierter Algorithmus)
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''
Das ist ein Algorithmus, der die korrekte Anwort mit einer bestimmten Wahrscheinlichkeit angibt. Die Wahrscheinlichkeit einer fehlerhaften Antwort wird durch die Wiederholung des Algorithmus verkleinert. Ein Beispiel hierfür ist der Monte-Carlo-Algorithmus. 


1. Quanten-Fouriertransformation (Shor's Algorithmus)
'''''''''''''''''''''''''''''''''''''''''''''''''''''
Der Shor-Algorithmus ist ein Faktorisierungsalgorithmus und wurde 1994 entwickelt. Es handelt sich dabei um einen probalistischen Algorithmus. Dieser Algorithmus ist sehr wichtig für die Kryptographie und die Zahlentheorie. Er ermöglicht es, Zahlen in ihre Primfaktoren zu zerlegen. Bei herkömmlichen Computern nimmt der Aufwand dazu exponentiell mit der Länge der Zahlen zu. Auf einem Quantencomputer wäre der Aufwand jedoch nur polynomial (quadratisch mit der Länge der Zahl).


2. Quanten-Suchalgorithmen (Grover's Algorithmus)
'''''''''''''''''''''''''''''''''''''''''''''''''
Der Grover-Algorithmus ist ein Suchalgorithmus für eine unsortierte Datenbank. Er wurde 1996 von Lov Grover veröffentlicht. Er ist ein probalistischer Algorithmus. Auf einem herkömmlichen Computer ist der schnellste Suchalgorithmus für eine unsortierte Datenbank die lineare Suche mit O(N) Rechenschritten. Der Grover Alrothmus benötigt nun O(N)^1/2 Schritte: Er hat also eine quadratische Beschleunigung, was gerade bei großen N deutlich schneller ist. 


3. Quanten-Simulation
'''''''''''''''''''''
Viele Naturphänomene sind zu komplex, um sie in Computermodellen darzustellen. Die Quantensimulation soll dabei helfen, solche komplexe Systeme darzustellen. Ein Anwendungsgebiet ist z.B. die Materialforschung. Dabei könnte man im voraus die Materialeigenschaften berechnen. Herkömmliche Computer sind dazu nicht in der Lage, weil sich ihre Rechengeschwindigkeit exponentiell verlangsamt, wenn sie Quanteneffekte berechnen. 


4. Weitere Algorithmen
''''''''''''''''''''''

4.1 Deutsch-Jozsa-Algorithmus
..........................
Mit dem Deutsch-Jozsa-Algorithmus kann bestimmt werden, ob eine auf einem Bit operierende Funktion konstant oder balanciert ist. 1985 beschäftigte sich David Deutsch mit diesem Problem (Problem von Deutsch, 1 Eingabebit). 1992 verallgemeinerten dann Deutsch und Jozsa die Idee (Problem von Deutsch-Josza, beliebig viele Eingabebits). Dieser Algorithmus hat keinen praktischen Nutzen. Aber er ist der erste Quantenalgorithmus, der eine Aufgabe nachweislich schneller löst, als ein klassischer Algorithmus. Klassische Algorithmen benötigen im worst case ``2^n-1 + 1`` Aufrufe, während ein Quantencomputer mit dem Deutsch-Jozsa-Algorithmus nur einen einzigen Aufruf benötigt. Dieser Algorithmus diente auch als Basis für den Shor-Algorithmus und den Grover-Algorithmus. 

4.2 Simon-Algorithmus
.....................
Der Simon-Algorithmus findet die Periode p einer periodischen Funktion f. Er wurde 1994 von Daniel Simon entwickelt. Ein klassischer Algorithmus benötigt für die Findung einer Lösung im Durchschnitt ``O(2^n/2)``. Der Quantencomputer kann die Superposition der Qubits ausnutzen und das Problem so in ```O(n^2 + nKf(n))``, ``Kf(n)`` = Kosten der Ausführung der Funktion selbst, lösen. Auch dieser Algorithmus hat keinen nennenswerten praktischen Nutzen. 



Nachteil: (besser mit herkömmlichen Computern)
-----------------------------------------------
Aufgaben, die eine sequentielle Arbeitsweise erfordern.

 

Quellen
-------

https://www.weltderphysik.de/gebiet/technik/quanten-technik/quantencomputer/

https://www.golem.de/news/quantengatter-die-bauteile-des-quantencomputers-1707-128276.html

http://www.chemie.de/lexikon/Quantengatter.html#2-Qubit_Gatter

http://www.chemie.de/lexikon/Grover-Algorithmus.html

https://de.wikipedia.org/wiki/Randomisierter_Algorithmus

https://www.weltderphysik.de/gebiet/technik/quanten-technik/quantensimulatoren/

https://de.wikipedia.org/wiki/Quantensimulation

http://www.theorie.physik.uni-goettingen.de/lehre/Uebungen/QM2-Seminar/0506/Quantencomputer.pdf


