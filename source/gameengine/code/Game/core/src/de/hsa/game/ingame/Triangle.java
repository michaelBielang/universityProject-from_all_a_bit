package de.hsa.game.ingame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Triangle implements WorldDrawable, CanKill {
	private TextureRegion texture;
	
	private Vector2[] shape;
	
	private Body body;
	
	private float x, y;
	
	private Vector2 first, second, third;
	
	private boolean kills = false;
	
	
	public Triangle(Texture texture, float x, float y, Vector2 first, Vector2 second, Vector2 third) {
		this(new TextureRegion(texture), x, y, first, second, third);
	}
	public Triangle(TextureRegion texture, float x, float y, Vector2 first, Vector2 second, Vector2 third) {
		this.x = x;
		this.y = y;
		
		this.texture = texture;
		
		this.first = first;
		this.second = second;
		this.third = third;
		
		shape = new Vector2[] {first, second, third};
	}
	
	
	@Override
	public boolean kills() {
		return kills;
	}
	
	
	public Triangle canKill(boolean value) {
		this.kills = value;
		
		return this;
	}


	@Override
	public void draw(Batch batch, float x, float y) {
		float c = Color.WHITE.toFloatBits();
		float u = texture.getU();
		float v = texture.getV();
		float u2 = texture.getU2();
		float v2 = texture.getV2();
		float[] vertices = new float[] {
				first.x+x, first.y+y, c, u, v,
				second.x+x, second.y+y, c, u, v2,
				third.x+x, third.y+y, c, u2, v,
				first.x+x, first.y+y, c, u, v,
			};
		
		batch.draw(texture.getTexture(), vertices, 0, vertices.length);
	}
	
	
	public void create(World world) {
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(new Vector2(x, y));  

		// Create a body from the defintion and add it to the world
		body = world.createBody(groundBodyDef);  

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
		groundBox.set(shape);
		// Create a fixture from our polygon shape and add it to our ground body  
		body.createFixture(groundBox, 0.0f).setUserData(this);;
		// Clean up after ourselves
		groundBox.dispose();
		
		body.setUserData(this);
	}
}
