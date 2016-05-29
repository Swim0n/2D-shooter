package view;

import com.jme3.input.FlyByCamera;
import com.jme3.math.Vector3f;
import com.jme3.scene.CameraNode;
import core.CameraModel;

/**
 * Created by David on 2016-05-18.
 */
public class CameraView {
    private final FlyByCamera flyCam;
    private final CameraNode camNode;
    private final CameraModel cameraData;

    public CameraView (WorldView worldView){
        this.flyCam = worldView.getFlyByCamera();
        this.camNode = worldView.getCameraNode();
        this.cameraData = worldView.getWorld().getCameraData();
        initiateFixedCamera();
        flyCam.setEnabled(false);
    }
    private void initiateFixedCamera(){
        camNode.setLocalTranslation(utils.Utils.vecMathToJMEVector3f(cameraData.getStaticPosition()));
        camNode.lookAt(utils.Utils.vecMathToJMEVector3f(cameraData.getStaticLookAt()),Vector3f.UNIT_Z);
    }

}
