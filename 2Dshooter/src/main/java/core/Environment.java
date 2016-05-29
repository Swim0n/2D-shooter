package core;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import org.lwjgl.Sys;
import utils.ApplicationAssets;

import javax.vecmath.Vector3f;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by David on 2016-04-15.
 */
public class Environment {
    private final static Random randomGenerator = new Random();
    private Boolean[][] occupiedTerrain;
    private Tile[][] grid;
    private Vector3f[][] positions;
    private int rocksAmount;
    private int treesAmount;
    private int height;
    private int width;
    private int tileHeight;
    private int tileWidth;
    private AssetManager assetManager;
    private ApplicationAssets applicationAssets;

    public Environment(int rocks, int trees, AssetManager am){
        this.rocksAmount = rocks;
        this.treesAmount = trees;
        this.assetManager = am;
    }

    public Vector3f getRandomPos(Boolean blockTile){
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
        if(blockTile){
        occupiedTerrain[x][z] = true;
        grid[x][z].setBlocked(true);}
        return positions[x][z];
    }

    //creates an array with the amount of tiles as the grid. it then fills the array with every tile position.
    //this method assumes that the java engine maps the center of the spatial to the position
    public void setPositions(float terrainWidth, float terrainHeight, float tileWidth, float tileHeight){
        this.tileWidth =  (int) tileWidth;
        this.tileHeight = (int) tileHeight;
        this.height = (int) (terrainHeight/tileHeight);
        this.width = (int) (terrainWidth/tileWidth + 1);
        grid = new Tile[width][height];
        occupiedTerrain = new Boolean[(int) (terrainWidth/tileWidth)+1][(int) (terrainHeight/tileHeight)];
        positions = new Vector3f[(int) (terrainWidth/tileWidth)+1][(int) (terrainHeight/tileHeight)];
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[i].length; j++) {
                positions[i][j] = new Vector3f(i*tileWidth-terrainWidth/2+tileWidth/2, 0, j*tileHeight-terrainHeight/2+tileWidth/2);
                this.grid[i][j] = new Tile((int) (i*tileWidth-terrainWidth/2+tileWidth/2 - 1),(int) (j*tileHeight-terrainHeight/2+tileWidth/2 - 1),(int) (i*width + j));
            }
        }
        //this is where the players spawn
        occupiedTerrain[1][occupiedTerrain[0].length-2] = true;
        occupiedTerrain[occupiedTerrain.length-2][1] = true;

        /**PathFinder pf = new PathFinder(this);
        System.out.println("widthheight" + width + height);
        System.out.println("xxx" + (getTileByCoords(-35, -24)).getID());
        ArrayList path = pf.findPath(-13, -13, 12, 12);
        System.out.println("path:");
        for(int i=0; i<path.size(); i++){
            System.out.println("X: " + ((Tile) path.get(i)).getX() + "Y: " + ((Tile) path.get(i)).getY());
        }
        System.out.println("/path");**/

    }

    public Tile[][] getTiles(){
        return grid;
    }

    public Tile getTileByCoords(int x, int y){
        for(int i=0; i<this.height; i++){
            for(int n=0; n<this.width; n++){
                if(grid[n][i].getY() + 2 > y && grid[n][i].getY() - 2 <= y && grid[n][i].getX() + 2 > x && grid[n][i].getX() - 2 <= x){
                    return grid[n][i];
                }
            }
        }
        return null;
    }
    public Tile getTileByID(int id){
        for(int i=0; i<this.height; i++){
            for(int n=0; n<this.width; n++){
                if(grid[i][n].getID() == id){
                    return grid[i][n];
                }
            }
        }
        return null;
    }
    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

    public ArrayList getTileNeighbors(Tile tile){

        ArrayList neighbors = new ArrayList();
        Boolean topLeft;
        Boolean topMid;
        Boolean topRight;

        Boolean midLeft;
        Boolean midRight;

        Boolean botLeft;
        Boolean botMid;
        Boolean botRight;


        //top row of neighbors

        if(tile.getY() > grid[0][0].getY()) {
            if (tile.getX() > grid[0][0].getX()) {
                topLeft = getTileByCoords((tile.getX() - 4), (tile.getY()) - 4).getBlocked();
            }else{
                topLeft = true;
            }

            topMid = getTileByCoords((tile.getX()), (tile.getY() - 4)).getBlocked();

            if (tile.getX() < grid[grid.length - 1][grid[0].length - 1].getX()) {
                topRight = getTileByCoords((tile.getX() + 4), (tile.getY()) - 4).getBlocked();

            } else {
                topRight = true;
            }

        }else{
            topLeft = true;
            topMid = true;
            topRight = true;
        }

        //second row of neighbors (left and right of center)
        if(tile.getX() > grid[0][0].getX()) {
                midLeft = getTileByCoords((tile.getX() - 4), (tile.getY())).getBlocked();
        }else{
            midLeft = true;
        }

        if(tile.getX() < grid[grid.length - 1][grid[0].length - 1].getX()) {
                midRight = getTileByCoords((tile.getX() + 4), (tile.getY())).getBlocked();
        }else{
            midRight = true;
        }

        //bottom row of neighbors
        if(tile.getY() < grid[grid.length - 1][grid[0].length - 1].getY()) {
            if(tile.getX() >  grid[0][0].getX()) {
                    botLeft = getTileByCoords((tile.getX() - 4), (tile.getY()) + 4).getBlocked();
                }else {
                botLeft = true;
            }

            botMid = getTileByCoords((tile.getX()), (tile.getY()) + 4).getBlocked();

            if(tile.getX() < grid[grid.length - 1][grid[0].length - 1].getX()) {
                    botRight = getTileByCoords((tile.getX() + 4), (tile.getY()) + 4).getBlocked();
            }else{
                botRight = true;
            }
        }else{
            botLeft = true;
            botMid = true;
            botRight = true;
        }

        //---------------------------------------------------

        //top row of neighbors
        if(tile.getY() > grid[0][0].getY()) {
            if(tile.getX() >  grid[0][0].getX()) {
                if(!topLeft && (!topMid && !midLeft)) {
                    neighbors.add(getTileByCoords((tile.getX() - 4), (tile.getY()) - 4));
                }
            }
            if(!topMid) {
                neighbors.add(getTileByCoords((tile.getX()), (tile.getY()) - 4));
            }

            if(tile.getX() < grid[grid.length - 1][grid[0].length - 1].getX()) {
                if(!topRight && (!topMid && !midRight)) {
                    neighbors.add(getTileByCoords((tile.getX() + 4), (tile.getY()) - 4));
                }
            }
        }

        //second row of neighbors (left and right of center)
        if(tile.getX() > grid[0][0].getX()) {
            if(!midLeft) {
                neighbors.add(getTileByCoords((tile.getX() - 4), (tile.getY())));
            }
        }

        if(tile.getX() <  grid[grid.length - 1][grid[0].length - 1].getX()) {
            if(!midRight) {
                neighbors.add(getTileByCoords((tile.getX() + 4), (tile.getY())));
            }

        }

        //bottom row of neighbors
        if(tile.getY() <  grid[grid.length - 1][grid[0].length - 1].getY()) {
            if(tile.getX() > grid[0][0].getX()) {
                if(!botLeft && (!midLeft && !botMid)) {
                    neighbors.add(getTileByCoords((tile.getX() - 4), (tile.getY()) + 4));
                }

            }
            if(!botMid) {
                neighbors.add(getTileByCoords((tile.getX()), (tile.getY()) + 4));
            }
            if(tile.getX() < grid[grid.length - 1][grid[0].length - 1].getX()) {
                if(!botRight && (!midRight && !botMid)) {
                    neighbors.add(getTileByCoords((tile.getX() + 4), (tile.getY()) + 4));
                }
            }
        }
        return neighbors;
    }

    public void setApplicationAssets(ApplicationAssets as){
        this.applicationAssets = as;
    }

    public int getRocksAmount() {
        return rocksAmount;
    }
    public int getTreesAmount() {
        return treesAmount;
    }
}


