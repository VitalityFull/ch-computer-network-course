package com.example;

import javax.swing.*;

public class MyJDialog extends JDialog {
    public MyJDialog(String str) {
        //设置大小
        this.setSize(200, 150);
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置名称
        this.setTitle("warning");
        //设置居中
        this.setLocationRelativeTo(null);
        //设置不关闭就无法执行其他任务
        this.setModal(true);

        JLabel jLabel = new JLabel(str);
        jLabel.setBounds(0, 0, 200, 150);
        this.getContentPane().add(jLabel);

        //设置显示
        this.setVisible(true);

        //取消JLabel居中放置
        this.setLayout(null);
    }
}
