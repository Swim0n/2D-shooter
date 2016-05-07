package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import game.ctrl.PlayerController;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016-04-26.
 */
public class BulletView {

    private Sphere bulletShape;

    private PlayerView player;
    private Material bulletMaterial;
    private AssetManager assetManager;
    private Node bulletNode;
    private GameView gameView;
    private PlayerController playerController;
    private List<Spatial> bullets;


    public BulletView(GameView gameView, PlayerController playerController, PlayerView player){
        this.gameView = gameView;
        this.assetManager = gameView.getAssetManager();
        this.bulletNode = gameView.getBulletNode();
        this.playerController = playerController;
        this.player = player;
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
        bullet.setLocalTranslation(player.getPlayerNode().getLocalTranslation());

        //attaching physics to bullet
        gameView.bulletCollisionControl(this, bullet);
        bullets.add(bullet);
    }

    public List<Spatial> getBullets(){
        return this.bullets;
    }

    public PlayerController getPlayerController(){
        return this.playerController;
    }

    public GameView getGameView(){
        return this.gameView;
    }

    public PlayerView getPlayerView(){
        return this.player;
    }
}
