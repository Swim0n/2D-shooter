package core;

/**
 * Represents a tile on the terrain grid
 */
public class Tile {

    private int x;
    private int y;
    private boolean blocked = false;
    private int tileID;

    // used in pathfinding
    private Tile cameFrom;
    private double gScore;
    private double fScore;

    public Tile(int x, int y, int id){
        this.x = x;
        this.y = y;
        this.tileID = id;
        this.gScore = 99999;
        this.fScore = 99999;
    }

    public void setBlocked(boolean blocked){
        this.blocked = blocked;
    }
    public boolean getBlocked(){
        return this.blocked;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public Tile getCameFrom(){
        return this.cameFrom;
    }
    public double getGScore(){
        return this.gScore;
    }
    public double getFScore(){
        return this.gScore;
    }
    public void setCameFrom(Tile cameFrom){
        this.cameFrom = cameFrom;
    }
    public void setgScore(double gScore){
        this.gScore = gScore;
    }
    public void setfScore(double gScore){
        this.gScore = gScore;
    }
}
