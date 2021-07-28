
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
/**
 * 由JFreeChart生成图片放到硬盘上
 * 对于Swing程序可以由org.jfree.chart.ChartUtilities类完成图片的生成
 *
 * @author
 *
 */
public class JfreeChartTest3 {
    private static FileOutputStream fileOut = null;
    private static BufferedImage bufferImg = null;
    //图片生成的路径
    private static String pathOfPicture = "D:/logs/company.jpeg";
    //Excel生成的路径
    private static String pathOfExcel = "D:/logs/test.xls";
    public static void main(String[] args) {
        //JFreeChart画图
        JFreeChart chart = ChartFactory.createPieChart("某公司组织结构图", getDataset(), true, false, false);
        chart.setTitle(new TextTitle("某公司组织结构图", new Font("仿宋", Font.BOLD, 20)));
        LegendTitle legend = chart.getLegend(0);
        legend.setItemFont(new Font("隶书", Font.TYPE1_FONT, 16));
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("宋体", Font.HANGING_BASELINE, 12));
        try {
            OutputStream os = new FileOutputStream(pathOfPicture);
            //OutputStream osExl = new FileOutputStream("测试.xls");
            try {
                // 由ChartUtilities生成文件到一个体outputStream中去
                ChartUtilities.writeChartAsJPEG(os, chart, 1000, 800);
                //ChartUtilities.writeChartAsJPEG(osExl, chart, 100, 80);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //处理图片文件，以便产生ByteArray
        ByteArrayOutputStream handlePicture = new ByteArrayOutputStream();
        handlePicture = handlePicture(pathOfPicture);
        //创建一个工作簿
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("picture sheet");
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 100, 50, (short) 1, 1, (short) 10, 20);
        //插入图片
        patriarch.createPicture(anchor, wb.addPicture(handlePicture.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
        //写入excel文件
        try {
            fileOut = new FileOutputStream(pathOfExcel);
            wb.write(fileOut);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (fileOut != null) {
                try {
                    fileOut.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    private static ByteArrayOutputStream handlePicture(String pathOfPicture) {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        try {
            bufferImg = ImageIO.read(new File(pathOfPicture));
            ImageIO.write(bufferImg, "jpeg", byteArrayOut);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return byteArrayOut;
    }
    private static DefaultPieDataset getDataset() {
        DefaultPieDataset dpd = new DefaultPieDataset();
        dpd.setValue("管理人员", 25);
        dpd.setValue("市场人员", 10);
        dpd.setValue("开发人员", 50);
        dpd.setValue("其它人员", 15);
        return dpd;
    }
}