package game.ctrl;


import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import game.gameView.BulletView;
import game.gameView.Player1View;
import game.gameView.Player2View;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private BulletView bulletView;
    private float bulletSpeed = 160;

    public BulletController(BulletView bulletView){
        this.bulletView = bulletView;
    }

    public void update(float tpf){
        super.update(tpf);
        CollisionResults results = new CollisionResults();

        bulletView.getGameView().getStageNode().collideWith(spatial.getWorldBound(), results);
        if (results.size() > 0){
            spatial.removeFromParent();
            bulletView.getGameView().getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
        }

        if (bulletView.getPlayerView() instanceof Player1View){
            bulletView.getGameView().getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            if (results.size() > 0){
                spatial.removeFromParent();
                bulletView.getGameView().getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
            }
        } else if (bulletView.getPlayerView() instanceof Player2View) {
            bulletView.getGameView().getPlayer1Node().collideWith(spatial.getWorldBound(), results);
            if (results.size() > 0){
                spatial.removeFromParent();
                bulletView.getGameView().getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
            }
        }

    }
}
