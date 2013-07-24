package ponggame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.Random;

public class Ball implements Runnable {
    //Game Over
    boolean gameOver = false;
    boolean Exit = false;
    boolean go = false;
    
    //restart
    boolean restart = false;
    boolean p1scored = false;
    boolean p2scored = false;

    //Global variables
    int x, y, xDirection, yDirection, x1, y1, AIyDirection;
    
    //Score
    int p1Score, p2Score;
    //Paddle 1 and ball
    Paddles p2 = new Paddles(370, 140, 2);
    Main m = new Main();
    Rectangle ball, AI;
    
    public Ball(int x, int y){
        //Score
        p1Score = p2Score = 0;
        //Move Ball
        this.x = x;
        this.y = y;
        //Set ball moving randomly
        Random r = new Random();
        int rDir = r.nextInt(1);
        if(rDir == 0){
            rDir++;
        setXDirection(rDir);
        }
        int yrDir = r.nextInt(1);
        if(yrDir == 0){
            yrDir--;
        setYDirection(yrDir);
        }
        //Create 'ball'
        ball = new Rectangle(this.x, this.y, 7, 7);
        AI = new Rectangle(25, this.y1, 10, 50);
    }
    
    public void setXDirection(int xdir){
        xDirection = xdir;
    }
    public void setYDirection(int ydir){
        yDirection = ydir;
    }
    public void setAIYDirection(int AIydir){
        AIyDirection = AIydir;
    }
    public void GameOver(){
  
        System.exit(1);
    }
    public void ThreadExit(){
        if(go){
        try{
            Thread.sleep(3000);
            GameOver();           
        }
        catch(Exception e){
            
        }
        }
    }
    public void ThreadRestart(){
        if(restart){
        try{
            Thread.sleep(1000);
            restart = false;
            p2scored = false;
            p1scored = false;

        }
        catch(Exception e){
            
        }
        }
    }
    //Draw Images
    public void draw(Graphics g){
        g.setColor(Color.YELLOW);
        g.fillRect(ball.x, ball.y, ball.width, ball.height);
        g.setColor(Color.BLACK);
        g.fillRect(AI.x, AI.y, AI.width, AI.height);
        if(p1scored){
            Graphics2D g3d = (Graphics2D)g;
            Font font = new Font("Arial", Font.BOLD, 30);
            g3d.setFont(font);
            g3d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g3d.setColor(Color.WHITE);
            g3d.fillRect(117, 105, 170, 30);
            g3d.setColor(Color.RED);
            g3d.drawString("You Score!", 120, 130);

        }
        if(p2scored){
            Graphics2D g3d = (Graphics2D)g;
            Font font = new Font("Arial", Font.BOLD, 30);
            g3d.setFont(font);
            g3d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g3d.setColor(Color.WHITE);
            g3d.fillRect(95, 105, 240, 30);
            g3d.setColor(Color.RED);
            g3d.drawString("Computer Score!", 95, 130);

        }
        if(Exit){
            ThreadExit();
            if(p2Score == 6){
            g.drawImage(m.bg, 0, 0, m);           
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D)g;
            Font font = new Font("Arial", Font.BOLD, 30);
            g2d.setFont(font);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawString("Game Over", 130, 130);
            Graphics2D g3d = (Graphics2D)g;
            Font font1 = new Font("Arial", Font.BOLD, 25);
            g2d.setFont(font1);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawString("YOU WIN!", 143, 175);
            go = true;
            }
            if(p1Score == 6){
                
            g.drawImage(m.bg, 0, 0, m);           
            g.setColor(Color.RED);
            Graphics2D g2d = (Graphics2D)g;
            Font font = new Font("Arial", Font.BOLD, 30);
            g2d.setFont(font);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawString("Game Over", 130, 130);
            Graphics2D g3d = (Graphics2D)g;
            Font font1 = new Font("Arial", Font.BOLD, 25);
            g2d.setFont(font1);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawString("YOU LOSE", 137, 170);
              go = true;
                
            }  
            
        }

    }
    //AI Player Paddle
    public void findPathtoTarget(){
        
        if(ball.x <= 140){
        
            if(AI.y < ball.y){
            setAIYDirection(1);
            }
        
            if(AI.y > ball.y){
            setAIYDirection(-1);
            }
        }
        
        else{
            setAIYDirection(0);
        }
        
        if(ball.x >= 270){
             if(AI.y < ball.y){
                setAIYDirection(1);
            }
            if(AI.y > ball.y){
                setAIYDirection(-1);
            }
        }

    }
    //AI Paddle Edges
    public void detectEdges(){
        if(AI.y <= 25)
            AI.y = 25;
        if(AI.y >= 250)
            AI.y = 250;
    }
    
    public void collision(){
        if(ball.intersects(p2.paddle))
            setXDirection(-1);
        if(ball.intersects(AI))
            setXDirection(1);
    }
    
    public void move(){
        collision();
        detectEdges();
        //Set Directions
        ball.x += xDirection;
        ball.y += yDirection;
        AI.y += AIyDirection;
        
        //Bounce the ball when edge is detected
        if(ball.x <= 0){
            setXDirection(+1);
            p2Score++;
            p1scored = true;
            restart = true;
            ThreadRestart();
        }
        if(ball.x >= 385){
            setXDirection(-1);
            p1Score++;
            restart = true;
            p2scored = true;
            ThreadRestart();
        }
        if(ball.y <= 25)
            setYDirection(+1);
        
        if(ball.y >= 285)
            setYDirection(-1);
    }
    
    //GameOver    
    public void end(){           
        if(p1Score == 6|| p2Score == 6){
                gameOver = true;
            }
        else{
            gameOver = false;
        }
    }
    public void close(){
           Exit = true;
           
        }
      
        //Run  
    @Override
    public void run(){
        try{
            while(true){
                detectEdges();
                findPathtoTarget();
                move();
                Thread.sleep(7);
                ThreadExit();
                end();
                if(gameOver){
                    close();
                }
            }
        }catch(Exception e){System.err.println(e.getMessage());}
    }
    
}
