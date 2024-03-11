package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {

    public static void main(String[] args) throws IOException {
        ArrayList<UserFile> list = new ArrayList<>();

        ServerSocket ss = new ServerSocket(12000);
        while (true) {
            Socket socket = ss.accept();
            new Thread(new MyRunnable(socket,list)).start();
        }
    }
}
