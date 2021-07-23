package Debug;

import java.io.*;

public class Forms {
    public static void main(String[] args) {
        FileWriter fw = null;

        File file = new File("FormsTest.txt");

        try {
            fw = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] actStrs = {
                "攻击", "防御", "跑动", "站立"
        };

        String[] heroStrs = {
                "myHeroName", "conHeroName"
        };

        int j = 0;
        for (String heroStr:
             heroStrs) {
            for (String actStr :
                    actStrs) {
                for (int i = 1; i <= 20; i++) {
                    String nameStr = actStr + i;
                    //String heroStr = "conHeroName";
                    //String heroStr = "myHeroName";
                    String dirStr = heroStr + "+\"/" + actStr;

                    try {
                        // fw.write("bufferedImage = ImageIO.read(new File(\"src/client/UI/OtherVersion/source/\"+" + dirStr + "/" + nameStr + ".jpg\")),\n");
                        fw.write("heroImagesMap.put(" + heroStr + "+\"" + nameStr + "\", myHeroImages[" + j++ + "]);\n");
                        fw.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
        }

        try {
            fw.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
        /*
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
    */

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
