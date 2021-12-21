package cl.ucn.dge.salud.visadocertificados.utils;

import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.Valoracion;
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

import static org.apache.poi.ss.util.CellUtil.createCell;

public class ValoracionesExcelExport {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Valoracion> listaValoraciones;

    public ValoracionesExcelExport(List<Valoracion> listaValoraciones){
        this.listaValoraciones = listaValoraciones;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine(){

        sheet = workbook.createSheet("Valoraciones");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Id Solicitud", style);
        createCell(row, 1, "Fecha evaluación", style);
        createCell(row, 2, "Valoración pregunta 1", style);
        createCell(row, 3, "Valoración pregunta 2", style);
        createCell(row, 4, "Valoración pregunta 3", style);
        createCell(row, 5, "Comentario", style);


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

    private void writeDataLines() {

        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Valoracion valoracion : listaValoraciones) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, String.valueOf(valoracion.getSolicitud().getId()), style);

            String fechaValoracion = valoracion.getFechaEvaluacion() == null ? ""
                    : String.valueOf(valoracion.getFechaEvaluacion().format(DateTimeFormatter.ofPattern("dd/MM/yyy")));
            createCell(row, columnCount++, fechaValoracion, style);

            createCell(row, columnCount++, String.valueOf(valoracion.getValoracion1()), style);
            createCell(row, columnCount++, String.valueOf(valoracion.getValoracion2()), style);
            createCell(row, columnCount++, String.valueOf(valoracion.getValoracion3()), style);

            String comentario = valoracion.getComentario() == null ? ""
                    : valoracion.getComentario();
            createCell(row,columnCount++,comentario,style);

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
