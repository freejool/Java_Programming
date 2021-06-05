package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

public class LoginFrame extends JFrame implements ActionListener {
    private JLabel l_user, l_pwd; //用户名标签，密码标签
    private JTextField t_user;//用户名文本框
    private JPasswordField t_pwd; //密码文本框
    private JButton b_ok, b_cancel; //登录按钮，退出按钮

    public LoginFrame() {
        super("欢迎使用个人理财账本!");
        l_user = new JLabel("用户名：", JLabel.RIGHT);
        l_pwd = new JLabel(" 密码：", JLabel.RIGHT);
        t_user = new JTextField(31);
        t_pwd = new JPasswordField(31);
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
        b_cancel.addActionListener(this);
        //界面大小不可调整 
        this.setResizable(false);
        this.setSize(455, 150);

        //界面显示居中
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (b_cancel == e.getSource()) {
            this.dispose();
            //添加退出代码
        } else if (b_ok == e.getSource()) {
            //添加代码，验证身份成功后显示主界面
            File file = new File("pwd.txt");
            RandomAccessFile raf = null;
            String userName, inputUserName;
            String passwd, inputPasswd;
            try {
                raf = new RandomAccessFile(file, "r");
                userName = raf.readLine();
                passwd = raf.readLine();

                inputUserName = t_user.getText();
                inputPasswd = Arrays.toString(t_pwd.getPassword());

//                if (!userName.equals(inputUserName)) {
//                    AlertDialog ad = new AlertDialog("用户名错误");
//                } else if (!passwd.equals(inputPasswd)) {
//                    AlertDialog ad = new AlertDialog("密码错误");
//                } else {
//                    new MainFrame(t_user.getText().trim());
//                }

                new MainFrame(t_user.getText().trim());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}