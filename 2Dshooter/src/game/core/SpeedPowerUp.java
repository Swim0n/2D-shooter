package game.core;

import game.gameView.GroundView;
import game.utils.ApplicationAssets;

import javax.vecmath.Vector3f;

/**
 * Created by Hannes on 10/05/2016.
 */
public class SpeedPowerUp implements PowerUp {

    private ApplicationAssets applicationAssets;
    private Vector3f position;
    private float x;
    private float z;

    public SpeedPowerUp(ApplicationAssets applicationAssets){
        this.applicationAssets = applicationAssets;
        setPosition();
    }


    public void setPosition() {
        position = applicationAssets.getWorld().getTerrain().getRandomPos();
        x = position.x;
        z = position.z;

    }


    public void setEffect(Player player) {
        player.setSpeed(player.getSpeed()+3);
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }
}
