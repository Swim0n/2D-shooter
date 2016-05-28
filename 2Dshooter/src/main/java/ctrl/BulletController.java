package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.light.PointLight;
import com.jme3.scene.Node;
import core.Player;
import gameView.BulletView;
import gameView.GameView;
import gameView.PlayerView;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private final Player player;
    private final Node playerNode;
    private final float damage;
    private final Node stageNode;
    private final Node terrainNode;
    private final BulletView bulletView;
    private final GameView gameView;
    private final PointLight light;
    private Player otherPlayer;
    private PlayerView otherPlayerView;
    private Node otherPlayerNode;

    public BulletController(BulletView bulletView, GameView gameView, PointLight light){
        this.bulletView = bulletView;
        this.gameView = gameView;
        this.stageNode = gameView.getStageNode();
        this.terrainNode = gameView.getTerrainNode();
        this.light = light;
        this.player = bulletView.getPlayerView().getPlayerData();
        this.playerNode = bulletView.getPlayerView().getPlayerNode();
        this.damage = player.getDamage();

        if (playerNode.equals(gameView.getPlayer1Node())){
            otherPlayerNode = gameView.getPlayer2Node();
            otherPlayer = gameView.getWorld().getPlayer2();
            otherPlayerView = gameView.getPlayer2View();

        }else if(playerNode.equals(gameView.getPlayer2Node())){
            otherPlayerNode = gameView.getPlayer1Node();
            otherPlayer = gameView.getWorld().getPlayer1();
            otherPlayerView = gameView.getPlayer1View();
        }
    }

    public void update(float tpf){
        if(gameView.getPaused()){
            spatial.removeFromParent();
            gameView.getBulletAppState().getPhysicsSpace().remove(spatial);
            return;
        }
        super.update(tpf);

        CollisionResults results = new CollisionResults();

        gameView.updateGUI();

        terrainNode.collideWith(spatial.getWorldBound(), results);
        stageNode.collideWith(spatial.getWorldBound(), results);

        collisionCheck(results,false);
        results.clear();

        otherPlayerNode.collideWith(spatial.getWorldBound(), results);
        collisionCheck(results,true);
    }

    public void collisionCheck(CollisionResults results,boolean playerCheck){
        if (results.size() > 0){
            spatial.removeFromParent();
            gameView.getBulletAppState().getPhysicsSpace().remove(spatial);
            gameView.getRootNode().removeLight(light);
            if(playerCheck) {
                takeDamage();
            }
        }
    }

    public void takeDamage(){
        otherPlayer.takeDamage(damage);
        otherPlayerView.setHealthBar(otherPlayer.getHealth());
        otherPlayerView.emitSparks();
        otherPlayerView.playPlayerHitSound();
        gameView.getNiftyView().updateText();
    }
}
