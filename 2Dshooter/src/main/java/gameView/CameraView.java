package gameView;

import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;

/**
 * Created by David on 2016-05-18.
 */
public class CameraView {

    private final GameView gameView;
    private final FlyByCamera flyCam;
    private final CameraNode camNode;


    public CameraView (GameView gameView){
        this.gameView = gameView;
        this.flyCam = gameView.getFlyByCamera();
        this.camNode = gameView.getCameraNode();
        initiateFixedCamera();
        flyCam.setEnabled(false);
    }
    private void initiateFixedCamera(){
        camNode.setLocalTranslation(new Vector3f(0f,-60f,-40));
        camNode.lookAt(new Vector3f(0,0,-4), new Vector3f(0,0,1));
    }

}
