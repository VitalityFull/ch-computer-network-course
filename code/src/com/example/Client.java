package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Socket;

public class Client {
    private String username;

    public Client(String username) throws IOException {
        this.username = username;

        JFrame jFrame = new JFrame(username);
        //设置大小
        initJFrame(jFrame);
        //设置按钮，文本发送
        initButton(jFrame);
        //文本显示框，文本接收
        getTxtData(jFrame);
        //文件发送
        initFile(jFrame);
        //文件接收
        initFileGet(jFrame);

        jFrame.getContentPane().repaint();

        //Socket socket = new Socket("127.0.0.1", 12000);

    }

    //文件接收
    public void initFileGet(JFrame jFrame) {
        //文件选择器
        JButton fileChoose = new JButton("文件接收选择");
        fileChoose.setBounds(0, 340, 200, 30);

        //路径显示输出
        JTextField fileName = new JTextField();
        fileName.setBounds(0, 375, 300, 30);
        jFrame.getContentPane().add(fileName);

        //选择路径
        fileChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser("code");
                jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                jFileChooser.showOpenDialog(jFrame);

                File file = jFileChooser.getSelectedFile();
                if (file == null) {
                    System.out.println("取消了选择文件");
                } else {
                    fileName.setText(file.getAbsolutePath());
                }
            }
        });
        jFrame.getContentPane().add(fileChoose);

        //接收按钮
        JButton getFile = new JButton("接收");
        getFile.setBounds(310, 375, 80, 20);
        getFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //连接服务器
                    Socket socket = new Socket("127.0.0.1", 12000);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(username + "\n");
                    writer.write("acceptFile\n");
                    //清空缓存
                    writer.flush();
                    //获取输入流
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //获取文件名(这里用_起名是因为文件路径也用的fileName)
                    String file_Name = reader.readLine();
                    System.out.println(fileName.getText()+"\\"+file_Name);
                    //获取存储路径
                    FileWriter fw = new FileWriter(fileName.getText()+"\\"+file_Name);
                    //接收数据
                    int len;
                    char[] arr = new char[30];
                    while ((len = reader.read(arr)) != -1) {
                        fw.write(arr, 0, len);
                    }
                    System.out.println("接收完毕---");
                    //释放资源
                    fw.close();
                    reader.close();
                    writer.close();
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        jFrame.getContentPane().add(getFile);
    }

    //文件发送
    public void initFile(JFrame jFrame) {
        //文件选择器
        JButton fileChoose = new JButton("文件选择");
        fileChoose.setBounds(0, 210, 100, 30);

        JTextField fileName = new JTextField();
        fileName.setBounds(0, 245, 300, 30);
        jFrame.getContentPane().add(fileName);

        fileChoose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser("code\\Data");
                jFileChooser.showOpenDialog(jFrame);

                File file = jFileChooser.getSelectedFile();
                if (file == null) {
                    System.out.println("取消了选择文件");
                } else {
                    fileName.setText(file.getAbsolutePath());
                    if (fileName.getText().endsWith(".jpg") || fileName.getText().endsWith(".png")) {
                        try {
                            new ImageDialog(file);
                        } catch (MalformedURLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        jFrame.getContentPane().add(fileChoose);

        JLabel name = new JLabel("接受者:");
        name.setBounds(0, 280, 50, 20);
        jFrame.getContentPane().add(name);

        JTextField receiver = new JTextField();
        receiver.setBounds(60, 280, 150, 20);
        jFrame.getContentPane().add(receiver);

        JButton send = new JButton("发送");
        send.setBounds(410, 280, 80, 20);
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(+fileName.getText());
                File file = new File(fileName.getText());
                String username2 = receiver.getText();
                //建立连接
                try {
                    Socket socket = new Socket("127.0.0.1", 12000);
                    //传输基本信息
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(username + "\n");
                    writer.write("sendFile\n");
                    writer.write(username2 + "\n");
                    writer.write(file.getName() + "\n");
                    System.out.println(file.getName());
                    //传输文件
                    FileReader fr = new FileReader(file);
                    int len;
                    char[] arr = new char[30];
                    while ((len = fr.read(arr)) != -1) {
                        writer.write(arr, 0, len);
                    }
                    //释放资源
                    fr.close();
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jFrame.getContentPane().add(send);
    }

    //文本显示框
    public void getTxtData(JFrame jFrame) throws IOException {
        //从本地文件读取
        BufferedReader fr = new BufferedReader(new FileReader("code\\Data\\user\\" + username + "\\" + username + ".txt"));
        //每次读一行
        String line;
        JTextArea dataTxt = new JTextArea();
        while ((line = fr.readLine()) != null) {
            dataTxt.append(line + "\n");
        }
        fr.close();
        //文本显示框
        dataTxt.setBounds(0, 550, 900, 250);
        dataTxt.setLineWrap(true);
        //设置滚轮
        JScrollPane jScrollPane = new JScrollPane(dataTxt);
        jScrollPane.setBounds(0, 550, 900, 200);
        jFrame.getContentPane().add(jScrollPane);
        JLabel title = new JLabel("文本数据：");
        title.setBounds(80, 510, 100, 30);
        jFrame.getContentPane().add(title);

        JButton again = new JButton("刷新");
        again.setBounds(0, 510, 60, 30);
        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket socket = new Socket("127.0.0.1", 12000);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(username + "\n");
                    writer.write("acceptTxt\n");
                    writer.flush();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    FileWriter fw = new FileWriter("code\\Data\\user\\" + username + "\\" + username + ".txt", true);
                    //接收数据
                    int len;
                    int flag = 0;
                    char[] arr = new char[30];
                    while ((len = reader.read(arr)) != -1) {
                        fw.write(arr, 0, len);
                        flag++;
                    }
                    writer.close();
                    fw.close();
                    reader.close();
                    socket.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                dataTxt.setText("");
                try {
                    //从本地文件读取
                    BufferedReader fr = new BufferedReader(new FileReader("code\\Data\\user\\" + username + "\\" + username + ".txt"));
                    //每次读一行
                    String line;
                    while ((line = fr.readLine()) != null) {
                        dataTxt.append(line + "\n");
                    }
                    fr.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                jFrame.getContentPane().repaint();
                System.out.println("刷新成功");
            }
        });
        jFrame.getContentPane().add(again);
        jFrame.getContentPane().repaint();
    }

    //发送文本数据
    public void initButton(JFrame jFrame) {
        JLabel name = new JLabel("接受者:");
        name.setBounds(0, 10, 50, 20);
        jFrame.getContentPane().add(name);

        JTextField receiver = new JTextField();
        receiver.setBounds(60, 10, 150, 20);
        jFrame.getContentPane().add(receiver);

        JLabel dataName = new JLabel("请输入数据：");
        dataName.setBounds(0, 30, 80, 20);
        jFrame.getContentPane().add(dataName);

        JTextArea txtData = new JTextArea();
        txtData.setBounds(90, 30, 300, 150);
        jFrame.getContentPane().add(txtData);

        JButton sendTxt = new JButton("发送");
        sendTxt.setBounds(410, 30, 80, 20);
        sendTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username2 = receiver.getText();
                String data = txtData.getText();
                try {
                    Socket socket = new Socket("127.0.0.1", 12000);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    writer.write(username + "\n");
                    writer.write("sendTxt\n");
                    writer.write(username2 + "\n");
                    writer.write(data);
                    writer.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        jFrame.getContentPane().add(sendTxt);
    }

    //基础设置
    public void initJFrame(JFrame jFrame) {
        jFrame.setSize(1000, 800);
        //设置居中
        jFrame.setLocationRelativeTo(null);
        //设置关闭模式(点击×就关闭窗口)
        jFrame.setDefaultCloseOperation(3);
        //设置显示
        jFrame.setVisible(true);
        //取消JLabel居中放置
        jFrame.setLayout(null);
    }
}
