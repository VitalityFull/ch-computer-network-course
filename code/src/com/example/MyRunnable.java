package com.example;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class MyRunnable implements Runnable {
    private Socket socket;

    private ArrayList<UserFile> list;

    public MyRunnable(Socket socket, ArrayList<UserFile> list) {
        this.socket = socket;
        this.list = list;
    }

    @Override
    public void run() {
        try {
            System.out.println("连接成功");
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //获取连接者
            String name = reader.readLine();
            //判断连接类型
            String flag = reader.readLine();

            if (flag.equals("sendTxt")) {
                txtSend(socket, reader, name);
            } else if (flag.equals("acceptTxt")) {
                txtAccept(socket, name);
            } else if (flag.equals("sendFile")) {
                fileSend(socket, reader, name);
            } else {
                fileAccept(socket, name);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getFilePath(String name) {
        int len = list.size();
        for (int i = 0; i < len; i++) {
            if (list.get(i).getName().equals(name)) {
                return list.get(i).getFilePath();
            }
        }
        return null;
    }

    //处理文件接受
    public void fileAccept(Socket socket, String name) throws IOException {
        System.out.println("acceptFile");
        //获取输出流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //获取文件路径
        String filePath = getFilePath(name);
        if (filePath == null) {
            System.out.println("没有需要接收的文件");
            writer.close();
            socket.close();
        }
        //获取文件对象
        File file = new File(filePath);
        //发送文件名
        writer.write(file.getName() + "\n");
        //获取输入流
        FileReader fr = new FileReader(file);
        //发送文件
        int len;
        char[] arr = new char[30];
        while ((len = fr.read(arr)) != -1) {
            writer.write(arr, 0, len);
        }
        //释放资源
        fr.close();
        writer.close();
        socket.close();
    }

    //处理文件发送
    public void fileSend(Socket socket, BufferedReader reader, String name) throws IOException {
        System.out.println("sendFile");
        //获取接受者
        String receiver = reader.readLine();
        //获取文件名
        String filename = reader.readLine();
        //写入本地
        FileWriter fw = new FileWriter("code\\Data\\server\\" + receiver + "\\" + "by-" + name + "-" + filename);
        //记录路径
        String filePath = "code\\Data\\server\\" + receiver + "\\" + "by-" + name + "-" + filename;
        System.out.println(filePath);
        //记录
        list.add(new UserFile(receiver, filePath));
        int len;
        char[] arr = new char[30];
        while ((len = reader.read(arr)) != -1) {
            fw.write(arr, 0, len);
        }
        //释放资源
        fw.close();
        reader.close();
    }

    //处理接受Txt
    public void txtAccept(Socket socket, String name) throws IOException {
        System.out.println("acceptTxt");
        //获取输出流
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        //获取本地文件的输入流
        FileReader fr = new FileReader("code\\Data\\server\\" + name + "\\" + name + ".txt");
        int len;
        char[] arr = new char[30];
        while ((len = fr.read(arr)) != -1) {
            writer.write(arr, 0, len);
        }
        fr.close();
        //清空数据
        FileWriter fw = new FileWriter("code\\Data\\server\\" + name + "\\" + name + ".txt");
        fw.close();
        writer.close();
        socket.close();
    }

    //处理发送txt
    public void txtSend(Socket socket, BufferedReader reader, String name) throws IOException {
        System.out.println("sendTxt");
        //寻找接受者
        String receiver = reader.readLine();
        System.out.println("发送给" + receiver);
        //写入本地
        FileWriter fw = new FileWriter("code\\Data\\server\\" + receiver + "\\" + receiver + ".txt");
        fw.write(name + "(" + LocalDateTime.now() + ")" + ":\n");
        int len;
        char[] arr = new char[30];
        while ((len = reader.read(arr)) != -1) {
            fw.write(arr, 0, len);
        }
        fw.write("\n");
        //释放资源
        fw.close();
        reader.close();
        System.out.println("发送成功");
        socket.close();
    }
}
