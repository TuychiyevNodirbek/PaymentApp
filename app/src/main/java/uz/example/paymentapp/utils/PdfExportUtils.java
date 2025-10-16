package uz.example.paymentapp.utils;

import android.content.Context;
import android.util.Log;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import uz.example.paymentapp.domain.model.Transaction;

public class PdfExportUtils {
    public static File exportTransactions(Context context, List<Transaction> transactions) {
        File file = new File(context.getFilesDir(), "transactions.pdf");

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Paragraph title = new Paragraph("Transaction History", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20f);
            document.add(title);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{3, 2, 2, 3});

            addCell(table, "ID", true);
            addCell(table, "Amount", true);
            addCell(table, "Success", true);
            addCell(table, "Timestamp", true);

            for (Transaction t : transactions) {
                addCell(table, t.getId(), false);
                addCell(table, String.valueOf(t.getAmount()), false);
                addCell(table, String.valueOf(t.getStatus()), false);
                addCell(table, String.valueOf(t.getTimestamp()), false);
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            Log.e("TTT", "Error exporting PDF: " + e.getMessage(), e);
            e.printStackTrace();
            return null;
        }

        return file;
    }

    private static void addCell(PdfPTable table, String text, boolean isHeader) {
        Font font = isHeader ? new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD) :
                new Font(Font.FontFamily.HELVETICA, 12);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5f);
        table.addCell(cell);
    }
}
