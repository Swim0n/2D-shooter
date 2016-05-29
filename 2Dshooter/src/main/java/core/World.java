package core;

import com.jme3.asset.AssetManager;
import utils.ApplicationAssets;

/**
 * Created by David on 2016-04-15.
 */
public class World {



    private final Player player1;
    private final Player player2;
    private final Environment terrain;
    private final CameraModel cameraData;
    private boolean isRunning;

    public World(int rocks, int trees, boolean dynamicCamEnabled, AssetManager assetManager){
        this.player1 = new Player();
        this.player2 = new Player();
        this.terrain = new Environment(rocks, trees, assetManager);
        this.cameraData = new CameraModel(dynamicCamEnabled);
    }

    public Player getPlayer1() {return player1;}
    public Player getPlayer2() {return player2;}
    public Environment getTerrain(){return terrain;}
    public CameraModel getCameraData() {return cameraData;}

    public boolean getIsRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
