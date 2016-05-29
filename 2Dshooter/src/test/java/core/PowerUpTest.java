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
        Environment environment = new Environment(1,1);
        environment.setPositions(5,5,1,1);
        HealthPowerUp powerUp = new HealthPowerUp(environment);
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
        Environment environment = new Environment(1,1);
        environment.setPositions(5,5,1,1);
        SpeedPowerUp powerUp = new SpeedPowerUp(environment);
        powerUp.setEffect(player);
        float afterSpeed = player.getSpeed();
        assertTrue(afterSpeed>beforeSpeed);

    }

    @Test
    public void testCorrectDamageGiven(){
        Player player = new Player();
        player.setDamage(10);
        float beforeDmg = player.getDamage();
        Environment environment = new Environment(1,1);
        environment.setPositions(5,5,1,1);
        WeaponPowerUp powerUp = new WeaponPowerUp(environment);
        powerUp.setEffect(player);
        float afterDmg = player.getDamage();
        assertTrue(beforeDmg<afterDmg);
    }


}