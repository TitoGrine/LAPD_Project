package com.jrpc.server;

import com.jrpc.shared.ColorClient;
import org.eclipse.lsp4j.jsonrpc.Launcher;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerLauncher {
    public static void main(String[] args) throws Exception {
        Server server = new Server();
        ExecutorService threadPool = Executors.newCachedThreadPool();

        Integer port = Integer.valueOf(args[0]);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.printf("Server is now running on port %d%n", port);

            threadPool.submit(() -> {
                while(true) {
                    Socket socket = serverSocket.accept();

                    Launcher<ColorClient> launcher = new Launcher.Builder<ColorClient>()
                            .setRemoteInterface(ColorClient.class)
                            .setExecutorService(threadPool)
                            .setInput(socket.getInputStream())
                            .setOutput(socket.getOutputStream())
                            .setLocalService(server)
                            .create();

                    Runnable removeClient = server.addClient(launcher.getRemoteProxy());

                    new Thread(() -> {
                        try {
                            launcher.startListening().get();
                            removeClient.run();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            });

            System.out.println("Enter any character to stop..");
            System.in.read();
            System.exit(0);
        }

    }
}