package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.LightControl;
import com.jme3.scene.shape.Sphere;
import ctrl.BulletController;

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


    public BulletView(PlayerView playerView){
        this.assetManager = playerView.getGameView().getAssetManager();
        this.bulletNode = playerView.getGameView().getBulletNode();
        this.playerView = playerView;
    }

    public void createBullet() {
        //creating the bullet
        bulletShape = new Sphere(5, 10, 0.3f);
        Geometry bullet = new Geometry("Bullet", bulletShape);
        bulletMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMaterial.setColor("Color", playerView.getBodyColor());
        bullet.setMaterial(bulletMaterial);
        bulletNode.attachChild(bullet);
        //setting starting point at players pos.
        bullet.setLocalTranslation(this.playerView.getPipePos());
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(playerView.getBodyColor().mult(5));
        lamp_light.setRadius(3.5f);
        LightControl lightControl = new LightControl(lamp_light);
        playerView.getGameView().getRootNode().addLight(lamp_light);
        addPhysics(bullet, lamp_light);
        bullet.addControl(lightControl);
    }

    private void addPhysics(Geometry bullet, PointLight light){
        BulletController bulletPhy = new BulletController(this, playerView.getGameView(), light);
        bullet.addControl(bulletPhy);
        bulletPhy.setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(50));
        playerView.getGameView().getBulletAppState().getPhysicsSpace().add(bulletPhy);
    }

    public PlayerView getPlayerView(){
        return this.playerView;
    }
}
