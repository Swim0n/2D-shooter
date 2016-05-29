package core;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Contains the logic of the AI
 */


public class AI {
    private Terrain terrain;
    private ArrayList closedSet = new ArrayList();
    private ArrayList openSet = new ArrayList();


    public AI(Terrain terrain) {
        this.terrain = terrain;
    }


    //finds an open path from one tile to another, based off of the aStar search algorithm
    public ArrayList findPath(int startX, int startY, int endX, int endY){

        clearTiles();

        Tile startTile;
        Tile endTile;

        if(startY > 20){
            startTile = terrain.getTileByCoords(startX, startY - 4);
        }else{
            startTile = terrain.getTileByCoords(startX, startY);
        }

        if(endY > 20){
            endTile = terrain.getTileByCoords(endX, endY - 4);
        }else{
            endTile = terrain.getTileByCoords(endX, endY);
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
            ArrayList neighbors = terrain.getTileNeighbors(current);



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

    //returns the distance between two tiles on the grid
    public double distanceBetweenTiles(Tile start, Tile end) {
        int diffX = Math.abs((start.getX() + 100) - (end.getX() + 100));
        int diffY = Math.abs((start.getY() + 100) - (end.getY() + 100));
        return (Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2)) * 10);
    }

    //returns the tile in the openSet with the lowest fScore
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

    //takes the end tile and uses the cameFrom variable to follow the calculated chain of tiles leading to the startTile
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

    //clears the tiles and the pathfinder object so that it can find a new path
    public void clearTiles(){
        for (int i = 0; i < terrain.getTiles().length; i++) {
            for (int j = 0; j < terrain.getTiles()[0].length; j++) {
                terrain.getTiles()[i][j].setCameFrom(null);
                terrain.getTiles()[i][j].setgScore(99999);
                terrain.getTiles()[i][j].setfScore(99999);

                this.openSet.clear();
                this.closedSet.clear();

            }
        }
    }

}
