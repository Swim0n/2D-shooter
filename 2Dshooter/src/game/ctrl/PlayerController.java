package game.ctrl;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by David on 2016-04-19.
 */
public class PlayerController extends AbstractControl{
    public boolean left,right,up,down;
    //movement speed for players
    private float speed = 100;

    public PlayerController(){}

    public void setLeft(boolean isPressed){
        left=isPressed;
    }
    public void setRight(boolean isPressed){
        right=isPressed;
    }
    public void setUp(boolean isPressed){up=isPressed;}
    public void setDown(boolean isPressed){
        down=isPressed;
    }



    protected void controlUpdate(float tpf) {
        if(left){
            spatial.move(tpf*-speed,0,0);
        }else if(right){
            spatial.move(tpf*speed,0,0);
        }else if(up){
            spatial.move(0,tpf*speed,0);
        }else if(down){
            spatial.move(0,tpf*-speed,0);
        }
    }

    protected void controlRender(RenderManager renderManager, ViewPort viewPort) {

    }
}
