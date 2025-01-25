package spring.custom.common.poi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiCellStyle {
  
  public static final String FONT = "맑은 고딕";
  
  public static CellStyle cellHeader(XSSFWorkbook workbook) {
    CellStyle style = workbook.createCellStyle();
    style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setBorderTop(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setWrapText(true);
    style.setFont(PoiCellStyle.fontTitle(workbook));
    return style;
  }
  
  public static CellStyle cellContent(XSSFWorkbook workbook) {
    CellStyle style = workbook.createCellStyle();
    //style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
    //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    style.setAlignment(HorizontalAlignment.CENTER);
    style.setVerticalAlignment(VerticalAlignment.CENTER);
    style.setBorderTop(BorderStyle.THIN);
    style.setBorderRight(BorderStyle.THIN);
    style.setBorderBottom(BorderStyle.THIN);
    style.setBorderLeft(BorderStyle.THIN);
    style.setWrapText(true);
    style.setFont(PoiCellStyle.fontContent(workbook));
    return style;
  }
  
  public static String ofLocalDateTime(LocalDateTime localDateTime) {
    if (localDateTime == null) return "";
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }
  
  public static String ofLocalDate(LocalDate localDate) {
    if (localDate == null) return "";
    return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }
  
  public static Font fontTitle(Workbook workbook) {
    Font font = workbook.createFont();
    font.setFontName("맑은 고딕");
    font.setBold(true);
    //font.setColor(Font.COLOR_RED);
    font.setFontHeight((short)(10*20));
    return font;
  }
  
  public static Font fontContent(Workbook workbook) {
    Font font = workbook.createFont();
    font.setFontName("맑은 고딕");
    font.setFontHeight((short)(10*20));
    return font;
  }
}
