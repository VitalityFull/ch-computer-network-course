package com.example;

import javax.swing.*;
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
        registerButton.addActionListener(e -> {
            String username1 = usernameTextField.getText();
            String password1 = passwordTextField.getText();
            if (username1.isBlank() || password1.isBlank())
                new MyJDialog("用户名密码不能为空");
            else {
                if (!isOnly(users, username1)) {
                    new MyJDialog("用户名重复");
                    return;
                }
                users.add(new User(username1, password1));
                new MyJDialog("注册成功!");
                dispose();
            }
        });
        //添加按钮
        this.getContentPane().add(registerButton);
    }

    //判断用户名是否重复
    public boolean isOnly(ArrayList<User> users, String username) {
        for (User user : users)
            if (user.getUsername().equals(username))
                return false;
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
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //设置显示
        this.setVisible(true);
        //取消JLabel居中放置
        this.setLayout(null);
    }
}
