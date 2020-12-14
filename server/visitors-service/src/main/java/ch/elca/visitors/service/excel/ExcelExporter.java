package ch.elca.visitors.service.excel;

import ch.elca.visitors.service.dto.SearchDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExporter {

    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    private final List<SearchDto> searchDtos;


    public ExcelExporter(List<SearchDto> searchDtos) {
        this.searchDtos = searchDtos;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Active-Visitors");

    }


    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Firstname");

        cell = row.createCell(1);
        cell.setCellValue("Lastname");

        cell = row.createCell(2);
        cell.setCellValue("Status");

        cell = row.createCell(3);
        cell.setCellValue("Date");
    }


    private void writeDataRows() {
        int rowCount = 1;
        for (SearchDto searchDto : searchDtos) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(searchDto.getFirstName());

            cell = row.createCell(1);
            cell.setCellValue(searchDto.getLastName());

            cell = row.createCell(2);
            cell.setCellValue(searchDto.getStatus());

            cell = row.createCell(3);
            cell.setCellValue(String.valueOf(searchDto.getDateTime()));
        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

}
