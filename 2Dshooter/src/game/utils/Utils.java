package game.utils;


import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

import javax.swing.*;

/**
 * Created by David on 2016-04-19.
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

}
