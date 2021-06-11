package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PwdChangedDialog extends JDialog implements ActionListener {

    private JLabel jLabel = new JLabel();
    private JButton jButton = new JButton();

    public PwdChangedDialog(String content) {
        jLabel.setText(content);
        jButton.setText("确定");
        jButton.addActionListener(this);
        this.setLayout(new FlowLayout());
        this.add(jLabel);
        this.add(jButton);
        this.setResizable(false);
        this.setSize(100, 100);

        //界面显示居中
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setVisible(true);
    }

    public void setBText(String str){
        this.jLabel.setText(str);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (jButton == e.getSource()) {
            System.exit(0);
            this.dispose();
        }
    }

}
