package game.core;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.Collections;
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