package ponggame;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import sun.audio.*;

public class Main extends JFrame {
    
    //Double buffering
    Image dbImage;
    Graphics dbg;
    
    //Ball objects
    static Ball b = new Ball(193, 143);
    //End
    boolean gameOver;
    
    //Background
    Image bg;
    
    //Variables for screen size
    int
    GWIDTH = 400,
    GHEIGHT = 300;
    
    //Dimension of GWIDTH*GHEIGHT
    Dimension screenSize = new Dimension(GWIDTH, GHEIGHT);
    

    //Create constructor to spawn window
    public Main(){
      
        this.setTitle("Pong Game");
        this.setSize(screenSize);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setBackground(Color.DARK_GRAY);
        this.addKeyListener(new AL());
        //Background Image
        ImageIcon i = new ImageIcon("C:/Users/School/Documents/NetBeansProjects/PongGame/src/ponggame/Tennis.gif/");
        bg = i.getImage();
        
    }
    
    public static void main(String[] args){
    
        
        //Create and start threads
        Thread ball = new Thread(b);
        ball.start();
        Thread p2 = new Thread(b.p2);
        p2.start();

    }

    //Paint To JFrame
    @Override
    public void paint(Graphics g){
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        draw(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }
    public void draw(Graphics g){
        //Scores, Background, and Paddle
        g.drawImage(bg, 0, 0, this);
        b.p2.draw(g);
        b.draw(g);

        //Player Scores Background
        g.setColor(Color.WHITE);
        g.fillRect(356, 258, 15, 15);
        g.fillRect(37, 258, 15, 15);
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(180, 35, 45, 20);
       //Display Title
        g.setColor(Color.RED);
         Graphics2D g2d = (Graphics2D)g;
         Font font = new Font("Arial", Font.PLAIN, 18);
         g2d.setFont(font);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g2d.drawString("Pong", 181, 50);

         //Display Score  
         Font font1 = new Font("Arial", Font.BOLD, 14);
        g2d.setFont(font1);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawString(""+b.p1Score, 41, 270);
        g2d.drawString(""+b.p2Score, 360, 270);
        g2d.drawString("YOU", 350, 295);
        g2d.drawString("Computer", 14, 295);
        
        repaint();
        
    }
    ////////EVENT LISTENER CLASS/////////
    public class AL extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            b.p2.keyPressed(e);
        }
        @Override
        public void keyReleased(KeyEvent e){
            b.p2.keyReleased(e);
        }
    }
    ///////END EVENT LISTENER CLASS/////
}

