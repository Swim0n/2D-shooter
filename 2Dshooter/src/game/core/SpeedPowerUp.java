package game.core;

import game.gameView.GroundView;

/**
 * Created by Hannes on 10/05/2016.
 */
public class SpeedPowerUp implements PowerUp {

    public SpeedPowerUp(){

        setPosition();
    }
    public void setPosition() {

    }

    public void setEffect(Player player) {
        player.setSpeed(player.getSpeed()+10);
    }
}
