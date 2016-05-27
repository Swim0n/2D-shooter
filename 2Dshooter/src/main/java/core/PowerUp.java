package core;

import javax.vecmath.Vector3f;

/**
 * Created by Hannes on 10/05/2016.
 */
public abstract class PowerUp {
    private final Environment terrain;
    protected Vector3f position;
    protected float x;
    protected float z;

    public PowerUp(Environment terrain){
        this.terrain = terrain;
        setPosition();
    }

    public void setPosition() {
        position = terrain.getRandomPos();
        this.x = position.x;
        this.z= position.z;
    }

    public abstract void setEffect(Player player);

    public float getX(){
        return this.x;
    }
    public float getZ(){
        return this.z;
    }

}
