| Felix Bühler, <felix.buehler@hs-augsburg.de>, IN6, #2008336

Datentypen
==========

Standard Typen
--------------

.. code-block:: go

    bool
    
    string
    
    int int8 int16 int32 int64
    uint uint8 uint16 uint32 uint64 uintptr 
    
    byte //das selbe wie uint8
    
    rune //representiert unicode code points
    
    float32 float64
    
    complex64 complex128
    
    int, uint und uintptr 

abgeändert [0]_
    
Nullwerte
.........

Wenn man bei Variablen keinen expliziten Startwert angibt werden sie mit Nullwerten belegt.
Bei Zahlen ist dieser Wert immer 0, bei Boolean false und bei Strings "".

Typ Konvertierung
..................

T(v) convertiert v zu T.

.. code-block:: go

    var v int = 68
    var t float64 = float64(v)

Maps
----

Map Objekte represäntieren eine Hashtabelle.

Erstellung
..........

.. code-block:: go

    var m map[a]b = make(map[a]b)
    
Maps werden mit der Methode make erstellt.
Typ der Schlüssel (Im Beispiel a) und des Wertes (Im Beispiel b) müssen explizit genannt werden.

Alternativ kann man eine Map direkt mit Daten initialisieren:

.. code-block:: go

    produkte := map[int]string{
        5598: "Milch",
        77567: "Butter",
        2323: "Brot",
    }

Dies kann auch verwendet werden um die make Methode zu umgehen:

.. code-block:: go

    m := map[string]int{}


Zugriff
.......

.. code-block:: go

    var m map[string]int = make(map[string]int)
    
    m["a"] = 100
    
    i := m["a"] //i == 100
    
    i := m["b"] //i == 0

Wenn der angegebene Schlüssel nicht existiert wird der Nullwert zurückgegeben.

Spezielle Methoden
..................

Mit Hilfe der len Methode kann die größe der Map herausgefunden werde:

.. code-block:: go

    n := len(m) //Für die Map von oben n == 1
 
.. code-block:: go

    i, ok := m["a"]
    
Gibt in der ersten Variable den aktuellen Wert des Eintrags zurück und in der Zweiten true oder false,
je nach dem ob ein Eintrag mit dem gegebenen Schlüssel existiert oder nicht.

Um zu testen ob ein Eintrag existiert kann man einen Unterstrich verwernden:

.. code-block:: go

    _, ok := m["a"] // ok == true
    
Die Methode delete erlaubt das löschen eines Eintrags.

.. code-block:: go

    delete(m, "a")
    
Iterieren
.........

Das Schlüsselwort range erlaubt es einem über eine Map zu iterieren.

..code-block:: go

    for key, value := range m {
        fmt.Println("Key:", key, "Value:", value)
    }
    
übernommen von [3]_
    
Map of Map
..............

Maps können auch mit structs verwendet werden:

.. code-block:: go

    produkte := make(map[string]map[string]int)
    
In dem gegebenen Beispiel wird eine Map Mit dem Schlüsseltypen string und dem Wertypen map[string]int erstellt.
Jeder Schlüssel der äußeren Map bezieht sich auf den Namen eines Produkts und mit einer weiteren Map verbunden, 
welche alle Hersteller mit der Produktnummer verbindet.

Arrays
------

Ein Array mit Elementen des Typens T der Größe n wird mit dem Typen [n]T dargestellt.

.. code-block:: go

    var a [10]int
    
übernommen von [4]_

deklariert a zu einem 10 großem integer array.

Die größe eines Arrays ist unveränderlich.

.. code-block:: go

    var a [2]int
    a[0] = 100
    a[1] = 300
    fmt.Println(a[0]) //100
    
Ein Array kann auch direkt befüllt werden:

.. code-block:: go

    staedte := [4]string{"Bremen", "Hamburg", "New York", "Amsterdam"}
    fmt.Println(staedte[2]) //New York

Die Größe des Arrays kann einfach mit der Methode len herausgefunden werden:

.. code-block:: go

    fmt.Println(len(staedte)) //4
    
Slices
......

Ein Array hat eine festgelegte Größe. 
Eine Slice erlaubt eine flexible Sicht auf die Elemente eines Arrays.

Eine Slice mit Elementen des Types T wird mit dem Typen []T angegeben.

Eine Slice wird mit Hilfe zweier Grenzwerte erstellt.

.. code-block:: go

    a[lower : upper]
    
Der Wert lower ist inclusiv der Wert upper ist exclusiv.
Beide können jeweils auch weggelassen werden, sofern der Doppelpunkt weiterhin angegeben wird.
Ohne lower beginnt die Slice bei 0, ohne upper endet sie beim letzten Element des Arrays.
Wenn man beides weglässt erhält man eine Slice die exact den Array wiederspiegelt.

.. code-block:: go

    staedte := [4]string{"Bremen", "Hamburg", "New York", "Amsterdam"}
    var s []string = staedte[:2]
    fmt.Println(s) // [Bremen Hamburg]
    
Slices enthalten keine Daten, sie beschreiben jediglich einen Teil des Arrays.
Wenn man Werte im Array ändert, ändern sich dies auch in der Slice

.. code-block:: go

    staedte[0] = "Munich"
    fmt.Println(s) // [Munich Hamburg]
    
Eine Slice kann auch ohne einen Array direkt mit Werten initalisiert werden.

.. code-block:: go

    a := [2]int{2,3}
    s := []int{2,3}
    
Die Slice und der Array enthalten die selben Daten, für die Slice wird automatisch ein Array erstellt den diese dann referenziert.

Länge und Kapazität
...................

Die Länge der Slice beschreibt die Anzahl an Elementen innerhalb der Slize.
Die Kapazität hingegen beschreibt die Größe des, der Slice zugeordneten, Arrays.

Die Länge und Kapazität der Slice s können mit Hilfe der Methoden len(s) und cap(s) erfahren werden.

So lang man genügend Kapazität hat lässt sich die länge der Slice beliebig ändern.

.. code-block:: go

    s := []{0, 4, 5, 6, 7, 8} // länge und kapazität 6
    
    s = s[:0] // 0 länge
    
    s = s[:4] // 4 länge

Quellen
-------

.. [0] Go Lang Basic types (besucht am 01.04.2019)  
    https://tour.golang.org/basics/11
Go Lang Basic types zero values (besucht am 01.04.2019)  
    https://tour.golang.org/basics/12
Go Lang Basic types conversion (besucht am 01.04.2019)  
    https://tour.golang.org/basics/13
.. [3] go maps in action (besucht am 01.04.2019)  
    https://blog.golang.org/go-maps-in-action
.. [4] Array (besucht am 01.04.2019)
    https://tour.golang.org/moretypes/6
Slices (besucht am 01.04.2019)
    https://tour.golang.org/moretypes/7