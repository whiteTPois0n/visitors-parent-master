package ch.elca.visitors.service.excel;

import ch.elca.visitors.service.dto.VisitDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class VisitExporter {

    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    private final List<VisitDto> visitDtos;


    public VisitExporter(List<VisitDto> visitDtos) {
        this.visitDtos = visitDtos;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("Active-Visitors");
    }


    private void writeHeaderRow() {
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("Title");

        cell = row.createCell(1);
        cell.setCellValue("Visitor Firstname");

        cell = row.createCell(2);
        cell.setCellValue("Visitor Lastname");

        cell = row.createCell(3);
        cell.setCellValue("Visitor Phone number");

        cell = row.createCell(4);
        cell.setCellValue("Visitor Checked In Time");

        cell = row.createCell(5);
        cell.setCellValue("Visitor Checked Out Time");

        cell = row.createCell(6);
        cell.setCellValue("Appointment Job title");

        cell = row.createCell(7);
        cell.setCellValue("Appointment Meeting type");

        cell = row.createCell(8);
        cell.setCellValue("Appointment date");

        cell = row.createCell(9);
        cell.setCellValue("Contact Person Visa");

    }


    private void writeDataRows() {

        int rowCount = 1;
        for (VisitDto visitDto : visitDtos) {
            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(String.valueOf(visitDto.getVisitor().getTitle()));

            cell = row.createCell(1);
            cell.setCellValue(visitDto.getVisitor().getFirstName());

            cell = row.createCell(2);
            cell.setCellValue(visitDto.getVisitor().getLastName());

            cell = row.createCell(3);
            cell.setCellValue(String.valueOf(visitDto.getVisitor().getPhoneNumber()));

            cell = row.createCell(4);
            cell.setCellValue(String.valueOf(visitDto.getCheckedIn()));

            if(Objects.nonNull(visitDto.getCheckedOut())) {
                cell = row.createCell(5);
                cell.setCellValue(String.valueOf(visitDto.getCheckedOut()));
            }

            if (Objects.nonNull(visitDto.getAppointment())) {
                cell = row.createCell(6);
                cell.setCellValue(visitDto.getAppointment().getJobTitle());

                cell = row.createCell(7);
                cell.setCellValue(String.valueOf(visitDto.getAppointment().getMeetingType()));

                cell = row.createCell(8);
                cell.setCellValue(String.valueOf(visitDto.getAppointment().getAppointmentDate()));

                cell = row.createCell(9);
                cell.setCellValue(visitDto.getAppointment().getContact().getVisa());
            }

            if (Objects.nonNull(visitDto.getContact())) {
                cell = row.createCell(9);
                cell.setCellValue(visitDto.getContact().getVisa());
            }
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
