package Components;

import Entity.Payslip;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

public class PayslipGenerator {

    public static void generatePayslip(String filePath, Payslip payslip) {
        Document document = new Document(PageSize.LETTER.rotate(), 36, 36, 36, 36);
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(filePath);
            PdfWriter.getInstance(document, fos);
            document.open();

            Font bold = new Font(Font.HELVETICA, 10, Font.BOLD);
            Font normal = new Font(Font.HELVETICA, 10, Font.NORMAL);
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));

            // Header Table
            PdfPTable header = new PdfPTable(3);
            header.setWidthPercentage(100);
            header.addCell(new PdfPCell(new Phrase("Department: " + payslip.getDepartment(), normal)));

            Date originalPayDate = payslip.getPayDate();
            if (originalPayDate == null) throw new Exception("Pay date is null");

            LocalDate localDate = ((java.sql.Date) originalPayDate).toLocalDate();
            LocalDate fiveDaysLater = localDate.plusDays(5);
            Date payDate = Date.from(fiveDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

            PdfPCell col2Cell = new PdfPCell(new Phrase("Pay Period:\n" + payslip.getPayPeriod(), normal));
            col2Cell.setRowspan(2);
            header.addCell(col2Cell);

            PdfPCell col3Cell = new PdfPCell(new Phrase("Pay Date: " + payDate, normal));
            col3Cell.setRowspan(2);
            header.addCell(col3Cell);

            header.addCell(new PdfPCell(new Phrase("Status: " + payslip.getEmploymentStatus(), normal)));

            // Top Table
            PdfPTable topTable = new PdfPTable(8);
            topTable.setWidthPercentage(100);
            topTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            topTable.addCell(createCell("Employee Name:\n" + payslip.getEmployeeName(), normal));
            topTable.addCell(createCell("Emp. ID:\n" + payslip.getEmployeeId(), normal));
            topTable.addCell(createCell("Rate:\n" + currencyFormat.format(payslip.getPayRate()), normal));
            topTable.addCell(createCell("TIN:\n" + payslip.getTinNumber(), normal));
            topTable.addCell(createCell("Phil. Health No.:\n" + payslip.getPhilhealthNumber(), normal));
            topTable.addCell(createCell("SSS No.:\n" + payslip.getSssNumber(), normal));
            topTable.addCell(createCell("Pag-ibig No.:\n" + payslip.getPagibigNumber(), normal));
            topTable.addCell(createCell("", normal));

            // Center Table
            PdfPTable centerTable = new PdfPTable(8);
            centerTable.setWidthPercentage(100);
            centerTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            centerTable.addCell(createCell("Days Worked:", bold));
            centerTable.addCell(createCell(String.valueOf(payslip.getDaysWorked()), normal));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getBasicPay()), normal));
            centerTable.addCell(createCell("SSS Deduct.:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getSssDeduction()), normal));
            centerTable.addCell(createCell("Salary Adj.:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getSalaryAdjustment()), normal));
            PdfPCell receivedBy = new PdfPCell(new Phrase("\n\n\n\n_____________\n\nReceived By:"));
            receivedBy.setRowspan(7);
            centerTable.addCell(receivedBy);

            centerTable.addCell(createCell("Late Amount:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getLateAmount()), normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("P.Health Deduct.:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getPhilhealthDeduction()), normal));
            centerTable.addCell(createCell("Allowance Adj.:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getAllowanceAdjustment()), normal));

            centerTable.addCell(createCell("Basic Pay:", bold));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getBasicPay()), normal));
            centerTable.addCell(createCell("Pag-Ibig Deduct.:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getPagibigDeduction()), normal));
            centerTable.addCell(createCell("Other Comp.:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getOtherCompensation()), normal));

            centerTable.addCell(createCell("ND Amount:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getNdAmount()), normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("EFund Deduct.:", bold));
            centerTable.addCell(createCell(currencyFormat.format(payslip.getEfundDeduction()), normal));
            centerTable.addCell(createCell("", normal));
            centerTable.addCell(createCell("", normal));

            // Bottom Table
            PdfPTable bottomTable = new PdfPTable(8);
            bottomTable.setWidthPercentage(100);
            bottomTable.setWidths(new float[]{2, 1, 1, 2, 2, 2, 2, 2});

            bottomTable.addCell(createCell("GROSS INCOME:", bold));
            bottomTable.addCell(createCell("", normal));
            bottomTable.addCell(createCell(currencyFormat.format(payslip.getGrossIncome()), normal));
            bottomTable.addCell(createCell("TOTAL DEDUCTION:", bold));
            bottomTable.addCell(createCell(currencyFormat.format(payslip.getTotalDeduction()), normal));
            bottomTable.addCell(createCell("TOTAL COMPENSATION:", bold));
            bottomTable.addCell(createCell(currencyFormat.format(payslip.getTotalCompensation()), normal));
            bottomTable.addCell(createCell(currencyFormat.format(payslip.getNetPay()), bold));

            // Add all tables
            document.add(header);
            document.add(topTable);
            document.add(centerTable);
            document.add(bottomTable);

            System.out.println("Payslip generated: " + filePath);

        } catch (Exception e) {
            System.err.println("Failed to generate payslip PDF:");
            e.printStackTrace();
        } finally {
            try {
                if (document != null && document.isOpen()) {
                    document.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                System.err.println("Error closing resources:");
                e.printStackTrace();
            }
        }
    }

    public static void previewPayslip(Payslip payslip) {
        try {
            String tempFilePath = System.getProperty("java.io.tmpdir") + "payslip_preview_" + System.currentTimeMillis() + ".pdf";
            generatePayslip(tempFilePath, payslip);

            File pdfFile = new File(tempFilePath);
            pdfFile.deleteOnExit();

            if (pdfFile.exists() && Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.err.println("Preview not supported or file not found.");
            }
        } catch (Exception e) {
            System.err.println("Failed to preview payslip:");
            e.printStackTrace();
        }
    }

    private static PdfPCell createCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5f);
        return cell;
    }
}
