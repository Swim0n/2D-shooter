package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;
import gameView.GameView;
import gameView.PlayerView;
import gameView.PowerUpView;

/**
 * Created by David on 2016-05-28.
 */
public class CollisionController extends RigidBodyControl {
    private final GameView gameView;
    private final Spatial target;
    private final PowerUpView powerUpView;
    private final PowerUpController powerUpController;

    public CollisionController(GameView gameView, PowerUpView powerUpView, PowerUpController powerUpController){
        this.gameView = gameView;
        this.powerUpView = powerUpView;
        this.powerUpController = powerUpController;
        this.target = powerUpView.getPowerUpSpatial();
        target.addControl(this);
    }
    public void update(float tpf){
        collisionCheck();
    }
    private void collisionCheck(){
        CollisionResults results = new CollisionResults();

            gameView.getPlayer1Node().collideWith(spatial.getWorldBound(), results);
            checkResult(powerUpView, results, gameView.getPlayer1View());

            gameView.getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            checkResult(powerUpView, results, gameView.getPlayer2View());
    }

    private void checkResult(PowerUpView powerUpView, CollisionResults results, PlayerView playerView){
        if (results.size() > 0){
            if (!powerUpView.hasCollided()){
                //add power up effect to the player
                powerUpView.getPowerUpType().setEffect(playerView.getPlayerData());
                playerView.setHealthBar(playerView.getPlayerData().getHealth());
                playerView.playPowerUpSound();
                powerUpView.getPowerUpSpatial().removeFromParent();
                results.clear();
                powerUpController.decActivePowerUps();
                powerUpView.setHasCollided(true);
            }
        }
    }
}
