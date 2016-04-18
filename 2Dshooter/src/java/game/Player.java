package game;

import com.jme3.math.Vector2f;

/**
 * Created by David on 2016-04-15.
 */
public class Player {

    private int positionX;
    private int positionY;

    public void up(){
        this.positionY++;
    }
    public void down(){
        this.positionY--;
    }
    public void left(){
        this.positionX--;
    }

    public int getPositionX(){
        return positionX;
    }
    public int getPositionY() {
        return positionY;
    }


}
