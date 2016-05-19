package game.ctrl;

import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.control.Control;
import game.core.CameraModel;
import game.core.Player;
import game.utils.ApplicationAssets;
import game.utils.Utils;

import java.io.IOException;

/**
 * Created by David on 2016-05-18.
 */
public class CameraController extends CameraControl implements ActionListener {

    private final CameraModel cameraData;
    private final Player player2;
    private final Player player1;
    private final CameraNode cameraNode;
    private Vector3f position;
    private Vector3f lookAt;

    public CameraController (ApplicationAssets appAssets){
        this.cameraData = appAssets.getWorld().getCameraData();
        this.player1 = appAssets.getWorld().getPlayer1();
        this.player2 = appAssets.getWorld().getPlayer2();
        this.cameraNode = appAssets.getGameView().getCameraNode();
    }

    public void update(float tpf) {
        updatePosition();
        updateLookAt();
        cameraNode.setLocalTranslation(position);
        cameraNode.lookAt(lookAt,Vector3f.UNIT_Z);
    }

    private void updateLookAt() {
        lookAt = Utils.VecMathToJMEVector3f(cameraData.getLookAt(player1.getPosition(),player2.getPosition()));
    }

    private void updatePosition() {
        position = Utils.VecMathToJMEVector3f(cameraData.getPosition(player1.getPosition(), player2.getPosition()));
    }

    public void onAction(String s, boolean b, float v) {}
    public Control cloneForSpatial(Spatial spatial) {return null;}
    public void setSpatial(Spatial spatial) {}
    public void render(RenderManager renderManager, ViewPort viewPort) {}
    public void write(JmeExporter jmeExporter) throws IOException {}
    public void read(JmeImporter jmeImporter) throws IOException {}
}