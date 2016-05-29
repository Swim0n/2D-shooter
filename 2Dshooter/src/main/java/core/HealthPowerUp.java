package core;

public class HealthPowerUp extends PowerUp {

    public HealthPowerUp(Terrain terrain){
        super(terrain);
    }

    @Override
    public void setEffect(Player player) {
        player.setHealth(player.getHealth()+12);
    }
}
