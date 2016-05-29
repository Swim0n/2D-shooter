/**
 * Created by David on 2016-04-18.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        ViewInit viewInit = new ViewInit();
        if(viewInit.getWorldView().getWorld().isShutDown()){return;}
        new ControlInit(viewInit);
    }
}
