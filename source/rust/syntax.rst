| Mirjam Lawall, <Mirjam.Lawall@hs-augsburg.de>, IN6, #2003849


Syntax
======

Die Sprache Rust ist syntaktisch ähnlich wie C. Anweisungen werden durch ``;`` getrennt und Blöcke in geschweifte Klammern gesetzt.


if und while Schleifen
-----------------------

.. code-block:: c

    if n < 0 {
        ...
    } else {

        ...
    }


.. code-block:: c 

    while n < 101 {
        ...    
    }

Die Schleife wird solang ausgeführt bis die Begingung wahr wird.
Die if else und while Schleifen sind wie in C, nur das die Bedingungen in keiner Klammer steht.



Loop
-----

for loop
'''''''''

.. code-block:: c

    // n will take the values: 1, 2, ..., 100 in each iteration
    for n in 1..101 {
        ...
    }


Infinite loop
''''''''''''''

.. code-block:: c

    loop {
        ...
    }

In Rust gibt eine Unendlichkeitsschleife ``loop``.
Die Schleife kann mit dem ``break`` Schlüsselwort jederzeit verlassen werden. Mit ``continue`` kann der Rest der Schleife übersprungen werden und die Schleife beginnt von neuem.


Nested loops
'''''''''''''

In Rust gibt es Verschachtelte Loops. Es ist möglich von der inneren Loop die Äußere zu beenden. Dies funktioniert mit sogennanten Labels.

.. code-block:: c

    'outer: loop {
            println!("Entered the outer loop");

            'inner: loop {
                println!("Entered the inner loop");

                // This would break only the inner loop
                //break;

                // This breaks the outer loop
                break 'outer;
            }

            println!("This point will never be reached");
        }


Match
------

Das Match Schlüsselwort ist das equivalent zu switch in C.

.. code-block:: c

    match number {
        // Match a single value
        1 => println!("One!"),
        // Match several values
        2 | 3 | 5 | 7 | 11 => println!("This is a prime"),
        // Match an inclusive range
        13...19 => println!("A teen"),
        // Handle the rest of cases
        _ => println!("Ain't special"),
    }

Neben Zahlen und Zeichenketten kann Pattern Matching auf beliebig verschachtelte Strukturen durchgeführt werden.



Mutex
------

Sehr wichtig für Nebenläufige Programmierung sind Mutexe. Ähnlich wie in C++ schützt
ein Mutex in Rust die Variable und synchronisiert alle Zugriffe.

.. code-block:: c

    static readonly_number: u64 = 42;
    static counter: Mutex<u64> = Mutex::new(0);

    pub fn init() {
       let guard = counter.lock().unwrap();
       guard = readonly_number;
    }


Die init Funktion versucht über ``lock()`` Zugriff auf eine zu schützende Variable zu bekommen. ``lock()`` blockiert so lang bis der Thread auf die Variable zugreifen darf. 

Auch hier muss der Mutex mindestens so lang leben wie der Thread. Das kann man
lösen indem man einen globalen mutex erzeugt. Allerdings schränkt das die Struktur des Codes deutlich ein. Eine bessere Lösung sind RC oder der Thread-sichere Gegenpart Arc. Über Rc oder Arv angelegte Variablen leben so lang, bis keine Referenz mehr auf die Variablen existieren.




Vererbung
----------

Vererbung gibt es in Rust nicht. Stattdessen gibt es die Komposition. 
Neben konkreten Typeen können hierzu auch Traits definiert werden.

Traits definiert eine Menge von Funktionen und Methoden, die dann jeweils zusammen von Datentypen implementiert werden und bei Typparametern als Einschränkung für die erlaubten Typen dienen können. Dieses Konzept wird auch für Operatoren verwendet, sodass beispielsweise der Operator ``+`` mit jedem Datentyp verwendet werden kann, der den Trait Add implementiert.

Implementierung eines Traits:

``impl MyTrait for MyType { … }``

Bsp.:

.. code-block:: c

    //Type
    struct Circle {
        x: f64,
        y: f64,
        radius: f64,
    }

    // define trait
    trait HasArea {
        fn area(&self) -> f64;
    }

    // implement den trait HasArea für den Typ Circle
    impl HasArea for Circle {
        fn area(&self) -> f64 {
            std::f64::consts::PI * (self.radius * self.radius)
        }
    }


Regeln für die implementierung von Traits
''''''''''''''''''''''''''''''''''''''''''

Man kann Traits nicht nur für structs anlegen, sondern für jeden Typ. 
Also auch für ``i32``

.. code-block:: c

    trait HasArea {
        fn area(&self) -> f64;
    }

    impl HasArea for i32 {
        fn area(&self) -> f64 {
            println!("this is silly");

            *self as f64
        }
    }

    5.area();

Methoden auf primitive Typen zu implementieren gilt als schlechter Stil. 
Es gibt jedoch 2 Einschränkungen die verhindern das alles außer Kontrolle gerät:

    1. Der Trait darf nur angewendet werden, wenn er in diesem Bereich 
       auch definiert ist.

    2. Entweder der Trait oder der Typ müssen von einem selbst definiert werden.


Speicherverwaltung
-------------------

Safe Rust
''''''''''

