package com.berkesoft.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class FlappyBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bee1;
	Texture bee2;
	Texture bee3;
	float birdx = 0;
	float birdy =0;
	int gameState = 0;
	float velocity = 0;
	float gravity = 0.6f;
	float enemyVelocity = 2f;
	Random random;
	Circle birdCircle;
	int score =0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	int numberOfEnemies = 4;
	float [] enemyx = new float[numberOfEnemies];
	float [] enemyOffset = new float[numberOfEnemies];
	float [] enemyOffset2 = new float[numberOfEnemies];
	float [] enemyOffset3 = new float[numberOfEnemies];

	Circle [] enemyCircles;
	Circle [] enemyCircles2;
	Circle [] enemyCircles3;
	ShapeRenderer shapeRenderer;


	float distance = 0;




	@Override
	public void create () {

		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		bee1 = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");
		random = new Random();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().scale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().scale(4);

		distance = Gdx.graphics.getWidth()/3;

		birdx = Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4;
		birdy= Gdx.graphics.getHeight()/2;

		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];
		shapeRenderer = new ShapeRenderer();


		for (int i = 0; i<numberOfEnemies; i++){

			enemyOffset [i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			enemyOffset2 [i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
			enemyOffset3 [i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

			enemyx[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;

			enemyCircles [i] = new Circle();
			enemyCircles2 [i] = new Circle();
			enemyCircles3 [i] = new Circle();

		}
	}




	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (Gdx.input.justTouched()){
			velocity = -12;
		}

		for (int i =0; i<numberOfEnemies; i++){

			if (enemyx[i] < Gdx.graphics.getWidth()/15){
				enemyx[i]= enemyx[i] + numberOfEnemies * distance;

				enemyOffset [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyOffset2 [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				enemyOffset3 [i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			}else{
				enemyx[i] = enemyx[i] - enemyVelocity;
			}

			enemyx[i] = enemyx[i] - enemyVelocity;
			batch.draw(bee1,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffset [i], Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
			batch.draw(bee2,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffset2 [i], Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
			batch.draw(bee3,enemyx[i],Gdx.graphics.getHeight()/2 + enemyOffset3 [i], Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);

			enemyCircles [i] = new Circle(enemyx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset [i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			enemyCircles2 [i] = new Circle(enemyx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset2 [i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			enemyCircles3 [i] = new Circle(enemyx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset3 [i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);

		}


		// Ekrana tıklandığında gameState 1 e eşit olsun.
		if (gameState ==1){

			if (enemyx [scoredEnemy] < Gdx.graphics.getWidth()/2 - Gdx.graphics.getWidth()/4){
				score++;

				if (scoredEnemy < numberOfEnemies - 1){
					scoredEnemy++;
				}else {
					scoredEnemy = 0;
				}
			}

			// kuş birdy yani yükseklik sıfır olana kadar düşsün, yükseklik sıfır olunca kuş dursun.
			if (birdy > 0){
				//gameState 1 e eşit olunca kuuşun yüksekliğini velocity değerinde çıkar.
				velocity = velocity + gravity;
				birdy = birdy - velocity;
			}else{
				gameState = 2;
			}
		// eğer oyun duruyorsa kuşu tıklanınca başlat.
		}else if (gameState == 0){
			if (Gdx.input.justTouched()) {
				gameState = 1;}

		}else if (gameState == 2){

			font2.draw(batch,"Game Over! Tap To Play Again.", 100, Gdx.graphics.getHeight()/2);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdy= Gdx.graphics.getHeight()/2;

				for (int i = 0; i<numberOfEnemies; i++){

					enemyOffset [i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
					enemyOffset2 [i] = (random.nextFloat()) * (Gdx.graphics.getHeight());
					enemyOffset3 [i] = (random.nextFloat()) * (Gdx.graphics.getHeight());

					enemyx[i] = Gdx.graphics.getWidth() - bee1.getWidth()/2 + i * distance;

					enemyCircles [i] = new Circle();
					enemyCircles2 [i] = new Circle();
					enemyCircles3 [i] = new Circle();

				}
				velocity = 0;
				scoredEnemy = 0;
				score = 0;
			}
		}

		batch.draw(bird,birdx,birdy,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch, String.valueOf(score), 100,200);

		batch.end();

		birdCircle.set(birdx + Gdx.graphics.getWidth()/30,birdy + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);
		
		for (int i = 0; i < numberOfEnemies; i++){

			//shapeRenderer.circle(enemyx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset [i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset2 [i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemyx[i]+Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + enemyOffset3 [i] + Gdx.graphics.getHeight()/20, Gdx.graphics.getWidth()/30);

			if (Intersector.overlaps(birdCircle,enemyCircles [i])|| Intersector.overlaps(birdCircle,enemyCircles2 [i]) || Intersector.overlaps(birdCircle, enemyCircles3 [i])){
				gameState =2;
			}
		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
