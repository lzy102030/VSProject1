package server.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataPDF {
    public CreateDataPDF(Connection connection, String tableName) {
        File file = new File("debug/logs");
        file.mkdirs();
        String path = file.getAbsolutePath().replace("\\", "/");

        Rectangle rectangle = new Rectangle(PageSize.A4);
        rectangle.setBackgroundColor(BaseColor.WHITE);
        Document document = new Document(rectangle);

        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(path + "/" + tableName + ".pdf"));
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }

        writer.setPdfVersion(PdfWriter.PDF_VERSION_1_7);

        document.addTitle("GameData");
        document.addAuthor("Noneid & lzy");
        document.addSubject("Game data from mysql data server");
        document.addKeywords("data");
        document.addCreator("Noneid & lzy");

        document.setMargins(50, 50, 40, 40);

        document.open();

        PdfPTable table = new PdfPTable(5);
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("                                                       Data"));
        cell.setColspan(5);
        table.addCell(cell);

        table.addCell("User-id");
        table.addCell("Damage");
        table.addCell("Defence");
        table.addCell("Remain-HP");
        table.addCell("Got-MP");

        ResultSet resultSet = null;
        Statement statement;
        try {
            String sql = "Select * from " + tableName;
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                for (int i = 1; i <= 5; ++i) {
                    table.addCell(String.valueOf(resultSet.getInt(i)));
                }
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

        try {
            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.close();
    }
}
