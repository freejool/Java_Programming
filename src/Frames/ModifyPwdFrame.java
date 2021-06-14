package Frames;

import Util.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;

//修改密码界面
class ModifyPwdFrame extends JFrame implements ActionListener {
    private JLabel l_oldPWD,l_newPWD,l_newPWDAgain;
    private JPasswordField t_oldPWD,t_newPWD,t_newPWDAgain;
    private JButton b_ok,b_cancel;
    private String username;

    public ModifyPwdFrame(String username){
        super("修改密码");
        this.username=username;
        l_oldPWD=new JLabel("旧密码:");
        l_newPWD=new JLabel("新密码:");
        l_newPWDAgain=new JLabel("再次输入:");
        t_oldPWD=new JPasswordField(20);
        t_newPWD=new JPasswordField(20);
        t_newPWDAgain=new JPasswordField(19);
        b_ok=new JButton("确定");
        b_cancel=new JButton("取消");
        Container c=this.getContentPane();
        c.setLayout(new FlowLayout());
        c.add(l_oldPWD);
        c.add(t_oldPWD);
        c.add(l_newPWD);
        c.add(t_newPWD);
        c.add(l_newPWDAgain);
        c.add(t_newPWDAgain);
        c.add(b_ok);
        c.add(b_cancel);
        b_ok.addActionListener(this);
        b_cancel.addActionListener(this);
        t_newPWDAgain.addActionListener(this);
        this.setResizable(false);
        this.setSize(280,160);
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width-this.getSize().width)/2,(screen.height-this.getSize().height)/2);
        this.show();
    }

    public void actionPerformed(ActionEvent e) {
        if(b_cancel==e.getSource()){
            this.dispose();
        }else if(b_ok==e.getSource()||t_newPWDAgain==e.getSource()){  //修改密码
            try {
                if(User.Modify(Arrays.toString(t_oldPWD.getPassword()), Arrays.toString(t_newPWD.getPassword()))){
                    new PwdChangedDialog("密码修改成功，请重新登录");
                }else{
                    new AlertDialog("修改失败,请重试");
                }
            } catch (ClassNotFoundException classNotFoundException) {
                new AlertDialog("未知错误请! 联系管理员");
            } catch (SQLException throwables) {
                new AlertDialog("数据库连接失败! 请联系管理员");
            }
        }
    }
}