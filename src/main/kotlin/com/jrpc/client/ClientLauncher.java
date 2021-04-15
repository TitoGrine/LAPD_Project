package com.jrpc.client;

import com.jrpc.shared.ColorServer;
import org.eclipse.lsp4j.jsonrpc.Launcher;

import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ClientLauncher {
    public static void main(String[] args) throws Exception {
        // create the chat client
        Client client = new Client();

        String host = args[0];
        Integer port = Integer.valueOf(args[1]);
        // connect to the server
        try (Socket socket = new Socket(host, port)) {
            // open a JSON-RPC connection for the opened socket
            Launcher<ColorServer> launcher = new Launcher.Builder<ColorServer>()
                    .setRemoteInterface(ColorServer.class)
                    .setExecutorService(Executors.newSingleThreadExecutor())
                    .setInput(socket.getInputStream())
                    .setOutput(socket.getOutputStream())
                    .setLocalService(client)
                    .create();
            /*
             * Start listening for incoming messages.
             * When the JSON-RPC connection is closed
             * disconnect the remote client from the chat server.
             */
            Future<Void> future = launcher.startListening();
            client.start(launcher.getRemoteProxy());
            future.get();
        }
    }
}
