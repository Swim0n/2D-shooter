package view;

import com.jme3.asset.AssetManager;
import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.LightControl;
import com.jme3.scene.shape.Box;

/**
 * Creates bullets
 */
public class BulletView {
    private Box bulletShape;
    private WorldView worldView;
    private Node bulletNode;
    private PointLight bulletLight;
    private AssetManager assetManager;

    public BulletView(WorldView worldView){
        this.worldView = worldView;
        this.assetManager = worldView.getAssetManager();
        this.bulletNode = worldView.getBulletNode();
    }

    public Geometry getNewBullet(PlayerView playerView) {
        bulletShape = new Box(0.17f,0.17f,0.17f);
        Geometry bullet = new Geometry("Bullet", bulletShape);
        Material bulletMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        bulletMaterial.setColor("Color", playerView.getBodyColor());
        bullet.setMaterial(bulletMaterial);
        bulletNode.attachChild(bullet);
        bullet.setLocalTranslation(playerView.getPipePos());
        bulletLight = new PointLight();
        bulletLight.setColor(playerView.getBodyColor().mult(5));
        bulletLight.setRadius(3.5f);
        worldView.getRootNode().addLight(bulletLight);
        bullet.addControl(new LightControl(bulletLight));
        return bullet;
    }

    public PointLight getBulletLight() {
        return bulletLight;
    }
}
