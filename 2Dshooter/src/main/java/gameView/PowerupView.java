package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import core.HealthPowerUp;
import core.PowerUp;
import core.WeaponPowerUp;
import ctrl.PowerUpController;
import core.SpeedPowerUp;
import utils.ApplicationAssets;
import java.util.*;
import java.util.Timer;


public class PowerupView {

    private Box powerBox;
    private Geometry powerupGeom;
    private Material boxMaterial;

    private AssetManager assetManager;
    private Node terrainNode;

    private Quad groundShape;
    private GameView gameView;
    private ApplicationAssets appAssets;

    private PowerUpController powerUpPhy;
    private static Timer timer = new Timer();


    private final static List<PowerupView> POWER_UP_VIEW_LIST = new ArrayList<PowerupView>();

    /** initializes two power ups and then spawn two new every 15 seconds*/
    public PowerupView(ApplicationAssets appAssets){
        this.appAssets = appAssets;
        this.assetManager = appAssets.getAssetManager();
        this.terrainNode = appAssets.getTerrainNode();
        this.groundShape =  appAssets.getGameView().getGroundSize();
        this.gameView = appAssets.getGameView();
        powerBox = new Box(1f,1f,1f);
        powerupGeom = new Geometry("Power up", powerBox);


        createPowerUp(new HealthPowerUp(appAssets));
        createPowerUp(new SpeedPowerUp(appAssets));
        createPowerUp(new WeaponPowerUp(appAssets));
        startTimer();
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (gameView.getPaused()){
            }else{
                if (POWER_UP_VIEW_LIST.size()<6){
                    createPowerUp(new HealthPowerUp(appAssets));
                    createPowerUp(new SpeedPowerUp(appAssets));
                    createPowerUp(new WeaponPowerUp(appAssets));
                }
            }
        }
    };

    private void startTimer(){
        timer.scheduleAtFixedRate(task,15000,15000);

    }

    public void stopTimer(){
        timer.cancel();
    }

    /**creates a power up at random position. Have to set type of power up.*/
    public void createPowerUp(PowerUp powerUpType){
        powerupGeom = new Geometry("Box", powerBox);
        boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        setType(powerUpType);
        powerupGeom.setMaterial(boxMaterial);
        powerupGeom.setLocalTranslation(powerUpType.getX(), -1, powerUpType.getZ());

        terrainNode.attachChild(powerupGeom);
        powerUpPhy = new PowerUpController(powerUpType,appAssets,this);
        powerupGeom.addControl(powerUpPhy);
        POWER_UP_VIEW_LIST.add(this);
    }

    private void setType(PowerUp powerUp){
        if (powerUp instanceof HealthPowerUp){

            boxMaterial.setColor("Color", ColorRGBA.Yellow);
        }
        if (powerUp instanceof SpeedPowerUp){
           boxMaterial.setColor("Color", ColorRGBA.Cyan);
        }
        if (powerUp instanceof WeaponPowerUp){
            boxMaterial.setColor("Color", ColorRGBA.Black);
        }
    }

    public List getPowerUpList(){
        return this.POWER_UP_VIEW_LIST;
    }

    public GameView getGameView(){
        return this.gameView;
    }
}
