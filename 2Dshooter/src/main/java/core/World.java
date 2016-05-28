package core;

/**
 * Created by David on 2016-04-15.
 */
public class World {



    private final Player player1;
    private final Player player2;
    private final Environment terrain;
    private final CameraModel cameraData;
    private boolean shutDown;

    public World(int rocks, int trees, boolean dynamicCamEnabled){
        this.player1 = new Player();
        this.player2 = new Player();
        this.terrain = new Environment(rocks, trees);
        this.cameraData = new CameraModel(dynamicCamEnabled);
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


}
