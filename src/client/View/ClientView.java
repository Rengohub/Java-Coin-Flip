package client.View;

import javax.swing.*;

//import javax.xml.ws.Action;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ClientView extends JFrame {
    

                                        //////////     START GAME       //////////
    public ClientView() {
        new ClientGameStart();
    }

    public void cycleFrame() {
        setVisible(false);
    }

}
