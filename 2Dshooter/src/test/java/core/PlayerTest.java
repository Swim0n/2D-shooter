package core;

import org.junit.Test;

import javax.vecmath.Vector3f;
import static org.junit.Assert.*;

/**
 * Created by Hannes on 24/05/2016.
 */
public class PlayerTest {

    @Test
    public void testWalkDirection(){
        Player player = new Player();
        player.left = true;
        player.up = true;
        Vector3f vector = new Vector3f();
        vector.set(player.getWalkingDirection());
        Vector3f correctDirectionVector = new Vector3f();
        correctDirectionVector.set((player.getDiagonalSpeed()*-1),0,player.getDiagonalSpeed());
        /** if one walkDirection is working correctly all should as they are implemented the same way */
        assertTrue(vector.length() == correctDirectionVector.length());
    }

    @Test
    public void testIfDmgWorks(){
        Player player = new Player();
        player.setHealth(100);
        float firstHp = player.getHealth();
        player.takeDamage(35);
        float afterDmgHp = player.getHealth();
        /** 100 = 100-35+35 */
        assertTrue(firstHp == afterDmgHp+35);
    }

    @Test
    public void testOverload(){
        Player player = new Player();
        player.overload();
        assertTrue(player.getShotMeterPercent()==100);

    }
    @Test
    public void testIncrementWins(){
        Player player1 = new Player();
        Player player2 = new Player();
        player1.incWins();
        player1.incWins();
        player2.incWins();
        assertTrue(player1.getWins()-1 == player2.getWins());
    }

    @Test
    public void testResetRound(){
        Player player1 = new Player();
        player1.setHealth(50);
        player1.reset();
        assertTrue(player1.getWins()==1);
        assertTrue(player1.getHealth()==100);


    }

    @Test
    public void testGunRotation(){
        Player player = new Player();
        player.gunLeft=true;
        /** -140f is a static value set in the setStandard() method */
        assertTrue(player.getGunRotation()==-140f);
    }

}