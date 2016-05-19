package game.core;

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
    public CameraModel(boolean dynamicCamEnabled){
        this.dynamicCamEnabled = dynamicCamEnabled;

    }

    public Vector3f getPosition (Vector3f player1Pos, Vector3f player2Pos){
        position.set(((player1Pos.x+player2Pos.x)/2),(-80f),((player1Pos.z+player2Pos.z)/2));
        if(position.equals(null)){
            position.set(staticPosition);
        }
        return position;
    }

    public Vector3f getLookAt(Vector3f player1Pos, Vector3f player2Pos) {
        lookAt.set(((player1Pos.x+player2Pos.x)/2),1,((player1Pos.z+player2Pos.z)/2));
        if(lookAt.equals(null)){
            lookAt.set(staticLookAt);
        }
        return lookAt;
    }

    public boolean getDynamicCameraEnabled() {
        return dynamicCamEnabled;
    }

    public void setDynamicCameraEnabled(boolean enabled) {
        this.dynamicCamEnabled = enabled;
    }


}
