package core;

import utils.ApplicationAssets;

/**
 * Created by Simon on 2016-05-21.
 */
public class WeaponPowerUp extends PowerUp {

    public WeaponPowerUp(ApplicationAssets applicationAssets){
        super(applicationAssets);
    }

    @Override
    public void setEffect(Player player) {
        player.setDamage(player.getDamage()+3);
    }
}
