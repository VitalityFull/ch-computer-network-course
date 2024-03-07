package com.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegisterJFrame extends JFrame {
    public RegisterJFrame(ArrayList<User> users) {
        initJFrame();
        initJTextField(users);
    }

    public void initJTextField(ArrayList<User> users) {

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

        //注册按钮
        JButton registerButton = new JButton("注册");
        registerButton.setBounds(150, 200, 210, 30);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = passwordTextField.getText();
                if (username.isBlank() || password.isBlank())
                    new MyJDialog("用户名密码不能为空");
                else {
                    while (!isOnly(users, username))
                        new MyJDialog("用户名重复");
                    users.add(new User(username, password));
                    new MyJDialog("注册成功!");
                    dispose();
                }
            }
        });
        //添加按钮
        this.getContentPane().add(registerButton);
    }

    //判断用户名是否重复
    public boolean isOnly(ArrayList<User> users, String username) {
        int len = users.size();
        for (int i = 0; i < len; i++) {
            if (users.get(i).getUsername().equals(username))
                return false;
        }
        return true;
    }

    //初始化
    public void initJFrame() {
        //设置大小
        this.setSize(500, 400);

        //设置名称
        this.setTitle("注册界面");

        //设置置顶
        this.setAlwaysOnTop(true);

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
