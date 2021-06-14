
import Frames.AlertDialog;
import Frames.LoginFrame;

import javax.swing.*;
import java.util.PrimitiveIterator;

public class MoneyManager {
    public static void main(String[] args) {
        LoginFrame lf=null;
        try {
            lf = new LoginFrame();
            lf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }catch (Exception e){
            new AlertDialog(e.getMessage());
        }
    }
}









//登录界面




//JOptionPane.showMessageDialog(null,"用户名密码出错", "警告", //JOptionPane.ERROR_MESSAGE);


