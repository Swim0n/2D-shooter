package game.ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;
import game.core.HealthPowerUp;
import game.core.PowerUp;
import game.core.SpeedPowerUp;
import game.gameView.GameView;
import game.gameView.PowerupView;
import game.utils.ApplicationAssets;

/**
 * Created by Hannes on 04/05/2016.
 */
public class PowerUpController extends RigidBodyControl {

    private PowerupView powerUpView;
    private PowerUp powerUp;
    private GameView gameView;
    private ApplicationAssets applicationAssets;
    private int x;
    private int z;

    /** initialize a PowerUpController to create and init a power up*/
    public PowerUpController(PowerUp powerUp, ApplicationAssets applicationAssets, PowerupView powerupView){
        this.applicationAssets = applicationAssets;
        this.powerUpView = powerupView;
        this.powerUp = powerUp;
        //this.gameView = appAssets.getGameView();
        createPowerUp(powerUp);
        addPhyControl();


    }

    /** Give location information to power up view*/
    private void createPowerUp(PowerUp powerUp){
        x = powerUp.getX();
        z = powerUp.getZ();
        powerUpView.createPowerUp(powerUp,x,z);
    }

    private void addPhyControl(){
        powerUpView.getPowerupGeom().addControl(this);
    }



    /** Check for collision, if it happens, give player the correct power up */
    private void collisionCheck(){
        CollisionResults results = new CollisionResults();
        powerUpView.getGameView().getPlayer2Node().collideWith(spatial.getWorldBound(), results);
        if (results.size() > 0){
            spatial.removeFromParent();
            results.clear();
            //add power up effect to player 2
            powerUp.setEffect(applicationAssets.getWorld().getPlayer2());
            //applicationAssets.getGameView().updateGUI();

        }
        powerUpView.getGameView().getPlayer1Node().collideWith(spatial.getWorldBound(), results);
        if (results.size()>0){
            spatial.removeFromParent();
            results.clear();
            //add power up effect to player 1
            powerUp.setEffect(applicationAssets.getWorld().getPlayer1());
            //applicationAssets.getGameView().updateGUI();
        }
    }




    public void update(float tpf){
        super.update(tpf);
        collisionCheck();

    }
}
