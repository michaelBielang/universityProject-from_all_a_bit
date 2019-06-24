package de.hsa.game.ingame.load;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import de.hsa.game.ingame.Player;

public interface LevelLoader extends Disposable {
	public static final AssetManager ASSET_MANAGER = new AssetManager();
	
	public Player load(World world);
}
