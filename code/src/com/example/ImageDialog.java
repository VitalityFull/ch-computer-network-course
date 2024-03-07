package com.example;

import javax.swing.*;
import java.io.File;
import java.net.MalformedURLException;

public class ImageDialog extends JDialog {
    public ImageDialog(File file) throws MalformedURLException {
        //设置大小
        this.setSize(300, 300);
        //设置置顶
        this.setAlwaysOnTop(true);
        //设置名称
        this.setTitle("图片预览");
        //设置居中
        this.setLocationRelativeTo(null);
        //设置不关闭就无法执行其他任务
        this.setModal(true);
        //放入图片
        JLabel jLabel = new JLabel(new ImageIcon(file.getPath()));
        jLabel.setBounds(0, 0, 270, 270);
        this.getContentPane().add(jLabel);
        //设置显示
        this.setVisible(true);
        //取消JLabel居中放置
        this.setLayout(null);
    }
}
