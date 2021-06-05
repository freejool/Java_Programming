package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AlertDialog extends JDialog implements ActionListener {

    private JLabel jLabel = new JLabel();
    private JButton jButton = new JButton();

    public AlertDialog(String content) {
        jLabel.setText(content);
        jButton.setText("继续");
        jButton.addActionListener(this);
        this.setLayout(new FlowLayout());
        this.add(jLabel);
        this.add(jButton);
        this.setResizable(true);
        this.setSize(100, 90);

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
            this.dispose();
        }
    }

}
