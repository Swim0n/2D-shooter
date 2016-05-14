package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import game.ctrl.BulletController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016-04-26.
 */
public class BulletView {

    private Sphere bulletShape;

    private PlayerView playerView;
    private Material bulletMaterial;
    private AssetManager assetManager;
    private Node bulletNode;
    private List<Spatial> bullets;


    public BulletView(PlayerView playerView){
        this.assetManager = playerView.getGameView().getAssetManager();
        this.bulletNode = playerView.getGameView().getBulletNode();
        this.playerView = playerView;
        this.bullets = new ArrayList<Spatial>();
    }

    public void createBullet() {
        //creating the bullet
        bulletShape = new Sphere(5, 10, 0.3f);
        Geometry bullet = new Geometry("Bullet", bulletShape);
        bulletMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMaterial.setColor("Color", ColorRGBA.randomColor());
        bullet.setMaterial(bulletMaterial);
        bulletNode.attachChild(bullet);
        //setting starting point at players pos.
        bullet.setLocalTranslation(this.playerView.getPipePos());
        addPhysics(bullet);
        bullets.add(bullet);
    }

    private void addPhysics(Geometry bullet){
        BulletController bulletPhy = new BulletController(this, playerView.getGameView());
        bullet.addControl(bulletPhy);
        bulletPhy.setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(50));
        playerView.getGameView().getBulletAppState().getPhysicsSpace().add(bulletPhy);
    }

    public PlayerView getPlayerView(){
        return this.playerView;
    }
}
