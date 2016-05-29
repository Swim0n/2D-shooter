package core;


/**
 * Created by Hannes on 10/05/2016.
 */
public class HealthPowerUp extends PowerUp {

    public HealthPowerUp(Environment terrain){
        super(terrain);
    }

    @Override
    public void setEffect(Player player) {
        player.setHealth(player.getHealth()+12);
    }

}
