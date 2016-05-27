package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import core.PowerUp;

import gameView.GameView;
import gameView.PowerupView;

/**
 * Created by Hannes on 04/05/2016.
 */
public class PowerUpController extends RigidBodyControl {

    private final GameView gameView;
    private PowerupView powerUpView;
    private PowerUp powerUp;
    private boolean hasCollided = false;

    /** initialize a PowerUpController to create and init a power up*/
    public PowerUpController(PowerUp powerUp, GameView gameView, PowerupView powerUpView){
        this.gameView = gameView;
        this.powerUpView = powerUpView;
        this.powerUp = powerUp;
    }



    /** Check for collision, if it happens, give player the correct power up */
    private void collisionCheck(){
        CollisionResults results = new CollisionResults();
        gameView.getPlayer2Node().collideWith(spatial.getWorldBound(), results);
        if (results.size() > 0){
            if (hasCollided==false){
                //add power up effect to player 2
                powerUp.setEffect(gameView.getWorld().getPlayer2());
               gameView.getPlayer2View().setHealthBar(gameView.getWorld().getPlayer2().getHealth());
                gameView.getPlayer2View().playPowerUpSound();
                spatial.removeFromParent();
                results.clear();
                hasCollided=true;
                if (powerUpView.getPowerUpList().size()>0){
                    powerUpView.getPowerUpList().remove(0);
                }


            }

        }
        gameView.getPlayer1Node().collideWith(spatial.getWorldBound(), results);
        if (results.size()>0){
            if (hasCollided==false){
                //add power up effect to player 1
                powerUp.setEffect(gameView.getWorld().getPlayer1());
                gameView.getPlayer1View().setHealthBar(gameView.getWorld().getPlayer1().getHealth());
                gameView.getPlayer1View().playPowerUpSound();
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
