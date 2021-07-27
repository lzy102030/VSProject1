package debug.chart;

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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class JFreeChartOutput {
    private String title;// 大标题（如：什么什么图）
    private String categoryAxisLabel;// X轴标题（如：按季度）
    private String valueAxisLabel;// Y轴标题 （如：销量）
    private OutputStream outputStream;// 接受数据的输出流
    private CategoryDataset dataset;// 数据集对象

    public JFreeChartOutput() {
        super();
    }

    public JFreeChartOutput(String title, String categoryAxisLabel,
                            String valueAxisLabel, OutputStream outputStream,
                            CategoryDataset dataset) {
        super();
        this.title = title;
        this.categoryAxisLabel = categoryAxisLabel;
        this.valueAxisLabel = valueAxisLabel;
        this.outputStream = outputStream;
        this.dataset = dataset;
    }

    public String getCategoryAxisLabel() {
        return categoryAxisLabel;
    }

    public void setCategoryAxisLabel(String categoryAxisLabel) {
        this.categoryAxisLabel = categoryAxisLabel;
    }

    public CategoryDataset getDataset() {
        return dataset;
    }

    public void setDataset(CategoryDataset dataset) {
        this.dataset = dataset;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValueAxisLabel() {
        return valueAxisLabel;
    }

    public void setValueAxisLabel(String valueAxisLabel) {
        this.valueAxisLabel = valueAxisLabel;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void drawAsPNG() throws IOException {
        // 创建3D柱形图标
        JFreeChart jfreechart = ChartFactory.createBarChart3D(title,
                categoryAxisLabel, valueAxisLabel, dataset,
                PlotOrientation.VERTICAL, true, true, false);
        // 设置标题字体
        jfreechart.getTitle().setFont(new Font("隶书", Font.ITALIC, 20));
        // 获得柱形图表情节对象
        CategoryPlot categoryPlot = (CategoryPlot) jfreechart.getPlot();

        // 设置网格线可见
        categoryPlot.setDomainGridlinesVisible(true);

        /********/
        NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis();
        numberAxis.setRange(0.0, 100.0);
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        /********/

        // 获得x轴对象
        CategoryAxis categoryAxis = categoryPlot.getDomainAxis();
        // 设置x轴显示的分类名称的显示位置，如果不设置则水平显示
        // 设置后，可以斜像显示，但分类角度，图表空间有限时，建议采用
        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions
                .createUpRotationLabelPositions(0.39269908169872414D));
        categoryAxis.setLabelFont(new Font("楷体", Font.BOLD, 14)); // X轴标题
        categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 12)); // x轴下标
        // 种类外部的间距
        categoryAxis.setCategoryMargin(0.2);

        // 柱形渲染器
        BarRenderer3D renderer = (BarRenderer3D) categoryPlot.getRenderer();
        renderer.setDrawBarOutline(false);// 设置不显示边框线
        renderer.setItemMargin(0.1);// 分类内部间距
        // 显示柱形数值
        renderer
                .setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
                ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));

        // 获取柱状
        ValueAxis rangeAxis = categoryPlot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("楷体", Font.BOLD, 14)); // Y轴标题

        // 图示项字体
        LegendTitle legend = jfreechart.getLegend();
        Font labelFont = new Font("宋体", Font.BOLD, 12);
        legend.setItemFont(labelFont);

        // 将图表已数据流的方式返回给客户端
        ChartUtilities.writeChartAsPNG(outputStream, jfreechart, 500, 300);
    }

    public static void main(String[] agrs) throws IOException {
        double[][] data = new double[][]{{200, 80.8, 26.9, 60.5},
                {40.3, 56.3, 24.5, 30.9}};
        String[] rowKeys = {"玩家1", "玩家2"};
        String[] columnKeys = {"造成伤害", "格挡伤害", "剩余血量", "剩余怒气"};
        CategoryDataset dataset = DatasetUtilities.createCategoryDataset(
                rowKeys, columnKeys, data);
        // 打开一个输出流
        File file = new File("debug/logs");
        file.mkdirs();
        String path = file.getAbsolutePath().replace("\\", "/");
        OutputStream outputStream = new FileOutputStream(path + "/BarChart.png");
        new JFreeChartOutput("对战信息", null, "Pts", outputStream,
                dataset).drawAsPNG();
    }
}
