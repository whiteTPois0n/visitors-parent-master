package ch.elca.visitors.service.excel;

import ch.elca.visitors.service.dto.AppointmentDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class AppointmentExporter {

    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    private final List<AppointmentDto> appointmentDtos;


    public AppointmentExporter(List<AppointmentDto> appointmentDtos) {
        this.appointmentDtos = appointmentDtos;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Future-Visitors");
    }


    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Title");

        cell = row.createCell(1);
        cell.setCellValue("Firstname");

        cell = row.createCell(2);
        cell.setCellValue("Lastname");

        cell = row.createCell(3);
        cell.setCellValue("Phone number");

        cell = row.createCell(4);
        cell.setCellValue("Job title");

        cell = row.createCell(5);
        cell.setCellValue("Meeting type");

        cell = row.createCell(6);
        cell.setCellValue("Appointment date");

        cell = row.createCell(7);
        cell.setCellValue("Contact Person Visa");

    }


    private void writeDataRows() {
        int rowCount = 1;
        for (AppointmentDto appointmentDto : appointmentDtos) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(appointmentDto.getVisitor().getTitle().toString());

            cell = row.createCell(1);
            cell.setCellValue(appointmentDto.getVisitor().getFirstName());

            cell = row.createCell(2);
            cell.setCellValue(appointmentDto.getVisitor().getLastName());

            cell = row.createCell(3);
            cell.setCellValue(String.valueOf(appointmentDto.getVisitor().getPhoneNumber()));

            cell = row.createCell(4);
            cell.setCellValue(String.valueOf(appointmentDto.getJobTitle()));

            cell = row.createCell(5);
            cell.setCellValue(String.valueOf(appointmentDto.getMeetingType()).toLowerCase());

            cell = row.createCell(6);
            cell.setCellValue(String.valueOf(appointmentDto.getAppointmentDate()));

            cell = row.createCell(7);
            cell.setCellValue(String.valueOf(appointmentDto.getContact().getVisa()));
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
