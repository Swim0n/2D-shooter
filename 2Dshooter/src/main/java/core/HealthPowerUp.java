package core;

import utils.ApplicationAssets;

/**
 * Created by Hannes on 10/05/2016.
 */
public class HealthPowerUp extends PowerUp {

    public HealthPowerUp(ApplicationAssets applicationAssets){
        super(applicationAssets);
    }

    @Override
    public void setEffect(Player player) {
        player.setHealth(player.getHealth()+20);
    }

}
