package core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hannes on 24/05/2016.
 */
public class PowerUpTest {

    /** Testing all three power ups in this class */



    @Test
    public void testCorrectHpGiven() throws Exception {
        Player player = new Player();
        player.setHealth(10);
        float beforeHp = player.getHealth();
        Terrain terrain = new Terrain(1,1);
        terrain.setPositions(5,5,1,1);
        HealthPowerUp powerUp = new HealthPowerUp(terrain);
        powerUp.setEffect(player);
        float afterHp = player.getHealth();
        assertTrue(beforeHp<afterHp);
        /** health power up is set to increase health by 20 */
        assertTrue(beforeHp+20==afterHp);



    }

    @Test
    public void testCorrectSpeedGiven(){
        Player player = new Player();
        player.setSpeed(10);
        float beforeSpeed = player.getSpeed();
        Terrain terrain = new Terrain(1,1);
        terrain.setPositions(5,5,1,1);
        SpeedPowerUp powerUp = new SpeedPowerUp(terrain);
        powerUp.setEffect(player);
        float afterSpeed = player.getSpeed();
        assertTrue(afterSpeed>beforeSpeed);

    }

    @Test
    public void testCorrectDamageGiven(){
        Player player = new Player();
        player.setDamage(10);
        float beforeDmg = player.getDamage();
        Terrain terrain = new Terrain(1,1);
        terrain.setPositions(5,5,1,1);
        WeaponPowerUp powerUp = new WeaponPowerUp(terrain);
        powerUp.setEffect(player);
        float afterDmg = player.getDamage();
        assertTrue(beforeDmg<afterDmg);
    }


}