package core;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Hannes on 29/05/2016.
 */
public class WorldTest {

    @Test
    public void testGameOver(){
        World world = new World(1,1,true);
        world.getPlayer1().setHealth(20);
        world.getPlayer2().setHealth(0);
        world.getPlayer2().setSpeed(2002);
        world.getPlayer1().setSpeed(35);
        /** players should have the same stats after this method call */
        world.setGameOver();
        assertEquals(100, world.getPlayer1().getHealth(), world.getPlayer2().getHealth());
        assertTrue(world.getPlayer2().getSpeed()==world.getPlayer1().getSpeed());


    }
}
