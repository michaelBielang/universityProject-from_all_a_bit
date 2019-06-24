package de.hsa.game.ingame;

import java.util.Comparator;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;

public interface WorldDrawable {
	public void draw(Batch batch, float x, float y);
	public default int getDepth() {
		return 0;
	};
	
	
	public static class DepthComparator implements Comparator<Body>{
		@Override
		public int compare(Body arg0, Body arg1) {
			if(arg0.getUserData() == null && arg1.getUserData() == null)
				return 0;
			
			if(arg0.getUserData() != null && arg1.getUserData() != null) {
				if(arg0.getUserData() instanceof WorldDrawable) {
					if(arg1.getUserData() instanceof WorldDrawable) {
						return Integer.compare(((WorldDrawable) arg0.getUserData()).getDepth(),
								((WorldDrawable) arg1.getUserData()).getDepth())*-1;
					}else
						return 1;
				}else if(arg1.getUserData() instanceof WorldDrawable)
					return -1;
				return 0;
			}
			
			if(arg0.getUserData() == null)
				return -1;
			else 
				return 1;
		}
	}
}
