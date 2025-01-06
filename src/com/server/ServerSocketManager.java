package com.server;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerSocketManager {
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

    public static String viewAllSocket()  {
        StringBuffer buffer = new StringBuffer();
        for(String id : connections.keySet()) {

                buffer.append(id + " ");
        }
        return new String(buffer);
    }

    public static Map.Entry[] getAllIfo() {
        return connections.entrySet().toArray(new Map.Entry[connections.size()]);
    }

}
