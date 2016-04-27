package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import game.ctrl.PlayerController;

/**
 * Created by David on 2016-04-26.
 */
public class BulletView {

    private Sphere bulletShape;
    private Geometry bullet;
    private Geometry player;
    private Material bulletMaterial;
    private AssetManager assetManager;
    private Node rootNode;
    private GameView gameView;
    private PlayerController playerController;


    public BulletView(AssetManager assetManager, Node rootNode, GameView gameView, PlayerController playerController, Geometry player){
        this.assetManager = assetManager;
        this.gameView = gameView;
        this.rootNode = rootNode;
        this.playerController = playerController;
        this.player = player;


        createBullet();
    }

    public void createBullet() {

        //creating the bullet
        bulletShape = new Sphere(5, 10, 0.3f);
        bullet = new Geometry("Sphere", bulletShape);
        bulletMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMaterial.setColor("Color", ColorRGBA.randomColor());
        bullet.setMaterial(bulletMaterial);
        rootNode.attachChild(bullet);

        //setting starting point at players pos.
        bullet.setLocalTranslation(player.getLocalTranslation());

        //attaching physics to bullet
        gameView.bulletCollisionControl(this.bullet, this.playerController);
    }

    public Geometry getBullet(){return this.bullet;}
}
