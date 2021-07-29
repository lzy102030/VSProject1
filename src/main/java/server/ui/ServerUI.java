package server.ui;

import server.service.GetUserIP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.SocketException;

public class ServerUI {
    String localIP, netIP;
    JFrame frame;

    public ServerUI() {
        frame = new JFrame("游戏服务器");

        getIPInfo();

        Toolkit theKit = frame.getToolkit();            // Get the window toolkit
        Dimension wndSize = theKit.getScreenSize();       // Get screen size
        // Set the position to screen center & size to half screen size
        frame.setBounds(wndSize.width / 3, wndSize.height / 3,   // Position
                400, 300);  // Size

        Container pane = frame.getContentPane();
        addComponentsToPane(pane);

        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exitConfirm();
            }
        });
        frame.setResizable(false);
        frame.setVisible(true);
    }

    //关闭UI界面确认
    private void exitConfirm() {
        int n = JOptionPane.showConfirmDialog(null,
                """
                        您已分享服务器地址，并进入游戏吗？
                        若您已结束游戏，本窗口将自动关闭。
                        这个操作将不会关闭服务器，仅关闭本窗口。
                        请确认是否继续？""", "请留步",
                JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION) {
            exitNow();
        }
    }

    public void exitNow(){
        frame.dispose();
    }

    //获取IP信息
    public void getIPInfo() {
        GetUserIP userIP = new GetUserIP();

        try {
            localIP = userIP.getInnetIp();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //网络连接错误提示
        if (localIP == null) {
            JOptionPane.showMessageDialog(null,
                    "网络连接错误，请检查您的网络后重试。","警告",
                    JOptionPane.WARNING_MESSAGE);
            System.exit(1);
        }

        netIP = userIP.getV4IP();
    }

    public void addComponentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("服务器准备就绪", JLabel.CENTER);
        title.setFont(new Font("黑体", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(title, c);

        JLabel local = new JLabel("您的局域网IP：" + localIP, JLabel.CENTER);
        local.setFont(new Font("黑体", Font.PLAIN, 20));
        local.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(10, 10, 5, 10);
        pane.add(local, c);

        JLabel net = new JLabel("您的互联网IP：" + netIP, JLabel.CENTER);
        net.setFont(new Font("黑体", Font.PLAIN, 20));
        net.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(5, 10, 10, 10);
        pane.add(net, c);

        JLabel tips = new JLabel("分享出去，摇人上号", JLabel.CENTER);
        tips.setFont(new Font("楷体", Font.ITALIC, 20));
        tips.setForeground(Color.BLACK);
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(tips, c);
    }
}
