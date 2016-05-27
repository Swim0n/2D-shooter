package core;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import static com.jme3.shader.VarType.Int;

/**
 * Created by Lukas on 2016-05-18.
 */

/**
public class PathFinder {
    private Environment environment;
    private Tile[][] tiles;
    private ArrayList closedSet = new ArrayList();
    private ArrayList openSet = new ArrayList();


    public PathFinder(Environment environment) {
        this.environment = environment;
        this.tiles = environment.getTiles();
    }


    public ArrayList findPath(int startX, int startY, int endX, int endY) {
        Tile startTile = environment.getTileByCoords(startX, startY);
        Tile endTile = environment.getTileByCoords(endX, endY);
        this.openSet.add(startTile);
        startTile.setgScore(0);
        startTile.setfScore(distanceBetweenTiles(startTile, endTile));

        while (openSet.size() != 0) {
            Tile current = getCurrent();
            if(current == endTile){
                return reconstructPath(current);
            }

            openSet.remove(current);
            closedSet.add(current);

            //for each neighbor (1 tile has 8 neighbors)
            ArrayList neighbors = environment.getTileNeighbors(current);
            for (int i = 0; i < neighbors.size(); i++) {
                if(closedSet.contains(neighbors.get(i))){
                    continue;
                }
                double tentGScore = current.getGScore() + distanceBetweenTiles(current, (Tile) neighbors.get(i));
                if(!openSet.contains(neighbors.get(i))){
                    openSet.add(neighbors.get(i));
                }else if(tentGScore <= ((Tile) neighbors.get(i)).getGScore()){
                    continue;
                }
                ((Tile) neighbors.get(i)).setCameFrom(current);
                ((Tile) neighbors.get(i)).setgScore(tentGScore);
                ((Tile) neighbors.get(i)).setfScore(((Tile) neighbors.get(i)).getGScore() + distanceBetweenTiles((Tile) neighbors.get(i), endTile));

            }

        }
        return null;
    }

    public int distanceBetweenTiles(Tile start, Tile end) {
        return (int) (Math.sqrt(Math.pow((Math.abs(start.getX() - end.getX())), 2) + Math.pow((Math.abs(start.getY() - end.getY())), 2)) * 10);
    }

    public Tile getCurrent() {
        Tile current = new Tile(0,0,55555555);
        current.setfScore(999999999);
        for (int i = 0; i < openSet.size(); i++) {
            if (((Tile) openSet.get(i)).getFScore() < current.getFScore()) {
                current = (Tile) openSet.get(i);
            }
        }
        return current;
    }

    public ArrayList reconstructPath(Tile end){
        Tile currentStep = end;
        ArrayList path = new ArrayList();
        while (currentStep.getCameFrom() != null){
            path.add(currentStep.getCameFrom());
            currentStep = currentStep.getCameFrom();
        }
        Collections.reverse(path);
        return path;
    }

}


public class Environment {
    private final static Random randomGenerator = new Random();
    private Tile[][] grid;
    private int rocksAmount;
    private int treesAmount;
    private int height;
    private int width;

    public Environment(int rocks, int trees){
        this.rocksAmount = rocks;
        this.treesAmount = trees;
    }

    public Environment(){}

    public int[] getRandomPos(float terrainWidth, float terrainHeight, float positionWidth, float positionHeight){
        int x = randomGenerator.nextInt((int) ((terrainWidth-positionWidth)/positionWidth+positionWidth/2));
        int z = randomGenerator.nextInt((int) ((terrainHeight-positionHeight)/positionHeight+positionHeight/2-0.5));
        while(true){
            if(grid[x][z].getBlocked() != false){
                x = randomGenerator.nextInt((int) ((terrainWidth-positionWidth)/positionWidth+positionWidth/2));
                z = randomGenerator.nextInt((int) ((terrainHeight-positionHeight)/positionHeight+positionHeight/2-0.5));
            } else {
                break;
            }
        }
        grid[x][z].setBlocked(true);
        return new int[] {x, z};
    }

    public void setPositionsAmount(int width, int height){
        this.height = height;
        this.width = width;
        grid = new Tile[height][width];
        for(int i=0; i<height; i++){
            for(int n=0; n<width; n++){
                this.grid[i][n] = new Tile(((width/(width/2)) * n) + 1, ((height/(height/2)) * i) + 1, i*width + n);
            }
        }
        PathFinder pf = new PathFinder(this);
         ArrayList path = pf.findPath(21, 21, 1, 1);
         System.out.println("path:");
         for(int i=0; i<path.size(); i++){
         System.out.println("X: " + ((Tile) path.get(i)).getX() + "Y: " + ((Tile) path.get(i)).getY());
         }
         System.out.println("/path");
        System.out.println("widthheight" + width + height);
    }


    public Tile[][] getTiles(){
        return grid;
    }

    public Tile getTileByCoords(int x, int y){
        for(int i=0; i<this.height; i++){
            for(int n=0; n<this.width; n++){
                if(grid[i][n].getY() + 1 >= y && grid[i][n].getY() - 1 <= y && grid[i][n].getX() + 1 >= x && grid[i][n].getX() - 1 <= x){
                    return grid[i][n];
                }
            }
        }
        return null;
    }
    public Tile getTileByID(int id){
        for(int i=0; i<this.height; i++){
            for(int n=0; n<this.width; n++){
                if(grid[i][n].getID() == i*width + n){
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
    public int getRocksAmount() {
        return rocksAmount;
    }
    public int getTreesAmount() {
        return treesAmount;
    }

    public ArrayList getTileNeighbors(Tile tile){
        ArrayList neighbors = new ArrayList();

        // top row of neighbors
        if(tile.getY() > 1) {
            if(tile.getX() > 1) {
                neighbors.add(getTileByCoords((tile.getX() - 2), (tile.getY()) - 2));
            }
            neighbors.add(getTileByCoords((tile.getX()), (tile.getY()) - 2));
            if(tile.getX() < this.width - 1) {
                neighbors.add(getTileByCoords((tile.getX() + 2), (tile.getY()) - 2));
            }
        }

        //second row of neighbors (left and right of center)
        if(tile.getX() > 1) {
            neighbors.add(getTileByCoords((tile.getX() - 2), (tile.getY())));
        }

        if(tile.getX() < this.width - 1) {
            neighbors.add(getTileByCoords((tile.getX() + 2), (tile.getY())));
        }

        //bottom row of neighbors
        if(tile.getY() < this.height) {
            if(tile.getX() > 1) {
                neighbors.add(getTileByCoords((tile.getX() - 2), (tile.getY()) + 2));
            }
            neighbors.add(getTileByCoords((tile.getX()), (tile.getY()) + 2));
            if(tile.getX() < this.width - 1) {
                neighbors.add(getTileByCoords((tile.getX() + 2), (tile.getY()) + 2));
            }
        }
        return neighbors;
    }


}**/