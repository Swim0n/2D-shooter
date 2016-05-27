package core;

/**
 * Created by Simon on 2016-05-21.
 */
public class WeaponPowerUp extends PowerUp {

    public WeaponPowerUp(Environment terrain){
        super(terrain);
    }

    @Override
    public void setEffect(Player player) {
        player.setDamage(player.getDamage()+3);
    }
}
