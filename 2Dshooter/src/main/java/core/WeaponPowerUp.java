package core;

import gameView.GameView;

/**
 * Created by Simon on 2016-05-21.
 */
public class WeaponPowerUp extends PowerUp {

    public WeaponPowerUp(GameView gameView){
        super(gameView);
    }

    @Override
    public void setEffect(Player player) {
        player.setDamage(player.getDamage()+3);
    }
}
