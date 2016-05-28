package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.light.PointLight;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import core.Player;
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
    private final GameView gameView;
    private final PointLight light;
    private final Geometry target;
    private Player otherPlayer;
    private PlayerView otherPlayerView;
    private Node otherPlayerNode;

    public BulletController(PlayerView playerView, Geometry target, GameView gameView, PointLight light){
        this.gameView = gameView;
        this.stageNode = gameView.getStageNode();
        this.terrainNode = gameView.getTerrainNode();
        this.light = light;
        this.player = playerView.getPlayerData();
        this.playerNode = playerView.getPlayerNode();
        this.damage = player.getDamage();
        this.target = target;
        if (playerNode.equals(gameView.getPlayer1Node())){
            otherPlayerNode = gameView.getPlayer2Node();
            otherPlayer = gameView.getWorld().getPlayer2();
            otherPlayerView = gameView.getPlayer2View();

        }else if(playerNode.equals(gameView.getPlayer2Node())){
            otherPlayerNode = gameView.getPlayer1Node();
            otherPlayer = gameView.getWorld().getPlayer1();
            otherPlayerView = gameView.getPlayer1View();
        }
        target.addControl(this);
        setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(player.getBulletSpeed()));
        playerView.getGameView().getBulletAppState().getPhysicsSpace().add(this);
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
