package de.hsa.game.ingame.load;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.utils.XmlReader.Element;

public class XMLImageParser {
	public Texture parse(Element element) {
		if(element == null)
			return null;
		
		if(element.get("type").equals("solid_color")) {
			Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
			
			pixmap.setColor(new Color(element.getFloat("r", 0)/256f, element.getFloat("g", 0)/256f, element.getFloat("b", 0)/256f, element.getFloat("a", 256)/256f));
			pixmap.fill();
			
			Texture texture = new Texture(pixmap);
			
			pixmap.dispose();
			
			return texture;
		}
		if(element.get("type").equals("image")) {
			return LevelLoader.ASSET_MANAGER.get(element.get("path"), Texture.class);
		}
		
		return null;
	}
}
