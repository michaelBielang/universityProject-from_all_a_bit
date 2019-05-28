Libgdx
=======================================

Libgdx ist ein Java-Framework für plattformunabhängige Spieleentwicklung. 
Als Open-Source-Software unter der Apache-2.0-Lizenz veröffentlichte Mario Zechner die Engine erstmals 2010. 
Der Name libGDX hat laut des des Entwicklers keine bestimmte Bedeutung.
Heute lässt sich mit Hilfe der Engine ein Spiel gleichzeitig auf Windows, Linux, Mac OS, Android, Blackberry, iOS und dem Webbrowser ausführen.
[0]_

Im vergleich zu Engines wie Unity und Unreal benötigt libgdx mehr Code und besitz 

Installation
------------

Da die Engine auf Java basiert sollte man zunächst Java installieren.
Danach kann man auf der Seite von [libgdx]_ eine Setup App runterladen welche einen durch die einzelnen Installationsschritte führt.
Eine IDE sollte auch installiert sein, da Dokumentation mitgeliefert wird und direkt in der IDE gelesen werden kann.

Plattformunabhängigkeit
-----------------------

Libgdx ist plattformunabhänig, dies erfolgt in erster Linie durch eine Spaltung des Projekts in mehrere Launcher und ein Core Projekt. 
Im Core Projekt befinden sich dann der von allen Plattformen geteilte Code, die Launcher enthalten jeweils den Code der zum starten benötigt wird.
Mit Hilfe von Java-Interfaces und abstrakten Klassen kann dann Code mit unterschiedlicher plattformabhäniger Programmierung geschrieben werden, 
welcher im Kern Projekt dann auf die selbe Weiße unabhänig von der Plattform genutzt werden kann.

Im Kern Projekt befindet sich ein Interface SaveGame welches den momentanen Spielstand speichert

.. code-block:: java

	public interface SaveGame{
		public void save(GameState state);
	}

und eine Klasse in der wir plattformabhängige Implementationen festlegen.

.. code-block:: java

	public class Implementations{
		public static SaveGame SAVE_GAME;
	}
	
In der Desktop Version möchte man nun den Spielstand direkt im Verzeichnis des Spiels installieren

.. code-block:: java

	...
	public static void main(String...args){
		Implementations.SAVE_GAME = (state)->{
			Gdx.files.local("saves/1.save").writeString(state.toSaveString());
		};
		...
	}
	...
	
Sofern alle Launcher eine eigene Implementation des Interfaces bereitstellen kann der Code plattformabhängig betrieben werden.

Dieses Pattern wird in libgdx recht häufig verwerndet, vorallem in der Klasse Gdx welche die Engine zusammenschweist und unter anderem für Ton, Grafiken und das Dateisystem zuständig ist.

Verschiedene Desktopversionen
-----------------------------

Standardmäßig liefert die Setup App einem eine Launcher Version mit einer Lwjgl 2 implementierung.
Die Desktopimplementierungen funktionieren jeweils für Windows, Linux und Mac OS.

Lwjgl2
......

Lwjgl 2 erlaubt es einem mit OpenGl zu programmieren.

Lwjgl3
......

Lwjgl 3 ist eine neuere version von Lwjgl in der das Framework noch einmal überarbeitet wurde.
Hier lassen sich unter anderem auch weitere Fenster öffnen.

Headless
........

Erlaubt es einem zum Beispiel einen Server oder Tests zu starten, da die Headless version kein eigenes Fenster öffnet.

Quellen
-------

.. [0] Libgdx Wikipedia
	https://de.wikipedia.org/wiki/LibGDX
	
.. [libgdx] Libgdx Download Setup App
	https://libgdx.badlogicgames.com/download.html