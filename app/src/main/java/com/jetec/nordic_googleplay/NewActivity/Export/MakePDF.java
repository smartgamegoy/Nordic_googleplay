package com.jetec.nordic_googleplay.NewActivity.Export;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.jetec.nordic_googleplay.CreatPDF.FooterHandler;
import com.jetec.nordic_googleplay.CreatPDF.HeaderHandler;
import com.jetec.nordic_googleplay.NewActivity.UserSQL.LogSQL;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MakePDF {

    private String TAG = "MakePDF";

    public MakePDF() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    public void todoPDF(Context context, File pdffile, List<String> nameList, List<String> timeList, List<List<String>> saveList) {
        try {
            Log.e(TAG, "nameList = " + nameList);
            Log.e(TAG, "timeList = " + timeList);
            Log.e(TAG, "saveList = " + saveList);
            LogSQL logSQL = new LogSQL(context);
            HeaderHandler head = new HeaderHandler();
            FooterHandler foot = new FooterHandler();
            CreatPDFTable creatPDFTable = new CreatPDFTable();
            int alldata = timeList.size(), page;
            page = calculatepage(alldata, nameList);
            Document document = new Document(PageSize.A4, -25, -25, 40, 40);
            FileOutputStream fOut = new FileOutputStream(pdffile);
            PdfWriter writer = PdfWriter.getInstance(document, fOut);
            BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UTF16-H", true);
            Font chineseFont = new Font(bfChinese, 12, Font.NORMAL);

            String getName = logSQL.getName();
            Paragraph title = new Paragraph(getName, chineseFont);

            head.setHeader(title);
            foot.setallpage(page);
            writer.setPageEvent(head);
            writer.setPageEvent(foot);

            document.open();
            document.add(creatPDFTable.creatTable(context, document, nameList, timeList, saveList, page));
            document.close();
            logSQL.close();

            Log.e(TAG, "已完成");
        } catch (IOException | DocumentException e) {
            Log.e(TAG, "錯誤 " + e);
            e.printStackTrace();
        }
    }

    private int calculatepage(int size, List<String> nameList){
        int page;
        if(nameList.size() > 3){
            if (size % 150 == 0) {
                page = size / 150;
            } else {
                page = (size / 150) + 1;
            }
        }
        else {
            if (size % 300 == 0) {
                page = size / 300;
            } else {
                page = (size / 300) + 1;
            }
        }
        return page;
    }
}
