package game.ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;
import game.core.PowerUp;
import game.gameView.GameView;
import game.gameView.PowerupView;

/**
 * Created by Hannes on 04/05/2016.
 */
public class PowerUpController extends RigidBodyControl {

    private PowerupView powerupView;
    private PowerUp powerUp;
    private GameView gameView;

    public PowerUpController(PowerupView powerupView, PowerUp powerUp, GameView gameView){
        this.powerupView = powerupView;
        this.powerUp = powerUp;
        this.gameView = gameView;
    }

    private void removeSpatial(Spatial spatial){
        spatial.removeFromParent();
    }

    /** Check for collision, if it happens, give player the correct power up */
    private void collisionCheck(){
        CollisionResults results = new CollisionResults();


            powerupView.getGameView().getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            if (results.size() > 0){
                removeSpatial(spatial);
                results.clear();
                //add power up effect to player 2
                powerUp.setEffect(gameView.getWorld().getPlayer2());
                System.out.println("hej");


            }

            powerupView.getGameView().getPlayer1Node().collideWith(spatial.getWorldBound(), results);
            if (results.size()>0){
                removeSpatial(spatial);
                results.clear();
                //add power up effect to player 1
                powerUp.setEffect(gameView.getWorld().getPlayer1());
                System.out.println("Okej");

            }



    }

    public void update(float tpf){
        super.update(tpf);
        collisionCheck();

    }
}
