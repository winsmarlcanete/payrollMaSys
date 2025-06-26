import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;

public class PayslipGenerator {
    public static void main(String[] args) {
        try {
            Document document = new Document(PageSize.LETTER.rotate(), 36, 36, 36, 36);
            PdfWriter.getInstance(document, new FileOutputStream("payslip.pdf"));
            document.open();

            // Title
            Font bold = new Font(Font.HELVETICA, 10, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);

            // Header Table
            PdfPTable header = new PdfPTable(3);
            header.setWidthPercentage(100);

            // Column 1, Row 1
            header.addCell(new PdfPCell(new Phrase("Department: Sales")));

            // Column 2, Row 1 (merged downwards)
            PdfPCell col2Cell = new PdfPCell(new Phrase("Pay Period:\nOctober 21 - November 5, 2024"));
            col2Cell.setRowspan(2);
            header.addCell(col2Cell);

            // Column 3, Row 1
            header.addCell(new PdfPCell(new Phrase("Pay Date: Nov 10, 2024")));

            // Column 1, Row 2
            header.addCell(new PdfPCell(new Phrase("Status: Regular")));

            // Column 3, Row 2
            header.addCell(new PdfPCell(new Phrase("Tax Status: ME2")));

            // Top Table
            PdfPTable topTable = new PdfPTable(8);
            topTable.setWidthPercentage(100);
            topTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            topTable.addCell(createCell("Employee Name:\nSupan, Marc Laurence", normal, 1));
            topTable.addCell(createCell("Emp. ID:\n123", normal, 1));
            topTable.addCell(createCell("Rate:\nP 123", normal, 1));
            topTable.addCell(createCell("TIN:\n123-123-123-13", normal, 1));
            topTable.addCell(createCell("Phil. Health No.:\n233-658-708", normal, 1));
            topTable.addCell(createCell("SSS No.:\n123-456-789", normal, 1));
            topTable.addCell(createCell("Pag-ibig No.:\n123-456-789", normal, 1));
            topTable.addCell(createCell("", normal, 1));

            // Center Table
            PdfPTable centerTable = new PdfPTable(8);
            centerTable.setWidthPercentage(100);
            centerTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            // Row 1
            centerTable.addCell(createCell("Days Worked:", bold, 1));
            centerTable.addCell(createCell("13.0000", normal, 1));
            centerTable.addCell(createCell("8,789", normal, 1));
            centerTable.addCell(createCell("SSS Deduct.:", bold, 1));
            centerTable.addCell(createCell("832.50", normal, 1));
            centerTable.addCell(createCell("Salary Adj.:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));

            // Column 8
            PdfPCell col8Cell = new PdfPCell(new Phrase("\n\n\n\n_____________\n\nReceived By:"));
            col8Cell.setRowspan(7);
            centerTable.addCell(col8Cell);

            // Row 2
            centerTable.addCell(createCell("Late Amount:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("P.Health Deduct.:", bold, 1));
            centerTable.addCell(createCell("273.83", normal, 1));
            centerTable.addCell(createCell("Allowance Adj.:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));

            // Row 3
            centerTable.addCell(createCell("Basic Pay:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("8,789.69", normal, 1));
            centerTable.addCell(createCell("Pag-Ibig Deduct.:", bold, 1));
            centerTable.addCell(createCell("100.00", normal, 1));
            centerTable.addCell(createCell("Other Comp.:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));

            // Row 4
            centerTable.addCell(createCell("ND Amount:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("EFund Deduct.:", bold, 1));
            centerTable.addCell(createCell("300.00", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));

            // Row 5
            centerTable.addCell(createCell("Sun/Sp Hol.:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("Other Deduct.:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));

            // Row 6
            centerTable.addCell(createCell("Leg. Holiday:", bold, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));

            // Row 7
            centerTable.addCell(createCell("OT Amount:", bold, 1));
            centerTable.addCell(createCell("118.02", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));
            centerTable.addCell(createCell("", normal, 1));

            PdfPTable bottomTable = new PdfPTable(8);
            bottomTable.setWidthPercentage(100);
            bottomTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});
            bottomTable.addCell(createCell("GROSS INCOME:", bold, 1));
            bottomTable.addCell(createCell("", normal, 1));
            bottomTable.addCell(createCell("8,789.69", normal, 1));
            bottomTable.addCell(createCell("TOTAL DEDUCTION:", bold, 1));
            bottomTable.addCell(createCell("1,506.33", normal, 1));
            bottomTable.addCell(createCell("TOTAL COMPENSATION:", bold, 1));
            bottomTable.addCell(createCell("0", normal, 1));
            bottomTable.addCell(createCell("7,401.36", bold, 1));

            document.add(header);
            document.add(topTable);
            document.add(centerTable);
            document.add(bottomTable);

            document.close();
            System.out.println("Payslip generated: payslip.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell createCell(String text, Font font, int colspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setColspan(colspan);
        cell.setPadding(5);
        return cell;
    }
}
