package ctrl;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.light.PointLight;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import core.Player;
import view.PlayerView;
import view.WorldView;

/**
 * Created by David on 2016-04-19.
 */
public class BulletController extends RigidBodyControl{
    private final Player player;
    private final Node playerNode;
    private final float damage;
    private final Node stageNode;
    private final Node terrainNode;
    private final WorldView worldView;
    private final PointLight light;
    private final Geometry target;
    private Player otherPlayer;
    private PlayerView otherPlayerView;
    private Node otherPlayerNode;

    public BulletController(PlayerView playerView, Player player, Geometry target, WorldView worldView, PointLight light){
        this.worldView = worldView;
        this.stageNode = worldView.getStageNode();
        this.terrainNode = worldView.getTerrainNode();
        this.light = light;
        this.player = player;
        this.playerNode = playerView.getPlayerNode();
        this.damage = player.getDamage();
        this.target = target;
        if (playerNode.equals(worldView.getPlayer1Node())){
            otherPlayerNode = worldView.getPlayer2Node();
            otherPlayer = worldView.getWorld().getPlayer2();
            otherPlayerView = worldView.getPlayer2View();

        }else if(playerNode.equals(worldView.getPlayer2Node())){
            otherPlayerNode = worldView.getPlayer1Node();
            otherPlayer = worldView.getWorld().getPlayer1();
            otherPlayerView = worldView.getPlayer1View();
        }
        target.addControl(this);
        setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(player.getBulletSpeed()));
        worldView.getBulletAppState().getPhysicsSpace().add(this);
    }

    public void update(float tpf){
        if(worldView.getWorld().isPaused()){
            spatial.removeFromParent();
            worldView.getBulletAppState().getPhysicsSpace().remove(spatial);
            return;
        }
        super.update(tpf);

        CollisionResults results = new CollisionResults();

        worldView.updateGUI();

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
            worldView.getBulletAppState().getPhysicsSpace().remove(spatial);
            worldView.getRootNode().removeLight(light);
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
        worldView.getNiftyView().updateText();
    }
}
