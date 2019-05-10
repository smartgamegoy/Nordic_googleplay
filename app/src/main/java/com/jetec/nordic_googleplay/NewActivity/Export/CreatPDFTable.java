package com.jetec.nordic_googleplay.NewActivity.Export;

import android.content.Context;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.jetec.nordic_googleplay.NewActivity.GetUnit;
import com.jetec.nordic_googleplay.R;

import java.util.ArrayList;
import java.util.List;

public class CreatPDFTable {

    private String TAG = "CreatPDFTable";

    public CreatPDFTable() {
        super();
    }

    public PdfPTable creatTable(Context context, Document document, List<String> namelist, List<String> timeList,
                                List<List<String>> saveList, int page) {
        GetUnit getUnit = new GetUnit();
        float fntSize = 6.0f;
        float lineSpacing = 8f;
        int row = namelist.size();
        PdfPTable table = null;

        /*if (row == 1) {
            table = new PdfPTable((row + 3) * 4);
        } else if (row == 2) {
            table = new PdfPTable((row + 2) * 4);
        } else if (row == 3) {
            table = new PdfPTable((row + 1) * 4);
        } else if (row == 4) {
            table = new PdfPTable(row * 4);
        } else if (row == 5) {
            table = new PdfPTable(row * 4);
        } else if (row == 6) {
            table = new PdfPTable(row * 4);
        }*/

        List<String> rowName = new ArrayList<>();
        rowName.clear();
        rowName.add(context.getString(R.string.pdftime));
        for (int i = 0; i < row; i++) {
            rowName.add(getUnit.getItemString(context, namelist.get(i), i));
        }

        Log.e(TAG, "rowName = " + rowName);

        if (row > 3) {
            table = new PdfPTable((row + 2) * 2);
            for (int i = 0; i < page; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < rowName.size(); k++) {
                        PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, rowName.get(k),
                                FontFactory.getFont(FontFactory.HELVETICA_BOLD, fntSize))));
                        if (k == 0) {
                            cell.setColspan(2);
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        }
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);
                    }
                }
                for (int j = (150 * i); j < 75 + (150 * i); j++) {
                    for (int k = 0; k < 2; k++) {
                        if ((j + (k * 75)) < timeList.size()) {
                            PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, timeList.get((j + (k * 75)))/*.substring(3)*/,
                                    FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                            cell.setColspan(2);
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                            table.addCell(cell);
                        } else {
                            PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                    FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                            cell.setColspan(2);
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            table.addCell(cell);
                        }
                        for (int l = 0; l < saveList.size(); l++) {
                            if ((j + (k * 75)) < saveList.get(l).size()) {  //saveList.get(l).get((j + (k * 75))
                                PdfPCell cell;
                                byte[] bytes = saveList.get(l).get((j + (k * 75))).getBytes();
                                if(bytes.length > 7){
                                    cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "-",
                                            FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                }
                                else {
                                    cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, saveList.get(l).get((j + (k * 75))),
                                            FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                }
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                table.addCell(cell);
                            } else {
                                PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                        FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                table.addCell(cell);
                            }
                        }
                    }
                }
            }
        } else {
            table = new PdfPTable((row + 2) * 4);
            for (int i = 0; i < page; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < rowName.size(); k++) {
                        PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, rowName.get(k),
                                FontFactory.getFont(FontFactory.HELVETICA_BOLD, fntSize))));
                        if (k == 0) {
                            cell.setColspan(2);
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        }
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        table.addCell(cell);
                    }
                }
                for (int j = (300 * i); j < 75 + (300 * i); j++) {
                    for (int k = 0; k < 4; k++) {
                        if ((j + (k * 75)) < timeList.size()) {
                            PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, timeList.get((j + (k * 75))).substring(3),
                                    FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                            cell.setColspan(2);
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                            table.addCell(cell);
                        } else {
                            PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                    FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                            cell.setColspan(2);
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            table.addCell(cell);
                        }
                        for (int l = 0; l < saveList.size(); l++) {
                            if ((j + (k * 75)) < saveList.get(l).size()) {  //saveList.get(l).get((j + (k * 75))
                                PdfPCell cell;
                                byte[] bytes = saveList.get(l).get((j + (k * 75))).getBytes();
                                if(bytes.length > 7){
                                    cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "-",
                                            FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                }
                                else {
                                    cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, saveList.get(l).get((j + (k * 75))),
                                            FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                }
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                table.addCell(cell);
                            } else {
                                PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                        FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                table.addCell(cell);
                            }
                        }
                    }
                }
            }
        }
        return table;
        /*if (row > 3) {
            for (int i = 0; i < page; i++) {
                for (int j = 0; j < 2; j++) {
                    for (int k = 0; k < rowName.size(); k++) {
                        PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, rowName.get(k),
                                FontFactory.getFont(FontFactory.HELVETICA_BOLD, fntSize))));
                        cell.setColspan(2);
                        if (k == 0)
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        assert table != null;
                        table.addCell(cell);
                    }
                }
                for (int j = (150 * i); j < 75 + (150 * i); j++) {
                    for (int k = 0; k < 2; k++) {
                        if (j % 75 == 0) {
                            if ((j + (k * 75)) < timeList.size()) {
                                PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, timeList.get((j + (k * 75))).substring(3),
                                        FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                cell.setColspan(2);
                                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                assert table != null;
                                table.addCell(cell);
                            } else {
                                PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                        FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                cell.setColspan(2);
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                assert table != null;
                                table.addCell(cell);
                            }
                            for (int l = 0; l < saveList.size(); l++) {
                                if ((j + (k * 75)) < saveList.get(l).size()) {
                                    PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, saveList.get(l).get((j + (k * 75))),
                                            FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                    table.addCell(cell);
                                } else {
                                    PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                            FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                    cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                    table.addCell(cell);
                                }
                            }
                        }
                    }
                }
            }
            return table;
        } else {
            for (int i = 0; i < page; i++) {
                for (int j = 0; j < 2; j++) {//
                    for (int k = 0; k < rowName.size(); k++) {
                        Log.e(TAG, "rowName -> 進來");
                        PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, rowName.get(k),
                                FontFactory.getFont(FontFactory.HELVETICA_BOLD, fntSize))));
                        cell.setColspan(2);
                        if (k == 0)
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                        assert table != null;
                        table.addCell(cell);
                    }
                }
                for (int j = (300 * i); j < 75 + (300 * i); j++) {
                    for (int k = 0; k < 4; k++) {
                        Log.e(TAG, "建立" + j);
                        Log.e(TAG, "創" + k);
                        if ((j + (k * 75)) < timeList.size()) {
                            PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, timeList.get((j + (k * 75))).substring(3),
                                    FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                            cell.setColspan(2);
                            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                            assert table != null;
                            table.addCell(cell);
                        } else {
                            PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                    FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                            cell.setColspan(2);
                            cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                            assert table != null;
                            table.addCell(cell);
                        }
                        for (int l = 0; l < saveList.size(); l++) {
                            if ((j + (k * 75)) < saveList.get(l).size()) {
                                PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, saveList.get(l).get((j + (k * 75))),
                                        FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                table.addCell(cell);
                            } else {
                                PdfPCell cell = new PdfPCell(new Paragraph(new Phrase(lineSpacing, "",
                                        FontFactory.getFont(FontFactory.HELVETICA, fntSize))));
                                cell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
                                table.addCell(cell);
                            }
                        }
                    }
                }
            }
            return table;
        }*/
    }
}
