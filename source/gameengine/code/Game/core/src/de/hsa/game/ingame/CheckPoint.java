package de.hsa.game.ingame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class CheckPoint implements WorldDrawable {
	private static CheckPoint activeCP;
	
	
	private float x, y;
	
	private Body body;
	
	private Drawable drawableDeac;
	private Drawable drawableAct;
	
	
	public CheckPoint(Drawable drawable, float x, float y) {
		this(drawable, drawable, x, y);
	}
	public CheckPoint(Drawable deac, Drawable act, float x, float y) {
		this.x = x;
		this.y = y;
		
		this.drawableDeac = deac;
		this.drawableAct = act;
	}
	
	
	@Override
	public void draw(Batch batch, float x, float y) {
		if(activeCP != null && activeCP.equals(this))
			drawableAct.draw(batch, x-1/2f, y-1/2f, 1, 1);
		else
			drawableDeac.draw(batch, x-1/2f, y-1/2f, 1, 1);
	}


	public void activate(Player player) {
		if(activeCP != null && activeCP.equals(this))
			return;
		
		System.out.println("activated check point");
		
		player.setSpawn(x+0.5f, y);
		
		activeCP = this;
	}
	
	
	public void create(World world) {
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();  
		// Set its world position
		groundBodyDef.position.set(new Vector2(x+1/2f, y+1/2f));  

		// Create a body from the defintion and add it to the world
		body = world.createBody(groundBodyDef);  

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();  
		// Set the polygon shape as a box which is twice the size of our view port and 20 high
		// (setAsBox takes half-width and half-height as arguments)
		groundBox.setAsBox(1/2f, 1/2f);
		// Create a fixture from our polygon shape and add it to our ground body  
		Fixture fixture = body.createFixture(groundBox, 0.0f);
		fixture.setUserData(this);
		fixture.setSensor(true);
		// Clean up after ourselves
		groundBox.dispose();
		
		body.setUserData(this);
	}
}
