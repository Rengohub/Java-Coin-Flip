/*
 * Auther: Patrick Rakowicz | Joey Bauer
 * */

package server;

import server.controller.ServerController;
import server.model.ServerModel;

public class MainServer {
    public static void main(String[] args) {
        ServerModel model = new ServerModel();
        ServerController server = new ServerController(12345, model);
        server.start();
    }
}
