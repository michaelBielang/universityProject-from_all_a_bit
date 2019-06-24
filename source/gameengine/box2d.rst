| Tobias Drüeke, <tobias.drueeke@hs-augsburg.de>, IN6, #2004114

Box2d
=====

Box2D ist eine beliebte Physik-Simulations-Bibliothek für 2D Spiele. 
Libgdx besitzt einen eigenen Port der Engine, welcher nur einen leichten Java wrapper um die C++ Engine enthält, 
was zur Folge hat, das man die offizielle Documentation von Box2D verwenden kann.
 
Verwendung
----------

Zunächst sollte einmal `Box2D.init()` aufgerufen werden um Box2d zu initalisieren.

Anschließend sollte man eine Welt erstellen.

.. code-block:: java

	World world = new World(new Vector2(0, -10), true); 
	
Der gegebene Vector definiert hier die Gravitation.

Ein DebugRenderer erlaubt einem die Objecte darzustellen.
Zusätzlich sollte noch eine Kamera verwendet werden um die Welt darzustellen.

.. code-block:: java

	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	OrthographicCamera camera = new OrthographicCamera(10f*Gdx.graphics.getWidth()/Gdx.graphics.getHeight(), 10);
	
In der render Methode muss man dann nur noch die Welt und Kamera updaten und rendern.

.. code-block:: java

	world.step(1/60f, 6, 2);

	camera.update();
	
	debugRenderer.render(world, camera.combined);
	
Die Welt updatet sich jetzt und wird gerendert, aber besitzt noch keine Objekte.

Bodies
------

Jeder Körper in Box2d besteht aus ein oder mehreren Fixtures, welche jeweils eine fixe Position und Orientierung besitzen. 

Eine Fixture besitzt eine Shape, Dichte, Reibung und eine Restitution.

Shape beschreibt die Form.
Dichte die Masse pro Quadratmeter.
Reibung die Größe der Kraft wenn Körper aneinander reiben.
Restitution beschreibt wie stark Körper voneinander abfedern.

Körper kommen in 3 verschiedenen Typen vor.

Dynamic
.......

Dynamische Körper werden von allen anderen Körpern beeinflusst.
Sie werden für alle Objekte verwendet die sich bewegen müssen und dabei von anderen Objekten beeinflusst werden.

.. code-block:: java

	// First we create a body definition
	BodyDef bodyDef = new BodyDef();
	// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
	bodyDef.type = BodyType.DynamicBody;
	// Set our body's starting position in the world
	bodyDef.position.set(100, 300);

	// Create our body in the world using our body definition
	Body body = world.createBody(bodyDef);

	// Create a circle shape and set its radius to 6
	CircleShape circle = new CircleShape();
	circle.setRadius(6f);

	// Create a fixture definition to apply our shape to
	FixtureDef fixtureDef = new FixtureDef();
	fixtureDef.shape = circle;
	fixtureDef.density = 0.5f; 
	fixtureDef.friction = 0.4f;
	fixtureDef.restitution = 0.6f; // Make it bounce a little bit

	// Create our fixture and attach it to the body
	Fixture fixture = body.createFixture(fixtureDef);

	// Remember to dispose of any shapes after you're done with them!
	// BodyDef and FixtureDef don't need disposing, but shapes do.
	circle.dispose();
	
[0]_

In dem Beispiel wird ein Ball erstellt.

Static
......

Statische Körper können sich nicht bewegen und werden nicht von anderen Objekten beeinflusst.
Sie werden zum Beispiel für den Boden oder Wände verwendet.

.. code-block:: java

	// Create our body definition
	BodyDef groundBodyDef = new BodyDef();  
	// Set its world position
	groundBodyDef.position.set(new Vector2(0, 10));  

	// Create a body from the defintion and add it to the world
	Body groundBody = world.createBody(groundBodyDef);  

	// Create a polygon shape
	PolygonShape groundBox = new PolygonShape();  
	// Set the polygon shape as a box which is twice the size of our view port and 20 high
	// (setAsBox takes half-width and half-height as arguments)
	groundBox.setAsBox(camera.viewportWidth, 10.0f);
	// Create a fixture from our polygon shape and add it to our ground body  
	groundBody.createFixture(groundBox, 0.0f); 
	// Clean up after ourselves
	groundBox.dispose();
	
[1]_

Mit dem oben erstellten Ball und dem gerade erstellten Boden prallt sieht man nun einen Ball vom Boden abprallen.

Kinematic
.........

Kinematische Körper sind der Mittelweg zischen dynamisch und statisch.
Sie können sich bewegen werden aber nicht von anderen Körpern beeinflusst.
Sie werden meist für bewegliche Platformen verwendet.

.. code-block:: java
	
	// Move upwards at a rate of 1 meter per second
	kinematicBody.setLinearVelocity(0.0f, 1.0f);
	
[2]_

Sensoren
--------

Eine Fixture kann als Sensor verwendet werden.
Dadurch interagiert diese nicht mehr mit anderen Körpern kann aber mithilfe eines ContactListeners Berührungen erkennen.

.. code-block:: java

	public class ListenerClass implements ContactListener {
		@Override
		public void endContact(Contact contact) {
				
		}
		
		@Override
		public void beginContact(Contact contact) {
			
		}
	};
	
Der Listener muss dann noch der Welt hinzugefügt werden.

.. code-block:: java

	world.setContactListener(ListenerClass);
	
Der Listener erhält alle Kontakte zwischen Sensoren und anderen Fixtures.

[3]_


Quellen
-------

.. [0] Libgdx Wiki Dynamic Bodies
	https://github.com/libgdx/libgdx/wiki/Box2d#dynamic-bodies
.. [1] Libgdx Wiki Static Bodies
	https://github.com/libgdx/libgdx/wiki/Box2d#static-bodies
.. [2] Libgdx Wiki Kinematic Bodies
	https://github.com/libgdx/libgdx/wiki/Box2d#kinematic-bodies
.. [3] Libgdx Wiki Contact Listeners
	https://github.com/libgdx/libgdx/wiki/Box2d#contact-listeners