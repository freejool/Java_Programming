package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class AlertDialog extends JDialog implements ActionListener, KeyListener {

    private JLabel alertLabel = new JLabel();
    private JButton alertButton = new JButton();

    public AlertDialog(String content) {
        alertLabel.setText(content);
        alertButton.setText("确定");
        alertButton.addActionListener(this);
        alertButton.addKeyListener(this);
        this.setLayout(new FlowLayout());
        this.add(alertLabel);
        this.add(alertButton);
        this.setResizable(false);

        this.setSize(230, 100);

        //界面显示居中
        Dimension screen = this.getToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.setVisible(true);
    }

    public void setBText(String str){
        this.alertLabel.setText(str);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (alertButton == e.getSource()) {
            this.dispose();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            this.dispose();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            this.dispose();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            this.dispose();
        }
    }

}
