package ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowOp extends WindowAdapter {

    @Override
    public void windowClosing(WindowEvent e) {
        UniversalWindow temp = (UniversalWindow) e.getWindow();
        if (temp.winStack.size() > 1) {
            UniversalWindow currentWindow = temp.winStack.pop();
            currentWindow.dispose();
            UniversalWindow previousWindow = temp.winStack.peek();
            previousWindow.setVisible(true);
        } else {
            System.exit(0);
        }
    }
}
