package com.library.service;

import java.awt.event.*;
public class WindowOp extends WindowAdapter{//窗口关闭事件 如果直接把该事件放在窗口的构造方法中，在获取栈值是取的是空值
    @Override
    public void windowClosing(WindowEvent e) {
        UniversalWindow temp=(UniversalWindow)e.getWindow();
        if (temp.winStack.size()>1){//栈>1
            UniversalWindow currentWindow =temp.winStack.pop();//出栈
            currentWindow.dispose();
            UniversalWindow previousWindow =temp.winStack.peek();//取栈顶窗口放置在前一个窗口
            previousWindow.setVisible(true);//设置为可见
        } else {//当栈为空时，当前窗口实际上就剩下主方法中创建的那个窗口了
            System.exit(0);
        }
    }
}

