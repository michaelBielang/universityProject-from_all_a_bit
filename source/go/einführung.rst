| Tobias Drüeke, <tobias.drueeke@hs-augsburg.de>, IN6, #2004114

Einführung
==========
Bereits zwei mal war sie die "Programming Language of the Year" bei TIOBE (2009 & 2016)und 2018 war sie die "Most promising programming language" im "The State of Developer Ecosystem" von JetBrains.
Durch Produkte wie Docker, Kubernetes, etcd, Consul, etc. ist sie gerade bei systemnaher Infrastruktursoftware nicht mehr wegzudenken. Die Progammiersprache von der hier die Rede ist, ist Go.
Bereits 2007 haben Robert Griesemer, Rob Pike und Ken Thompson begonnen die Ziele für die neue Progammiersprache zu definieren. Dann endlich 2008 begann Ken den Compiler umzusetzen, der C code als Output generiert.
Ian Taylor begann auch 2008, allerdings unabhängig, für das Go front end eine GNU Compiler Collection zu spezifizieren. Ende des Jahres begann Russ Cox dann zu helfen die Progammiersprache und Bibliotheken mit umzusetzen.
Am 10. November 2009 wurde dann Go als open source Projekt veröffentlicht.

Wenn man von Go redet darf man natürlich auch das Maskotchen "Go gopher" nicht vergessen um das es einen regelrechten Hype gibt.
Wer Go kennt, der kennt auch Go gopher (anders herum ist das warscheinlich nicht immer der Fall).

.. _figlabel:

.. figure:: img/modelsheet.jpg

[Go gopher] mit seinem Drawing Sheet


Warum Go?
---------

Warum eine neue Progammiersprache? 
``````````````````````````````````
Schon vor 10 Jahren, als die Entwicklung von Go begonnen hat, gab es eine große Auswahl an Progammiersprachen. Allerdings hatte jede Sprache ihre Vor- und Nachteile und
keine stellte die Entwickler von Go so richtig zufrieden. Bei der Auswahl einer Sprache musste man immer gewisse Abstriche machen.
Man konnte sich entscheiden zwischen Effizienz bei der Ausführung, Effizienz bei der Compilierung oder einer komfortablen Programmierung.
Beispielsweise JavaScript oder Python lassen eine angenehme Programmierung zu, sind dafür allerding nicht annähernd so sicher oder effizient wie C, C++ oder auch Java.
Zudem bieten die wenigsten Sprachen eine effiziente und zuverlässige Unterstützung bei der nebenläufigen Verarbeitung (welche mit den heutigen Prozessoren fast immer möglich ist und erhebliche Performanceverbesserungen liefern kann)


Was macht dann jetzt Go?
````````````````````````
 Bei der Entwicklung von Go wurde nach dem [Language Design] besonders versucht folgende Schmerzpunkte zu adressieren:
 
 - langsame builds
 
 - unkontrolierte Abhängigkeiten
 
 - jeder Programmierer verwendet ein unterschiedliche Version/ unterschiedliche Subkomponenten
 
 - schwere Programm lesbarkeit + schlechte dokumentation
 
 - dublizierter Aufwandt
 
 - risiken von Updates
 
 - Redundater Code/Packages -> version skew
 
 - probleme beim schreiben von automatisierenden Tools
 
 - cross-language builds
 
Diese Problempunkte geht Go an indem versucht wird die einfachheit einer interpretierden, dynamisch Typisierten Sprache mit der Effizienz und Sicherheit einer statisch typisierten und compilierten Sprache zu kombinieren.
Netzwerk und Nebenläufige Verarbeitung stehen als weite Punkte im Fokus. Und zu guter letzt soll das entwickeln mit Go schnell sein. 
Zur Umsetzung dieser Ziele sind, ein aussagekräftiges aber leichtgeweichtiges Typen System, Nebenläufigkeit, Garbage Collection, genaue spezifizierung von Abhängigkeiten und weitere, als sprachlichen Vorrausetzungen notwendig.
Diese Vorraussetzung sind jetzt Teil der Go implementierung.
 
Google selbst beschreibt Go gut zusammengefasst in einem [Blogeintrag]:
Go attempts to combine the development speed of working in a dynamic language like Python with the performance and safety of a compiled language
like C or C++. In our experiments with Go to date, typical builds feel instantaneous; even large binaries compile in just a few seconds. And the
compiled code runs close to the speed of C. Go is designed to let you move fast.


Entwickeln mit Go
-----------------

