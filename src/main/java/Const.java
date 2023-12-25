import java.awt.*;

public class Const {
    public  static  Font font=new Font("宋体",Font.CENTER_BASELINE,20);
    public  static String[] tableBookTitle = {"图书编号","书名","作者","书号","价格","库存数量"};
    //bookinfo id bookname  author isbn price storage
    public  static String[] tableBorrowTitle = {"借阅编号","读者","图书","借出时间","归还时间"};
    //borrow id readerid bookid borrowTime backTime
    public  static String[] tableReaderTitle = {"读者编号","姓名","性别","证件类型","证件号","电话","邮箱"};
    public  static String[] tablemanagerTitle = {"编号","姓名","密码","删除","修改"};
    //reader id   name sex paperType  paperNO tel email
    //菜单文本
    public  static  String[]  menuTitle = {"系统信息","读者管理","图书管理","借阅管理","用户管理"};
    //子菜单文本
    public  static  String[] menuItemsUser ={"注册用户","修改用户","查询用户","删除用户"};
    public  static  String[] menuItemsBook ={"添加图书","修改图书","删除图书"};
    public  static  String[] menuItemsBorrow ={"图书借阅","图书归还"};
    public  static  String[] menuItemsReader = {"添加读者","修改读者","删除读者"};
    public  static  String[] menuItemsSystem = {"信息设置","关于我们"};
}
