package Frames;

import Util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel l_user, l_pwd; //用户名标签，密码标签
    private JTextField t_user;//用户名文本框
    private JPasswordField t_pwd; //密码文本框
    private JButton b_ok, b_cancel; //登录按钮，退出按钮

    public LoginFrame() {
        super("欢迎使用个人理财账本!");
        l_user = new JLabel("用户名：", JLabel.RIGHT);
        l_pwd = new JLabel("    密码：", JLabel.RIGHT);
        t_user = new JTextField("admin", 31);
        t_pwd = new JPasswordField("123", 31);
        b_ok = new JButton("登录");
        b_cancel = new JButton("退出");
        //布局方式FlowLayout，一行排满排下一行
        Container c = this.getContentPane();
        c.setLayout(new FlowLayout());

        c.add(l_user);
        c.add(t_user);
        c.add(l_pwd);
        c.add(t_pwd);
        c.add(b_ok);
        c.add(b_cancel);

        //为按钮添加监听事件
        b_ok.addActionListener(this);
        t_user.addActionListener(this);
        t_pwd.addActionListener(this);
        b_cancel.addActionListener(this);
        //界面大小不可调整
        this.setResizable(true);
        this.setSize(450, 120);

        //界面显示居中
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (b_cancel == e.getSource()) {
            this.dispose();
            //添加退出代码
        } else if (b_ok == e.getSource()||t_pwd==e.getSource()) {
            //添加代码，验证身份成功后显示主界面
            try {
                if(!User.login(t_user.getText(),t_pwd.getText()))
                    new AlertDialog("      用户名或密码错误       ");
                else {
                    new MainFrame(t_user.getText().trim());
                    this.dispose();
                }
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}