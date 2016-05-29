import java.io.IOException;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        ViewInit viewInit = new ViewInit();
        if(viewInit.getWorldView().getWorld().isShutDown()){return;}
        new ControlInit(viewInit);
    }
}
