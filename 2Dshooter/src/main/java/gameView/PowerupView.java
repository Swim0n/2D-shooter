package gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import core.PowerUp;

public class PowerUpView {

    private final PowerUp powerUpType;
    private Geometry powerupGeom;
    private boolean hasCollided;

    /** initializes two power ups and then spawn two new every 15 seconds*/
    public PowerUpView(Node targetNode,GameView gameView, PowerUp powerUpType, ColorRGBA colorRGBA){
        AssetManager assetManager = gameView.getAssetManager();
        this.powerUpType = powerUpType;

        Box powerBox = new Box(1f, 1f, 1f);
        powerupGeom = new Geometry("Power up", powerBox);
        powerupGeom = new Geometry("Box", powerBox);
        Material boxMaterial = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        powerupGeom.setMaterial(boxMaterial);
        powerupGeom.setLocalTranslation(powerUpType.getX(), -1, powerUpType.getZ());
        boxMaterial.setColor("Color", colorRGBA);
        targetNode.attachChild(powerupGeom);
    }

    public Spatial getPowerUpSpatial(){
        return powerupGeom;
    }

    public PowerUp getPowerUpType() {
        return powerUpType;
    }

    public boolean hasCollided() {
        return hasCollided;
    }
    public void setHasCollided(boolean hasCollided) {
        this.hasCollided = hasCollided;
    }
}
