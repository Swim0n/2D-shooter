package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 2016-04-26.
 */
public class BulletView {

    private final GameView gameView;
    private Box bulletShape;

    private PlayerView playerView;
    private Material bulletMaterial;
    private AssetManager assetManager;
    private Node bulletNode;
    private List<Spatial> bullets;
    private Geometry bullet;


    public BulletView(PlayerView playerView, GameView gameView){
        this.gameView = gameView;
        this.assetManager = gameView.getAssetManager();
        this.bulletNode = gameView.getBulletNode();
        this.playerView = playerView;
        this.bullets = new ArrayList<>();

        //creating the bullet
        bulletShape = new Box(0.17f,0.17f,0.17f);
        bullet = new Geometry("Bullet", bulletShape);
        bulletMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMaterial.setColor("Color", playerView.getBodyColor());
        bullet.setMaterial(bulletMaterial);
        bulletNode.attachChild(bullet);
        //setting starting point at players pos.
        bullet.setLocalTranslation(this.playerView.getPipePos());
        bullets.add(bullet);
    }

    public PlayerView getPlayerView(){
        return this.playerView;
    }

    public Geometry getBullet() {
        return bullet;
    }
}
