package core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hannes on 24/05/2016.
 */
public class HealthPowerUpTest {
    Player player = new Player();
    private float firstHP;
    private float secondHP;


    private void initPlayerHP(){
        player.setHealth(50);
        firstHP = player.getHealth();
    }

    @Test
    public void setEffect() throws Exception {
        initPlayerHP();
        player.setHealth(player.getHealth()+20);
        secondHP = player.getHealth();
        assertTrue(firstHP==secondHP-20);
        assertTrue(secondHP==70);

    }


}