package uet.oop.bomberman.gui;

import uet.oop.bomberman.Game;

import javax.swing.*;
import java.awt.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Swing Panel hiển thị thông tin thời gian, điểm mà người chơi đạt được
 */
public class InfoPanel extends JPanel {

    private JLabel timeLabel;
    private JLabel pointsLabel;
    private JLabel livesLabel;
    private JLabel portLabel;

    private String host = "";

    public InfoPanel(Game game) {
        setLayout(new GridLayout());
        try {
            host = Inet4Address.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        timeLabel = new JLabel("Time: " + game.getBoard().getTime());
        timeLabel.setForeground(Color.white);
        timeLabel.setHorizontalAlignment(JLabel.CENTER);

        pointsLabel = new JLabel("Points: " + game.getBoard().getPoints());
        pointsLabel.setForeground(Color.white);
        pointsLabel.setHorizontalAlignment(JLabel.CENTER);

        livesLabel = new JLabel("♥: " + game.getBoard().getLives());
        livesLabel.setForeground(Color.white);
        livesLabel.setHorizontalAlignment(JLabel.CENTER);

        portLabel = new JLabel("Address: " + host + ":" + game.getPort());
        portLabel.setForeground(Color.white);
        portLabel.setHorizontalAlignment(JLabel.CENTER);

        add(timeLabel);
        add(pointsLabel);
        add(portLabel);
        add(livesLabel);

        setBackground(Color.black);
        setPreferredSize(new Dimension(0, 40));
    }

    public void setTime(int t) {
        timeLabel.setText("Time: " + t);
    }

    public void setPoints(int t) {
        pointsLabel.setText("Score: " + t);
    }

    public void setLives(int t){
        livesLabel.setText("♥: "+t);
    }

    public void setPort(int t) {
        pointsLabel.setText("Address: " + host + ":" + t);
    }

}