Runtime
```````
Die Laufzeitumgebung ist Teil von jedem Go Programm. In diese sind Garbage Collection, Nebenläufigkeit, Stack management und ander wichtige Features von Go implementiert.
Das Gos Laufzeitumgebung, im Gegensatz zu anderen Sprachen wie Java, keine virtuelle Maschine zur Ausführung verwendet ist ein wichtiges Detail welches bei der wahl der Sprache verstanden werden sollte.
Die Go Programme sind bereits in nativen Maschienencode kompiliert. Daher ist in Go der Begriff "Runtime" als eine Bibliothek mit wichtigen Services zu sehen anstatt der eigentlichen Bedeutung der virtuellen Umgebung. 

IDE's zu Entwicklung
````````````````````
Für die Entwicklung mit Go ist keine spezielle IDE angedacht, allerdings unerstützen die meisten etablierten IDE entweder direkt oder mit einem Plugin die Entwicklung mit Go.

IDE's/Editoren mit Go unterstützung:
- Emacs
- Vim
- VSCode
- Atom
- Eclipse
- Sublime
- IntelliJ (bzw. Goland)
- und viele weitere

basics
``````
Ist Go eigentlich eine Objektorientierte Sprache? Auf diese Frage gibt es keine eindeutige Antwort. Go erlaubt mit Typen und Methoden zwar Objektorientierung, allerding gibt es wiederum keine feste Typhierachie.
Es gibt zwar den Ansatz eines Interfaces, jedoch wird dieser offener gehalten. Es gibt keine feste implentierung eines definierten Interfaces. Viel mehr kann ein Type einem Interface zugewiesen werden wenn es die definierten Methoden beinhaltet.
Metaphorisch gesprochen: Es wächst wie eine Banane, riecht wie eine Banane und schmeckt wie eine Banane dann wird es wohl eine Banane sein.

Weitere signifikante Unterschied zu anderen Programmiersprachen sind, dass es Beispielsweise keine wirklichen Exceptions gibt, Threads in goroutines gehandelt werden, überladen nicht erlaubt ist und vielen weitere kleine aber entscheidende Unterschiede.

kleiner einblick in Syntax/ Besonderheiten
``````````````````````````````````````````

Bei der Programmierung mit Go ist es wichtig gewisse Syntaxkonventionen einzuhalten (besonder bei den Zeichen "go func() { for { dst <- <-src } }()").

Kommentare
..........
Block Kommentare: /* <Comment> */ 

Zeilen Kommentare: // <Comment>

Zugriffsmodifikator
...................
Private: duck (camel case)
Public: Duck (pascal case)

Grammatik
.........
; Semikolons werden automatisch bei Verarbeiten hinzugefügt. Dadurch wird allerding für den Code eine gewisse Syntax vorausgesetzt. Nur in wenigen Ausnahmen werden noch Semikolons benötigt (z.B. bei einer "for" Schleife zur abtrennung).
:= ist eine Deklarierung
=  ist eine Zuweisung

Instanziierung
..............
Speicher Allokieren: new
Inizialisierung des slice, map und channel types: make

Kontrollstrukturen
..................
if

.. code-block:: go

	if x > 0 {
		return y
	}
	
switch

.. code-block:: go

	switch randomNumber{
    case '0' <= randomNumber && randomNumber <= '9':
        return randomNumber - '0'
    case 'a' <= randomNumber, randomNumber <= 'f':
        return randomNumber - 'a' + 10
    case 'A' <= randomNumber && randomNumber <= 'F':
        return randomNumber - 'A' + 10
    }
    return 0

Schleifen
.........

.. code-block:: go

	// normale for schleife
	for init; condition; post { }

	// wie eine while schleife
	for condition { }

Funktionen
..........
Definition

.. code-block:: go

	func sumSub(z int, i int) (int, int) {
		return z+i, z-i
	}

Aufruf

.. code-block:: go

	x, j = sumSub(b, i)


Über die [Tour of Go] lässt sich ein umfassender und spielerischer Einstieg in Go finden, mit Syntax und besonderheiten.
	
hello world
```````````

.. code-block:: go

	package main

	import fmt “fmt” // Package implementing formatted I/O.

	func main() {
	fmt.Printf(“Hello, world”);
	}
	
Einen ersten Einblick in die Programmierung mit Go (wie "Hello Wolrd") kann man sich im [Go Playground] machen.



Quellen:
--------

https://golang.org/doc/faq

https://golang.org/doc/effective_go.html

[Tour of Go] https://tour.golang.org

[Go Playground] https://play.golang.org/p/-MKUWeDBml7

[Blogeintrag] https://techcrunch.com/2009/11/10/google-go-language/?guccounter=1&guce_referrer_us=aHR0cHM6Ly9kZS53aWtpcGVkaWEub3JnLw&guce_referrer_cs=84Pa_kv4lIYv9fLLImg_9w

[Go] https://golang.org/doc/gopher/modelsheet.jpg

[Language Design] https://talks.golang.org/2012/splash.article
