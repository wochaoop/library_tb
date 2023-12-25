package com.library.service;

import javax.swing.*;
import java.util.Stack;

public class UniversalWindow extends JFrame {
    Stack<UniversalWindow> winStack=null;
    protected UniversalWindow(String title, int w, int h){//窗口构造方法
        setSize(w,h);//设置窗口大小
        setTitle(title);//设置窗口标题
        setVisible(true);//设置可见性
        setLayout(null);//设置布局方式
        setLocationRelativeTo(null);//设置屏幕居中显示
    }
    public void setWinStack(UniversalWindow universalWindow,Stack<UniversalWindow> winStack){//把栈放入到成员变量中，同时把当前窗口放入栈中
        this.winStack=winStack;
        winStack.push(universalWindow);
    }
}