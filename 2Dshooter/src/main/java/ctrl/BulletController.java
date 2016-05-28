package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.light.PointLight;
import core.Player;
import gameView.BulletView;
import gameView.GameView;
import gameView.PlayerView;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private final Player player;
    private BulletView bulletView;
    private GameView gameView;
    private PointLight light;

    public BulletController(BulletView bulletView, GameView gameView, PointLight light){
        this.bulletView = bulletView;
        this.gameView = gameView;
        this.light = light;
        this.player = bulletView.getPlayerView().getPlayerData();
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
            gameView.getBulletAppState().getPhysicsSpace().remove(spatial);
            gameView.getRootNode().removeLight(light);
        }

        results.clear();

        if (bulletView.getPlayerView().getPlayerNode().equals(gameView.getPlayer1Node())){
            gameView.getPlayer2Node().collideWith(spatial.getWorldBound(), results);
            if (results.size() > 0){
                spatial.removeFromParent();
                gameView.getBulletAppState().getPhysicsSpace().remove(spatial);
                gameView.getRootNode().removeLight(light);
                takeDamage(gameView.getWorld().getPlayer1().getDamage(),gameView.getWorld().getPlayer2(),gameView.getPlayer2View());
            }
        } else if (bulletView.getPlayerView().getPlayerNode().equals(gameView.getPlayer2Node())) {
            gameView.getPlayer1Node().collideWith(spatial.getWorldBound(), results);

            if (results.size() > 0){
                spatial.removeFromParent();
                gameView.getBulletAppState().getPhysicsSpace().remove(spatial);
                gameView.getRootNode().removeLight(light);
                takeDamage(gameView.getWorld().getPlayer2().getDamage(),gameView.getWorld().getPlayer1(),gameView.getPlayer1View());
            }
        }
    }

    public void takeDamage(float damage, Player player,PlayerView playerView){
        player.takeDamage(damage);
        playerView.setHealthBar(player.getHealth());
        playerView.emitSparks();
        playerView.playPlayerHitSound();
        gameView.getNiftyView().updateText();
    }
}
