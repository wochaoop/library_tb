package com.library.service;

import java.awt.*;
public class Const {
    public  static  Font font=new Font("宋体", Font.BOLD,20);
    public  static String[] tableBookTitle = {"图书编号","书名","作者","书号","价格","库存数量"};
    //bookinfo id bookname  author isbn price storage
    public  static String[] tableBorrowTitle = {"借阅编号","读者","图书","借出时间","归还时间"};
    //borrow id readerid bookid borrowTime backTime
    public  static String[] tableReaderTitle = {"读者编号","姓名","性别","证件类型","证件号","电话","邮箱"};
    //reader id   name sex paperType  paperNO tel email
    //菜单文本
    public  static  String[]  menuTitle = {"系统设置","读者管理","图书管理","借阅管理","用户管理"};
    //子菜单文本
    public  static  String[] menuItemsTitle ={"刷新","重启","版本号","添加读者","修改读者","删除读者","添加图书","修改图书","删除图书","添加借阅记录","修改借阅记录","删除借阅记录","用户注册","用户删除","用户登出","16"};
}
