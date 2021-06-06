package Util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.ResourceBundle;

public class Conn {
    private Connection con = null;
    private Statement stm = null;
    private ResultSet rs = null;
    String sql;

    public Conn() {
        ResourceBundle bundle = ResourceBundle.getBundle("Util/Account");
        String url = bundle.getString("url");
        String username = bundle.getString("username");
        String password = bundle.getString("password");
        try {
            //注册驱动
            //DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            Class.forName("com.mysql.cj.jdbc.Driver");
            //连接数据库
            con = DriverManager.getConnection(url, username, password);
            //创建数据库执行对象
            stm = con.createStatement();
            //执行sql语句
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void insert(Record re) {
        sql = "insert into t_shouzhi values(" + re.getid() + ",\"" + re.getDate() +
                "\",\"" + re.getType() + "\",\"" + re.getContent() + "\"," + re.getAmount() + ")";
        carryout();
    }

    //通过编号确定修改哪条语句
    public void update(Record record) {
        sql = "update t_shouzhi set date=\"" + record.getDate() + "\",Type=\"" + record.getType() +
                "\",content=\"" + record.getContent() + "\",amount=" + record.getAmount() + " where id=" + record.getid();
        carryout();
    }

    public void delete(Record record) {

        sql = "delete from t_shouzhi where id=" + record.getid();
        carryout();
    }

    private void carryout() {
        try {
            int num = stm.executeUpdate(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
            System.out.println(sdf.format(new Date()) + "成功修改" + num + "条数据");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void select() {
        try {
            while (rs.next()) {
                System.out.println(rs.getString(1) + "   " + rs.getString(2) +
                        "    " + rs.getString(3) + "    " + rs.getString(4));
                Object[] ob = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)};
                System.out.println(Arrays.toString(ob));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {

        //释放资源
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (stm != null)
                stm.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            if (con != null)
                con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }
}
