package Util;

import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class DataRefresh {
    public static Vector refresh(){
        Conn con= null;
        try {
            con = new Conn();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        List<Record> list = null;
        try {
            list = con.select();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Vector<Vector<Object>> vData = new Vector<>();
        Vector<Object> vRow = null;
        if (list != null) {
            for (Record i : list) {
                vRow = new Vector<>();
                vRow.add(i.getid());
                vRow.add(i.getDate());
                vRow.add(i.getType());
                vRow.add(i.getContent());
                vRow.add(i.getAmount());
                vData.add(vRow);
            }
        }
        return vData;
    }
}