Der Entwickler muss sich bei Rust wenig mit der Speicherverwaltung auseinandersetzen. Auf dem Stack werden alle primitiven Datentypen abgelegt und alle restlichen auf dem Heap. Mit dem Zeigertyp ``Box`` ist es auch möglich Daten explizit auf dem Heap abzulegen. 

Rust setzt auf Pointer-Ownership und Pointer Lifetime um sichere Speicherzugriffe zu gewährleisten. 

In Rust gibt es Referenzen. Eine Referenz zeigt immer auf gültigen Speicher und darf niemals den Wert null haben. Referenzen kann man in 2 Arten unterteilen:

    - gemeinsame Referenzen (shared references) ``&``
    - veränderbare Referenzen (mutable references) ``&mut``

Keine Referenz darf länger als das von ihr referenzierte Objekt leben, das Objekt  darf nicht verändert worden sein und die veränderbare Referenz muss die einzige Referenz auf dieses Objekt sein. Das wird statisch durch den Rust-Compiler garantiert. In C würde es beispielsweise keine Fehlermeldung beim Kompillieren geben, wenn nach der Freigabe des Speichers einer Variable auf diese zugegriffen wird. 

Borrowing bezeichnet das Erstellen von Referenzen. Zusammen mit Ownershipt ist das die Grundlage der sicheren Speicherverwaltung ohne Garbage Collector. 

Ownership bedeutet, dass jedes Objekt die Variable besitzt der es bei der Erstellung zugewiesen wurde. 


Unsafe Rust
''''''''''''

Neben der Safe Rust Umgebung existiert auch eine Unsafe Rust Umgebung, welche durch das Schlüsselwort ``unsafe`` aktiviert wird. Das hat zur Folge, dass einige Regeln aufgehoben sind und es so auch zu potentiell unsicheren Speicherzugriffen kommen kann. 

Der Grund weshalb man diese Unsichere Umgebung benötigt, ist um daraus andere unsichere Funktionen aufrufen zu können. Alle Funktionen aus der C-Bibliothek sind in Rust generell immer ``unsafe``. Das ist deshalb so, weil der Rust-Compiler die Speicherverwaltung dieser Funktionen nicht kontrollieren kann. 

Ein weiterer Grund für den Einsatz der unsafe Umgebung sind Raw-Pointer. Diese entsprechen den Zeigern in C. Diese Pointer dürfen nur in explizit ``unsafe`` makierten Code dereferenziert werden. hier besteht das Risiko auf einen undefinierten Speicherbereich zuzugreifen und dadurch fehlerhafte oder falsch konfigurierte Hardware und Programmierfehler zu erhalten. 

Für die meisten Anwendungen reicht allerdings die Safe Rust Umgebung aus. 



Fehlerbehandlung
-----------------

In Rust werden Fehler in 2 Kategorien geteilt.

recoverable Mistakes (behabbare Fehler)
''''''''''''''''''''''''''''''''''''''''

Wenn zB eine Datei nicht gefunden wurde, soll das PRogramm dem Benutzer das melden. Dann kann der Vorgang wiederholt werden. 

Behebbare Fehler werden in Rust als gewöhnliche Rückgabewerte von Funktionen modelliert. Dazu gibt es die enum Typen ``Result<T,E>`` und ``Option<T>``. ``Result<T,E>`` kann zwischen ``Ok(T)`` welches einem normalem Wert entspricht und ``Err(E)`` welches ein Fehlerwert ist, unterscheiden. ``Option<T>`` definiert ``Some<T>`` als normalen Wert und ``None`` für keinen Wert.


unrecoverable Mistakes (nicht behebbare Fehler)
''''''''''''''''''''''''''''''''''''''''''''''''

Wenn zB. versucht wird auf den Speicherort außerhalb eines Arrays zuzugreifen.

Wenn ein nicht behebbarer Fehler auftritt, wird das ``panic!``-Macro ausgeführt. Dabei gibt das Programm eine Fehlermeldung aus, räumt den Stack auf und beedet sich.




Quellen
--------

    https://de.wikipedia.org/wiki/Rust_(Programmiersprache)

    https://www.golem.de/news/rust-ist-die-neue-programmiersprache-besser-1606-121227-2.html

    https://doc.rust-lang.org/book/ch09-00-error-handling.html

    https://jaxenter.de/rust-1-0-alte-liebe-rostet-nicht-21065

    https://doc.rust-lang.org/rust-by-example/flow_control/loop/return.html

    https://doc.rust-lang.org/1.8.0/book/traits.html


