package Debug;

import java.io.*;

public class Forms {
    public static void main(String[] args) {
        FileWriter fw = null;

        String[] strings = {
                "tFieldName",
                "tFieldXLoc",
                "tFieldYLoc",
                "tFieldXHead",
                "tFieldImpactAmt",
                "tFieldDefendInt",
                "tFieldDefendAmt",
                "tFieldFlashDis",
                "tFieldHp",
                "tFieldMp",
                "tFielduserID",
                "tFieldGameFlag",
                "tFieldNowCondt"};
        File file = new File("FormsTest.txt");

        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int j = 2, i = 1;

        for (String str :
                strings) {
            /*
            try {
                fw.write(str + " = new JTextField();\n" +
                        "c.gridx = " + i + ";\n" +
                        "c.gridy = " + j + ";\n" +
                        "c.insets = new Insets(5, 5, 5, 5);\n" +
                        "pane.add(" + str + ", c);\n\n");

                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (i < 7) {
                i += 2;
            } else {
                i = 1;
                j++;
            }

             */

            try {
                fw.write(str + ".setText(null);\n");
                fw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
}
