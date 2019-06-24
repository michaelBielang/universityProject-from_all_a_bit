Felix Bühler, <felix.buehler@hs-augsburg.de>, IN6, #2008336

Beispiel
========

Beschreibung
------------

Der Spieler steuert ein Quadrat und muss mit diesem das Ziel des Levels erreichen.
Mit a und d bewegt sich der Charakter nach links bzw. rechts und mit der Leertaste kann er springen.

Ein Level wird jeweils aus einer XML-Datei geladen.

Level bestehen aus Recht- und Dreiecken welche der Spieler nicht durchschreiten kann.

Weiterhin kann ein Level Checkpoints besitzen, welche den Respawnpunkt des Spielers ändern.

Die Recht- und Dreiecke die das Levle definieren können mithilfe des Attributs `kills` den Spieler töten.

Auch lässt sich die Farbe der einzelnen Levelbausteine ändern.

link zur jar_.

.. _jar: https://r-n-d.informatik.hs-augsburg.de:8080/dva/berichte-2019/51/tree/master/source/gameengine/code/desktop-1.0.jar