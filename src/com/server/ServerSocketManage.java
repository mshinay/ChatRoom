package com.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class ServerSocketManage {
    static HashMap<String,Socket> connections = new HashMap<>();

    /**
     *
     * @param id userID
     * @param socket
     */
    public static void addSocket(String id,Socket socket ) {
        connections.put(id,socket);
    }

    public static Socket getSocket(String id) {
        return connections.get(id);
    }

    public static void removeSocket(String id) throws IOException {

        connections.remove(id).close();
    }

}
