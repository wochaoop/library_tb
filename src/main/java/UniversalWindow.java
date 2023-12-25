import javax.swing.*;
import java.awt.event.*;
import java.util.Stack;
class WindowOp extends WindowAdapter{//窗口关闭事件 如果直接把该事件放在窗口的构造方法中，在获取栈值是取的是空值
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
public class UniversalWindow extends JFrame {
    Stack<UniversalWindow> winStack=null;
    UniversalWindow(String title, int w, int h){//窗口构造方法
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
