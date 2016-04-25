package game.core;

import com.jme3.math.Vector3f;

/**
 * Created by David on 2016-04-15.
 */
public class Player {

    private float radius;
    private float height;
    private float mass;
    private Vector3f playerDirection;


    public Player(float radius, float height, float mass){
        this.radius = radius;
        this.height = height;
        this.mass = mass;
        playerDirection.set(0f,0f,0f);
    }


    public void setPlayerDirection(Vector3f playerDirection){
        this.playerDirection = playerDirection;
    }

    public Vector3f getPlayerDirection(){
        return this.playerDirection;
    }

}
