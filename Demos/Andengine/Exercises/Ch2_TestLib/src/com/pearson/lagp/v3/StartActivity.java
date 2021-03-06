package com.pearson.lagp.v3;

import java.io.IOException;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.music.MusicFactory;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.StrokeFont;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.HorizontalAlign;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.widget.Toast;

public class StartActivity extends BaseGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 320;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private Texture mTexture, mBatTexture, mHeroTexture;
	private TiledTextureRegion mBatTextureRegion;
	private TiledTextureRegion mHeroTextureRegion;
	private TextureRegion mSplashTextureRegion;
	private Handler mHandler;

	static protected Music mMusic;
	private SharedPreferences audioOptions;
	private SharedPreferences.Editor audioEditor;

	private Texture mFontTexture;
	private Font mFont;
	private Texture mStrokeFontTexture;
	private StrokeFont mStrokeFont;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		audioOptions = getSharedPreferences("audio", MODE_PRIVATE);
		audioEditor = audioOptions.edit();

		mHandler = new Handler();
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT),
				this.mCamera).setNeedsMusic(true));
	}

	@Override
	public void onLoadResources() {

		this.mFontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mStrokeFontTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, Typeface.create(
				Typeface.DEFAULT, Typeface.BOLD), 20, true, Color.YELLOW);
		this.mStrokeFont = new StrokeFont(this.mStrokeFontTexture,
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 20, true,
				Color.RED, 2.0f, Color.GREEN, true);

		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getTextureManager().loadTexture(this.mStrokeFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);
		this.mEngine.getFontManager().loadFont(this.mStrokeFont);

		MusicFactory.setAssetBasePath("mfx/");
		try {
			StartActivity.mMusic = MusicFactory.createMusicFromAsset(
					this.mEngine.getMusicManager(), getApplicationContext(),
					"Somewhere Sunny.mp3");
			StartActivity.mMusic.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}

		this.mTexture = new Texture(512, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mSplashTextureRegion = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "gfx/screens/game_screen.jpg", 0, 0);

		// this.mHeroTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		// this.mHeroTextureRegion = TextureRegionFactory.createTiledFromAsset(
		// this.mHeroTexture, this, "gfx/tiles/hero1.png", 0, 0, 4, 4);
		// this.mEngine.getTextureManager().loadTexture(this.mHeroTexture);
		// this.mBatTexture = new Texture(256, 256, TextureOptions.DEFAULT);
		// this.mBatTextureRegion = TextureRegionFactory.createTiledFromAsset(
		// this.mBatTexture, this, "gfx/tiles/hero148x48.png", 0, 0, 4, 1);
		// this.mEngine.getTextureManager().loadTexture(this.mBatTexture);
		this.mEngine.getTextureManager().loadTexture(this.mTexture);

	}

	@Override
	public Scene onLoadScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(1);

		/* Center the splash on the camera. */
		final int centerX = (CAMERA_WIDTH - this.mSplashTextureRegion
				.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mSplashTextureRegion
				.getHeight()) / 2;

		/* Create the sprite and add it to the scene. */
		final Sprite splash = new Sprite(centerX, centerY,
				this.mSplashTextureRegion);
		scene.getLastChild().attachChild(splash);

		final Text textCenter = new Text(100, 60, this.mFont,
				"Demo with AndEngine", HorizontalAlign.CENTER);
		final Text textStroke = new Text(100, 160, this.mStrokeFont,
				"Author: Nguyen Duy Thai\nEmail:ndthaik1@gmail.com",
				HorizontalAlign.CENTER);
		scene.getLastChild().attachChild(textCenter);
		scene.getLastChild().attachChild(textStroke);

		// /* Create the animated bat sprite and add to scene */
		// final AnimatedSprite bat = new AnimatedSprite(100, 100,
		// this.mBatTextureRegion);
		// bat.animate(100);
		// scene.getLastChild().attachChild(bat);
		//
		// /* Create the animated bat sprite and add to scene */
		// final AnimatedSprite hero = new AnimatedSprite(200, 100,
		// this.mHeroTextureRegion);
		// hero.animate(100, false);
		// scene.getLastChild().attachChild(hero);

		// Start the music!
		mMusic.play();
		if (audioOptions.getBoolean("musicOn", false)) {
			mMusic.pause();
		}

		return scene;
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
		// if (audioOptions.getBoolean("musicOn", false))
		// StartActivity.mMusic.resume();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
		// StartActivity.mMusic.pause();
	}

	@Override
	public void onLoadComplete() {
		onNextToMenu();
	}

	private Runnable mLaunchTask = new Runnable() {
		public void run() {
			Intent myIntent = new Intent(StartActivity.this,
					MainMenuActivity.class);
			StartActivity.this.startActivity(myIntent);
		}
	};

	private void onNextToMenu() {
		mHandler.postDelayed(mLaunchTask, 2000);
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
