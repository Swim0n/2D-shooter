package game.gameView;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

/**
 * Created by Hannes on 24/04/2016.
 */
public class GroundView {

    private Geometry groundGeom;
    private Node rootNode;
    private AssetManager assetManager;

    public GroundView(AssetManager assetManager, Node rootNode){
        this.rootNode = rootNode;
        this.assetManager = assetManager;
    }
    public void createGround(){
        Quad groundShape = new Quad(71f, 53f); //quad to represent ground in game
        groundGeom= new Geometry("Ground",groundShape); //geometry to represent ground
        Material groundMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md"); //material for ground
        Texture dirt = assetManager.loadTexture("Textures/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        groundMat.setTexture("ColorMap", dirt);
        groundGeom.setMaterial(groundMat);
        groundGeom.rotate(FastMath.HALF_PI,0,0);
        groundGeom.setLocalTranslation(-groundShape.getWidth()/2, 0, -groundShape.getHeight()/2);

        rootNode.attachChild(groundGeom);
    }

    public Geometry getGroundGeom(){
        return this.groundGeom;
    }

}
