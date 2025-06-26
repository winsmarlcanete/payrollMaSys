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
            Font bold = new Font(Font.HELVETICA, 12, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);

            // Main table
            PdfPTable mainTable = new PdfPTable(5);
            mainTable.setWidthPercentage(100);
            mainTable.setWidths(new float[]{2, 2, 3, 2, 2});

            // First row: Department, Pay Period, Pay Date, Tax Status
            mainTable.addCell(createCell("Department:\nSales", bold, 1));
            mainTable.addCell(createCell("Status:\nRegular", bold, 1));
            mainTable.addCell(createCell("Pay Period:\nOctober 21 - November 5, 2024", bold, 1));
            mainTable.addCell(createCell("Pay Date:\n10-Nov-24", bold, 1));
            mainTable.addCell(createCell("Tax Status:\nME2", bold, 1));

            // Second row: Employee info
            PdfPCell empInfoCell = new PdfPCell(new Phrase("Employee Name:\n", bold));
            empInfoCell.setColspan(1);
            mainTable.addCell(empInfoCell);

            mainTable.addCell(createCell("Emp. ID:\n", bold, 1));
            mainTable.addCell(createCell("Rate:\n", bold, 1));
            mainTable.addCell(createCell("TIN:\n", bold, 1));
            mainTable.addCell(createCell("Phil.Health No. / SSS No.:\n233-658-708", bold, 1));

            document.add(mainTable);

            document.add(Chunk.NEWLINE);

            // Salary breakdown table
            PdfPTable salTable = new PdfPTable(4);
            salTable.setWidthPercentage(100);
            salTable.setWidths(new float[]{2, 1, 2, 1});

            salTable.addCell(createCell("Days Worked:", normal, 1));
            salTable.addCell(createCell("13.0000", normal, 1));
            salTable.addCell(createCell("SSS Deduct:", normal, 1));
            salTable.addCell(createCell("832.50", normal, 1));

            salTable.addCell(createCell("Late Amount:", normal, 1));
            salTable.addCell(createCell("", normal, 1));
            salTable.addCell(createCell("P.Health Deduct:", normal, 1));
            salTable.addCell(createCell("273.83", normal, 1));

            salTable.addCell(createCell("Basic Pay:", normal, 1));
            salTable.addCell(createCell("8,789.69", normal, 1));
            salTable.addCell(createCell("Pag-ibig Deduct:", normal, 1));
            salTable.addCell(createCell("100.00", normal, 1));

            salTable.addCell(createCell("ND Amount:", normal, 1));
            salTable.addCell(createCell("", normal, 1));
            salTable.addCell(createCell("Efund Deduct:", normal, 1));
            salTable.addCell(createCell("300.00", normal, 1));

            salTable.addCell(createCell("Sun/Sp Hol.:", normal, 1));
            salTable.addCell(createCell("", normal, 1));
            salTable.addCell(createCell("Other Deduct:", normal, 1));
            salTable.addCell(createCell("", normal, 1));

            salTable.addCell(createCell("Leg. Holiday:", normal, 1));
            salTable.addCell(createCell("", normal, 1));
            salTable.addCell(createCell("Salary Adj:", normal, 1));
            salTable.addCell(createCell("", normal, 1));

            salTable.addCell(createCell("OT Amount:", normal, 1));
            salTable.addCell(createCell("118.02", normal, 1));
            salTable.addCell(createCell("Allowance Adj:", normal, 1));
            salTable.addCell(createCell("", normal, 1));

            salTable.addCell(createCell("", normal, 1));
            salTable.addCell(createCell("", normal, 1));
            salTable.addCell(createCell("Other Comp:", normal, 1));
            salTable.addCell(createCell("", normal, 1));

            document.add(salTable);

            document.add(Chunk.NEWLINE);

            // Totals
            PdfPTable totals = new PdfPTable(4);
            totals.setWidthPercentage(100);
            totals.setWidths(new float[]{2, 1, 2, 1});

            totals.addCell(createCell("GROSS INCOME:", bold, 1));
            totals.addCell(createCell("8,907.71", bold, 1));
            totals.addCell(createCell("TOTAL DEDUCT:", bold, 1));
            totals.addCell(createCell("1506.33", bold, 1));

            totals.addCell(createCell("TOTAL COMP:", bold, 1));
            totals.addCell(createCell("0", bold, 1));
            totals.addCell(createCell("", bold, 1));
            totals.addCell(createCell("7,401.38", bold, 1));

            document.add(totals);

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
