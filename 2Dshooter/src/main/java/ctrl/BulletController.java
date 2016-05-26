package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.light.PointLight;
import core.Player;
import gameView.BulletView;
import gameView.GameView;
import gameView.PlayerView;
import utils.ApplicationAssets;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private final ApplicationAssets appAssets;
    private final Player player;
    private BulletView bulletView;
    private GameView gameView;
    private PointLight light;

    public BulletController(BulletView bulletView, ApplicationAssets appAssets, PointLight light){
        this.bulletView = bulletView;
        this.appAssets = appAssets;
        this.gameView = appAssets.getGameView();
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
                takeDamage(appAssets.getWorld().getPlayer2().getDamage(),appAssets.getWorld().getPlayer1(),appAssets.getGameView().getPlayer1View());
            }
        } else if (bulletView.getPlayerView().getPlayerNode().equals(gameView.getPlayer2Node())) {
            gameView.getPlayer1Node().collideWith(spatial.getWorldBound(), results);

            if (results.size() > 0){
                spatial.removeFromParent();
                gameView.getBulletAppState().getPhysicsSpace().remove(spatial.getControl(0));
                gameView.getRootNode().removeLight(light);
                takeDamage(appAssets.getWorld().getPlayer1().getDamage(),appAssets.getWorld().getPlayer2(),appAssets.getGameView().getPlayer2View());
            }


        }
    }

    public void takeDamage(float damage, Player player,PlayerView playerView){
        player.setHealth(player.getHealth() - damage);
        playerView.setHealthBar(player.getHealth());
        playerView.emitSparks();
        playerView.playPlayerHitSound();
        appAssets.getGameView().getNiftyView().updateText();
    }
}
