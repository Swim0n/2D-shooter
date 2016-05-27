package core;

import gameView.GameView;

/**
 * Created by Hannes on 10/05/2016.
 */
public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(GameView gameView){
        super(gameView);
    }

    @Override
    public void setEffect(Player player) {
        player.setSpeed(player.getSpeed()+3);
    }

}
