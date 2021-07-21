package Debug;

import client.Service.inGame.MyHeroPro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;


public class ConsoleUI {
    //init
    JFrame frame = new JFrame("Server Debug Console");
    private JButton send1Button,
            send2Button,
            storeButton,
            clearButton;
    private JLabel title,
            captureLine,
            labelName1,
            labelXLoc1,
            labelYLoc1,
            labelXHead1,
            labelImpactAmt1,
            labelDefendInt1,
            labelDefendAmt1,
            labelFlashDis1,
            labelHp1,
            labelMp1,
            labeluserID1,
            labelGameFlag1,
            labelNowCondt1,
            labelName2,
            labelXLoc2,
            labelYLoc2,
            labelXHead2,
            labelImpactAmt2,
            labelDefendInt2,
            labelDefendAmt2,
            labelFlashDis2,
            labelHp2,
            labelMp2,
            labeluserID2,
            labelGameFlag2,
            labelNowCondt2;
    static JTextField tFieldName1,
            tFieldXLoc1,
            tFieldYLoc1,
            tFieldXHead1,
            tFieldImpactAmt1,
            tFieldDefendInt1,
            tFieldDefendAmt1,
            tFieldFlashDis1,
            tFieldHp1,
            tFieldMp1,
            tFielduserID1,
            tFieldGameFlag1,
            tFieldNowCondt1,
            tFieldName2,
            tFieldXLoc2,
            tFieldYLoc2,
            tFieldXHead2,
            tFieldImpactAmt2,
            tFieldDefendInt2,
            tFieldDefendAmt2,
            tFieldFlashDis2,
            tFieldHp2,
            tFieldMp2,
            tFielduserID2,
            tFieldGameFlag2,
            tFieldNowCondt2;
    static String filename = "data.ser";
    static TestMain clientSim;

    //init for format
    final static boolean shouldFill = true;
    final static boolean RIGHT_TO_LEFT = false;

    //init for background
    ImageIcon background = new ImageIcon(
            Objects.requireNonNull(
                    this.getClass().getResource("/source/bg.jpg")));

    public ConsoleUI() {
        clientSim = TestMain.testMain;

        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(this::createAndShowGUI);
    }

