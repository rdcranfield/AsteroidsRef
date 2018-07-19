package Asteroids;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 28/05/13
 * Time: 16:10
 */
public class GameView extends JComponent {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3702290349970507616L;
	AsteroidsAIGame game;
    Image im = Constants.MILKYWAY1;
    AffineTransform bgTransf;
    public static double SCALE = 1.5;
    //public ArrayList<GameObject> objects;
    public GameView(AsteroidsAIGame game){
        this.game = game;
        double imWidth = im.getWidth(null);
        double imHeight = im.getHeight(null);
        double stretchx = (imWidth > Constants.FRAME_WIDTH? 1 : Constants.FRAME_WIDTH/imWidth);
        double stretchy = (imHeight > Constants.FRAME_HEIGHT? 1 : Constants.FRAME_WIDTH/imHeight);
        bgTransf = new AffineTransform();
        bgTransf.scale(stretchx, stretchy);
       // objects = new ArrayList<GameObject>();
        // insert asteroids into object array of GameObject, via
        // Initial asteroid amount (useful for different shapes later i.e ship
        //for(int i = 0; i<N_INITIAL_ASTEROIDS; i++){
           // game.objects.add(game.makeAsteroid());
        //}
        //game.objects.add(game.getShip());
    }

    @Override
    public void paintComponent(Graphics g0) {

        Graphics2D g =
                (Graphics2D) g0;


        // paint the background

        g.setColor(Constants.BG_COLOR);
        g.fillRect(0, 0 ,getWidth(), getHeight());
        g.drawImage(im, bgTransf, null);




        //synchronized (this){
            synchronized (game)  {
                for(GameObject o : game.getGameObjects()){
               // game.ship.draw(g);
                //game.getLevel();

                    o.draw(g);
                    o.update();


            }


        if(!(game.getLife()==0)){

            g.setColor(Color.white);
            g.scale(SCALE, SCALE);
            g.drawString("LIFE " + game.getLife(), 9, 10);

            g.drawString("SCORE" + " " + " " + game.getScore(), 135, 10);
            g.drawString("LEVEL" + " " + " " + game.getLevel(), 230, 10 );
        }
            else if(game.getLife() == 0){
//            game.updateParticles();
                g.setColor(Color.white);
                g.scale(SCALE, SCALE);
                g.drawRect( 0, 20, 270, 55) ;

                g.setColor(Color.LIGHT_GRAY);
               g.drawRect( 2, 18, 270, 55) ;
                g.setColor(Color.GRAY);
                g.drawRect( 4, 16, 270, 55) ;
                g.setColor(Color.DARK_GRAY);
                g.drawRect( 6, 14, 270, 55) ;
                //g.fillRect( 0, 20, 250, 50);

                g.setColor(Color.white);
                g.drawString("GAME OVER! Your score was " + game.getScore() , 50, 50);

        }
            }
        repaint();
        }



    @Override
    public Dimension getPreferredSize(){
        return Constants.FRAME_SIZE;
    }



}
