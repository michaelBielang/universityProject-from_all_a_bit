.. |p| raw:: html

   <p />

Nebenläufigkeit vs. Parallelität
================================

Einführung Parallelität vs. Sequenzialität
------------------------------------------

Es gibt Geschehen, die echt parallel ausgeführt werden können und bei manchen Dingen sieht es so aus, als ob sie parallel passieren, aber in Wirklichkeit passiert es nur schnell hintereinander. 

Wenn zwei Menschen etwa gleichzeitig essen, ist das parallel. 

Aber wenn jemand isst und atmet, so sieht das von außen zwar gleichzeitig aus, ist es aber nicht, denn schlucken und atmen läuft sequenziell ab.  
 

Nebenläufigkeit und Parallelität in der EDV
-------------------------------------------

Moderne Computersysteme können Parallelität sowie Nebenläufigkeit u.a. in Multicore-Systemen, Netzwerken, Betriebssystemen und auch schon in kleinen Programmen realisieren. Im weiteren Verlauf dieses Berichts liefere ich Ihnen anschauliche Beispiele zu diesem Thema.

Parallelität in der Softwareentwicklung ist wenn zwei oder mehr Threads den selben Programmteil auf unterschiedlichen Prozessoren  ausführt. 

Nebenläufigkeit hingegen kann einen Programmteil auf einem Prozessor umsetzen. Der Scheduler ist dann für die Zuteilung der Rechenzeiten auf diesem Prozessor zuständig.


Nebenläufigkeit und Parallelität in Go
--------------------------------------

Rob Pike [4]_, Chefentwickler der Programmiersprache Go bei Google, hat sich speziell diesem Thema auf mehreren Veranstaltungen gewidmet. 

Go unterstützt unter anderem:
nebenläufige Ausführung -> ``goroutines``

sychronisation und messsaging -> ``channels``

multi-way-concurrent control -> ``select``


Go arbeitet dabei nach dem Prinzip kommuniziere nicht über Speicherteilung, sondern teile Speicher durch Kommunikation. [1]_


Googles Definition von Nebenläufigkeit
--------------------------------------

"Programming as the composition of independently executing processes
(Process in general sense, not Linux processes. Famously hard to define) [2]_


Googles Definition von Parallelität
-----------------------------------

Programming as the simultaneous execution of (possibly related) computations. [2]_


**Nebenläufigkeit** anhand eines Beispiels
------------------------------------------


.. code-block:: go

	package main

	import (
		"fmt"
		"runtime"
		"sync"
	)

	func main() {
		runtime.GOMAXPROCS(1)

		var wg sync.WaitGroup
		wg.Add(2)

		fmt.Println("Starting Go Routines")
		go func() {
			defer wg.Done()

			for char := 'a'; char < 'a'+26; char++ {
				fmt.Printf("%c ", char)
			}
		}()

		go func() {
			defer wg.Done()

			for number := 1; number < 27; number++ {
				fmt.Printf("%d ", number)
			}
		}()

		fmt.Println("Waiting To Finish")
		wg.Wait()

		fmt.Println("\nTerminating Program")
	}
	
	
Das Programm startet mittels dem Keyword ``go`` zwei Routinen und zwei anonyme Funktionen.
Die erste Routine gibt auf der Konsole das englische Alphabet aus während die zweite die Nummer 1 bis 26 anzeigen.

``Starting Go Routines``

``Waiting To Finish``  

``1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 -``
  
``a b c d e f g h i j k l m n o p q r s t u v w x y z -``  

``Terminating Program``  


Hierbei werden zuerst die zwei Routinen ausgeführt während die main Funktion auf den Abschluss dieser zwei Routinen wartet. Dies wird mittels dem Befehl ``wg.Wait()`` umgesetzt


Fügt man nun in den zweiten Codeblock folgende Funktionalität ein

``time.Sleep(1000 * time.Microsecond)``

so ändert sich zwar die Reihenfolge in der Ausgabe, an der Programmlogik hingegen aber nichts:

``Starting Go Routines``

``Waiting To Finish``

``a b c d e f g h i j k l m n o p q r s t u v w x y z -``

``1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 -``

``Terminating Program``

Es ist lediglich der Scheduler der hier die Rechenzeiten an Aufgaben verteilt die sofort zur Abarbeitung bereitstehen.

**Parallelität** anhand eines Beispiels
---------------------------------------

Nun füge ich mittels dem Befehl ``runtime.GOMAXPROCS(2)`` dem Programm einen zweiten logischen Prozessor hinzu, sodass sich der Programmcode wie folgt darstellt:


.. code-block:: go

	package main

	import (
		"fmt"
		"runtime"
		"sync"
	)

	func main() {
		runtime.GOMAXPROCS(2)

		var wg sync.WaitGroup
		wg.Add(2)

		fmt.Println("Starting Go Routines")
		go func() {
			defer wg.Done()

			for char := 'a'; char < 'a'+26; char++ {
				fmt.Printf("%c ", char)
			}
		}()

		go func() {
			defer wg.Done()

			for number := 1; number < 27; number++ {
				fmt.Printf("%d ", number)
			}
		}()

		fmt.Println("Waiting To Finish")
		wg.Wait()

		fmt.Println("\nTerminating Program")
	}
	
	
und man erhält zum Beispiel die Ausgabe:	


``Starting Go Routines``

``Waiting To Finish``

``a b 1 2 3 4 c d e f 5 g h 6 i 7 j 8 k 9 10 11 12 l m n o p q 13 r s 14 t 15 u v 16 w 17 x y 18 z 19 20 21 22 23 24 25 26``

``Terminating Program``

Wie man nun sehen kann arbeiten die beiden Routinen parallel und ringen um die Konsolenausgabe.

Daraus folgt, dass die Ergebnisse jedes mal anders aussehen können. 

Abschlussbemerkung
------------------

Insbesondere bei parallel arbeitenden Programmen ist darauf zu achten, dass Operationen auf gemeinsam genutzte Variablen stets atomar vollzogen werden um race-conditions und daraus resultierende Fehler zu vermeiden. 

Synchronisation kann in Go mittels channels umgesetzt werden. 

Da dies leider den Umfang sprengen würde, findet der interessierte Leser weitere Informationen über folgende Quelle [3]_


Quellen:
--------

.. [1] https://talks.golang.org/2012/waza.slide#7


.. [2] http://www.golangbootcamp.com/book/concurrency


.. [3] https://tour.golang.org/concurrency/2

.. [4] https://de.wikipedia.org/wiki/Rob_Pike

https://vimeo.com/49718712

https://stackoverflow.com/questions/25106526/parallel-processing-in-golang

http://www.tutego.de/blog/javainsel/2013/08/nebenlufigkeit-und-parallelitt/

https://www.ardanlabs.com/blog/2014/01/concurrency-goroutines-and-gomaxprocs.html