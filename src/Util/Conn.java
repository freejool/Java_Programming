package Util;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Conn {
    private Connection con = null;
    private Statement stm = null;
    private ResultSet rs = null;
    private String sql;

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
        updateCarryout();
    }

    //通过编号确定修改哪条语句
    public void update(Record record) {
        String date = null;//date以yyyy-MM-dd 格式
        String type = null;
        String content = null;
        double amount = 0;
        try {
            rs = stm.executeQuery("select * from t_shouzhi where id=" + record.getid());
            if (!rs.next()) {
                System.out.println("编号输入错误，没有这条数据");
                return;
            } else {
                date = rs.getString("date");//date以yyyy-MM-dd 格式
                type = rs.getString("type");
                content = rs.getString("content");
                amount = rs.getDouble("amount");
            }
            if (record.getDate() != null) date = record.getDate();
            if (record.getType() != null) type = record.getType();
            if (record.getContent() != null) content = record.getContent();
            if (record.getAmount() != null) amount = record.getAmount();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        sql = "update t_shouzhi set date=\"" + date + "\",Type=\"" + type +
                "\",content=\"" + content + "\",amount=" + amount + " where id=" + record.getid();
        updateCarryout();
    }

    public void delete(Record record) {

        sql = "delete from t_shouzhi where id=" + record.getid();
        updateCarryout();
    }

    private void updateCarryout() {
        try {
            int num = stm.executeUpdate(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss sss");
            System.out.println(sdf.format(new Date()) + "成功修改" + num + "条数据");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Record> select(String fromDate, String toDate, String type) throws SQLException {
        if (type == null) {
            sql = "select * from t_shouzhi where date between '" + fromDate + "' and '" + toDate + "'";
        } else {
            sql = "select * from t_shouzhi where type= '" + type + "' and date between '" + fromDate + "' and '" + toDate + "'";
        }
        return QueryCarryout();
    }

    public List<Record> select(String type) throws SQLException {
        sql = "select * from t_shouzhi where type='" + type + "'";
        return QueryCarryout();
    }

    public List<Record> select(Integer id) throws SQLException {
        if (id != null) {
            sql = "select * from t_shouzhi where id='" + id + "'";
        } else {
            sql = "select * from t_shouzhi";
        }
        return QueryCarryout();
    }

    public List<Record> select(String type, String content) throws SQLException {
        sql = "select * from t_shouzhi where type='" + type + "' and content='" + content + "'";
        return QueryCarryout();
    }

    public List<Record> select(double amount, String type, String content) throws SQLException {
        sql = "select * from t_shouzhi where type='" + type + "' and content='" + content + "'and amount=" + amount;
        return QueryCarryout();
    }

    public List<Record> select(int id, String type, String content) throws SQLException {
        sql = "select * from t_shouzhi where type='" + type + "' and content='" + content + "'and id=" + id;
        return QueryCarryout();
    }

    public List<Record> select(String type, String content, String date, double amount) throws SQLException {
        sql = "select * from t_shouzhi where type='" + type + "' and content='" + content + "' and  date= '" + date + "' and amount=" + amount;
        return QueryCarryout();
    }

    public List<Record> select() throws SQLException {
        sql = "select * from t_shouzhi ";
        return QueryCarryout();
    }

    public Double sumIncome() throws SQLException {
        sql = "select sum(amount) sumincome from t_shouzhi where type='income'";
        rs = stm.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return rs.getDouble("sumincome");
    }

    public Double sumOutcome() throws SQLException {
        sql = "select sum(amount) sumoutcome from t_shouzhi where type='outcome'";
        rs = stm.executeQuery(sql);
        if (!rs.next()) {
            return null;
        }
        return rs.getDouble("sumoutcome");
    }

    private List<Record> QueryCarryout() throws SQLException {
        rs = stm.executeQuery(sql);
        if (!rs.next()) {
            System.out.println("没有查询到收支记录");
            return null;
        }
        List<Record> list = new ArrayList<>();
        list.add(new Record(rs.getInt("id"), rs.getString("date"),
                rs.getString("type"), rs.getString("content"), rs.getDouble("amount")));

        while (rs.next()) {
            list.add(new Record(rs.getInt("id"), rs.getString("date"),
                    rs.getString("type"), rs.getString("content"), rs.getDouble("amount")));
        }
        return list;

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
