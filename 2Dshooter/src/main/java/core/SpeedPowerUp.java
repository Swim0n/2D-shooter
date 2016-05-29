package core;

/**
 * Created by Hannes on 10/05/2016.
 */
public class SpeedPowerUp extends PowerUp {

    public SpeedPowerUp(Terrain terrain){
        super(terrain);
    }

    @Override
    public void setEffect(Player player) {
        player.setSpeed(player.getSpeed()+2);
    }

}
