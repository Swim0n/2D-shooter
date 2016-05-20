package game.core;

import game.utils.ApplicationAssets;

import javax.vecmath.Vector3f;

/**
 * Created by Hannes on 10/05/2016.
 */
public abstract class PowerUp {
    protected ApplicationAssets applicationAssets;
    protected Vector3f position;
    protected float x;
    protected float z;

    public PowerUp(ApplicationAssets applicationAssets){
        this.applicationAssets = applicationAssets;
        setPosition();
    }

    public void setPosition() {
        position = applicationAssets.getWorld().getTerrain().getRandomPos();
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
