package Asteroids;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 28/05/13
 * Time: 16:16
 */
public class Constants implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	// background colour
    public static final Color BG_COLOR = Color.black;



    public static final String[] SERVER_NAMES = {}; //{"localhost"} for multiplayer testing
    public static final Color[] SHIP_COLORS= {Color.cyan, Color.yellow,
            Color.green, Color.red};
    public static final int REGISTRY_PORT = 8899;
    // frame dimensions
    public static final int FRAME_HEIGHT = 480;
    public static final int FRAME_WIDTH = 640;

    public static final Dimension FRAME_SIZE =
            new Dimension(Constants.FRAME_WIDTH, Constants.FRAME_HEIGHT);

    // constants relating to frame rate
    public static final int DELAY = 20;
    public static final double DT = DELAY / 1000.0;
    public static final double DT2 = DELAY / 5000.0;

    // number of asteroids on 1st level
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static int astCleared = N_INITIAL_ASTEROIDS;



    public static int score = 0;
    public static int SAUCER_POINTS =100;
    public static float bulletLife = 35;
    public static float bulletLife2 = 30;
    public static float PBulletLife = 35;
    public static float currentBulletLife = 0;
    public static float currentBulletLife2 = 0;
    public static float currentPBulletLife = 0;
    public static float currentSpawnLimit = 0;
    public static float currentSpawnLimitSaucer = 0;
    public static int SHOT_DELAY = 0;
    public static int SHOT_DELAY2 = 0;
    public static int PSHOT_DELAY = 0;
    public static int SHOT_READY = 30;
    public static int SHOT_READY2 = 20;
    public static int PSHOT_READY = 150;
    public static int Mid = 0;
    public static int Sid = 1;
    public static int setDelay = 0;
    public static int setReady = 10000;
    public static boolean PBull;
    public static int LIFE = 3;
    public static String LIFESymbol = " {+} ";
    public static boolean gameGoing = true;
    public static boolean saucerHit;
    public static float saucerCount;
    public static boolean fullscreenmode;
    public static int Epn = 0;
    public static int Spn = 1;
    public static boolean astHit;
    public static boolean split;
    static Random RANDOM = new Random();


    public static int currentLevel = 1;


    // constants related to max velocity of asteroid
    public static final double VMAX = 10;

    public static int astSplitAst = 2;
    public static int astHitsToTake = 3;
    public static int EshipHitsToTakeS = 6;
    public static int Rr = 25;

    // value used to divide by DT in creation of bullet
    // to control ships bullet speed
    public static final double BMAX = 5;


    //ships max speed
    public static double SHIP_VMAX = 200;

    public static boolean thrusting;


    public static boolean SaveGame;
    public static final String SAVE_FILE = "newSaveGame.txt";


    public static Image ASTEROID1, MILKYWAY1;
    static {
        try {
            ASTEROID1 = ImageManager.loadImage("asteroid1");
            MILKYWAY1 = ImageManager.loadImage("milkyway1");
        } catch (IOException e) { System.exit(1); }
    }
}
