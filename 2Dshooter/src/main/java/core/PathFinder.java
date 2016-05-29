package core;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import org.lwjgl.Sys;
import utils.ApplicationAssets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import static com.jme3.shader.VarType.Int;

/**
 * Created by Lukas on 2016-05-18.
 */


public class PathFinder {
    private Environment environment;
    private Tile[][] tiles;
    private ArrayList closedSet = new ArrayList();
    private ArrayList openSet = new ArrayList();
    private ApplicationAssets applicationAssets;
    private AssetManager assetManager;


    public PathFinder(Environment environment, ApplicationAssets applicationAssets, AssetManager assetManager) {
        this.applicationAssets = applicationAssets;
        this.assetManager = assetManager;
        this.environment = environment;
        this.tiles = environment.getTiles();
    }


    public ArrayList findPath(int startX, int startY, int endX, int endY){

        clearTiles();

        Tile startTile;
        Tile endTile;

        if(startY > 20){
            startTile = environment.getTileByCoords(startX, startY - 4);
        }else{
            startTile = environment.getTileByCoords(startX, startY);
        }

        if(endY > 20){
            endTile = environment.getTileByCoords(endX, endY - 4);
        }else{
            endTile = environment.getTileByCoords(endX, endY);
        }

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
                }else if(tentGScore >= ((Tile) neighbors.get(i)).getGScore()){
                    continue;
                }

                ((Tile) neighbors.get(i)).setCameFrom(current);
                ((Tile) neighbors.get(i)).setgScore(tentGScore);
                ((Tile) neighbors.get(i)).setfScore(((Tile) neighbors.get(i)).getGScore() + distanceBetweenTiles((Tile) neighbors.get(i), endTile));

            }

        }
        return null;
    }

    public double distanceBetweenTiles(Tile start, Tile end) {
        int diffX = Math.abs((start.getX() + 100) - (end.getX() + 100));
        int diffY = Math.abs((start.getY() + 100) - (end.getY() + 100));
        return (Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) * 10);
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
        while (currentStep != null){
            path.add(currentStep);
            currentStep = currentStep.getCameFrom();
        }
        Collections.reverse(path);
        return path;
    }

    public void clearTiles(){
        for (int i = 0; i < environment.getTiles().length; i++) {
            for (int j = 0; j < environment.getTiles()[0].length; j++) {
                environment.getTiles()[i][j].setCameFrom(null);
                environment.getTiles()[i][j].setgScore(99999);
                environment.getTiles()[i][j].setfScore(99999);

                this.openSet.clear();
                this.closedSet.clear();

            }
        }
    }

}
