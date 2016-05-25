package gameView;

import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import utils.ApplicationAssets;

/**
 * Created by David on 2016-05-18.
 */
public class CameraView {

    private final ApplicationAssets applicationAssets;
    private final FlyByCamera flyCam;
    private final CameraNode camNode;


    public CameraView (ApplicationAssets applicationAssets){
        this.applicationAssets = applicationAssets;
        this.flyCam = applicationAssets.getGameView().getFlyByCamera();
        this.camNode = applicationAssets.getGameView().getCameraNode();
        initiateFixedCamera();
        flyCam.setEnabled(false);
    }
    private void initiateFixedCamera(){
        camNode.setLocalTranslation(new Vector3f(0f,-60f,-40));
        camNode.lookAt(new Vector3f(0,1,0), new Vector3f(0,0,1));
    }

}
