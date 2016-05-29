package view;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import core.PowerUp;

/**
 * Creates power ups
 */
public class PowerUpView {
    private final AssetManager assetManager;

    public PowerUpView(WorldView worldView){
        this.assetManager = worldView.getAssetManager();
    }

    public Geometry createPowerUp(Node targetNode, PowerUp powerUp, ColorRGBA colorRGBA){
        Box powerBox = new Box(1f, 1f, 1f);
        Geometry powerUpGeom = new Geometry("Power up", powerBox);
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        powerUpGeom.setMaterial(boxMaterial);
        powerUpGeom.setLocalTranslation(powerUp.getX(), -1, powerUp.getZ());
        boxMaterial.setColor("Color", colorRGBA);
        targetNode.attachChild(powerUpGeom);
        return powerUpGeom;
    }
}
