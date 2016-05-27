package core;

import gameView.GameView;

/**
 * Created by Hannes on 10/05/2016.
 */
public class HealthPowerUp extends PowerUp {

    public HealthPowerUp(GameView gameView){
        super(gameView);
    }

    @Override
    public void setEffect(Player player) {
        player.setHealth(player.getHealth()+20);
    }

}
