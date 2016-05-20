package game.core;

import game.gameView.GroundView;
import game.utils.ApplicationAssets;

import javax.vecmath.Vector3f;

/**
 * Created by Hannes on 10/05/2016.
 */
public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(ApplicationAssets applicationAssets){
        super(applicationAssets);
    }

    @Override
    public void setEffect(Player player) {
        player.setSpeed(player.getSpeed()+3);
    }

}
