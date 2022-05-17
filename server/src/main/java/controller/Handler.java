package controller;

import java.io.*;
import java.net.Socket;


public class Handler extends Thread {
    private static BufferedReader is;
    private static BufferedWriter os;
    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while (socket.isConnected()) {
                System.out.println("Пользователь с IP " + socket.getInetAddress().getHostName() + " в ожидании ответа");

                String json;
                json = is.readLine();
                System.out.println(json);

                new RequestChecker(json);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(String message) {
        try {
            os.write(message + "\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
