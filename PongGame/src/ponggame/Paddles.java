package ponggame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Paddles implements Runnable {
    
    int x, y, yDirection, id;
    
    Rectangle paddle;
    
    public Paddles(int x, int y, int id){
        this.x = x;
        this.y = y;
        this.id = id;
        paddle = new Rectangle(x, y, 10, 50);
    }

  //Moves Paddles  
    public void keyPressed(KeyEvent e){


                if(e.getKeyCode() == e.VK_UP){
                    setYDirection(-1);
                }
                if(e.getKeyCode() == e.VK_DOWN){
                    setYDirection(+1);
                }

        }
    
    public void keyReleased(KeyEvent e){

                if(e.getKeyCode() == e.VK_W){
                    setYDirection(0);
                }
                if(e.getKeyCode() == e.VK_S){
                    setYDirection(0);
                }
                if(e.getKeyCode() == e.VK_UP){
                    setYDirection(0);
                }
                if(e.getKeyCode() == e.VK_DOWN){
                    setYDirection(0);
                }
            
        }
    
    public void setYDirection(int ydir){
        yDirection = ydir;
    }
   //Stop at Edges 
    public void move(){
        paddle.y += yDirection;
        if(paddle.y <= 25)
            paddle.y = 25;
        if(paddle.y >= 250)
            paddle.y = 250;
    }
    //Draw Paddle
    public void draw(Graphics g){
        g.setColor(Color.PINK);
        g.fillRect(paddle.x, paddle.y, paddle.width, paddle.height);
    }

    @Override
    public void run() {
        try{
            while(true){
                move();
                Thread.sleep(8);
            }
        }catch(Exception e){System.err.println(e.getMessage());}
    }
    
}