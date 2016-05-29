package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import core.*;
import view.PowerUpView;
import view.WorldView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hannes on 04/05/2016.
 */
public class PowerUpController extends RigidBodyControl {

    private final WorldView worldView;
    private final World world;
    private static Timer timer = new Timer();
    private final Node targetNode;
    private boolean readyToPlace = true;
    private final int maxActivePowerUps = 5;
    private final int nrOfPowerUpTypes = 3;
    private final long powerUpCoolDownMS = 7000;
    private int placeOrder = 1;
    private int activePowerUps;
    private PowerUpView powerUpView;

    /** initialize a PowerUpController to create and inits power ups*/
    public PowerUpController(Node targetNode, WorldView worldView){
        this.worldView = worldView;
        this.world = worldView.getWorld();
        this.targetNode = targetNode;
        this.powerUpView = worldView.getPowerUpView();
        startTimer();
        setReadyToPlace(true);
        targetNode.addControl(this);
    }

    public void update(float tpf){
        placeOrder = FastMath.nextRandomInt(1,nrOfPowerUpTypes);
        Geometry powerUpGeom;
        PowerUp powerUp;
        if(readyToPlace){
            if(placeOrder==1) {
                powerUpGeom = powerUpView.createPowerUp(targetNode, powerUp = new HealthPowerUp(worldView.getWorld().getTerrain()), ColorRGBA.Red);
                new CollisionController(worldView,powerUpGeom,powerUp,this);
            }
            if(placeOrder==2) {
                powerUpGeom = powerUpView.createPowerUp(targetNode, powerUp = new WeaponPowerUp((worldView.getWorld().getTerrain())), ColorRGBA.Blue);
                new CollisionController(worldView,powerUpGeom,powerUp,this);
            }
            if(placeOrder==3) {
                powerUpGeom = powerUpView.createPowerUp(targetNode, powerUp = new SpeedPowerUp(worldView.getWorld().getTerrain()), ColorRGBA.Blue);
                new CollisionController(worldView,powerUpGeom,powerUp,this);
            }
            incActivePowerUps();
            readyToPlace = false;
        }
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            if (world.isShutDown()){
                timer.cancel();
            }
            if (worldView.getPaused()) {
                return;
            } else {
                if (activePowerUps < maxActivePowerUps) {
                    setReadyToPlace(true);
                }
            }
        }
    };

    private void startTimer(){
        timer.scheduleAtFixedRate(task,powerUpCoolDownMS,powerUpCoolDownMS);
    }
    public void incActivePowerUps() {
        this.activePowerUps+=1;
    }
    public void decActivePowerUps() {
        this.activePowerUps-=1;
    }
    public void setReadyToPlace(boolean readyToPlace) {
        this.readyToPlace = readyToPlace;
    }

}