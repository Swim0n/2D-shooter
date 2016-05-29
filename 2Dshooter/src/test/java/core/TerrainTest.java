package core;

import org.junit.Test;

import javax.vecmath.Vector3f;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.*;

/**
 * Created by Hannes on 28/05/2016.
 */
public class TerrainTest {
    @Test
    public void testTerrainPlacement() throws Exception {
        Terrain terrain = new Terrain(0,0);
        List <Vector3f> vectorList = new ArrayList<>();
        List <Boolean > booleanList = new ArrayList<>();
        Vector3f vector = new Vector3f();
        /** creates a map with a size of 10x10, the objects would have a size of 1x1 */
        terrain.setPositions(10,10,1,1);
        /** 98 since two tiles are occupied since start by players */
        for (int i=0;i<98;i++){
            vector.set(terrain.getRandomPos(false));
            for (Vector3f vector1 : vectorList){
                if (vector.equals(vector1)){
                    booleanList.add(false);
                }
            }
        }
        /** if the method generated a non unique position, the list would be bigger than 0 */
        assertTrue(booleanList.size()==0);

    }
}