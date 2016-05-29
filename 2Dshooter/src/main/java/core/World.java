package core;

/**
 * Created by David on 2016-04-15.
 */
public class World {



    private final Player player1;
    private final Player player2;
    private final Environment terrain;
    private final CameraModel cameraData;
    private float height;
    private float width;
    private boolean paused = true;
    private boolean deathMatch = true;
    private boolean ai = false;
    private boolean inGame;
    private boolean shutDown;


    public World(int rocks, int trees, boolean dynamicCamEnabled){
        this.player1 = new Player();
        this.player2 = new Player();
        this.terrain = new Environment(rocks, trees);
        this.cameraData = new CameraModel(dynamicCamEnabled);
        this.width = 71f;
        this.height = 53f;
    }

    public Player getPlayer1() {return player1;}
    public Player getPlayer2() {return player2;}
    public Environment getTerrain(){return terrain;}
    public CameraModel getCameraData() {return cameraData;}
    public void setGameOver(){
        player1.reset();
        player2.reset();
    }

    public void setShutDown() {
        shutDown = true;
    }

    public boolean isShutDown() {
        return shutDown;
    }

    public float getHeight(){
        return this.height;
    }
    public float getWidth(){
        return this.width;
    }
    public boolean isInGame() {
        return inGame;
    }
    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    public void setAI(boolean state){
        this.ai = state;
    }
    public boolean isAI(){
        return this.ai;
    }
    public boolean isDeathMatch() {
        return deathMatch;
    }
    public void setDeathMatch(boolean deathMatch) {
        this.deathMatch = deathMatch;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
