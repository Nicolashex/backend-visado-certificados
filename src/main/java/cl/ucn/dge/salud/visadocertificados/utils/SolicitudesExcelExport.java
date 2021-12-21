package cl.ucn.dge.salud.visadocertificados.utils;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SolicitudesExcelExport {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Solicitud> listaSolicitudes;

    public SolicitudesExcelExport(List<Solicitud> listaSolicitudes){
        this.listaSolicitudes = listaSolicitudes;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){

        sheet = workbook.createSheet("Solicitudes");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id Solicitud", style);
        createCell(row, 1, "Rut Solicitante", style);
        createCell(row, 2, "Rut Medico Tratante", style);
        createCell(row, 3, "Estado", style);
        createCell(row, 4, "Fecha Inicio Reposo", style);
        createCell(row, 5, "Fecha Fin Reposo", style);
        createCell(row, 6, "Fecha Inicio Solicitud", style);
        createCell(row, 7, "Fecha Fin Solicitud", style);
        createCell(row, 8, "Fecha Ingreso Solicitud", style);
        createCell(row, 9, "Fecha Respuesta Solicitud", style);

    }

    public void createCell(Row row, int columnCount,Object value,CellStyle style){

        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof Integer){
            cell.setCellValue((Integer) value);
        }else if(value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else{
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);

    }

    private void writeDataLines(){

        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Solicitud solicitud:listaSolicitudes){
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row,columnCount++,String.valueOf(solicitud.getId()),style);
            createCell(row,columnCount++,String.valueOf(solicitud.getEstudiante().getRut()),style);
            String rutMedico = (solicitud.getIdProfesional() == null) ? " "
                    : String.valueOf(solicitud.getIdProfesional().getRut());
            createCell(row,columnCount++,rutMedico,style);
            createCell(row,columnCount++,String.valueOf(solicitud.getEstado()),style);
            String fechaInicioReposo = solicitud.getFechaInicioReposo() == null ? ""
                    : String.valueOf(solicitud.getFechaInicioReposo().format(DateTimeFormatter.ofPattern("dd/MM/yyy")));
            createCell(row,columnCount++,fechaInicioReposo,style);
            String fechaFinReposo = solicitud.getFechaFinReposo() == null ? ""
                    : String.valueOf(solicitud.getFechaFinReposo().format(DateTimeFormatter.ofPattern("dd/MM/yyy")));
            createCell(row,columnCount++,fechaFinReposo,style);
            String fechaInicioSolicitud = solicitud.getFechaInicioSolicitud() == null ? ""
                    : String.valueOf(solicitud.getFechaInicioSolicitud().format(DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm")));
            createCell(row,columnCount++,fechaInicioSolicitud,style);
            String fechaFinSolicitud = solicitud.getFechaFinSolicitud() == null ? ""
                    : String.valueOf(solicitud.getFechaFinSolicitud().format(DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm")));
            createCell(row,columnCount++,fechaFinSolicitud,style);
            String fechaIngresoEvaluacion = solicitud.getIngresoEvaluacion() == null ? ""
                    : String.valueOf(solicitud.getIngresoEvaluacion().format(DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm")));
            createCell(row,columnCount++,fechaIngresoEvaluacion,style);
            String fechaRespuestaEvaluacion = solicitud.getRespuestaEvaluacion() == null ? ""
                    : String.valueOf(solicitud.getRespuestaEvaluacion().format(DateTimeFormatter.ofPattern("dd/MM/yyy hh:mm")));
            createCell(row,columnCount++,fechaRespuestaEvaluacion,style);

        }

    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();

    }



}
