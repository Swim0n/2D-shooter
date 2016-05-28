package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import core.Player;
import core.PowerUp;
import gameView.GameView;
import gameView.PlayerView;
import gameView.PowerUpView;

/**
 * Created by David on 2016-05-28.
 */
public class CollisionController extends RigidBodyControl {
    private final GameView gameView;
    private final Spatial target;
    private final PowerUpController powerUpController;
    private final PowerUp powerUp;
    private boolean hasCollided;

    public CollisionController(GameView gameView, Geometry target, PowerUp powerUp, PowerUpController powerUpController){
        this.gameView = gameView;
        this.powerUp = powerUp;
        this.powerUpController = powerUpController;
        this.target = target;
        target.addControl(this);
    }
    public void update(float tpf){
        collisionCheck();
    }
    private void collisionCheck(){
        CollisionResults results = new CollisionResults();

            gameView.getPlayer1Node().collideWith(spatial.getWorldBound(), results);
            checkResult(gameView.getWorld().getPlayer1(), results, gameView.getPlayer1View());

            gameView.getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            checkResult(gameView.getWorld().getPlayer2(), results, gameView.getPlayer2View());
    }

    private void checkResult(Player player, CollisionResults results, PlayerView playerView){
        if (results.size() > 0){
            if (!hasCollided){
                powerUp.setEffect(player);
                playerView.setHealthBar(player.getHealth());
                playerView.playPowerUpSound();
                target.removeFromParent();
                results.clear();
                powerUpController.decActivePowerUps();
                hasCollided = true;
            }
        }
    }
}
