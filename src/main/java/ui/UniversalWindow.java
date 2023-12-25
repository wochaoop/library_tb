package ui;

import javax.swing.*;
import java.util.Stack;

public class UniversalWindow extends JFrame {
    Stack<UniversalWindow> winStack=null;

    UniversalWindow(String title, int w, int h) {
        setSize(w, h);
        setTitle(title);
        setVisible(true);
        setLayout(null);
        setLocationRelativeTo(null);
    }

    public void setWinStack(UniversalWindow universalWindow, Stack<UniversalWindow> winStack) {
        this.winStack=winStack;
        winStack.push(universalWindow);
    }
}
