package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import core.*;
import gameView.GameView;
import gameView.PowerUpView;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hannes on 04/05/2016.
 */
public class PowerUpController extends RigidBodyControl {

    private final GameView gameView;
    private final World world;
    private static Timer timer = new Timer();
    private final Node targetNode;
    private boolean readyToPlace = true;
    private final int maxActivePowerUps = 5;
    private final int nrOfPowerUpTypes = 3;
    private final long powerUpCoolDownMS = 7000;
    private int placeOrder = 1;
    private int activePowerUps;

    /** initialize a PowerUpController to create and inits power ups*/
    public PowerUpController(Node targetNode, GameView gameView){
        this.gameView = gameView;
        this.world = gameView.getWorld();
        this.targetNode = targetNode;
        startTimer();
        setReadyToPlace(true);
        targetNode.addControl(this);
    }

    public void update(float tpf){
        placeOrder = FastMath.nextRandomInt(1,nrOfPowerUpTypes);
        if(readyToPlace){
            if(placeOrder==1) {
                PowerUpView healthPowerUp = new PowerUpView(targetNode,gameView, new HealthPowerUp(gameView.getWorld().getTerrain()), ColorRGBA.Red);
                new CollisionController(gameView,healthPowerUp,this);
            }
            if(placeOrder==2) {
                PowerUpView weaponPowerUp = new PowerUpView(targetNode,gameView, new WeaponPowerUp(gameView.getWorld().getTerrain()), ColorRGBA.Black);
                new CollisionController(gameView,weaponPowerUp,this);
            }
            if(placeOrder==3) {
                PowerUpView speedPowerUp = new PowerUpView(targetNode,gameView, new SpeedPowerUp(gameView.getWorld().getTerrain()), ColorRGBA.Blue);
                new CollisionController(gameView,speedPowerUp,this);
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
            if (gameView.getPaused()) {
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
    @Override
    public void write(JmeExporter jmeExporter) throws IOException {}
    @Override
    public void read(JmeImporter jmeImporter) throws IOException {}
    @Override
    public Control cloneForSpatial(Spatial spatial) {return null;}
    @Override
    public void setSpatial(Spatial spatial) {}
    @Override
    public void render(RenderManager renderManager, ViewPort viewPort) {}


}