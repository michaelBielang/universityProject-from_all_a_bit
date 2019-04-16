Java Maven Hello World
======================

Simples Beispiel wie man einen Container um ein Java Programm erstellen kann:

Ein simples Hello World Programm:

.. code-block:: java

	package org.examples.java;

	public class App {
		public static void main(String... args ){
			System.out.println("Hello Java");
		}
	}

Die java datei befindet sich in einem Projekt im ordner "src/main/java/org/examples/java"
	
Die Dockerfile

.. code-block:: yml
	
	FROM openjdk:latest

	COPY target/helloworld-1.0-SNAPSHOT.jar /jars/helloworld-1.0-SNAPSHOT.jar

	CMD java -cp /jars/helloworld-1.0-SNAPSHOT.jar org.examples.java.App
	
Das Projekt benötigt für maven natürlich noch eine pom.xml.

Als nächstes muss die jar datei mit ``mvn package`` erstellt werden.

Mit ``docker build -t hellojava .`` wird dann das image erstellt.

``docker run hellojava`` liefert dann folgenden output: ``Hello Java``