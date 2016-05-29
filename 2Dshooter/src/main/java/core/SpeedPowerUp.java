package core;

/**
 * Created by Hannes on 10/05/2016.
 */
public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(Environment terrain){
        super(terrain);
    }

    @Override
    public void setEffect(Player player) {
        player.setSpeed(player.getSpeed()+2);
    }

}
