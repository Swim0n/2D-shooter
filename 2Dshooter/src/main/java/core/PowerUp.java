package core;

import gameView.GameView;

import javax.vecmath.Vector3f;

/**
 * Created by Hannes on 10/05/2016.
 */
public abstract class PowerUp {
    private final GameView gameView;
    protected Vector3f position;
    protected float x;
    protected float z;

    public PowerUp(GameView gameView){
        this.gameView = gameView;
        setPosition();
    }

    public void setPosition() {
        position = gameView.getWorld().getTerrain().getRandomPos();
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
