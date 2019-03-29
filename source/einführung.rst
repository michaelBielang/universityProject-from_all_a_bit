| Tobias Drüeke, <tobias.drueeke@hs-augsburg.de>, IN6, #2004114

Einführung
==========
Bereits zwei mal war sie die "Programming Language of the Year" bei TIOBE (2009 & 2016)und 2018 war sie die "Most promising programming language" im "The State of Developer Ecosystem" von JetBrains.
Durch Produkte wie Docker, Kubernetes, etcd, Consul, etc. ist sie gerade bei systemnaher Infrastruktursoftware nicht mehr wegzudenken. Die Progammiersprache von der hier die Rede ist, ist Go.
Bereits 2007 haben Robert Griesemer, Rob Pike und Ken Thompson begonnen die Ziele für die neue Progammiersprache zu definieren. Dann endlich 2008 begann Ken den Compiler umzusetzen, der C code als Output generiert.
Ian Taylor begann auch 2008, allerdings unabhängig, für das Go front end eine GNU Compiler Collection zu spezifizieren. Ende des Jahres begann Russ Cox dann zu helfen die Progammiersprache und Bibliotheken mit umzusetzen.
Am 10. November 2009 wurde dann Go als open source Projekt veröffentlicht.

Wenn man von Go redet darf man natürlich auch das Maskotchen "Go gopher" vergessen um das es einen regelrechten Hype gibt.
Wer Go kennt, der kennt auch Go gopher (anders herum ist das warscheinlich nicht immer der Fall).

:numref:`figlabel`
.. _figlabel: [Go gopher] mit seinem Drawing Sheet


Warum Go?
=========

Warum eine neue Progammiersprache? 
----------------------------------
Schon vor 10 Jahren, als die Entwicklung von Go begonnen hat, gab es eine große Auswahl an Progammiersprachen. Allerdings hatte jede Sprache ihre Vor- und Nachteile und
keine stellte die Entwickler von Go so richtig zufrieden. Bei der Auswahl einer Sprache musste man immer gewisse Abstriche machen.
Man konnte sich entscheiden zwischen Effizienz bei der Ausführung, Effizienz bei der Compilierung oder einer komfortablen Programmierung.
Beispielsweise JavaScript oder Python lassen eine angenehme Programmierung zu, sind dafür allerding nicht annähernd so sicher oder effizient wie C, C++ oder auch Java.
Zudem bieten die wenigsten Sprachen eine effiziente und zuverlässige Unterstützung bei der nebenläufigen Verarbeitung (welche mit den heutigen Prozessoren fast immer möglich ist und erhebliche Performanceverbesserungen liefern kann)


Was macht dann jetzt Go?
------------------------


Go addressed these issues by attempting to combine the ease of programming of an interpreted, dynamically typed language with the efficiency
 and safety of a statically typed, compiled language. It also aimed to be modern, with support for networked and multicore computing. Finally,
 working with Go is intended to be fast: it should take at most a few seconds to build a large executable on a single computer. To meet these
 goals required addressing a number of linguistic issues: an expressive but lightweight type system; concurrency and garbage collection; rigid 
 dependency specification; and so on. These cannot be addressed well by libraries or tools; a new language was called for. 

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
 
Google selbst beschreibt Go gut zusammengefasst in einem [Blogeintrag]:
Go attempts to combine the development speed of working in a dynamic language like Python with the performance and safety of a compiled language
like C or C++. In our experiments with Go to date, typical builds feel instantaneous; even large binaries compile in just a few seconds. And the
compiled code runs close to the speed of C. Go is designed to let you move fast.
 

Was kann/ist Go | Was kann/ist Go nicht
```````````````````````````````````````

Entwickeln mit Go
=================

In welchen IDEs:

Welche bestehenden Programme/Frameworks können weiter verwendet werden

kleiner einblick in syntax/ besonderheiten

hello world
-----------

.. code-block::
	package main

	import fmt “fmt” // Package implementing formatted I/O.

	func main() {
	fmt.Printf(“Hello, world”);
	}




https://www.heise.de/developer/meldung/Programmiersprachen-Erste-Plaene-zu-Go-2-veroeffentlicht-3771307.html
https://golang.org/doc/faq
https://golang.org/doc/effective_go.html
[Go Playground] https://play.golang.org/p/-MKUWeDBml7
[Blogeintrag] https://techcrunch.com/2009/11/10/google-go-language/?guccounter=1&guce_referrer_us=aHR0cHM6Ly9kZS53aWtpcGVkaWEub3JnLw&guce_referrer_cs=84Pa_kv4lIYv9fLLImg_9w
[Go gopher] https://golang.org/doc/gopher/modelsheet.jpg
[Language Design] https://talks.golang.org/2012/splash.article