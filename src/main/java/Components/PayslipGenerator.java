package Components;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;

public class PayslipGenerator {

    public static void generatePayslip(String filePath) {
        try {
            Document document = new Document(PageSize.LETTER.rotate(), 36, 36, 36, 36);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Font bold = new Font(Font.HELVETICA, 10, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);

            // Header Table
            PdfPTable header = new PdfPTable(3);
            header.setWidthPercentage(100);

            header.addCell(new PdfPCell(new Phrase("Department: Sales")));
            PdfPCell col2Cell = new PdfPCell(new Phrase("Pay Period:\nOctober 21 - November 5, 2024"));
            col2Cell.setRowspan(2);
            header.addCell(col2Cell);
            PdfPCell col3Cell = new PdfPCell(new Phrase("Pay Date: Nov 10, 2024"));
            col3Cell.setRowspan(2);
            header.addCell(col3Cell);
            header.addCell(new PdfPCell(new Phrase("Status: Regular")));

            // Top Table
            PdfPTable topTable = new PdfPTable(8);
            topTable.setWidthPercentage(100);
            topTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            topTable.addCell(createCell("Employee Name:\nSupan, Marc Laurence", normal));
            topTable.addCell(createCell("Emp. ID:\n123", normal));
            topTable.addCell(createCell("Rate:\nP 123", normal));
            topTable.addCell(createCell("TIN:\n123-123-123-13", normal));
            topTable.addCell(createCell("Phil. Health No.:\n233-658-708", normal));
            topTable.addCell(createCell("SSS No.:\n123-456-789", normal));
            topTable.addCell(createCell("Pag-ibig No.:\n123-456-789", normal));
            topTable.addCell(createCell("", normal));

            // Center Table
            PdfPTable centerTable = new PdfPTable(8);
            centerTable.setWidthPercentage(100);
            centerTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            centerTable.addCell(createCell("Days Worked:", bold));
            centerTable.addCell(createCell("13.0000", normal));
            centerTable.addCell(createCell("8,789", normal));
            centerTable.addCell(createCell("SSS Deduct.:", bold));
            centerTable.addCell(createCell("832.50", normal));
            centerTable.addCell(createCell("Salary Adj.:", bold));
            centerTable.addCell(createCell("", normal));
            PdfPCell receivedBy = new PdfPCell(new Phrase("\n\n\n\n_____________\n\nReceived By:"));
            receivedBy.setRowspan(7);
            centerTable.addCell(receivedBy);

            centerTable.addCell(createCell("Late Amount:", bold));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("P.Health Deduct.:", bold));
            centerTable.addCell(createCell("273.83", normal));
            centerTable.addCell(createCell("Allowance Adj.:", bold));
            centerTable.addCell(createCell("", normal));

            centerTable.addCell(createCell("Basic Pay:", bold));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("8,789.69", normal));
            centerTable.addCell(createCell("Pag-Ibig Deduct.:", bold));
            centerTable.addCell(createCell("100.00", normal));
            centerTable.addCell(createCell("Other Comp.:", bold));
            centerTable.addCell(createCell("", normal));

            centerTable.addCell(createCell("ND Amount:", bold));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("EFund Deduct.:", bold));
            centerTable.addCell(createCell("300.00", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));

            centerTable.addCell(createCell("Sun/Sp Hol.:", bold));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("Other Deduct.:", bold));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));

            centerTable.addCell(createCell("Leg. Holiday:", bold));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));

            centerTable.addCell(createCell("OT Amount:", bold));
            centerTable.addCell(createCell("118.02", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));

            // Bottom Table
            PdfPTable bottomTable = new PdfPTable(8);
            bottomTable.setWidthPercentage(100);
            bottomTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            bottomTable.addCell(createCell("GROSS INCOME:", bold));
            bottomTable.addCell(createCell("", normal));
            bottomTable.addCell(createCell("8,789.69", normal));
            bottomTable.addCell(createCell("TOTAL DEDUCTION:", bold));
            bottomTable.addCell(createCell("1,506.33", normal));
            bottomTable.addCell(createCell("TOTAL COMPENSATION:", bold));
            bottomTable.addCell(createCell("0", normal));
            bottomTable.addCell(createCell("7,401.36", bold));

            // Add all tables
            document.add(header);
            document.add(topTable);
            document.add(centerTable);
            document.add(bottomTable);
            document.close();

            System.out.println("Payslip generated: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5f);
        return cell;
    }
}
