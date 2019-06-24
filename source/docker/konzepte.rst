Konzepte
========

Services
--------

Verschiedene Teile einer Anwendung werden als "services" bezeichnet.
Eine Filesharing Seite, besitzt, zum Beispiel, wahrscheinlich einen Service für das front-end und einen zum Speichern von Daten in einer Datenbacnk.

Auf einem Service läuft nur ein Image, definiert aber wie das Image läuft. So entscheidet der Service zum Beispiel welche Ports verwendet werden oder wie viele Container das Image laufen lassen.
Um einen Service zu skalieren lässt sich die Menge an Containern Instanzen die den Code ausführen ändern oder die Rechenressourcen erhöhen.

Implementation
..............

Implementieren lässt sich das ganze mit einer ``docker-compose.yml`` Datei.

Beispiel:

.. code-block:: yml

	version: "1"
	services:
		web:
			image: user/repo:tag
			deploy:
				replicas: 3
				resources:
					limits:
						cpus: "0.2"
						memory: 70M
				restart_policy:
					condition: on-failure
			ports:
				- "4000:80"
			networks:
				- webnet
	networks:
		webnet:

Die Datei lässt insgesamt 3 Instanzen des Images laufen, welche jeweils maximal 70 MB RAM zur Verfügung haben und maximal 20% CPU Zeit in Anspruch nehmen können.
Wenn ein Container fehl schlägt wird dieser direkt neugestartet.

Um den Service dann zu starten muss man zunächst ``docker swarm init`` ausführen.

Anschließend lässt sich mit ``docker stack deploy -c docker-compose.yml myService`` der Service starten.

"myService" ist hierbei der Name der Application, welcher beliebig gewählt werden kann.

Um den Schwarm und den Service wieder zu beenden können die Befehle ``docker stack rm myService`` und ``docker swarm leave --force`` benutzt werden.

Swarms
------

Als Schwarm wird eine Gruppe von Maschinen bezeichnet, welche Docker laufen lassen und in einem Cluster verbunden sind.
Ein Schwarmmanager wendet dann alle Befehle auf das Cluster an. Machinen innerhalb eines Schwarms können physisch oder virtuell sein, werden nach dem betreten eines Schwarms aber immer als Nodes bezeichnet.

In der compose Datei könne dann verschiedene Strategien festgelegt werden um dem Schwarmmanager zu zeigen wie er Cotainer Instanzen laufen lassen soll.
Hier gibt es zum Beispiel "global", was sicherstellt das jede Maschine exact eine Instanz eines spezifischen Containers bekommt, oder "emptiest node" was immer die am wenigsten benutzte Maschine mit Containern fült.

Schwarmmanager sind die einzigen in einem Schwarm befindlichen Maschinen welche Befehle ausführen können oder andere Maschinen dem Schwarm als Arbeiter hinzufügen können.
Arbeiter sie sind nur dafür da um Kapazität zu schaffen und können deshalb keinen anderen Maschinen etwas befehlen.

Erstellen
..........

Durch den Befehl ``docker swarm init`` wird auf der Maschine der Schwarm modus aktiviert und die Maschine zum Manager.
Der Befehl ``docker swarm join`` lässt dann andere Maschinen als Arbeiter dem Schwarm beitreten.

Stacks
------

Ein Stack ist eine Menge von Services die zusammen eine Anwendung in einer bestimmten Umgebung bilden.

Sie werden verwendet um mehrere Services die untereinander verbunden sind automatisch bereitzustellen, ohne sie einzeln seperat zu definieren.

Ein stack kann in einer seperaten docker.yml Datei definiert werden. 
So können zum Beispiel Enwticklung, Staging und Production voneinander getrennt jeweils eine docker-dev.yml, docker-staging.yml und eine docker-production.yml definieren um verschiedene Produktionsschritte voneinander zu trennen.

Ein Stack wird wie oben beschrieben auch mit ``docker stack deploy -c stack-file.yml myService`` gestartet.

Quellen
--------
https://docs.docker.com/get-started/part3/
https://docs.docker.com/get-started/part4/
https://docs.docker.com/v17.12/docker-cloud/apps/stacks/