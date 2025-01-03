package com.server;

import java.net.Socket;
import java.util.HashMap;

public class ServerSocketManage {
    static HashMap<String, Socket> connections = new HashMap<>();

    /**
     *
     * @param id userID
     * @param socket
     */
    public static void addSocket(String id, Socket socket) {
        connections.put(id, socket);
    }

    public static Socket getSocket(String id) {
        return connections.get(id);
    }
}
