package core;


import javax.vecmath.Vector3f;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hannes on 28/05/2016.
 */
public class CameraModelTest {



    @Test
    public void testCameraPosition() throws Exception {
        CameraModel cameraModel = new CameraModel(true);

        Vector3f vector1 = new Vector3f();
        Vector3f vector2 = new Vector3f();

        /** vector1 should have bigger x value and the same z value as vector */
        vector1.set(cameraModel.getPosition(new Vector3f(1,0,5),new Vector3f(2,0,20)));
        vector2.set(cameraModel.getPosition(new Vector3f(1,0,1),new Vector3f(2,0,2)));

        assertTrue(vector1.z>vector2.z);
        assertTrue(vector1.x==vector2.x);
    }

    @Test
    public void testCameraFocus() throws Exception {
        CameraModel cameraModel = new CameraModel(true);
        Vector3f vector1 = new Vector3f();
        Vector3f vector2 = new Vector3f();

        vector1.set(cameraModel.getLookAt(new Vector3f(5,0,10), new Vector3f(0,0,0)));
        System.out.println(vector1);
        vector2.set(cameraModel.getLookAt(new Vector3f(0,0,0), new Vector3f(5,0,10)));
        System.out.println(vector2);

        /** method with the 2 same vectors should give same vector */
        assertTrue(vector1.length()==vector2.length());
    }

    @Test
    public void testCameraHeight() throws Exception {
        CameraModel cameraModel = new CameraModel(true);
        float height1 = cameraModel.getCamHeight(new Vector3f(10,0,20), new Vector3f(0,0,0));
        System.out.println(height1);
        float height2 = cameraModel.getCamHeight(new Vector3f(0,0,0), new Vector3f(10,0,20));
        System.out.println(height2);
        float height3 = cameraModel.getCamHeight(new Vector3f(0,0,0), new Vector3f(1,0,1));
        System.out.println(height3);

        /** same vectors in different order should give same result */
        assertTrue(height1==height2);
        /** height1 has a further gap and should have bigger camera height */
        assertTrue(height1>height3);
    }




}