package game.core;

import javax.vecmath.Vector3f;
import java.util.Random;

/**
 * Created by David on 2016-04-15.
 */
public class Environment {
    private final static Random randomGenerator = new Random();
    private Boolean[][] occupiedTerrain;
    private Vector3f[][] positions;
    private int rocksAmount;
    private int treesAmount;

    public Environment(int rocks, int trees){
        this.rocksAmount = rocks;
        this.treesAmount = trees;
    }

    public Environment(){}

    public Vector3f getRandomPos(){
        int x = randomGenerator.nextInt(positions.length);
        int z = randomGenerator.nextInt(positions[0].length);
        while(true){
            if(occupiedTerrain[x][z] != null){
                x = randomGenerator.nextInt(positions.length);
                z = randomGenerator.nextInt(positions[0].length);
            } else {
                break;
            }
        }
        occupiedTerrain[x][z] = true;
        return positions[x][z];
    }

    public void setPositions(float terrainWidth, float terrainHeight, float tileWidth, float tileHeight){
        occupiedTerrain = new Boolean[(int) (terrainWidth/tileWidth)+1][(int) (terrainHeight/tileHeight)];
        positions = new Vector3f[(int) (terrainWidth/tileWidth)+1][(int) (terrainHeight/tileHeight)];
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                positions[i][j] = new Vector3f(i*tileWidth-terrainWidth/2+tileWidth/2, 0, j*tileHeight-terrainHeight/2+tileWidth/2);
            }
        }
    }

    public int getRocksAmount() {
        return rocksAmount;
    }
    public int getTreesAmount() {
        return treesAmount;
    }
}
