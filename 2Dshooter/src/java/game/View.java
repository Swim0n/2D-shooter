package game;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;

/**
 * Created by David on 2016-04-18.
 */
public class View extends SimpleApplication {

    private Geometry blue;
    private World world = new World();
    public void simpleInitApp() {

        //        setup camera for 2D games
        cam.setParallelProjection(false);
        cam.setLocation(new Vector3f(0,0,70f));
        getFlyByCamera().setEnabled(false);

//        turn off stats view (you can leave it on, if you want)
        setDisplayStatView(true);
        setDisplayFps(true);

        Quad groundShape = new Quad(50f, 50f); //quad to represent ground in game
        Geometry groundGeom = new Geometry("Ground",groundShape); //geometry to represent ground
        Material groundMat = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md"); //material for ground
        groundMat.setColor("Color", ColorRGBA.Green);
        groundGeom.setMaterial(groundMat);
        groundGeom.rotate(0,0,0);
        groundGeom.setLocalTranslation(groundShape.getWidth()/-2, groundShape.getHeight()/-2, 0);
        rootNode.attachChild(groundGeom);

        Box box1 = new Box(1,1,1);
        blue = new Geometry("Box", box1);
        Material mat1 = new Material(assetManager,
                "Common/MatDefs/Misc/Unshaded.j3md");
        blue.setLocalTranslation(0,0,0.5f);
        mat1.setColor("Color", ColorRGBA.Red);
        blue.setMaterial(mat1);
        rootNode.attachChild(blue);


    }

    public void simpleUpdate(float tpf){

            world.getPlayer1().up();
            blue.setLocalTranslation(world.getPlayer1().getPositionX(),world.getPlayer1().getPositionY(), 0);

    }
}
