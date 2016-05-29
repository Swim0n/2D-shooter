package jME3.utils;


import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import javax.vecmath.Vector3f;

/**
 * Generic jMonkeyEngine3 utils
 */
public final class Utils {
    public static Geometry getBox(float xDim, float yDim, float zDim){
        Box box = new Box(xDim,yDim,zDim);
        Geometry geometry = new Geometry("Box", box);

        return geometry;
    }

    public static Material getMaterial(AssetManager assetManager, ColorRGBA colorRGBA){
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", colorRGBA);

        return material;
    }

    public static Vector3f jMEToVecMathVector3f(com.jme3.math.Vector3f vector3f){
        return new Vector3f(vector3f.getX(),vector3f.getY(),vector3f.getZ());
    }

    public static com.jme3.math.Vector3f vecMathToJMEVector3f(Vector3f vector3f){
        return new com.jme3.math.Vector3f(vector3f.x,vector3f.y,vector3f.z);
    }
}