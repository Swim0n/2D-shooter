package game.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by David on 2016-04-19.
 */
public class PlayerController extends AbstractControl{
    private boolean left,right,up,down;
    public void setLeft(boolean isPressed){
        left=isPressed;
    }
    public void setRight(boolean isPressed){
        right=isPressed;
    }
    public void setUp(boolean isPressed){
        up=isPressed;

    }
    public void setDown(boolean isPressed){
        down=isPressed;
    }

    protected void controlUpdate(float v) {
        if(left){

        }else if(right){

        }else if(up){

        }
        else if(down){

        }
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
