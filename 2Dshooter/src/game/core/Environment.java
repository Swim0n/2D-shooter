package game.core;

import java.util.Random;

/**
 * Created by David on 2016-04-15.
 */
public class Environment {
    private final static Random randomGenerator = new Random();
    private Boolean[][] occupiedTerrain;
    private int rocksAmount;
    private int treesAmount;

    public Environment(int rocks, int trees){
        this.rocksAmount = rocks;
        this.treesAmount = trees;
    }

    public int[] getRandomPos(float terrainWidth, float terrainHeight, float positionWidth, float positionHeight){
        int x = randomGenerator.nextInt((int) ((terrainWidth-positionWidth)/positionWidth+positionWidth/2));
        int z = randomGenerator.nextInt((int) ((terrainHeight-positionHeight)/positionHeight+positionHeight/2-0.5));
        while(true){
            if(occupiedTerrain[x][z] != null){
                x = randomGenerator.nextInt((int) ((terrainWidth-positionWidth)/positionWidth+positionWidth/2));
                z = randomGenerator.nextInt((int) ((terrainHeight-positionHeight)/positionHeight+positionHeight/2-0.5));
            } else {
                break;
            }
        }
        occupiedTerrain[x][z] = true;
        return new int[] {x, z};
    }

    public void setPositionsAmount(int width, int height){
        occupiedTerrain = new Boolean[width][height];
    }

    public int getRocksAmount() {
        return rocksAmount;
    }
    public int getTreesAmount() {
        return treesAmount;
    }
}
