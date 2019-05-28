package de.hsa.game.ingame;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public abstract class Entity {
	private float spawnY, spawnX;
	
	private Body body;
	
	private List<OnDeath> onDeath = new LinkedList<OnDeath>();
	
	
	public Entity(float spawnX, float spawnY) {
		setSpawn(spawnX, spawnY);
	}
	
	
	public Body getBody() {
		return body;
	}
	
	public float getSpawnX() {
		return spawnX;
	}
	public float getSpawnY() {
		return spawnY;
	}
	
	
	public void setSpawn(float x, float y) {
		this.spawnX = x;
		this.spawnY = y;
	}
	
	
	public void destroy() {
		Gdx.app.postRunnable(()->{
			body.getWorld().destroyBody(body);
			
			for(OnDeath listener : onDeath) {
				listener.died(Entity.this);
			}
		});
	}
	
	
	public void create(World world) {
		body = createBody(world);
	}
	public abstract Body createBody(World world);
	
	
	public void addListener(OnDeath listener) {
		onDeath.add(listener);
	}
	public void removeListener(OnDeath listener) {
		onDeath.remove(listener);
	}
	
	
	public static interface OnDeath {
		public void died(Entity entity);
	}
}
