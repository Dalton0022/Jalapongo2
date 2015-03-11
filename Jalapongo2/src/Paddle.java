import javafx.scene.paint.Color;
import javafx.scene.shape.*;

//Added by Leslie
/**
 * @author Nick
 * @version 1.0
 * @created 02-Mar-2015 3:19:45 PM
 */
public class Paddle {

	private Rectangle paddle;
	//private Line Paddle;
	private int PaddleLocX;
	private int PaddleLocY;
	private int pos;
	private int speed = 5;

	public Paddle(int pos){
		if(pos == 1){ //left
			PaddleLocX = 0;
			paddle = new Rectangle(PaddleLocX,325,30,150); //Rectangle(x, y, width, height)
			this.pos = pos;
			paddle.setFill(Color.BLUE);
		}
		if(pos == 2){ //bottom
			PaddleLocY = 670;
			paddle = new Rectangle(325,PaddleLocY,150,30);
			this.pos = pos;
			paddle.setFill(Color.GREEN);
		}
		if(pos == 3){ //right
			PaddleLocX = 670;
			paddle = new Rectangle(PaddleLocX,325,30,150);
			this.pos = pos;
			paddle.setFill(Color.RED);
		}
		if(pos == 4){ //top
			PaddleLocY = 0;
			paddle = new Rectangle(325,PaddleLocY,150,30);
			this.pos = pos;
			paddle.setFill(Color.YELLOW);
		}	
	}

	public int getLength(){
		int length;
		if(pos == 1 || pos == 3){
			length = (int)(paddle.getHeight());
		}else{
			length = (int)(paddle.getWidth());
		}
		return length;
	}

	public void setLength(int length){
		if(pos == 1 || pos == 3){
			paddle.setHeight((double)length);
		}
		else{
			paddle.setWidth((double)length);
		}
	}

	public void paddleMove(int direction){

		int min = GameScreen.min();
		int max = GameScreen.max() - getLength();
		
		if(pos == 1 || pos == 3) {
			paddle.setY(paddle.getY() + direction*speed);
			if (paddle.getY() > max)
				paddle.setY(max);
			else if (paddle.getY() < min)
				paddle.setY(min);
		}
		else {
			paddle.setX(paddle.getX() + direction*speed);
			if (paddle.getX() > max)
				paddle.setX(max);
			else if (paddle.getX() < min)
				paddle.setX(min);
			
		}
		
	}
	
	public int getPos() {
		return pos;
	}
	
	public Rectangle getPaddle() {
		return paddle;
	}
}//end Paddle
