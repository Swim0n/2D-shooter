package game.core;


import javax.vecmath.Matrix4f;
import javax.vecmath.Vector3f;

/**
 * Created by David on 2016-05-18.
 */
public class CameraModel {
    private boolean dynamicCamEnabled;
    private Vector3f position = new Vector3f();
    private Vector3f lookAt = new Vector3f();
    private final Vector3f staticPosition= new Vector3f(0f,-80f,0);
    private final Vector3f staticLookAt = new Vector3f(0f,1f,0f);
    private float height;
    public CameraModel(boolean dynamicCamEnabled){
        this.dynamicCamEnabled = dynamicCamEnabled;

    }

    public Vector3f getPosition (Vector3f player1Pos, Vector3f player2Pos){
        position.set((
                (player1Pos.x+player2Pos.x)/2),
                (-getCamHeight(player1Pos,player2Pos)),
                ((player1Pos.z+player2Pos.z)/2));
        if(position.equals(null)){
            position.set(staticPosition);
        }
        return position;
    }

    public Vector3f getLookAt(Vector3f player1Pos, Vector3f player2Pos) {
        lookAt.set((
                (player1Pos.x+player2Pos.x)/2),
                (1),
                ((player1Pos.z+player2Pos.z)/2));
        if(lookAt.equals(null)){
            lookAt.set(staticLookAt);
        }
        return lookAt;
    }

    public float getCamHeight(Vector3f player1Pos, Vector3f player2Pos){
       height = ((float)
               Math.sqrt(Math.pow((player2Pos.x - player1Pos.x),2) +
                       Math.pow((player2Pos.y - player1Pos.y),2) +
                       Math.pow((player2Pos.z - player1Pos.z),2)
       )*2);
        if(height<35f){
            height = 35f;
        }else if(height>80f){
            height = 80f;
        }

        return height;
    }
    public boolean getDynamicCameraEnabled() {
        return dynamicCamEnabled;
    }

    public void setDynamicCameraEnabled(boolean enabled) {
        this.dynamicCamEnabled = enabled;
    }


}