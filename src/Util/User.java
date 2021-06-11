package Util;

import java.sql.*;
import java.util.ResourceBundle;

public class User {
    public static boolean login(String uname,String pword) throws ClassNotFoundException, SQLException {
        boolean login=false;
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Util/Account");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
        stm = con.createStatement();
        rs=stm.executeQuery("select * from login where username='"+uname+"' and password='"+pword+"'");
        if (rs.next()) {
            login=true;
        }
        rs.close();
        stm.close();
        con.close();
        return login;
    }
    public static boolean Modify(String oldpwd,String newpwd) throws ClassNotFoundException, SQLException {
        boolean change=false;
        Connection con = null;
        Statement stm = null;
        ResultSet rs = null;
        ResourceBundle bundle = ResourceBundle.getBundle("Util/Account");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url, username, password);
        stm = con.createStatement();
        rs=stm.executeQuery("select * from login where password='"+oldpwd+"'" );
        if (rs.next()) {
            stm.executeUpdate("update login set password='"+newpwd+"' where password='"+oldpwd+"'");
            change=true;
        }
        rs.close();
        stm.close();
        con.close();
        return change;
    }

}
