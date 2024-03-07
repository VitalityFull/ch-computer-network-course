package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


public class LoginJFrame extends JFrame {
    static ArrayList<User> users = new ArrayList<>();

    static {
        users.add(new User("chen", "123"));
        users.add(new User("zhang", "123"));
        users.add(new User("liu","123"));
    }

    public LoginJFrame() {
        //页面初始化
        initJFrame();

        //输入框初始化
        initJTextField();

        //刷新页面
        this.getContentPane().repaint();

    }

    //登录判断
    public boolean login(User user) {
        int len = users.size();
        for (int i = 0; i < len; i++) {
            if (users.get(i).equals(user))
                return true;
        }
        return false;
    }

    //输入框与按钮初始化
    public void initJTextField() {

        //用户名字段
        JLabel username = new JLabel("用户名: ");
        username.setBounds(150, 100, 60, 20);
        this.getContentPane().add(username);

        //用户名输入框
        JTextField usernameTextField = new JTextField();
        usernameTextField.setBounds(200, 100, 150, 20);
        this.getContentPane().add(usernameTextField);

        //密码字段
        JLabel password = new JLabel("密码: ");
        password.setBounds(150, 150, 60, 20);
        this.getContentPane().add(password);

        //密码输入框
        JPasswordField passwordTextField = new JPasswordField();
        passwordTextField.setBounds(200, 150, 150, 20);
        this.getContentPane().add(passwordTextField);

        //登录按钮
        JButton loginButton = new JButton("登录");
        loginButton.setBounds(150, 200, 100, 20);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                if (username.isBlank() || password.isBlank())
                    new MyJDialog("用户名密码不能为空");
                else {
                    if (!login(new User(username, password)))
                        new MyJDialog("用户名或密码错误");
                    else {
                        try {
                            dispose();
                            new Client(username);
                            System.out.println("登录成功");
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });
        //注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.setBounds(260, 200, 100, 20);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterJFrame(users);
            }
        });
        this.getContentPane().add(loginButton);
        this.getContentPane().add(registerButton);

    }

    //界面大小初始化
    public void initJFrame() {
        //设置大小
        this.setSize(500, 400);
        //设置名称
        this.setTitle("登录界面");
        //设置居中
        this.setLocationRelativeTo(null);
        //设置关闭模式(点击×就关闭窗口)
        this.setDefaultCloseOperation(3);
        //设置显示
        this.setVisible(true);
        //取消JLabel居中放置
        this.setLayout(null);
    }
}
