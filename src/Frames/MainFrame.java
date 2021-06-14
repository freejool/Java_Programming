package Frames;

import Util.Conn;
import Util.Record;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PipedReader;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

//主界面
class MainFrame extends JFrame implements ActionListener {
    private JMenuBar mb = new JMenuBar();
    private JMenu m_system = new JMenu("系统管理");
    private JMenu m_fm = new JMenu("收支管理");
    private JMenuItem mI[] = {new JMenuItem("密码重置"), new JMenuItem("退出系统")};
    private JMenuItem m_FMEdit = new JMenuItem("收支编辑");
    private JLabel l_type, l_fromdate, l_todate, l_bal;
    private JButton b_select;
    private JTextField t_fromdate, t_todate;
    private JComboBox<String> c_type;
    private JPanel p_condition, p_detail;
    private String balTypes[] = {"总账单", "收入", "支出"};
    private double bal1, bal2;
    private JTable table;
    private String username;
    private DefaultTableModel model = null;
    private DateChooser dateChooserFromDate = DateChooser.getInstance("yyyy-MM-dd");
    private DateChooser dateChooserToDate = DateChooser.getInstance("yyyy-MM-dd");

    public MainFrame(String username) {
        super(username + ",欢迎使用个人理财账本!");
        this.username = username;
        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());
        c.add(mb, "North");
        mb.add(m_system);
        mb.add(m_fm);
        m_system.add(mI[0]);
        m_system.add(mI[1]);
        m_fm.add(m_FMEdit);
        m_FMEdit.addActionListener(this);
        mI[0].addActionListener(this);
        mI[1].addActionListener(this);

        l_type = new JLabel("收支类型：");
        c_type = new JComboBox<>(balTypes);
        l_fromdate = new JLabel("起始时间");
        t_fromdate = new JTextField(" 点击选择日期 ", 8);
        dateChooserFromDate.register(t_fromdate);
        l_todate = new JLabel("终止时间");
        t_todate = new JTextField(" 点击选择日期 ", 8);
        dateChooserToDate.register(t_todate);
        b_select = new JButton("查询");
        p_condition = new JPanel();
        p_condition.setLayout(new GridLayout(3, 1));
        p_condition.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("输入查询条件"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        p1.add(l_type);
        p1.add(c_type);
        p2.add(l_fromdate);
        p2.add(t_fromdate);
        p2.add(l_todate);
        p2.add(t_todate);
        p3.add(b_select);
        p_condition.add(p1);
        p_condition.add(p2);
        p_condition.add(p3);
        c.add(p_condition, "Center");

        b_select.addActionListener(this);

        p_detail = new JPanel();
        p_detail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("收支明细信息"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        l_bal = new JLabel();
        Conn con = null;
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
        } catch (
                SQLException throwables) {
            throwables.printStackTrace();
        }
        String[] column = {"编号", "日期", "类型", "内容", "金额",};
        Vector<String> vTitle = new Vector<>();
        Collections.addAll(vTitle, column);
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

        model = new DefaultTableModel(vData, vTitle);

        table = new JTable();
        //不可编辑
        table.setEnabled(false);
        //列不可拖动
        table.getTableHeader().setReorderingAllowed(false);
        //列大小不可变
        table.getTableHeader().setResizingAllowed(false);
        table.setModel(model);

        //居中显示
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cr);


        JScrollPane scrollpane = new JScrollPane(table);
        scrollpane.setPreferredSize(new Dimension(580, 350));
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.setViewportView(table);
        p_detail.add(l_bal);
        p_detail.add(scrollpane);
        c.add(p_detail, "South");

        try {
            bal1 = con.sumIncome();
            bal2 = con.sumOutcome();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (bal1 - bal2 < 0)
            l_bal.setText("个人总收入为:" + bal1 + "元，个人总支出:" + bal2 + " 您已超支，请适度消费！");
        else
            l_bal.setText("个人总收入为:" + bal1 + "元，个人总支出:" + bal2);

        this.setResizable(false);
        this.setSize(600, 580);
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setVisible(true);
        con.close();
    }

    public void actionPerformed(ActionEvent e) {
        Object temp = e.getSource();
        if (temp == mI[0]) {
            //打开修改密码界面
            new ModifyPwdFrame(username);
        } else if (temp == mI[1]) {
            //退出程序
            System.exit(0);
        } else if (temp == m_FMEdit) {
            //打开收支编辑窗口
            new BalEditFrame();

        } else if (temp == b_select) {
            //根据收支类型查询
            Conn con = null;
            try {
                con = new Conn();
            } catch (ClassNotFoundException classNotFoundException) {
                classNotFoundException.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            String fromDate = dateChooserFromDate.getStrDate();
            String toDate = dateChooserToDate.getStrDate();
            if (!dateChooserFromDate.isTouched()) {
                fromDate = "0001-01-01";
            }
            if (!dateChooserToDate.isTouched()) {
                toDate = "3000-01-01";
            }
            // 获取起止时间

            List<Record> list = null;
            ((DefaultTableModel) table.getModel()).getDataVector().clear();
            String[] column = {"编号", "日期", "类型", "内容", "金额",};
            Vector<String> vTitle = new Vector<>();
            Collections.addAll(vTitle, column);
            Vector<Vector<Object>> vData = new Vector<>();
            Vector<Object> vRow = null;
            if (Objects.equals(c_type.getSelectedItem(), "总账单")) {
                try {
                    list = con.select(fromDate, toDate, null);
                }catch (
                        SQLException throwables) {
                    throwables.printStackTrace();
                }

            } else if (Objects.equals(c_type.getSelectedItem(), "收入")) {
                try {
                    list = con.select(fromDate, toDate, "收入");
                } catch (
                        SQLException throwables) {
                    throwables.printStackTrace();
                }
            } else if (Objects.equals(c_type.getSelectedItem(), "支出")) {
                try {
                    list = con.select(fromDate, toDate, "支出");
                } catch (
                        SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
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

            for (Vector<Object> v : vData) {
                model.addRow(v);
            }
            p_detail.repaint();
            con.close();

        }
    }
}