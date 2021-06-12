package Frames;

import Util.Conn;
import Util.Record;
import Util.DataRefresh;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;


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
    private DefaultTableModel model = null;

    private String[] balType = {"收入", "支出"};
    private String[] itemTypeIn = {"工资", "奖金", "其他"};
    private String[] itemTypeOut = {"购物", "餐饮", "居家", "交通", "娱乐", "人情", "其他"};
    private String[] cloum = {"编号", "日期", "类型", "内容", "金额"};
    private JButton tempButton;
    private JDialog tempDialog;


    public BalEditFrame() {
        super("收支编辑");
        l_id = new JLabel("编号：");
        l_date = new JLabel("日期：");
        l_bal = new JLabel("金额：");
        l_type = new JLabel("类型：");
        l_item = new JLabel("内容：");
        t_id = new JTextField(null, 8);
        t_date = new JTextField(null);
        dateChooser.register(t_date); // using JTextField to decorate DataChooser
        t_bal = new JTextField(null, 8);


        c_type = new JComboBox<>(balType);
        c_type.addItemListener(this);
        c_type.addActionListener(this);

        c_item = new JComboBox<>(itemTypeIn);
//        c_item.setEditable(true);
        b_new = new JButton("录入");
        b_new.setToolTipText("在左侧输入要录入的内容，然后点击该按钮");
        b_update = new JButton("修改");
        b_update.setToolTipText("在右侧双击要修改的记录，输入修改后的内容，然后点击该按钮");
        b_delete = new JButton("删除");
        b_delete.setToolTipText("在右侧选中要删除的记录，然后点击该按钮");
        b_select = new JButton("查询");
        b_select.setToolTipText("仅支持精确查找，在\"编号\"中输入要查询的编号或者不输入来获得所有记录");
        b_clear = new JButton("清空");
        b_clear.setToolTipText("要恢复初始状态，请点击该按钮。\n注意，该操作将不保存任何修改！");

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

        Conn con = new Conn();
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
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column != 0;
            }
        };
        table.setModel(model);
        //不可编辑
//        table.setEnabled(false);
        //列不可拖动
        table.getTableHeader().setReorderingAllowed(false);
        //列大小不可变
        table.getTableHeader().setResizingAllowed(false);
        table.setModel(model);

        //居中显示
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, cr);
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

        new AlertDialog("将鼠标移到按钮内来获取帮助");


    }

    public void actionPerformed(ActionEvent e) {
        Conn conn = new Conn();
        List<Record> list = null;
        String[] column = {"编号", "日期", "类型", "内容", "金额",};
        Vector<String> vTitle = new Vector<>();
        Collections.addAll(vTitle, column);
        Vector<Vector<Object>> vData = new Vector<>();
        Vector<Object> vRow = null;
        if (b_select == e.getSource()) {  //查询所有收支信息
            //添加代码
            //编号为空时返回所有记录，非空时先检查合法性再返回
            String textID = t_id.getText();
            Integer id = null;
            if (!textID.equals("")) {
                id = Integer.parseInt(textID);
            }
            try {
                list = conn.select(id);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else if (b_update == e.getSource()) {  // 修改某条收支信息
            /*System.out.println(t_date.getText().trim().equals(""));
            System.out.println(t_bal.getText().trim().equals(""));*/
        } else if (b_delete == e.getSource()) {   //删除某条收支信息

            int indexSelected = table.getSelectedRow();
            Object o=table.getValueAt(indexSelected, 0);
            Integer s = ((Integer) o);
//            int id = Integer.parseInt(s);
            conn.delete(new Record(s, null, null, null, null));
            try {
                list = conn.select((Integer) null);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } else if (b_new == e.getSource()) {   //新增某条收支信息
            //添加代码
        } else if (b_clear == e.getSource()) {   //清空输入框
            //添加代码
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
        ((DefaultTableModel) table.getModel()).getDataVector().clear();

        for (Vector<Object> v : vData) {
            model.addRow(v);
        }
        p3.repaint();
        conn.close();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().equals("收入")) {
                c_item.removeAllItems();
                for (String i : itemTypeIn) {
                    c_item.addItem(i);
                }
            } else if (e.getItem().equals("支出")) {
                c_item.removeAllItems();
                for (String i : itemTypeOut) {
                    c_item.addItem(i);
                }
            }
        }
        p1.repaint();
    }
}

