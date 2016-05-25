package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.light.PointLight;
import com.jme3.scene.control.LightControl;
import gameView.BulletView;
import gameView.GameView;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private BulletView bulletView;
    private GameView gameView;
    private PointLight light;

    public BulletController(BulletView bulletView, GameView gameView, PointLight light){
        this.bulletView = bulletView;
        this.gameView = gameView;
        this.light = light;
    }

    public void update(float tpf){
        if(gameView.getPaused()){
            spatial.removeFromParent();
            gameView.getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
            return;
        }
        super.update(tpf);
        CollisionResults results = new CollisionResults();

        gameView.updateGUI();

        gameView.getTerrainNode().collideWith(spatial.getWorldBound(), results);
        gameView.getStageNode().collideWith(spatial.getWorldBound(), results);

        if (results.size() > 0){
            spatial.removeFromParent();
            gameView.getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
            gameView.getRootNode().removeLight(light);
        }

        results.clear();

        if (bulletView.getPlayerView().getPlayerNode().equals(gameView.getPlayer1Node())){
            gameView.getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            if (results.size() > 0){
                spatial.removeFromParent();
                gameView.getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
                gameView.getRootNode().removeLight(light);
                gameView.getPlayer2Control().takeDamage(gameView.getPlayer1Control().getPlayerData().getDamage());
            }
        } else if (bulletView.getPlayerView().getPlayerNode().equals(gameView.getPlayer2Node())) {
            gameView.getPlayer1Node().collideWith(spatial.getWorldBound(), results);

            if (results.size() > 0){
                spatial.removeFromParent();
                gameView.getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
                gameView.getRootNode().removeLight(light);
                gameView.getPlayer1Control().takeDamage(gameView.getPlayer2Control().getPlayerData().getDamage());
            }
        }
    }
}
