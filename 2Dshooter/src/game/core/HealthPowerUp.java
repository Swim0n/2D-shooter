package game.core;

import game.utils.ApplicationAssets;

import javax.vecmath.Vector3f;

/**
 * Created by Hannes on 10/05/2016.
 */
public class HealthPowerUp implements PowerUp {

    private ApplicationAssets applicationAssets;

    private Vector3f position;
    private float x;
    private float z;

    public HealthPowerUp(ApplicationAssets applicationAssets){
        this.applicationAssets = applicationAssets;
        setPosition();
    }

    public void setPosition() {
        position = applicationAssets.getWorld().getTerrain().getRandomPos();
        this.x = position.x;
        this.z= position.z;

    }

    public void setEffect(Player player) {
        player.setHealth(player.getHealth()+20);


    }
    public float getX(){
        return this.x;
    }
    public float getZ(){
        return this.z;
    }
}
