package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import core.PowerUp;

import gameView.PowerupView;
import utils.ApplicationAssets;

/**
 * Created by Hannes on 04/05/2016.
 */
public class PowerUpController extends RigidBodyControl {

    private PowerupView powerUpView;
    private PowerUp powerUp;
    private ApplicationAssets applicationAssets;
    private boolean hasCollided = false;

    /** initialize a PowerUpController to create and init a power up*/
    public PowerUpController(PowerUp powerUp, ApplicationAssets applicationAssets, PowerupView powerupView){
        this.applicationAssets = applicationAssets;
        this.powerUpView = powerupView;
        this.powerUp = powerUp;
    }

    /** Check for collision, if it happens, give player the correct power up */
    private void collisionCheck(){
        CollisionResults results = new CollisionResults();
        powerUpView.getGameView().getPlayer2Node().collideWith(spatial.getWorldBound(), results);
        if (results.size() > 0){
            if (hasCollided==false){
                //add power up effect to player 2
                powerUp.setEffect(applicationAssets.getWorld().getPlayer2());
                applicationAssets.getGameView().getPlayer2View().setHealthBar(applicationAssets.getWorld().getPlayer2().getHealth());
                applicationAssets.getGameView().getPlayer2View().playPowerUpSound();
                spatial.removeFromParent();
                results.clear();
                hasCollided=true;
                if (powerUpView.getPowerUpList().size()>0){
                    powerUpView.getPowerUpList().remove(0);
                }


            }

        }
        powerUpView.getGameView().getPlayer1Node().collideWith(spatial.getWorldBound(), results);
        if (results.size()>0){
            if (hasCollided==false){
                //add power up effect to player 1
                powerUp.setEffect(applicationAssets.getWorld().getPlayer1());
                applicationAssets.getGameView().getPlayer1View().setHealthBar(applicationAssets.getWorld().getPlayer1().getHealth());
                applicationAssets.getGameView().getPlayer1View().playPowerUpSound();
                spatial.removeFromParent();
                results.clear();
                hasCollided=true;
                if (powerUpView.getPowerUpList().size()>0){
                    powerUpView.getPowerUpList().remove(0);
                }

            }

        }
    }






    public void update(float tpf){
        super.update(tpf);
        collisionCheck();

    }
}
