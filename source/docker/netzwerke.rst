Felix Bühler, <felix.buehler@hs-augsburg.de>, IN6, #2008336

Netzwerke
=========

Docker Containe und Services können sowohl untereinander als auch mit nicht-Docker Anwendungen verbunden werden.

Brücken
-------

Brücken beizeichnen eine Ebene die Traffic zwischen Netzwerksegmenten verteilt.

In Docker wird ein Brückennetzwerk verwendet um nur Containern im selben Netz zu erlauben zu kommunizieren.

Brückennetzwerke funktionieren nur für Cotainer die auf dem selben Docker daemon host laufen.

Beim starten von Docker wird ein Standardbrückennetzwerk, auch genannt ``bridge`` erstellt zu dem sich alle Container verbinden sofern nichts anderes angegeben ist.

Nutzerdefinierte Brücken
........................

Mit hilfe des Befehls ``docker network create`` kann ein Brückennetz erstellt werden.

.. code-block:: shell

	docker network create my-net
	

``docker network rm`` erlaubt es einem dann das Netz wieder zu löschen.

.. code-block:: shell

	docker network rm my-net
	
Um einen Container einem Netz hinzuzufügen kann entweder ``--network my-net`` bei Erstellung des Containers verwendet werden oder man nutzt den Befehl ``docker network connect``.

.. code-block:: shell

	docker network connect my-net my-container
	
Wobei der erste Parameter das erstellt Netz ist und der zweite den Container angibt.

``docker network disconnect`` kann mit Hilfe der selben Parameter den Container wieder vom Netz entfernen.

Overlay Netzwerke
-----------------

Der overlay Netzwerktreiber erlaubt es Containern innerhalb eines Schwarmes auch zwischen verschiedenen Maschienen zu kommunizieren.

Der Befehl ``docker network create`` erlaubt einem nicht nur Brücken sondern auch overlay Netze zu erstellen, hierbei muss ``-d overlay`` angehängt werden.

.. code-block:: shell

	docker network create -d overlay my-overlay

Um ein overlay Netz für alleinstehende und Schwarm Services zu verwenden kann die ``--attachable`` flag benutzt werden.

.. code-block:: shell

	docker network create -d overlay --attachable my-attachable-overlay
	
Vorteile
--------

In Brückennetzwerken lassen sich die Containernamen und IDs statt IP-Adressen verwenden .

.. code-block:: shell

	docker network create alpine-net
	//netzwerk alpine-net wird erstellt
	
	docker run -dit --name alpine1 --network alpine-net alpine ash
	docker run -dit --name alpine2 --network alpine-net alpine ash
	//zwei container alpine1 und alpine2 werden erstell und mit alpine-net verbunden
	
	docker container attach alpine1
	//zugriff auf die console von apline1
	
	ping -c 2 alpine2
	//alpine2 lässt sich von alpine1 aus anpingen da sie im selben Netz sind
	
	PING alpine2 (172.18.0.3): 56 data bytes
	64 bytes from 172.18.0.3: seq=0 ttl=64 time=0.085 ms
	64 bytes from 172.18.0.3: seq=1 ttl=64 time=0.090 ms

	--- alpine2 ping statistics ---
	2 packets transmitted, 2 packets received, 0% packet loss
	round-trip min/avg/max = 0.085/0.087/0.090 ms


Quellen:

https://docs.docker.com/network/bridge/
https://docs.docker.com/network/overlay/
https://docs.docker.com/network/network-tutorial-standalone/