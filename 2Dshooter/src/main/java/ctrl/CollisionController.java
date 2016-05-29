package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import core.Player;
import core.PowerUp;
import view.PlayerView;
import view.PowerUpView;
import view.WorldView;

/**
 * Created by David on 2016-05-28.
 */
public class CollisionController extends RigidBodyControl {
    private final WorldView worldView;
    private final Spatial target;
    private final PowerUpController powerUpController;
    private final PowerUp powerUp;
    private final PowerUpView powerUpView;
    private boolean hasCollided;

    public CollisionController(WorldView worldView, Geometry target, PowerUp powerUp, PowerUpController powerUpController){
        this.worldView = worldView;
        this.powerUp = powerUp;
        this.powerUpView = worldView.getPowerUpView();
        this.powerUpController = powerUpController;
        this.target = target;
        target.addControl(this);
    }
    public void update(float tpf){
        collisionCheck();
    }
    private void collisionCheck(){
        CollisionResults results = new CollisionResults();

            worldView.getPlayer1Node().collideWith(spatial.getWorldBound(), results);
            checkResult(worldView.getWorld().getPlayer1(), results, worldView.getPlayer1View());

            worldView.getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            checkResult(worldView.getWorld().getPlayer2(), results, worldView.getPlayer2View());
    }

    private void checkResult(Player player, CollisionResults results, PlayerView playerView){
        if (results.size() > 0){
            if (!hasCollided){
                powerUp.setEffect(player);
                playerView.setHealthBar(player.getHealth());
                playerView.playPowerUpSound();
                target.removeFromParent();
                results.clear();
                powerUpView.decActivePowerUps();
                hasCollided = true;
            }
        }
    }
}
