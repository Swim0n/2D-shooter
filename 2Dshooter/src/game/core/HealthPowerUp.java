package game.core;

/**
 * Created by Hannes on 10/05/2016.
 */
public class HealthPowerUp implements PowerUp {

    public HealthPowerUp(){
        setPosition();
    }

    public void setPosition() {
        /** hämta storlek på spelkarta och använda random metod för position */
    }

    public void setEffect(Player player) {
        player.setHealth(player.getHealth()+20);
    }
}
