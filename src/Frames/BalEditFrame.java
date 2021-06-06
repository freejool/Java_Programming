package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


//收支编辑界面
class BalEditFrame extends JFrame implements ActionListener, ItemListener {
    private JLabel l_id, l_date, l_bal, l_type, l_item;
    private JTextField t_id, t_date, t_bal;
    private DateChooser dateChooser = DateChooser.getInstance("yyyy-MM-dd");
    private JComboBox c_type, c_item;
    private JButton b_update, b_delete, b_select, b_new, b_clear;
    private JPanel p1, p2, p3;
    private JScrollPane scrollpane;
    private JTable table;

    private String[] balType = {"收入", "支出"};
    private String[] itemTypeIn = {"工资", "奖金", "其他"};
    private String[] itemTypeOut = {"购物", "餐饮", "居家", "交通", "娱乐", "人情", "其他"};
    private String[] cloum = {"编号", "日期", "类型", "内容", "金额"};

    public BalEditFrame() {
        super("收支编辑");
        l_id = new JLabel("编号：");
        l_date = new JLabel("日期：");
        l_bal = new JLabel("金额：");
        l_type = new JLabel("类型：");
        l_item = new JLabel("内容：");
        t_id = new JTextField(8);
        t_date = new JTextField("单击选择日期");
        dateChooser.register(t_date); // using JTextField to decorate DataChooser
        t_bal = new JTextField(8);


        c_type = new JComboBox<>(balType);
        c_item = new JComboBox<>(itemTypeIn);
        c_type.addItemListener(this);
        c_type.addActionListener(this);
        b_select = new JButton("查询");
        b_update = new JButton("修改");
        b_delete = new JButton("删除");
        b_new = new JButton("录入");
        b_clear = new JButton("清空");

        Container c = this.getContentPane();
        c.setLayout(new BorderLayout());

        p1 = new JPanel();
        p1.setLayout(new GridLayout(5, 2, 10, 10));
        p1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("编辑收支信息"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        p1.add(l_id);
        p1.add(t_id);
        p1.add(l_date);
        p1.add(t_date);
        p1.add(l_type);
        p1.add(c_type);
        p1.add(l_item);
        p1.add(c_item);
        p1.add(l_bal);
        p1.add(t_bal);
        c.add(p1, BorderLayout.WEST);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(5, 1, 10, 10));
        p2.add(b_new);
        p2.add(b_update);
        p2.add(b_delete);
        p2.add(b_select);
        p2.add(b_clear);

        c.add(p2, BorderLayout.CENTER);

        p3 = new JPanel();
        p3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("显示收支信息"),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));


        Object[][] row = new Object[50][5];


        table = new JTable(row, cloum);
        scrollpane = new JScrollPane(table);
        scrollpane.setViewportView(table);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        p3.add(scrollpane);
        c.add(p3, BorderLayout.EAST);

        b_update.addActionListener(this);
        b_delete.addActionListener(this);
        b_select.addActionListener(this);
        b_new.addActionListener(this);
        b_clear.addActionListener(this);

        //添加代码，为table添加鼠标点击事件监听addMouseListener

        this.setResizable(false);
        this.setSize(800, 300);
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (b_select == e.getSource()) {  //查询所有收支信息
            //添加代码
        } else if (b_update == e.getSource()) {  // 修改某条收支信息
            //添加代码
        } else if (b_delete == e.getSource()) {   //删除某条收支信息
            //添加代码
        } else if (b_new == e.getSource()) {   //新增某条收支信息
            //添加代码
        } else if (b_clear == e.getSource()) {   //清空输入框
            //添加代码
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().equals("收入")) {
                c_item = new JComboBox<>(itemTypeIn);
            } else if (e.getItem().equals("支出")) {
                c_item = new JComboBox<>(itemTypeOut);
            }
        }
        p1.repaint();
    }
}

