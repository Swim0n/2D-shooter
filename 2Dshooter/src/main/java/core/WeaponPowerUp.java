package core;

public class WeaponPowerUp extends PowerUp {

    public WeaponPowerUp(Terrain terrain){
        super(terrain);
    }

    @Override
    public void setEffect(Player player) {
        player.setDamage(player.getDamage()+2);
    }
}
