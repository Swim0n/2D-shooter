package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.LightControl;
import com.jme3.scene.shape.Sphere;
import ctrl.BulletController;
import utils.ApplicationAssets;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016-04-26.
 */
public class BulletView {

    private final ApplicationAssets appAssets;
    private Sphere bulletShape;

    private PlayerView playerView;
    private Material bulletMaterial;
    private AssetManager assetManager;
    private Node bulletNode;
    private List<Spatial> bullets;
    private Geometry bullet;


    public BulletView(PlayerView playerView, ApplicationAssets appAssets){
        this.appAssets = appAssets;
        this.assetManager = appAssets.getAssetManager();
        this.bulletNode = appAssets.getGameView().getBulletNode();
        this.playerView = playerView;
        this.bullets = new ArrayList<>();

        //creating the bullet
        bulletShape = new Sphere(5, 10, 0.3f);
        bullet = new Geometry("Bullet", bulletShape);
        bulletMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMaterial.setColor("Color", playerView.getBodyColor());
        bullet.setMaterial(bulletMaterial);
        bulletNode.attachChild(bullet);
        //setting starting point at players pos.
        bullet.setLocalTranslation(this.playerView.getPipePos());


        bullets.add(bullet);
    }

    public void createBullet() {
        //creating the bullet
        bulletShape = new Sphere(5, 10, 0.3f);
        bullet = new Geometry("Bullet", bulletShape);
        bulletMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMaterial.setColor("Color", playerView.getBodyColor());
        bullet.setMaterial(bulletMaterial);
        bulletNode.attachChild(bullet);
        //setting starting point at players pos.
        bullet.setLocalTranslation(this.playerView.getPipePos());
        PointLight lamp_light = new PointLight();
        lamp_light.setColor(playerView.getBodyColor().mult(5));

        lamp_light.setRadius(3.5f);




        LightControl lightControl = new LightControl(lamp_light); //TBR
        playerView.getGameView().getRootNode().addLight(lamp_light);



        addPhysics(bullet, lamp_light);

        bullet.addControl(lightControl);//TBR

        bullets.add(bullet);
    }


    private void addPhysics(Geometry bullet, PointLight light){
        BulletController bulletPhy = new BulletController(this, appAssets, light);
        bullet.addControl(bulletPhy);
        bulletPhy.setLinearVelocity(playerView.getGunRotation().getRotationColumn(2).mult(50));
        playerView.getGameView().getBulletAppState().getPhysicsSpace().add(bulletPhy);
    }

    public PlayerView getPlayerView(){
        return this.playerView;
    }

    public Geometry getBullet() {
        return bullet;
    }
}
