package com.kutaykerem.helicopterrescue;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import java.util.Random;

public class HelicopterRescue extends ApplicationAdapter {



	SpriteBatch batch;
	Texture path;
	Texture helicopter;
	Texture enemy;
	Texture enemy2;
	Texture enemy3;
	Random random;


	int gameState = 0;



	Circle helicopterCircle;


	float helicopterY = 0;
	float helikopterX = 0;

	int helicopterSet = 3;

	float[] enemyX = new float[helicopterSet];
	float[] randomEnemy = new float[helicopterSet];
	float[] randomEnemy2 = new float[helicopterSet];
	float[] randomEnemy3= new float[helicopterSet];


	float distance = 0;
	float veloctiy = 0;
	float enemyVelocity = 3.0f;
	float gravity = 0.1f;

	Circle[] enemyCircle;
	Circle[] enemyCircle2;
	Circle[] enemyCircle3;


	ShapeRenderer shapeRenderer;

	int score = 0;
	int scorEnemy = 0;
	int highScore = 0;
	BitmapFont font;





	@Override
	public void create () {
		batch = new SpriteBatch();
		path = new Texture("path2.png");
		helicopter = new Texture("helicopter.png");
		enemy = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();


	/*	Preferences preferences = Gdx.app.getPreferences("Score");  // Rekor kaydedilcek ise..
		this.highScore = preferences.getInteger("HighScore",0);

		if(score > highScore){
			preferences.getInteger("HighScore",score);
			preferences.flush();
		}
		*/



		helikopterX = Gdx.graphics.getWidth() / 3 ;
		helicopterY = Gdx.graphics.getHeight() / 3;

		shapeRenderer = new ShapeRenderer();


		helicopterCircle = new Circle();        // Roketlerin random olarak gelmesi.
		enemyCircle = new Circle[helicopterSet];
		enemyCircle2 = new Circle[helicopterSet];
		enemyCircle3 = new Circle[helicopterSet];

		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.getData().setScale(4);



		for (int i = 0; i<helicopterSet; i++) {


			randomEnemy[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);    // Random ayarları.
			randomEnemy2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			randomEnemy3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - helicopter.getWidth() / 2 + i * distance;   // Roketlerin geliş aralıkları.


			enemyCircle[i] = new Circle();         // Çarpışma ayarları için initialize.
			enemyCircle2[i] = new Circle();
			enemyCircle3[i] = new Circle();

		}




	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(path,0,0);

		if (gameState ==1) {    // Oyun başladığında...


			if(score > 19){


			}



			if (enemyX[scorEnemy] < Gdx.graphics.getWidth() / 2 - helicopter.getHeight() / 2) {   // Score arttırma.
				score++;
				enemyVelocity++;

				if (scorEnemy < helicopterSet - 1) {
					scorEnemy++;
				} else {
					scorEnemy = 0;
				}

			}





			if (Gdx.input.justTouched()) {  // Tıklandığında helikopterin yükselmesi.
				veloctiy = -4;

			}


			for (int i = 0; i < helicopterSet; i++) {


				if (enemyX[i] < Gdx.graphics.getWidth() / 15) {      // Roketleri random ayarı.
					enemyX[i] = enemyX[i] + helicopterSet * distance;

					randomEnemy[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					randomEnemy2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					randomEnemy3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;   // Roketlerin geliş hızı.
				}


				//  Obje çizimleri.
				batch.draw(enemy,enemyX[i],Gdx.graphics.getHeight()/2 + randomEnemy[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy,enemyX[i],Gdx.graphics.getHeight()/2 + randomEnemy2[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy,enemyX[i],Gdx.graphics.getHeight()/2 + randomEnemy3[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);

				// Çarpışma ayarı çizimleri.
				enemyCircle[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40,  Gdx.graphics.getHeight()/2 + randomEnemy[i] + Gdx.graphics.getHeight() / 30,Gdx.graphics.getWidth() / 40);
				enemyCircle2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40,  Gdx.graphics.getHeight()/2 + randomEnemy2[i] + Gdx.graphics.getHeight() / 30,Gdx.graphics.getWidth() / 40);
				enemyCircle3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 40,  Gdx.graphics.getHeight()/2 + randomEnemy3[i] + Gdx.graphics.getHeight() / 30,Gdx.graphics.getWidth() / 40);


			}



			if (helicopterY > 100) {       // Helikopter yere indiğinde oyunu bitir.
				veloctiy = veloctiy + gravity;
				helicopterY = helicopterY - veloctiy;
			} else {
				gameState = 2;

			}


		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2){      // Oyun bittiğinde yeniden başlat.
			if (Gdx.input.justTouched()) {
				gameState = 1;

				helicopterY = Gdx.graphics.getHeight() / 3;
				helikopterX = Gdx.graphics.getWidth() / 2;


				for (int i = 0; i<helicopterSet; i++) {


					randomEnemy[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					randomEnemy2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					randomEnemy3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - helicopter.getWidth() / 2 + i * distance;


					enemyCircle[i] = new Circle();
					enemyCircle2[i] = new Circle();
					enemyCircle3[i] = new Circle();

				}

				veloctiy = 0;
				score = 0;
				scorEnemy = 0;
				enemyVelocity = 3;


			}
		}




		batch.draw(helicopter,helikopterX, helicopterY, Gdx.graphics.getWidth() / 10,Gdx.graphics.getHeight() / 10);

		font.draw(batch,String.valueOf(score),1800,1050);

		batch.end();

		helicopterCircle.set(helikopterX +Gdx.graphics.getWidth() / 40 ,helicopterY + Gdx.graphics.getHeight() / 30,Gdx.graphics.getWidth() / 40);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);





		//  Neyin ney ile çarpışma ayarı.
		for ( int i = 0; i < helicopterSet; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

			if (Intersector.overlaps(helicopterCircle,enemyCircle[i]) || Intersector.overlaps(helicopterCircle,enemyCircle2[i]) || Intersector.overlaps(helicopterCircle,enemyCircle3[i])) {
				gameState = 2;
			}
		}

		//shapeRenderer.end();

	}

	@Override
	public void dispose () {

	}
}