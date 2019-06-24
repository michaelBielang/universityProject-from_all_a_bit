package beispiele;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter{
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new Game(), config);
	}
	
	private Texture image;
	private Texture pixmapImage;
	
	private SpriteBatch batch;
	private OrthographicCamera camera;
	
	
	@Override
	public void create(){
		image = new Texture(Gdx.files.classpath("beispiele/Image.png"));
		
		Pixmap pixmap = new Pixmap(16, 16, Format.RGBA8888);
		pixmap.setColor(Color.YELLOW);
		pixmap.fill();
		
		pixmapImage = new Texture(pixmap);
		
		pixmap.dispose();
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera(64*Gdx.graphics.getWidth()/Gdx.graphics.getHeight(), 64);
	}
	@Override
	public void render(){
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		batch.draw(pixmapImage, 0, 0);
		batch.draw(image, 16, 0);
		batch.end();
	}
}