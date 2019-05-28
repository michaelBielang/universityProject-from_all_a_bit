package de.hsa.game.ingame;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

import de.hsa.game.InGame;
import de.hsa.game.ingame.Player.SensorData;

public class WorldContactListener implements ContactListener {
	private int footContacts;
	
	private InGame inGame;
	
	
	public WorldContactListener(InGame inGame) {
		this.inGame = inGame;
	}
	

	@Override
	public void endContact(Contact contact) {
		Object dataA = contact.getFixtureA().getUserData();
		Object dataB = contact.getFixtureB().getUserData();
		
		sensorEnd(dataA, dataB);
		sensorEnd(dataB, dataA);
	}
	
	@Override
	public void beginContact(Contact contact) {
		Object dataA = contact.getFixtureA().getUserData();
		Object dataB = contact.getFixtureB().getUserData();
		
		sensorBegin(dataA, dataB);
		sensorBegin(dataB, dataA);
	}
	
	private void sensorBegin(Object dataA, Object dataB) {
		if(dataA == null)
			return;
		
		if(dataA instanceof Player.SensorData) {
			SensorData sdata = (SensorData) dataA;
			
			if(sdata.type == Player.FOOT_SENSOR) {
				if(!(dataB instanceof CheckPoint || dataB instanceof Goal))
					footContacts++;
			}else if(sdata.type == Player.RANGE_SENSOR) {
				if(dataB != null) {
					if(dataB instanceof CanKill) {
						if(((CanKill) dataB).kills()) {
							sdata.player.destroy();
						}
					}else if (dataB instanceof CheckPoint) {
						((CheckPoint) dataB).activate(sdata.player);
					}else if(dataB instanceof Goal) {
						inGame.finished();
					}
				}	
			}
		}
	}
	private void sensorEnd(Object dataA, Object dataB) {
		if(dataA == null)
			return;
		
		if(dataA instanceof Player.SensorData) {
			SensorData sdata = (SensorData) dataA;
			
			if(sdata.type == Player.FOOT_SENSOR) {
				if(!(dataB instanceof CheckPoint || dataB instanceof Goal))
					footContacts--;
			}
		}
	}
	
	
	public float getFootContacts() {
		return footContacts;
	}


	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}
