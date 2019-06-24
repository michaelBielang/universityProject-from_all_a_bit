package de.hsa.game.ingame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Box implements WorldDrawable, CanKill {
	private Drawable drawable;
	
	private float x, y;
	private float width, height;
	
	private Body body;
	
	private boolean kills = false;
	
	
	public Box(Drawable drawable, float x, float y, float width, float height) {
		this.drawable = drawable;
		
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
	}


	@Override
	public void draw(Batch batch, float x, float y) {
		drawable.draw(batch, x-width/2f, y-height/2f, width, height);
	}
	
	@Override
	public boolean kills() {
		return kills;
	}
	
	
	public Box canKill(boolean value) {
		this.kills = value;
		
		return this;
	}
	
	
	public void create(World world) {
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(new Vector2(x+width/2f, y+height/2f));  

		// Create a body from the defintion and add it to the world
		body = world.createBody(groundBodyDef);  

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(width/2f, height/2f);
		// Create a fixture from our polygon shape and add it to our ground body  
		body.createFixture(groundBox, 0.0f).setUserData(this);
		// Clean up after ourselves
		groundBox.dispose();
		
		body.setUserData(this);
	}
}
