package de.hsa.game.ingame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Player extends Entity implements WorldDrawable {
	public static final int RANGE_SENSOR = 2;
	public static final int FOOT_SENSOR = 3;
	
	private Drawable drawable;
	
	private int deaths = 0;
	
	private boolean destroy = false;
	
	
	public Player(Drawable drawable, float x, float y) {
		super(x, y);
		
		this.drawable = drawable;
	}
	
	
	@Override
	public void draw(Batch batch, float x, float y) {
		drawable.draw(batch, x-1/2f, y-1/2f, 1, 1);
	}
	@Override
	public void destroy() {
		if(destroy)
			return;
		
		destroy = true;
		
		super.destroy();
		deaths++;
	}
	
	@Override
	public int getDepth() {
		return -1;
	}
	public int getDeaths() {
		return deaths;
	}
	
	
	@Override
	public Body createBody(World world) {
		destroy = false;
		
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(getSpawnX(), getSpawnY());

		// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

		// Create a polygon shape
		PolygonShape shape = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		shape.setAsBox(0.9f/2f, 0.9f/2f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 0.5f; 
		fixtureDef.friction = 0f;
		fixtureDef.restitution = 0f;

		// Create our fixture and attach it to the body
		body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		shape.dispose();
		
		body.setUserData(this);
		body.setFixedRotation(true);
		
		createJumpSensor(body);
		createRangeSensor(body);
		
		return body;
	}
	private void createJumpSensor(Body body) {
		// Create a polygon shape
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(0.7f/2f, 0.25f, new Vector2(0, -0.5f), 0);
		
		FixtureDef def = new FixtureDef();
		def.shape = shape;
		
		def.isSensor = true;
		Fixture fixture = body.createFixture(def);
		fixture.setUserData(new SensorData(FOOT_SENSOR, this));
	}
	private void createRangeSensor(Body body) {
		// Create a polygon shape
		PolygonShape shape = new PolygonShape();  
		shape.setAsBox(1f/2f, 1f/2f, new Vector2(0, 0), 0);
		
		FixtureDef def = new FixtureDef();
		def.shape = shape;
		
		def.isSensor = true;
		Fixture fixture = body.createFixture(def);
		fixture.setUserData(new SensorData(RANGE_SENSOR, this));
	}
	
	
	public class SensorData{
		public final int type;
		public final Player player;
		
		
		public SensorData(int type, Player player) {
			this.type = type;
			this.player = player;
		}
	}
}
