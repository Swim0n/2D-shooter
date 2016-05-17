package game.core;

import game.gameView.GroundView;
import game.utils.ApplicationAssets;

/**
 * Created by Hannes on 10/05/2016.
 */
public class SpeedPowerUp implements PowerUp {

    private ApplicationAssets applicationAssets;
    Ground groundModel = new Ground();
    private int [] position;
    private int x;
    private int z;

    public SpeedPowerUp(ApplicationAssets applicationAssets){
        this.applicationAssets = applicationAssets;
        setPosition();
    }


    public void setPosition() {
        position = applicationAssets.getWorld().getTerrain().getRandomPos(groundModel.getWidth(),groundModel.getHeight(),4,4);
        x = position[0];
        System.out.println(x);
        z = position[1];
        System.out.println(z);
    }


    public void setEffect(Player player) {
        player.setSpeed(player.getSpeed()+10);
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }
}
