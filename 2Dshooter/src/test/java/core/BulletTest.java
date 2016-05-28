package core;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hannes on 28/05/2016.
 */
public class BulletTest {
    @Test
    public void testBulletSpeed() throws Exception {
        Bullet bullet = new Bullet();
        float bulletSpeed = bullet.getBulletSpeed();
        /** 160f is the default value set for bullet */
        assertTrue(bulletSpeed == 160f);
    }

}