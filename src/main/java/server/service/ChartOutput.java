package server.service;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import java.awt.*;
import java.io.*;

public class ChartOutput {
    private String title = "对战信息";                       //大标题
    private String categoryAxisLabel = null;                //X轴标题
    private String valueAxisLabel = "Pts";                  //Y轴标题
    private OutputStream outputStream;                      //接受数据的输出流
    private CategoryDataset dataset;                        //数据集对象

    public ChartOutput(double[][] data, String timeTag) {
        super();

        //数据标签录入
        String[] rowKeys = {"玩家1", "玩家2"};
        String[] columnKeys = {"造成伤害", "格挡伤害", "剩余血量", "获得怒气"};

        //打开文件输出流
        File file = new File("logs");
        file.mkdirs();
        String path = file.getAbsolutePath().replace("\\", "/");
        try {
            outputStream = new FileOutputStream(path + "/BattleInfo" + timeTag + ".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //创建数据集
        dataset = DatasetUtilities.createCategoryDataset(
                rowKeys, columnKeys, data);
    }

    public void drawAsPNG(double maxLimit) throws IOException {
        //创建3D柱形图标
        JFreeChart jfreechart = ChartFactory.createBarChart3D(title,
                categoryAxisLabel, valueAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);

        jfreechart.getTitle().setFont(new Font("隶书", Font.ITALIC, 20));
        CategoryPlot categoryPlot = (CategoryPlot) jfreechart.getPlot();

        //设置网格线可见
        categoryPlot.setDomainGridlinesVisible(true);

        //设置表格上下限
        NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
        numberAxis.setRange(0.0, maxLimit);
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        //获得x轴对象
        CategoryAxis categoryAxis = categoryPlot.getDomainAxis();

        //设置x轴显示的分类名称的显示位置，如果不设置则水平显示
        //设置后，可以斜像显示，但分类角度，图表空间有限时，建议采用
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions
                .createUpRotationLabelPositions(0.39269908169872414D));
        categoryAxis.setLabelFont(new Font("楷体", Font.BOLD, 14));
        categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12));

        //种类外部的间距
        categoryAxis.setCategoryMargin(0.2);

        //柱形渲染器
        BarRenderer3D renderer = (BarRenderer3D) categoryPlot.getRenderer();
        renderer.setDrawBarOutline(false);                          //设置不显示边框线
        renderer.setItemMargin(0.1);                                //分类内部间距

        //显示柱形数值
        renderer
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));

        //获取柱状
        ValueAxis rangeAxis = categoryPlot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("楷体", Font.BOLD, 14)); // Y轴标题

        //图示项字体
        LegendTitle legend = jfreechart.getLegend();
        Font labelFont = new Font("宋体", Font.BOLD, 12);
        legend.setItemFont(labelFont);

        //将图表已数据流的方式返回给客户端
        ChartUtilities.writeChartAsPNG(outputStream, jfreechart, 500, 300);
    }
}
