package game.gameView;

import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import game.utils.ApplicationAssets;

/**
 * Created by David on 2016-05-18.
 */
public class CameraView {

    private final ApplicationAssets applicationAssets;
    private final FlyByCamera flyCam;
    private final Camera cam;
    private final CameraNode camNode;
    private boolean dynamicCameraEnabled;


    public CameraView (ApplicationAssets applicationAssets){
        this.applicationAssets = applicationAssets;
        this.flyCam = applicationAssets.getGameView().getFlyByCamera();
        this.cam = applicationAssets.getGameView().getCamera();
        this.camNode = applicationAssets.getGameView().getCameraNode();
        this.dynamicCameraEnabled = applicationAssets.getWorld().getCameraData().getDynamicCameraEnabled();
        if(dynamicCameraEnabled){
            initiateDynamicCamera();
        }else {
            initiateFixedCamera();
        }
        flyCam.setEnabled(false);
    }
    private void initiateFixedCamera(){
        camNode.removeControl(applicationAssets.getGameView().getCameraControl());
        camNode.setLocalTranslation(new Vector3f(0f,-60f,-40));
        camNode.lookAt(new Vector3f(0,1,0), new Vector3f(0,0,1));
    }

    public void initiateDynamicCamera(){
        camNode.addControl(applicationAssets.getGameView().getCameraControl());
    }
}
