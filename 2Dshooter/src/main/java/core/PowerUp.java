package core;

import javax.vecmath.Vector3f;

/**
 * Created by Hannes on 10/05/2016.
 */
public abstract class PowerUp {
    private final Terrain terrain;
    protected Vector3f position;
    protected float x;
    protected float z;

    public PowerUp(Terrain terrain){
        this.terrain = terrain;
        setPosition();
    }

    public void setPosition() {
        position = terrain.getRandomPos(false);
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
