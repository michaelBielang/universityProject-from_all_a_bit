
Objektorientierung ohne Klassen
===============================

In der Programmiersprache Go gibt es keine Klassen und Vererbung. Durch die Kombination von Methoden, Kompositionen und Interface-Typen kann jedoch genauso objektorientiert werden wie mit anderen Programmiersprachen. 


Typdefinition
--------------
In Go werden ``structs`` verwendet, um einen Typ zu definieren. Diese werden durch das Schlüsselwort ``type`` gefolgt von dem Structnamen und dem Schlüsselwort ``struct`` definiert.

.. code-block:: go

   type Circle struct {
      radius float64
   }


Eine Struktur ist ersteinmal eine Sammlung von Variablen, jedoch können sie auch als Funktionen definiert werden.

.. code-block:: go

   func (c Circle) Area() float64{
      return math.Pi * c.radius * c.radius
   }

   func main() {
      c := Circle{2}
      fmt.Println(c.Area())
   }


Methoden:
`````````
Methoden bündeln Datentypen und Operationen und können wie Objekte genutzt werden.

.. code-block:: go

   func nextInt(b []byte, i int) (int, int) {
      . . .
      return x, i
   }

   func (c Circle) Enlarge() {
      // Kopie wird geändert
     c.radius += 1
   }

Go verwendet die Call-by-Value-Semantik. Beim Aufruf der Methode werden die Parameter, hier der Circle, immer als Kopie übergeben. Die Änderung wird also nur auf eine Kope angewendet und der ursprungs Circle verändert sich nicht. Um den eigentlichen Parameter ändern zu können, muss man ihn als Pointer übergeben:

.. code-block:: go

   func (c *Circle) Enlarge() {
      c.radius += 1
   }



Interfaces:
-----------

In Go gibt es Interfaces, die Definition beginnt mit dem Schlüsselwort ``type`` gefolgt von dem Interfacenamen und danach das Schlüsselwort ``interface``. Ein Interface besteht aus einer Liste von Methodensignaturen, sie enthalten also nur virtuelle Methoden. Eine Struktur kann beliebig viele Interfaces implementieren. In Gegensatz zu anderen Programmiersprachen wie beispielsweise Java muss ein Typ in Go nicht explizit mit ``implements`` deklariert werden. Es reicht wenn alle Methoden des Interfaces implementiert sind.

Ein Interface der Standardbibliothek ist ``Stringer`` aus dem Package ``fmt``:

.. code-block:: go

   type Stringer interface {
      String() string
   }

Jeder Typ der eine Methode ``String()`` enthält, implementiert also automatisch das Interface Stringer.

Es gibt auch das leere Interface ``[i]interface{}``, welches keine Methoden implementiert. Dieses Interface wird von jedem Typ implementiert. Nützlich ist es für Typen bei denen die Struktur beim kompilieren noch nicht bekannt ist.

Bsp.: Objekt Typ Circle kann Variable Typ Shape zugeordnet werden weil er die Methoden implementiert hat:

.. code-block:: go

    type Shape interface {
        Area() float64
    }

    func main() {
        var shape Shape = Circle{2}
        fmt.Println(shape.Area())
    }


Polymorphie:
````````````

Go unterstützt auch Polymorphie, dadurch können Objekte unterschiedliche Datentypen gleichzeitig annehmen.

Durch die Interfaces ermöglicht Go Polymorphie.





Datenkapselung: 
---------------

In der Programmiersprache C++ oder Java können Daten in Klassen durch die Typen private, protected und public gekapselt werden. In Go gibt es jedoch keine Klassen, hier werden die Daten auf Modulebene gekapselt. Beginnt der Typ mit einem Kleinen Buchstaben, so ist er nur in diesem Package sichtbar. Beginnt er jedoch mit einem Großbuchstaben, dann handelt es sich um einen exportiereten Datentyp welcher auch außerhalb des Packages sichtbar ist. So kann auch die Sichbarkeit jedes einzelnen Felder in einem Struct kontrolliert werden. Dies kann dann von Vorteil sein, um ungültige eingaben zu verhindern, indem man einen Typen nur von innerhalb des Moduls ändern kann.

Bsp.:

.. code-block:: go

   type IPv4 struct {
      Addr uint32
      cidr uint8
   }

Der Typ ``Addr`` ist auch von außerhalb des Moduls zugreifbar, ``cidr`` wiederum nur von innerhalb des Moduls.



Komposition: / Vererbung
------------
Vererbung wird in vielen Programmiersprachen für die Wiederverwendung von Vorhandenen Objektdefinition genutzt. In Go gibt es jedoch keine klassische Vererbung. Dies hat den Vorteil, dass es Änderungen im Programmcode deutlich einfacher macht. Beispielsweise haben wir eine Klasse ``Fluss`` die Klasse ``Wasser`` erbt. Wenn man nun etwas in der Klasse ``Wasser`` ändert, dann hat das unter Umständen Einfluss auf andere Klassen die die Klasse ``Wasser`` erben. Dann muss man alle Klassen die von "Wasser" erben anpassen, je nach Codeumfang kann das sehr aufwändig sein.

Go unterstützt stattdessen eine Art der Einbettung. Eine Datenstruktur kann beliebig viele andere Datenstrukturen einbetten. So erhalten sie die Methoden und Attribute der eingebetteten Datenstrukturen.

Bsp:

.. code-block:: go

   package main

   import "fmt"

   type A struct {
      a1 int
      a2 int
   }

   type B struct {
      A         // namenlose Einbindung des Verbunds A
      b1 int
      b2 int
   }

   func main() {
      var b B

      b.a1 = 1   // entspricht b.A.a1
      b.a2 = 2   // entspricht b.A.a2
      b.b1 = 3
      b.b2 = 4

      fmt.Println(b)
   }

Es ist ein Verbund ``A`` definiert. Der Verbund ``B`` bindet den Verbund ``A`` ein. Dies geschieht indem in ``B`` einfach der Typ ``A`` angegeben wird. Wenn durch ``B`` auf ein Attribut oder Methode aus ``A`` zugegriffen wird, geschieht das durch die Kurzschreibweise. Das heißt auf die Attribute und Methoden aus ``A`` kann direkt über ``B`` zugegriffen werden mit ``b.a1``, anstatt der aufwändigeren Schreibweise ``b.A.a1``.
Hier muss man jedoch aufpassen bei der Mehrfachvererbung. Es ist möglich mehrere Verbundstypen in ``B`` einzubetten. Wenn diese jedoch Attribute mit dem gleichen Namen enthalten, kann man sie nicht mehr über die Kurzschreibweise aufrufen.

Bsp.:

.. code-block:: go

   package main

   import "fmt"

   type A struct {
      a int
      x int
   }

   type B struct {
      b int
      x int
   }

   type C struct {
      A
      B
   }

   func main() {
      var c C

      c.a = 1     // entspricht c.A.a
      c.b = 2     // entspricht c.B.b

      c.x = 3     // ERROR: ambiguous selector c.x
      c.A.x = 4   // keine Kurzschreibweise möglich
      c.B.x = 5   // keine Kurzschreibweise möglich

      fmt.Println(c)
   }









Objekterzeugung:
----------------

Da es keine Klassen in Go gibt, gibt es auch keine Konstruktoren um neue Objekte zu erzeugen. Eine Möglichkeit um trotzdem Objekte zu erzeugen sind Fabrikmethoden.
Bsp:

.. code-block:: go

   package shape

   func NewCircle(radius int) *circle {
      c := new(circle)
      c.radius = radius
      return c
   }

circle ist hier klein geschrieben, es ist also nur in seinem Package sichtbar und kann nicht von anderen Paketen mit ``new(shape.circle)`` erzeugt werden. Um trotzdem so ein Objekt zu instanziieren schreibt man ``shape.NewCircle(2)``.


Zur Objekterzeugung kann auch ein Builder mit Fluent API verwendet werden. 


Quellen:
--------

https://www.heise.de/developer/artikel/Ein-Einstieg-in-die-Programmiersprache-Go-Teil-1-4282998.html?seite=5

https://entwickler.de/online/development/einfuehrung-programmierung-go-166821.html

https://www.innoq.com/de/blog/golang-objektorientierung/

http://hweidner.de/golang/OO/

https://www.yuhiro.de/vorteile-und-nachteile-von-golang-go-die-google-programmiersprache/