    private void createAndShowGUI() {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Toolkit theKit = frame.getToolkit();            // Get the window toolkit
        Dimension wndSize = theKit.getScreenSize();       // Get screen size
        // Set the position to screen center & size to half screen size
        frame.setBounds(wndSize.width / 4, wndSize.height / 5,   // Position
                828, 500);  // Size
        //Set up the content pane.
        addComponentsToPane(frame.getContentPane());
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        //set the background
        JLabel label = new JLabel(background);
        label.setBounds(0, 0, 828, 500);
        JPanel imagePanel = (JPanel) frame.getContentPane();
        imagePanel.setOpaque(false);
        frame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));

        new preLoadData();

        //Display the window.
        frame.setVisible(true);
    }

    public void addComponentsToPane(Container pane) {
        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(
                    ComponentOrientation.RIGHT_TO_LEFT);
        }

        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        //set all the contents by gridBagsLayout
        //set title
        title = new JLabel("Server Debug Console", JLabel.CENTER);
        title.setFont(new Font("Consolas", Font.BOLD, 30));
        title.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 8;
        c.gridheight = 2;
        c.insets = new Insets(10, 10, 10, 10);
        pane.add(title, c);

        //set info label
        labelName1 = new JLabel("Name");
        labelName1.setForeground(Color.WHITE);
        labelName1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 20;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelName1, c);

        labelXLoc1 = new JLabel("X-Loc");
        labelXLoc1.setForeground(Color.WHITE);
        labelXLoc1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 2;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelXLoc1, c);

        labelYLoc1 = new JLabel("Y-Loc");
        labelYLoc1.setForeground(Color.WHITE);
        labelYLoc1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 4;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelYLoc1, c);

        labelXHead1 = new JLabel("X-Head");
        labelXHead1.setForeground(Color.WHITE);
        labelXHead1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 6;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelXHead1, c);

        labelImpactAmt1 = new JLabel("Impact-Amt");
        labelImpactAmt1.setForeground(Color.WHITE);
        labelImpactAmt1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelImpactAmt1, c);

        labelDefendInt1 = new JLabel("Defend-Int");
        labelDefendInt1.setForeground(Color.WHITE);
        labelDefendInt1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 2;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelDefendInt1, c);

        labelDefendAmt1 = new JLabel("Defend-Amt");
        labelDefendAmt1.setForeground(Color.WHITE);
        labelDefendAmt1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 4;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelDefendAmt1, c);

        labelFlashDis1 = new JLabel("Flash-Dis");
        labelFlashDis1.setForeground(Color.WHITE);
        labelFlashDis1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 6;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelFlashDis1, c);

        labelHp1 = new JLabel("HP");
        labelHp1.setForeground(Color.WHITE);
        labelHp1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelHp1, c);

        labelMp1 = new JLabel("MP");
        labelMp1.setForeground(Color.WHITE);
        labelMp1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 2;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelMp1, c);

        labeluserID1 = new JLabel("User-ID");
        labeluserID1.setForeground(Color.WHITE);
        labeluserID1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 4;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labeluserID1, c);

        labelGameFlag1 = new JLabel("Game-Flag");
        labelGameFlag1.setForeground(Color.WHITE);
        labelGameFlag1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 6;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelGameFlag1, c);

        labelNowCondt1 = new JLabel("NowCondt");
        labelNowCondt1.setForeground(Color.BLACK);
        labelNowCondt1.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelNowCondt1, c);

        tFieldName1 = new JTextField();
        c.gridx = 1;
        c.gridy = 2;
        c.ipadx = 40;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldName1, c);

        tFieldXLoc1 = new JTextField();
        c.gridx = 3;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldXLoc1, c);

        tFieldYLoc1 = new JTextField();
        c.gridx = 5;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldYLoc1, c);

        tFieldXHead1 = new JTextField();
        c.gridx = 7;
        c.gridy = 2;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldXHead1, c);

        tFieldImpactAmt1 = new JTextField();
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldImpactAmt1, c);

        tFieldDefendInt1 = new JTextField();
        c.gridx = 3;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldDefendInt1, c);

        tFieldDefendAmt1 = new JTextField();
        c.gridx = 5;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldDefendAmt1, c);

        tFieldFlashDis1 = new JTextField();
        c.gridx = 7;
        c.gridy = 3;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldFlashDis1, c);

        tFieldHp1 = new JTextField();
        c.gridx = 1;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldHp1, c);

        tFieldMp1 = new JTextField();
        c.gridx = 3;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldMp1, c);

        tFielduserID1 = new JTextField();
        c.gridx = 5;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFielduserID1, c);

        tFieldGameFlag1 = new JTextField();
        c.gridx = 7;
        c.gridy = 4;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldGameFlag1, c);

        tFieldNowCondt1 = new JTextField();
        c.gridx = 1;
        c.gridy = 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldNowCondt1, c);

        //set buttons
        send1Button = new JButton("Send#1");
        c.gridx = 4;
        c.gridy = 5;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 2;
        c.gridheight = 1;
        //c.ipadx = 140;
        pane.add(send1Button, c);

        clearButton = new JButton("Clear");
        c.gridx = 6;
        c.gridy = 5;
        c.insets = new Insets(5, 5, 5, 5);
        c.gridwidth = 2;
        c.gridheight = 1;
        //c.ipadx = 140;
        pane.add(clearButton, c);

        //set capture line
        captureLine = new JLabel("--------------------   用 户 分 界   -------------------", JLabel.CENTER);
        captureLine.setFont(new Font("黑体", Font.BOLD, 15));
        captureLine.setForeground(Color.WHITE);
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 8;
        c.insets = new Insets(0, 5, 10, 5);
        pane.add(captureLine, c);

        labelName2 = new JLabel("Name");
        labelName2.setForeground(Color.WHITE);
        labelName2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 2 + 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.ipadx = 20;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelName2, c);

        labelXLoc2 = new JLabel("X-Loc");
        labelXLoc2.setForeground(Color.WHITE);
        labelXLoc2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 2;
        c.gridy = 2 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelXLoc2, c);

        labelYLoc2 = new JLabel("Y-Loc");
        labelYLoc2.setForeground(Color.WHITE);
        labelYLoc2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 4;
        c.gridy = 2 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelYLoc2, c);

        labelXHead2 = new JLabel("X-Head");
        labelXHead2.setForeground(Color.WHITE);
        labelXHead2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 6;
        c.gridy = 2 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelXHead2, c);

        labelImpactAmt2 = new JLabel("Impact-Amt");
        labelImpactAmt2.setForeground(Color.WHITE);
        labelImpactAmt2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelImpactAmt2, c);

        labelDefendInt2 = new JLabel("Defend-Int");
        labelDefendInt2.setForeground(Color.WHITE);
        labelDefendInt2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 2;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelDefendInt2, c);

        labelDefendAmt2 = new JLabel("Defend-Amt");
        labelDefendAmt2.setForeground(Color.WHITE);
        labelDefendAmt2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 4;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelDefendAmt2, c);

        labelFlashDis2 = new JLabel("Flash-Dis");
        labelFlashDis2.setForeground(Color.WHITE);
        labelFlashDis2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 6;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelFlashDis2, c);

        labelHp2 = new JLabel("HP");
        labelHp2.setForeground(Color.WHITE);
        labelHp2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelHp2, c);

        labelMp2 = new JLabel("MP");
        labelMp2.setForeground(Color.WHITE);
        labelMp2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 2;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelMp2, c);

        labeluserID2 = new JLabel("User-ID");
        labeluserID2.setForeground(Color.WHITE);
        labeluserID2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 4;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labeluserID2, c);

        labelGameFlag2 = new JLabel("Game-Flag");
        labelGameFlag2.setForeground(Color.WHITE);
        labelGameFlag2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 6;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelGameFlag2, c);

        labelNowCondt2 = new JLabel("NowCondt");
        labelNowCondt2.setForeground(Color.WHITE);
        labelNowCondt2.setFont(new Font("Arial", Font.BOLD, 12));
        c.gridx = 0;
        c.gridy = 5 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(labelNowCondt2, c);

        tFieldName2 = new JTextField();
        c.gridx = 1;
        c.gridy = 2 + 5;
        c.ipadx = 40;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldName2, c);

        tFieldXLoc2 = new JTextField();
        c.gridx = 3;
        c.gridy = 2 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldXLoc2, c);

        tFieldYLoc2 = new JTextField();
        c.gridx = 5;
        c.gridy = 2 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldYLoc2, c);

        tFieldXHead2 = new JTextField();
        c.gridx = 7;
        c.gridy = 2 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldXHead2, c);

        tFieldImpactAmt2 = new JTextField();
        c.gridx = 1;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldImpactAmt2, c);

        tFieldDefendInt2 = new JTextField();
        c.gridx = 3;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldDefendInt2, c);

        tFieldDefendAmt2 = new JTextField();
        c.gridx = 5;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldDefendAmt2, c);

        tFieldFlashDis2 = new JTextField();
        c.gridx = 7;
        c.gridy = 3 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldFlashDis2, c);

        tFieldHp2 = new JTextField();
        c.gridx = 1;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldHp2, c);

        tFieldMp2 = new JTextField();
        c.gridx = 3;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldMp2, c);

        tFielduserID2 = new JTextField();
        c.gridx = 5;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFielduserID2, c);

        tFieldGameFlag2 = new JTextField();
        c.gridx = 7;
        c.gridy = 4 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldGameFlag2, c);

        tFieldNowCondt2 = new JTextField();
        c.gridx = 1;
        c.gridy = 5 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(tFieldNowCondt2, c);

        send2Button = new JButton("Send#2");
        c.gridx = 4;
        c.gridy = 5 + 5;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(send2Button, c);

        storeButton = new JButton("Store");
        c.gridx = 6;
        c.gridy = 5 + 5;
        c.insets = new Insets(5, 5, 5, 5);
        pane.add(storeButton, c);

        //add action listener
        storeButton.addActionListener(new storeHandler());
        clearButton.addActionListener(new clearHandler());
        send1Button.addActionListener(new send1Handler());
        send2Button.addActionListener(new send2Handler());

        frame.setVisible(true);
    }

    private MyHeroPro hero1Packaged() {
        if (tFieldName1.getText().equals("") |
                tFieldXLoc1.getText().equals("") |
                tFieldYLoc1.getText().equals("") |
                tFieldXHead1.getText().equals("") |
                tFieldImpactAmt1.getText().equals("") |
                tFieldDefendInt1.getText().equals("") |
                tFieldDefendAmt1.getText().equals("") |
                tFieldFlashDis1.getText().equals("") |
                tFieldHp1.getText().equals("") |
                tFieldMp1.getText().equals("") |
                tFielduserID1.getText().equals("") |
                tFieldGameFlag1.getText().equals("") |
                tFieldNowCondt1.getText().equals("")) {
            return null;
        } else {
            return new MyHeroPro(tFieldName1.getText(),
                    Integer.parseInt(tFieldXLoc1.getText()),
                    Integer.parseInt(tFieldYLoc1.getText()),
                    Integer.parseInt(tFieldXHead1.getText()),
                    Integer.parseInt(tFieldImpactAmt1.getText()),
                    Integer.parseInt(tFieldDefendInt1.getText()),
                    Integer.parseInt(tFieldDefendAmt1.getText()),
                    Integer.parseInt(tFieldFlashDis1.getText()),
                    Integer.parseInt(tFieldHp1.getText()),
                    Integer.parseInt(tFieldMp1.getText()),
                    Integer.parseInt(tFielduserID1.getText()),
                    Integer.parseInt(tFieldGameFlag1.getText()),
                    Integer.parseInt(tFieldNowCondt1.getText()));
        }
    }

    private MyHeroPro hero2Packaged() {
        if (tFieldName2.getText().equals("") |
                tFieldName2.getText().equals("") |
                tFieldXLoc2.getText().equals("") |
                tFieldYLoc2.getText().equals("") |
                tFieldXHead2.getText().equals("") |
                tFieldImpactAmt2.getText().equals("") |
                tFieldDefendInt2.getText().equals("") |
                tFieldDefendAmt2.getText().equals("") |
                tFieldFlashDis2.getText().equals("") |
                tFieldHp2.getText().equals("") |
                tFieldMp2.getText().equals("") |
                tFielduserID2.getText().equals("") |
                tFieldGameFlag2.getText().equals("") |
                tFieldNowCondt2.getText().equals("")) {
            return null;
        } else {
            return new MyHeroPro(tFieldName2.getText(),
                    Integer.parseInt(tFieldXLoc2.getText()),
                    Integer.parseInt(tFieldYLoc2.getText()),
                    Integer.parseInt(tFieldXHead2.getText()),
                    Integer.parseInt(tFieldImpactAmt2.getText()),
                    Integer.parseInt(tFieldDefendInt2.getText()),
                    Integer.parseInt(tFieldDefendAmt2.getText()),
                    Integer.parseInt(tFieldFlashDis2.getText()),
                    Integer.parseInt(tFieldHp2.getText()),
                    Integer.parseInt(tFieldMp2.getText()),
                    Integer.parseInt(tFielduserID2.getText()),
                    Integer.parseInt(tFieldGameFlag2.getText()),
                    Integer.parseInt(tFieldNowCondt2.getText()));
        }
    }

    private void hero1UnPackage(MyHeroPro hero) {
        tFieldName1.setText(hero.getName());
        tFieldXLoc1.setText(String.valueOf(hero.getxLoc()));
        tFieldYLoc1.setText(String.valueOf(hero.getyLoc()));
        tFieldXHead1.setText(String.valueOf(hero.getxHead()));
        tFieldImpactAmt1.setText(String.valueOf(hero.getImpactAmt()));
        tFieldDefendInt1.setText(String.valueOf(hero.getDefendInt()));
        tFieldDefendAmt1.setText(String.valueOf(hero.getDefendAmt()));
        tFieldFlashDis1.setText(String.valueOf(hero.getFlashDis()));
        tFieldHp1.setText(String.valueOf(hero.getHp()));
        tFieldMp1.setText(String.valueOf(hero.getMp()));
        tFielduserID1.setText(String.valueOf(hero.getUserID()));
        tFieldGameFlag1.setText(String.valueOf(hero.isGameOverFlag()));
        tFieldNowCondt1.setText(String.valueOf(hero.getNowCondition()));
    }

    private void hero2UnPackage(MyHeroPro hero) {
        tFieldName2.setText(hero.getName());
        tFieldXLoc2.setText(String.valueOf(hero.getxLoc()));
        tFieldYLoc2.setText(String.valueOf(hero.getyLoc()));
        tFieldXHead2.setText(String.valueOf(hero.getxHead()));
        tFieldImpactAmt2.setText(String.valueOf(hero.getImpactAmt()));
        tFieldDefendInt2.setText(String.valueOf(hero.getDefendInt()));
        tFieldDefendAmt2.setText(String.valueOf(hero.getDefendAmt()));
        tFieldFlashDis2.setText(String.valueOf(hero.getFlashDis()));
        tFieldHp2.setText(String.valueOf(hero.getHp()));
        tFieldMp2.setText(String.valueOf(hero.getMp()));
        tFielduserID2.setText(String.valueOf(hero.getUserID()));
        tFieldGameFlag2.setText(String.valueOf(hero.isGameOverFlag()));
        tFieldNowCondt2.setText(String.valueOf(hero.getNowCondition()));
    }

    public class storeHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //store data
            if (storeData()) {
                JOptionPane.showMessageDialog(frame,
                        "Stored data successfully!",
                        "Result", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame,
                        "Failed to store data.\n" +
                                "Please try again.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class clearHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clear();
        }
    }

    private class send1Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                clientSim.getServerOut().writeObject(hero1Packaged());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    private class send2Handler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                clientSim.getServerOut().writeObject(hero2Packaged());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //pre-load data from the file
    private class preLoadData {
        private preLoadData() {
            boolean preLoadFlag;
            MyHeroPro hero1 = null, hero2 = null;

            try {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                hero1 = (MyHeroPro) ois.readObject();
                hero2 = (MyHeroPro) ois.readObject();
                ois.close();
                fis.close();
                preLoadFlag = true;
            } catch (IOException | ClassNotFoundException e) {
                preLoadFlag = false;
                e.printStackTrace();
            }

            if (preLoadFlag) {
                if (hero1 == null | hero2 == null) {
                    System.err.println("[ERROR]Logical Fatal! Please check code.");
                } else {
                    hero1UnPackage(hero1);
                    hero2UnPackage(hero2);
                }
            }
        }
    }

    //从服务器更新hero数据
    public void updateHeroInfo(ArrayList<MyHeroPro> heroList) {
        hero1UnPackage(heroList.get(0));
        hero2UnPackage(heroList.get(1));
        System.out.println(heroList.get(0).getName() + " and " + heroList.get(1).getName() + "  in  lenth = " + heroList.size());
    }

    //clear the field
    private void clear() {
        tFieldName1.setText("");
        tFieldXLoc1.setText("");
        tFieldYLoc1.setText("");
        tFieldXHead1.setText("");
        tFieldImpactAmt1.setText("");
        tFieldDefendInt1.setText("");
        tFieldDefendAmt1.setText("");
        tFieldFlashDis1.setText("");
        tFieldHp1.setText("");
        tFieldMp1.setText("");
        tFielduserID1.setText("");
        tFieldGameFlag1.setText("");
        tFieldNowCondt1.setText("");

        tFieldName2.setText("");
        tFieldXLoc2.setText("");
        tFieldYLoc2.setText("");
        tFieldXHead2.setText("");
        tFieldImpactAmt2.setText("");
        tFieldDefendInt2.setText("");
        tFieldDefendAmt2.setText("");
        tFieldFlashDis2.setText("");
        tFieldHp2.setText("");
        tFieldMp2.setText("");
        tFielduserID2.setText("");
        tFieldGameFlag2.setText("");
        tFieldNowCondt2.setText("");
    }

    //store the data
    private boolean storeData() {
        String filename = "data.ser";
        FileOutputStream fos;
        ObjectOutputStream out;

        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(hero1Packaged());
            out.writeObject(hero2Packaged());
            out.close();
            fos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        return true;
    }

    //save before exit
    private void exit() {
        if (!storeData()) {
            JOptionPane.showMessageDialog(frame,
                    "Failed to store data.\n",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        System.exit(0);
    }
}


