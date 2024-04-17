package server;

public class MainServer {
    public static void main(String[] args) {
        ServerModel model = new ServerModel();
        ServerController server = new ServerController(5000, model);
        server.start();
    }
}
