package view;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import core.PowerUp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Creates power ups
 */
public class PowerUpView {
    private final AssetManager assetManager;
    private final WorldView worldView;
    private static Timer timer = new Timer();
    private int activePowerUps;
    private int maxActivePowerUps = 5;
    private long powerUpCoolDownMS = 7000;
    private boolean readyToPlace = false;

    public PowerUpView(WorldView worldView){
        this.worldView = worldView;
        this.assetManager = worldView.getAssetManager();
        startTimer();
        readyToPlace = true;
    }

    public Geometry createPowerUp(Node targetNode, PowerUp powerUp, ColorRGBA colorRGBA){
        Box powerBox = new Box(1f, 1f, 1f);
        Geometry powerUpGeom = new Geometry("Power up", powerBox);
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        powerUpGeom.setMaterial(boxMaterial);
        powerUpGeom.setLocalTranslation(powerUp.getX(), -1, powerUp.getZ());
        boxMaterial.setColor("Color", colorRGBA);
        targetNode.attachChild(powerUpGeom);
        return powerUpGeom;
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (worldView.getPaused()) {
                return;
            } else {

                if (activePowerUps < maxActivePowerUps) {
                    readyToPlace = true;
                }
            }
        }
    };
    private void startTimer(){timer.scheduleAtFixedRate (task,powerUpCoolDownMS,powerUpCoolDownMS);}
    public void stopTimer(){timer.cancel();}
    public void incActivePowerUps() {this.activePowerUps+=1;}
    public void decActivePowerUps() {this.activePowerUps-=1;}
    public boolean isReadyToPlace() {return readyToPlace;}
    public void setPlaced() {this.readyToPlace = false;}
}
