package spring.custom.api.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import spring.custom.api.controller.internal.FundInternalController;
import spring.custom.api.controller.internal.JpaSampleInternalController;
import spring.custom.api.controller.internal.MybatisSampleInternalController;
import spring.custom.api.dto.FundDto;
import spring.custom.api.dto.JpaSampleDto;
import spring.custom.api.dto.MybatisSampleDto;
import spring.custom.code.EnumScientist;
import spring.custom.common.constant.Constant;
import spring.custom.common.enumcode.EnumMapper;
import spring.custom.common.exception.AppException;
import spring.custom.common.exception.data.DataNotFoundException;
import spring.custom.common.mybatis.PageResponse;
import spring.custom.common.poi.PoiCellStyle;
import spring.custom.common.syscode.ERROR;
import spring.custom.common.util.StringFormat;

@RestController
@RequiredArgsConstructor
public class ExcelDownController {
  
  @Nullable final MybatisSampleInternalController mybatisSampleInternalController;
  @Nullable final JpaSampleInternalController jpaSampleInternalController;
  @Nullable final FundInternalController fundInternalController;
  final EnumMapper enumMapper;
  
  @GetMapping("/excel-down/multi-thread/v1/fund/fund-mst/all")
  public ResponseEntity<byte[]> allFundMstsExcelDownMultiThread() {
    // data
    List<FundDto.FundMstRes> data = fundInternalController.allFundMsts();
    if (data.size() == 0) {
      throw new DataNotFoundException();
    }
    
    final String subject = "펀드_all(multi-thread)";
    
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      
      // sheet
      Sheet sheet = workbook.createSheet(subject);
      
      // header row:0
      @AllArgsConstructor
      enum HeaderStar0 {
        no("No.", 10),
        fundCd("fundCd", 13),
        fundFnm("fundFnm", 70),
        seoljYmd("seoljYmd", 10),
        fstSeoljAek("fstSeoljAek", 25),
        bmCd("bmCd", 12),
        bmNm("bmNm", 65),
        ;
        String name;
        int width;
      }
      Row row0 = sheet.createRow(0);
      for (int i=0; i<HeaderStar0.values().length; i++) {
        HeaderStar0 header = HeaderStar0.values()[i];
        sheet.setDefaultColumnStyle(i, PoiCellStyle.cellContent(workbook));
        sheet.setColumnWidth(i, 256*header.width);
        
        Cell cell = row0.createCell(i);
        cell.setCellValue(header.name);
        cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      }
      
      int cntHeader = 1;
      
      // contents
      int chunkSize = 10000;
      int cnt = data.size();
      int threadCount = (int) Math.ceil((double) cnt / chunkSize);
      ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
      List<Future<Workbook>> futures = new ArrayList<>();
      for (int i = 0; i < threadCount; i++) {
        final int start = i * chunkSize;
        final int end = Math.min(start + chunkSize, cnt);
        futures.add(executorService.submit(() -> createWorkbookChunk(data.subList(start, end), start)));
      }
      
      StopWatch stopWatch = new StopWatch("merge processing");
      stopWatch.start();
      int rowIndex = cntHeader;
      for (Future<Workbook> future : futures) {
        Workbook chunkWorkbook;
        try {
          chunkWorkbook = future.get(); // chunkWorkbook: thread 별 처리 결과
        } catch (InterruptedException | ExecutionException e) {
          e.printStackTrace();
          continue;
        }
        Sheet chunkSheet = chunkWorkbook.getSheetAt(0);
        for (Row row : chunkSheet) {
          Row newRow = sheet.createRow(rowIndex++);
          for (Cell cell : row) {
            Cell newCell = newRow.createCell(cell.getColumnIndex());
            PoiCellStyle.copyCell(cell, newCell);
          }
        }
      }
      stopWatch.stop();
      System.err.println(stopWatch.shortSummary());
      
      executorService.shutdown();
      
      // response
      workbook.write(out);
      String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String filename = URLEncoder.encode(subject+"_"+now+".xlsx", Constant.ENCODING_UTF8);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\";")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(out.toByteArray());
    } catch (IOException e) {
      throw new AppException(ERROR.E906, e, new Object[] {subject+".xlsx"});
    }
  }
  
  private Workbook createWorkbookChunk(List<FundDto.FundMstRes> chunkData, int startRowIndex) {
    StopWatch stopWatch = new StopWatch("chunk processing");
    stopWatch.start();
    Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("chunk-sheet");
    CellStyle numberStyle = PoiCellStyle.cellNumber(workbook);
    
    IntStream.range(0, chunkData.size()).forEach(ridx -> {
      Row row = sheet.createRow(ridx);
      FundDto.FundMstRes item = chunkData.get(ridx);

      Cell cell;
      int cidx = 0;

      cell = row.createCell(cidx++);
      cell.setCellValue(startRowIndex + ridx + 1);

      cell = row.createCell(cidx++);
      cell.setCellValue(item.getFundCd());

      cell = row.createCell(cidx++);
      cell.setCellValue(item.getFundFnm());

      cell = row.createCell(cidx++);
      cell.setCellValue(item.getSeoljYmd());

      cell = row.createCell(cidx++);
      cell.setCellValue(item.getFstSeoljAek() == null ? 0.0 : item.getFstSeoljAek());
      cell.setCellStyle(numberStyle);

      cell = row.createCell(cidx++);
      cell.setCellValue(item.getBmCd());

      cell = row.createCell(cidx++);
      cell.setCellValue(item.getBmNm());
    });
    stopWatch.stop();
    System.err.println(stopWatch.shortSummary());
    return workbook;
  }
  
  @GetMapping("/excel-down/v1/fund/fund-mst/all")
  public ResponseEntity<byte[]> allFundMstsExcelDown() {
    // data
    List<FundDto.FundMstRes> data = fundInternalController.allFundMsts();
    if (data.size() == 0) {
      throw new DataNotFoundException();
    }
    
    final String subject = "펀드_all";
    
    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      
      // sheet
      Sheet sheet = workbook.createSheet(subject);
      
      // header row:0
      @AllArgsConstructor
      enum HeaderStar0 {
        no("No.", 10),
        fundCd("fundCd", 13),
        fundFnm("fundFnm", 70),
        seoljYmd("seoljYmd", 10),
        fstSeoljAek("fstSeoljAek", 25),
        bmCd("bmCd", 12),
        bmNm("bmNm", 65),
        ;
        String name;
        int width;
      }
      Row row0 = sheet.createRow(0);
      for (int i=0; i<HeaderStar0.values().length; i++) {
        HeaderStar0 header = HeaderStar0.values()[i];
        sheet.setDefaultColumnStyle(i, PoiCellStyle.cellContent(workbook));
        sheet.setColumnWidth(i, 256*header.width);
        
        Cell cell = row0.createCell(i);
        cell.setCellValue(header.name);
        cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      }
      
      int cntHeader = 1;
      
      // contents
      StopWatch stopWatch = new StopWatch("contents processing");
      stopWatch.start();
      int cnt = data.size();
      AtomicInteger no = new AtomicInteger(0);
      CellStyle numberStyle = PoiCellStyle.cellNumber(workbook);
      for (int ridx=0; ridx<cnt; ridx++) {
        Row row = sheet.createRow(ridx+cntHeader);
        FundDto.FundMstRes item = data.get(ridx);
        
        Cell cell;
        AtomicInteger cidx = new AtomicInteger(0);
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(no.incrementAndGet());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getFundCd());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getFundFnm());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getSeoljYmd());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getFstSeoljAek() == null ? 0.0 : item.getFstSeoljAek());
        cell.setCellStyle(numberStyle);
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getBmCd());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getBmNm());
      }
      stopWatch.stop();
      System.err.println(stopWatch.shortSummary());
      
      // response
      workbook.write(out);
      String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String filename = URLEncoder.encode(subject+"_"+now+".xlsx", Constant.ENCODING_UTF8);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\";")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(out.toByteArray());
    } catch (IOException e) {
      throw new AppException(ERROR.E906, e, new Object[] {subject+".xlsx"});
    }
  }
  
  @GetMapping("/excel-down/v1/jpa-sample/stars/all")
  public ResponseEntity<byte[]> allStarsExcelDown() {
    // data
    List<JpaSampleDto.StarRes> data = jpaSampleInternalController.allStars();
    if (data.size() == 0) {
      throw new DataNotFoundException();
    }
    
    final String subject = "항성_all";
    
    try (Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      
      // sheet
      Sheet sheet = workbook.createSheet(subject);
      
      // header row:0
      @AllArgsConstructor
      enum HeaderStar0 {
        No("No.", 6),
        id("id", 6),
        NAME("name", 25),
        distance("distance", 14),
        brightness("brightness", 14),
        mass("mass", 14),
        temperature("temperature", 14),
        ;
        String name;
        int width;
      }
      Row row0 = sheet.createRow(0);
      for (int i=0; i<HeaderStar0.values().length; i++) {
        HeaderStar0 header = HeaderStar0.values()[i];
        sheet.setDefaultColumnStyle(i, PoiCellStyle.cellContent(workbook));
        sheet.setColumnWidth(i, 256*header.width);
        
        Cell cell = row0.createCell(i);
        cell.setCellValue(header.name);
        cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      }
      
      int cntHeader = 1;
      
      // contents
      int cnt = data.size();
      AtomicInteger no = new AtomicInteger(0);
      for (int ridx=0; ridx<cnt; ridx++) {
        Row row = sheet.createRow(ridx+cntHeader);
        JpaSampleDto.StarRes item = data.get(ridx);
        
        Cell cell;
        AtomicInteger cidx = new AtomicInteger(0);
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(no.incrementAndGet());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getId());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getName());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getDistance());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getBrightness());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getMass());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getTemperature());
      }
      
      // response
      workbook.write(out);
      String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String filename = URLEncoder.encode(subject+"_"+now+".xlsx", Constant.ENCODING_UTF8);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\";")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(out.toByteArray());
    } catch (IOException e) {
      throw new AppException(ERROR.E906, e, new Object[] {subject+".xlsx"});
    }
  }
  
  @GetMapping("/excel-down/v1/jpa-sample/stars/search")
  public ResponseEntity<byte[]> searchStarsExcelDown(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    
    // data
    Page<JpaSampleDto.StarRes> result = jpaSampleInternalController.searchStars(name, page, size);
    List<JpaSampleDto.StarRes> data = result.getContent();
    if (data.size() == 0) {
      throw new DataNotFoundException();
    }
    
    final String subject = "항성";
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      
      // sheet
      Sheet sheet = workbook.createSheet(subject);
      
      sheet.setColumnWidth(0, 256*6);
      sheet.setColumnWidth(1, 256*25);
      sheet.setColumnWidth(2, 256*14);
      sheet.setColumnWidth(3, 256*14);
      sheet.setColumnWidth(4, 256*14);
      sheet.setColumnWidth(5, 256*14);
      
      // header row0
      Row row0 = sheet.createRow(0);
      Cell cell = row0.createCell(0);
      cell.setCellValue("id");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(1);
      cell.setCellValue("name");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(2);
      cell.setCellValue("distance");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(3);
      cell.setCellValue("brightness");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(4);
      cell.setCellValue("mass");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(5);
      cell.setCellValue("temperature");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      // contents
      int cnt = data.size();
      for (int ridx=0; ridx<cnt; ridx++) {
        Row row = sheet.createRow(ridx+1);
        JpaSampleDto.StarRes item = data.get(ridx);
        AtomicInteger cidx = new AtomicInteger(0);
        
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getId());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getName());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getDistance());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getBrightness());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getMass());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getTemperature());
      }
      
      // response
      workbook.write(out);
      String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String filename = URLEncoder.encode(subject+"_"+now+".xlsx", Constant.ENCODING_UTF8);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\";")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(out.toByteArray());
    } catch (IOException e) {
      throw new AppException(ERROR.E906, e, new Object[] {subject+".xlsx"});
    }
  }
  
  @GetMapping("/excel-down/v1/mybatis-sample/scientists/all")
  public ResponseEntity<byte[]> allScientistsExcelDown() {
    // data
    MybatisSampleDto.ScientistListRes result = mybatisSampleInternalController.allScientists();
    List<MybatisSampleDto.ScientistRes> data = result.getList();
    if (data.size() == 0) {
      throw new DataNotFoundException();
    }
    
    final String subject = "과학자_all";
    
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      
      // sheet
      Sheet sheet = workbook.createSheet(subject);
      
      // header merge
      sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 0));
      sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 1));
      sheet.addMergedRegion(new CellRangeAddress(0, 1, 2, 2));
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));
      sheet.addMergedRegion(new CellRangeAddress(0, 1, 6, 6));
      
      // header row:0
      @AllArgsConstructor
      enum Header0 {
        No("No.", 6),
        Id("id", 6),
        Name("name", 25),
        Century("birth and death years", 14),
        BirthYear("birth and death years", 14),
        DeathYear("birth and death years", 14),
        Fos("field of study", 14),
        ;
        String name;
        int width;
      }
      Row row0 = sheet.createRow(0);
      for (int i=0; i<Header0.values().length; i++) {
        Header0 header = Header0.values()[i];
        sheet.setDefaultColumnStyle(i, PoiCellStyle.cellContent(workbook));
        sheet.setColumnWidth(i, 256*header.width);
        
        Cell cell = row0.createCell(i);
        cell.setCellValue(header.name);
        cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      }
      
      // header row:1
      Row row1 = sheet.createRow(1);
      @AllArgsConstructor
      enum Header1 {
        No("No.", 6),
        Id("id", 6),
        Name("name", 25),
        Century("century", 14),
        BirthYear("birth", 14),
        DeathYear("death", 14),
        Fos("field of study", 14),
        ;
        String name;
        int width;
      }
      for (int i=0; i<Header1.values().length; i++) {
        Header1 header = Header1.values()[i];
        sheet.setDefaultColumnStyle(i, PoiCellStyle.cellContent(workbook));
        sheet.setColumnWidth(i, 256*header.width);
        
        Cell cell = row1.createCell(i);
        cell.setCellValue(header.name);
        cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      }
      int cntHeader = 2;
      
      // contents
      int cnt = data.size();
      AtomicInteger no = new AtomicInteger(0);
      for (int ridx=0; ridx<cnt; ridx++) {
        Row row = sheet.createRow(ridx+cntHeader);
        MybatisSampleDto.ScientistRes item = data.get(ridx);
        
        Cell cell;
        AtomicInteger cidx = new AtomicInteger(0);
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(no.incrementAndGet());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getId());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getName());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(StringFormat.toOrdinal((int)Math.ceil(item.getBirthYear()/100.0)));
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getBirthYear());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(item.getDeathYear());
        
        cell = row.createCell(cidx.getAndIncrement());
        cell.setCellValue(Optional.ofNullable(item.getFosCd()).map(EnumScientist.FieldOfStudy::getLabel).orElse(""));
      }
      
      // response
      workbook.write(out);
      String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String filename = URLEncoder.encode(subject+"_"+now+".xlsx", Constant.ENCODING_UTF8);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\";")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(out.toByteArray());
    } catch (IOException e) {
      throw new AppException(ERROR.E906, e, new Object[] {subject+".xlsx"});
    }
  }
  
  @GetMapping("/excel-down/v1/mybatis-sample/scientists/search")
  public ResponseEntity<byte[]> searchScientistsExcelDown(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String fosCd,
      @RequestParam(required = false) @Min(1) @Max(21) @Nullable Integer century,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    
    // data
    PageResponse<MybatisSampleDto.ScientistRes> result =
        mybatisSampleInternalController.searchScientists(name, fosCd, century, page, size);
    List<MybatisSampleDto.ScientistRes> data = result.getPageData();
    if (data.size() == 0) {
      throw new DataNotFoundException();
    }
    
    final String subject = "과학자";
    try (XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      
      // sheet
      Sheet sheet = workbook.createSheet(subject);
      
      sheet.setColumnWidth(0, 256*6);
      sheet.setColumnWidth(1, 256*25);
      sheet.setColumnWidth(2, 256*14);
      sheet.setColumnWidth(3, 256*14);
      sheet.setColumnWidth(4, 256*14);
      
      // header row0
      Row row0 = sheet.createRow(0);
      Cell cell = row0.createCell(0);
      cell.setCellValue("id");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(1);
      cell.setCellValue("name");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(2);
      cell.setCellValue("birth_year");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(3);
      cell.setCellValue("death_year");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      cell = row0.createCell(4);
      cell.setCellValue("fos_cd");
      cell.setCellStyle(PoiCellStyle.cellHeader(workbook));
      
      // contents
      int cnt = data.size();
      for (int ridx=0; ridx<cnt; ridx++) {
        Row row = sheet.createRow(ridx+1);
        MybatisSampleDto.ScientistRes item = data.get(ridx);
        AtomicInteger cidx = new AtomicInteger(0);
        
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getId());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getName());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getBirthYear());
        row.createCell(cidx.getAndIncrement()).setCellValue(item.getDeathYear());
        row.createCell(cidx.getAndIncrement()).setCellValue(Optional.ofNullable(item.getFosCd()).map(EnumScientist.FieldOfStudy::getValue).orElse(""));
      }
      
      // response
      workbook.write(out);
      String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
      String filename = URLEncoder.encode(subject+"_"+now+".xlsx", Constant.ENCODING_UTF8);
      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\";")
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(out.toByteArray());
    } catch (IOException e) {
      throw new AppException(ERROR.E906, e, new Object[] {subject+".xlsx"});
    }
  }
}
