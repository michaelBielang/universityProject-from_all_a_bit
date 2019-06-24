package de.hsa.game.ingame.load;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

import de.hsa.game.ingame.Box;
import de.hsa.game.ingame.CheckPoint;
import de.hsa.game.ingame.Goal;
import de.hsa.game.ingame.Player;
import de.hsa.game.ingame.Triangle;

public class XMLLevelLoader implements LevelLoader {
	private FileHandle file;
	
	private Texture wallTexture;
	private Texture playerTexture;
	
	private Texture cpActTexture;
	private Texture cpDeactTexture;
	
	
	public XMLLevelLoader(FileHandle file) {
		this.file = file;
		
		loadDefaults();
	}
	
	
	private void loadDefaults() {
		Pixmap wallPixmap = new Pixmap(1, 1, Format.RGBA8888);
		wallPixmap.setColor(Color.WHITE);
		wallPixmap.fill();
		
		wallTexture = new Texture(wallPixmap);
		
		wallPixmap.dispose();
		
		
		Pixmap playerPixmap = new Pixmap(1, 1, Format.RGBA8888);
		playerPixmap.setColor(Color.RED);
		playerPixmap.fill();
		
		playerTexture = new Texture(playerPixmap);
		
		playerPixmap.dispose();
		
		
		Pixmap cpPixmap = new Pixmap(1, 1, Format.RGBA8888);
		cpPixmap.setColor(Color.RED);
		cpPixmap.fill();
		
		cpActTexture = new Texture(cpPixmap);
		
		
		cpPixmap.setColor(Color.GREEN);
		cpPixmap.fill();
		
		cpDeactTexture = new Texture(cpPixmap);
		
		cpPixmap.dispose();
	}
	@Override
	public Player load(World world) {
		Drawable wallDrawable = new TextureRegionDrawable(wallTexture);
		Drawable playerDrawable = new TextureRegionDrawable(playerTexture);
		
		Drawable cpActDrawable = new TextureRegionDrawable(cpActTexture);
		Drawable cpDeactDrawable = new TextureRegionDrawable(cpDeactTexture);
		
		
		Element root = new XmlReader().parse(file);
		
		for(Element child : root.getChildrenByName("box")) {
			Texture texture = new XMLImageParser().parse(child.getChildByName("drawable"));
			
			new Box(texture == null ? wallDrawable : new TextureRegionDrawable(texture),
					child.getFloat("x", 0), child.getFloat("y", 0),
					child.getFloat("width", 1), child.getFloat("height", 1))
				.canKill(child.getBoolean("kills", false))
				.create(world);
		}
		for(Element child : root.getChildrenByName("triangle")) {
			Texture texture = new XMLImageParser().parse(child.getChildByName("drawable"));
			
			List<Vector2> vertices = new LinkedList<Vector2>();
			
			for(Element point : child.getChildrenByName("point")) {
				vertices.add(new Vector2(point.getFloat("x", 0), point.getFloat("y", 0)));
			}
			
			if(vertices.size() != 3)
				continue;
			
			new Triangle(texture == null ? wallTexture : texture,
					child.getFloat("x", 0), child.getFloat("y", 0),
					vertices.get(0), vertices.get(1), vertices.get(2))
				.canKill(child.getBoolean("kills", false))
				.create(world);
		}
		for(Element child : root.getChildrenByName("goal")) {
			Texture texture = new XMLImageParser().parse(child.getChildByName("drawable"));
			
			new Goal(texture == null ? cpActDrawable : new TextureRegionDrawable(texture),
					child.getFloat("x", 0), child.getFloat("y", 0)).create(world);
		}
		for(Element child : root.getChildrenByName("checkpoint")) {
			Texture activation = new XMLImageParser().parse(child.getChildByName("activation_drawable"));
			Texture deactivation = new XMLImageParser().parse(child.getChildByName("deactivation_drawable"));
			
			new CheckPoint(activation == null ? cpActDrawable : new TextureRegionDrawable(activation),
					deactivation == null ? cpDeactDrawable : new TextureRegionDrawable(deactivation),
					child.getFloat("x", 0), child.getFloat("y", 0)).create(world);
		}
		
		
		Player player = new Player(playerDrawable, root.getFloat("spawnX", 0), root.getFloat("spawnY", 0));
		player.addListener((entity)-> {
			player.create(world);
		});
		player.create(world);
		
		return player;
	}
	@Override
	public void dispose() {
		LevelLoader.ASSET_MANAGER.dispose();
		
		playerTexture.dispose();
		wallTexture.dispose();
		
		cpActTexture.dispose();
		cpDeactTexture.dispose();
	}
}
