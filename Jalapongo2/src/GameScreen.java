import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class GameScreen {

	//Corner Bumpers
	int rectW = 100;
	int rectH = 30;
	//Pane Size
	int paneWH = 700;
	
	// Start Screen GUI Menu
	// Construct GUI Opjects
	Pane gamePane = new Pane();
	Scene gameScene = new Scene(gamePane, paneWH, paneWH);
	
	//Ball
	Ball gameBall = new Ball();
	
	//Players
	AI player1 = new AI(1);
	Player player2 = new Player(2);
	AI player3 = new AI(3);
	AI player4 = new AI(4);
	
	//Paddles
	Paddle paddle1 = player1.getPaddle();
	Paddle paddle2 = player2.getPaddle();
	Paddle paddle3 = player3.getPaddle();
	Paddle paddle4 = player4.getPaddle();
	
	

	
	public GameScreen() {
		
		//Make the Rectangles that make up the corners of the game screen
		Rectangle rect1 = new Rectangle(rectW, rectH); //650,0
		Rectangle rect2 = new Rectangle(rectW, rectH); //10, 0
		Rectangle rect3 = new Rectangle(rectH, rectW); //0, 10 
		Rectangle rect4 = new Rectangle(rectH, rectW); //0, 650
		Rectangle rect5 = new Rectangle(rectW, rectH); //10, 650
		Rectangle rect6 = new Rectangle(rectW, rectH); //650, 690
		Rectangle rect7 = new Rectangle(rectH, rectW); //690, 650
		Rectangle rect8 = new Rectangle(rectH, rectW); //690, 10
		
		//Get the rectangles and add them to the screen
		gamePane.getChildren().addAll(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8);
		
		
		//Set the location of all the rectangles to their respective corners
		rect1.setX(paneWH-rectW); 	rect1.setY(0);
		rect2.setX(0); 				rect2.setY(0);
		rect3.setX(0); 				rect3.setY(0);
		rect4.setX(0); 				rect4.setY(paneWH-rectW);
		rect5.setX(0); 				rect5.setY(paneWH-rectH);
		rect6.setX(paneWH-rectW); 	rect6.setY(paneWH-rectH);
		rect7.setX(paneWH-rectH); 	rect7.setY(paneWH-rectW);
		rect8.setX(paneWH-rectH); 	rect8.setY(0);
		
		
		//----------------------------
		//Add shapes		
		gamePane.getChildren().addAll(paddle1.getPaddle(), paddle2.getPaddle(),
									  paddle3.getPaddle(), paddle4.getPaddle(), gameBall.getBall());
		
		//Moves the paddles, can only used one at a time
		//movePaddleOnKeyPress(gameScene, paddle1);
		movePaddleOnKeyPress(gameScene, paddle2);
		//movePaddleOnKeyPress(gameScene, paddle3);
		//movePaddleOnKeyPress(gameScene, paddle4);
		
}
	
	//Method to test paddle movement
	private void movePaddleOnKeyPress(Scene scene, Paddle paddle) {
	    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	      public void handle(KeyEvent event) { //@Override
	    	  
	    	  //Sets up the paddle movement for players 1 and 3 on the sides
	    	  if ((paddle.getPos() == 1) || (paddle.getPos() == 3)) {
	    		boolean minY = paddle.getPaddle().getY() >= rectW;
	    	  	boolean maxY = paddle.getPaddle().getY() <=  paneWH - rectW - 150;
	    	  	switch (event.getCode()) {
	    	  		case UP:  	if (minY) paddle.paddleMove(-1);
	    		  				else paddle.paddleMove(1); break;
	    	  		case DOWN: 	if(maxY) paddle.paddleMove(1); 
	    		  			 	else paddle.paddleMove(-1); break;
	    	  	}
	    	  }//if
	    	  
	    	  //Sets up the paddle movement for players 2 and 4
	    	  else {
	    		  boolean minX = paddle.getPaddle().getX() >= rectW;
		    	  boolean maxX = paddle.getPaddle().getX() <=  paneWH - rectW - 150;
		    	  switch (event.getCode()) {
		    		  case LEFT:  if (minX) paddle.paddleMove(-1);
		    		  			  else paddle.paddleMove(1); break;
		    		  case RIGHT: if(maxX) paddle.paddleMove(1); 
		    		  			  else paddle.paddleMove(-1); break;
		    	  }
	    	  }//else  
	      }
	    });
	  }
	
	//Changes the direction of the ball if there is contact
	private void checkReverse() {
		int x = gameBall.getXLoc();
		int y = gameBall.getYLoc();
		
		//Corner Hits
		if (x > (paneWH - rectH - 20) 
				&& (y > (paneWH - rectW - 20) || y < 100 ) 
				&& gameBall.getXSpeed() > 0) //right side
			gameBall.reverseX();
		if (x < rectH
				&& (y > (paneWH - rectW - 20) || y < 100 ) 
				&& gameBall.getXSpeed() < 0) //left side
			gameBall.reverseX();
		if (y > (paneWH - rectH - 20)
				&& (x > (paneWH - rectW - 20) || x < 100 )
				&& gameBall.getYSpeed() > 0) //bottom
			gameBall.reverseY();
		if (y < rectH
				&& (x > (paneWH - rectW - 20) || x < 100 )
				&& gameBall.getYSpeed() < 0) //top
			gameBall.reverseY();
		
		//Life Lost if Ball Passes Player's Paddle
		if (x < 0 && gameBall.getXSpeed() < 0) {
			gameBall.reverseX();
			player1.scoredOn();
			System.out.println("Player 1 Was Scored On");
		}
		if (y > (paneWH - 20) && gameBall.getYSpeed() > 0) {
			gameBall.reverseY();
			player2.scoredOn();
			System.out.println("Player 2 Was Scored On");
		}
		if (x > (paneWH - 20) && gameBall.getXSpeed() > 0) {
			gameBall.reverseX();
			player3.scoredOn();
			System.out.println("Player 3 Was Scored On");
		}
		if (y < 0 && gameBall.getYSpeed() < 0) {
			gameBall.reverseY();
			player4.scoredOn();
			System.out.println("Player 4 Was Scored On");
		}
		
		//Paddle Hits
		checkCollisionWith1();
		checkCollisionWith2();
		checkCollisionWith3();
		checkCollisionWith4();
	}
	
	
	// ------The following check the collision with the players paddles--------------
	
	private void checkCollisionWith1() {
		if ( (gameBall.getYLoc() + 20 > paddle1.getPaddle().getY())
				&& (gameBall.getYLoc() < paddle1.getPaddle().getY() + paddle1.getLength()) )
			if ( gameBall.getXLoc() < (rectH) &&
					(gameBall.getXSpeed() < 0) ) {
				gameBall.reverseX();
				System.out.println("1 hit");
			}
	}
	
	private void checkCollisionWith2() {
		if ( (gameBall.getXLoc() + 20 > paddle2.getPaddle().getX()) 
				&& (gameBall.getXLoc() < paddle2.getPaddle().getX() + paddle2.getLength()) )
			if ( (gameBall.getYLoc() > (paneWH - (20 + rectH))) &&
					(gameBall.getYSpeed() > 0) ) {
				gameBall.reverseY();
				System.out.println("2 hit");
			}
	}
	
	private void checkCollisionWith3() {
		if ( (gameBall.getYLoc() + 20 > paddle3.getPaddle().getY()) 
				&& (gameBall.getYLoc() < paddle3.getPaddle().getY() + paddle3.getLength()) )
			if ( gameBall.getXLoc() > (paneWH - (20 + rectH)) &&
					(gameBall.getXSpeed() > 0) ) {
				gameBall.reverseX();
				System.out.println("3 hit");
			}
	}
	
	private void checkCollisionWith4() {
		if ( (gameBall.getXLoc() + 20 > paddle4.getPaddle().getX())
				&& (gameBall.getXLoc() < paddle4.getPaddle().getX() + paddle4.getLength()) )
			if ( gameBall.getYLoc() < (rectH) &&
					(gameBall.getYSpeed() < 0) ) {
				gameBall.reverseY();
				System.out.println("4 hit");
			}
	}
	
	
	//Continuously update the game screen to keep the ball moving
	public void continuousUpdate() {
		Task task = new Task<Void>() {
			  @Override
			  public Void call() throws Exception {
			    while (true) {
			      Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			          gameBall.moveBall();
			          checkReverse();
			          player1.moveAI(gameBall);
			          player3.moveAI(gameBall);
			          player4.moveAI(gameBall);
			        }
			      });
			      Thread.sleep(50);
			    }
			  }
			};
			Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
	}
	
	public Scene getGameScene() {
		return this.gameScene;
	}
}
