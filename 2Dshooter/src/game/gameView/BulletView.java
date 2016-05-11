package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
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
    private PlayerController playerController;
    private List<Spatial> bullets;
    private GameView gameView;

    public BulletView(GameView gameView, PlayerView player){
        this.gameView = gameView;
        this.assetManager = gameView.getAssetManager();
        this.bulletNode = gameView.getBulletNode();
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
        bullet.setLocalTranslation(this.player.getPipePos());

        //attaching physics to bullet
        gameView.bulletCollisionControl(this, bullet);
        bullets.add(bullet);
    }

    public List<Spatial> getBullets(){
        return this.bullets;
    }

    public GameView getGameView(){
        return this.gameView;
    }

    public PlayerView getPlayerView(){
        return this.player;
    }
}
