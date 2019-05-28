package de.hsa.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import de.hsa.game.ingame.Player;
import de.hsa.game.ingame.WorldContactListener;
import de.hsa.game.ingame.WorldDrawable;
import de.hsa.game.ingame.load.LevelLoader;
import de.hsa.game.ingame.load.XMLLevelLoader;
import de.hsa.game.utils.GdxUtils;

public class InGame extends ScreenAdapter {
	private OrthographicCamera guiCamera;
	private BitmapFont font;
	
	private Box2DDebugRenderer debugRenderer;
	
	private World world;
	
	private OrthographicCamera camera;
	
	private SpriteBatch batch;
	
	private Player player;
	
	private WorldContactListener contacts;
	
	private float accumulator = 0;
	
	private LevelLoader level;
	
	
	public InGame(FileHandle xml) {
		level = new XMLLevelLoader(xml);
	}
	public InGame() {
		level = new XMLLevelLoader(Gdx.files.internal("level1.xml"));
	}
	
	
	@Override
	public void show() {
		contacts = new WorldContactListener(this);
		
		guiCamera = new OrthographicCamera();
		guiCamera.setToOrtho(false);
		font = new BitmapFont();
		
		batch = new SpriteBatch();
		
		world = new World(new Vector2(0, -10), true); 
		
		world.setContactListener(contacts);
		
		debugRenderer = new Box2DDebugRenderer();
		
		camera = new OrthographicCamera(10f*GdxUtils.aspectRatio(), 10);
		
		createLevel();
	}
	@Override
	public void render(float delta) {
		processInput(delta);
		
		update(delta);
		
		if(player.getBody() != null)
			camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		Array<Body> bodies = new Array<Body>();
		
		world.getBodies(bodies);
		
		bodies.sort(new WorldDrawable.DepthComparator());
		batch.begin();
		
		for(Body body : bodies) {
			if(body.getUserData() == null)
				continue;
			
			if(!(body.getUserData() instanceof WorldDrawable))
				continue;
			
			((WorldDrawable) body.getUserData()).draw(batch, body.getPosition().x, body.getPosition().y);
		}
		
		batch.setProjectionMatrix(guiCamera.combined);
		font.draw(batch, "DEATHS: "+player.getDeaths(), 0, Gdx.graphics.getHeight());
		
		batch.end();
		
		if(Game.DEBUG)
			debugRenderer.render(world, camera.combined);
	}
	@Override
	public void hide() {
		batch.dispose();
		debugRenderer.dispose();
		world.dispose();
	}
	
	private void processInput(float delta) {
		handleMoveInput(delta);
		
		Vector2 pos = player.getBody().getPosition();
		
		if(contacts.getFootContacts() < 1) {
			return;
		}
		
		Vector2 vel = this.player.getBody().getLinearVelocity();
		
		if(vel.y < 15 && Gdx.input.isKeyPressed(Keys.SPACE)) {
			player.getBody().applyLinearImpulse(0, 30*delta, pos.x, pos.y, true);
		}
	}
	private void handleMoveInput(float delta) {
		Vector2 vel = this.player.getBody().getLinearVelocity();
		Vector2 pos = this.player.getBody().getPosition();
		
		float acceleration = vel.y != 0 ? 0.2f : 0.8f;
		float speedLimit = vel.y != 0 ? 2 : 5;
				
		// apply left impulse, but only if max velocity is not reached yet
		if (Gdx.input.isKeyPressed(Keys.A) && vel.x > -speedLimit) {	
		     this.player.getBody().applyLinearImpulse(-acceleration, 0, pos.x, pos.y, true);
		}

		// apply right impulse, but only if max velocity is not reached yet
		if (Gdx.input.isKeyPressed(Keys.D) && vel.x < speedLimit) {
		     this.player.getBody().applyLinearImpulse(acceleration, 0, pos.x, pos.y, true);
		}
		
		if(vel.y == 0 && !(Gdx.input.isKeyPressed(Keys.A) || Gdx.input.isKeyPressed(Keys.D))){
			player.getBody().setLinearVelocity(0, 0);
		}
	}
	private void update(float delta) {
		// fixed time step
	    // max frame time to avoid spiral of death (on slow devices)
	    float frameTime = Math.min(delta, 0.25f);
	    accumulator += frameTime;
	    while (accumulator >= 1f/60f) {
	        world.step(1f/60f, 6, 2);
	        accumulator -= 1f/60f;
	    }
	}
	
	private void createLevel() {
		player = level.load(world);
	}
	
	
	public void finished() {
		Gdx.app.postRunnable(()->{
			level.dispose();
			
			Array<Body> bodies = new Array<Body>();
			
			world.getBodies(bodies);
			
			for(Body body : bodies) {
				world.destroyBody(body);
			}
			
			if(!hasNextLevel())
				((Game) Gdx.app.getApplicationListener()).setScreen(new ScoreScreen(player.getDeaths()));
		});
	}
	private boolean hasNextLevel() {
		return false;
	}
	
	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = 10f*width/height;
		camera.viewportHeight = 10;
	}
}
