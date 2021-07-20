
package Debug;

import client.Service.inGame.MyHeroPro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashSet;
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
    private JTextField tFieldName1,
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
    private static HashSet<String> userNameSet = new HashSet<>();
    static String filename = "data.ser";

    //init for format
    final static boolean shouldFill = true;
    final static boolean RIGHT_TO_LEFT = false;

    //init for background
    ImageIcon background = new ImageIcon(
            Objects.requireNonNull(
                    this.getClass().getResource("/source/bg.jpg")));

    public static void main(String[] args) {
        new preLoadData();

        try {
            UIManager.setLookAndFeel(
                    "com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        new ConsoleUI();
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
                //exit();
            }
        });

        //set the background
        JLabel label = new JLabel(background);
        label.setBounds(0, 0, 828, 500);
        JPanel imagePanel = (JPanel) frame.getContentPane();
        imagePanel.setOpaque(false);
        frame.getLayeredPane().add(label, Integer.valueOf(Integer.MIN_VALUE));

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
        labelNowCondt1.setForeground(Color.WHITE);
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
        //storeButton.addActionListener(new storeHandler());

        frame.setVisible(true);
    }

    public ConsoleUI() {
        javax.swing.SwingUtilities.invokeLater(this::createAndShowGUI);
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

    //pre-load data from the file
    public static class preLoadData {
        public preLoadData() {
            boolean preLoadFlag = false;

            try {
                FileInputStream fis = new FileInputStream(filename);
                ObjectInputStream ois = new ObjectInputStream(fis);
                // bank = (Bank) ois.readObject();
                //Bank.setBankInstance(bank);
                ois.close();
                fis.close();
                preLoadFlag = true;
            } catch (IOException ex) {
                // bank = Bank.getBank();
            }
/*
            if (preLoadFlag) {
                new MyListModel();

                for (Iterator<Customer> cust_iter = bank.getCustomers();
                     cust_iter.hasNext(); ) {
                    Customer customer = cust_iter.next();

                    if (!userNameSet.add(customer.getUserName())) {
                        JOptionPane.showMessageDialog(null,
                                "Unexpected Error",
                                "Debug Mode", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

 */
        }
    }


    //clear the field
    private void clear() {
        tFieldName1.setText(null);
        tFieldXLoc1.setText(null);
        tFieldYLoc1.setText(null);
        tFieldXHead1.setText(null);
        tFieldImpactAmt1.setText(null);
        tFieldDefendInt1.setText(null);
        tFieldDefendAmt1.setText(null);
        tFieldFlashDis1.setText(null);
        tFieldHp1.setText(null);
        tFieldMp1.setText(null);
        tFielduserID1.setText(null);
        tFieldGameFlag1.setText(null);
        tFieldNowCondt1.setText(null);
    }

    //store the data
    private boolean storeData() {
        MyHeroPro heroPro = null;

        String filename = "data.ser";
        FileOutputStream fos;
        ObjectOutputStream out;

        try {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(heroPro);
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


