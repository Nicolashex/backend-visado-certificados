package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.model.Certificado;
import cl.ucn.dge.salud.visadocertificados.model.Solicitud;
import cl.ucn.dge.salud.visadocertificados.model.User;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioCertificado;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioCertificado {

    @Autowired
    private final RepositorioCertificado repositorioCertificado;

    private static final PDFont FONT = PDType1Font.HELVETICA;
    private static final float FONT_SIZE = 12;
    private static final float LEADING = -1.5f * FONT_SIZE;
    private static final int logoSize = 80;

    public ServicioCertificado(RepositorioCertificado repositorioCertificado) {
        this.repositorioCertificado = repositorioCertificado;
    }

    public Certificado getCertificadoPorId(String Id){
        return this.repositorioCertificado.getById(Id);
    }

    public Solicitud getSolicitudByCertificadoID(String id){
        return this.repositorioCertificado.getById(id).getSolicitud();
    }



    public byte[] crearPDF(Solicitud solicitud) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (final PDDocument doc = new PDDocument()){

            PDPage page = new PDPage();
            doc.addPage(page);


            PDRectangle mediaBox = page.getMediaBox();
            float marginY = 40;
            float marginX = 120;
            float width = mediaBox.getWidth() - 2 * marginX;
            float startX = mediaBox.getLowerLeftX() + marginX;
            float startY = mediaBox.getUpperRightY() - marginY - logoSize;

            Path path = Paths.get(ClassLoader.getSystemResource("escudo_ucn.png").toURI());
            PDImageXObject pdImage
                    = PDImageXObject.createFromFile(path.toAbsolutePath().toString(), doc);

            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            float startXImage = mediaBox.getWidth()/2 - logoSize/2;
            contentStream.drawImage(pdImage, startXImage, startY , logoSize, logoSize);

            String text ="";

            User estudiante = solicitud.getEstudiante();
            if(solicitud.isEsCarga()){
                //TODO: CAMBIAR PARA CARGA
                text = "El área de salud de la Dirección General Estudiantil (DGE) de la Universidad Católica" +
                        " del Norte, mediante el presente documento certifica la validez del certificado médico asociado" +
                        " al alumno con nombre: " + estudiante.getNombre() + " " + estudiante.getPrimerApellido() +
                        " y  RUT: " + estudiante.getRut() + ". Certificado que fue emitido por el doctor con nombre: " +
                        solicitud.getNombreMedicoTratante() + " y con fecha de inicio de reposo: " +
                        solicitud.getFechaInicioReposo() + " y fecha de término de reposo: " +
                        solicitud.getFechaFinReposo() + ".";

            } else{

                text = "El área de salud de la Dirección General Estudiantil (DGE) de la Universidad Católica" +
                        " del Norte, mediante el presente documento certifica la validez del certificado médico asociado" +
                        " al alumno con nombre: " + estudiante.getNombre() + " " + estudiante.getPrimerApellido() +
                        " y  RUT: " + estudiante.getRut() + ". Certificado que fue emitido por el doctor con nombre: " +
                        solicitud.getNombreMedicoTratante() + " y con fecha de inicio de reposo: " +
                        solicitud.getFechaInicioReposo() + " y fecha de término de reposo: " +
                        solicitud.getFechaFinReposo() + ".";

            }
            String text2 =  "El presente documento fue emitido con fecha: " + solicitud.getFechaFinSolicitud().toLocalDate() +".";



            contentStream.beginText();
            addParagraph(contentStream, width, startX, startY - marginY, text, true);
            addParagraph(contentStream, width, 0, -FONT_SIZE, text2, true);
            //addParagraph(contentStream, width, 0, -FONT_SIZE, text, false);

            contentStream.endText();

            String text3 = "Código de verificación: " + solicitud.getCertificado().getId();
            contentStream.beginText();
            addParagraph(contentStream, width, startX, 0 + marginY, text3, true);
            contentStream.endText();


            contentStream.close();


            doc.save(baos);


        } catch (IOException | URISyntaxException e){
            System.err.println("Exception while trying to create pdf document - " + e);
        }
        return  baos.toByteArray();
    }

    private static void addParagraph(PDPageContentStream contentStream, float width, float sx,
                                     float sy, String text, boolean justify) throws IOException {
        List<String> lines = parseLines(text, width);
        contentStream.setFont(FONT, FONT_SIZE);
        contentStream.newLineAtOffset(sx, sy);
        for (String line: lines) {
            float charSpacing = 0;
            if (justify){
                if (line.length() > 1) {
                    float size = FONT_SIZE * FONT.getStringWidth(line) / 1000;
                    float free = width - size;
                    if (free > 0 && !lines.get(lines.size() - 1).equals(line)) {
                        charSpacing = free / (line.length() - 1);
                    }
                }
            }
            contentStream.setCharacterSpacing(charSpacing);
            contentStream.showText(line);
            contentStream.newLineAtOffset(0, LEADING);
        }
    }

    private static List<String> parseLines(String text, float width) throws IOException {
        List<String> lines = new ArrayList<String>();
        int lastSpace = -1;
        while (text.length() > 0) {
            int spaceIndex = text.indexOf(' ', lastSpace + 1);
            if (spaceIndex < 0)
                spaceIndex = text.length();
            String subString = text.substring(0, spaceIndex);
            float size = FONT_SIZE * FONT.getStringWidth(subString) / 1000;
            if (size > width) {
                if (lastSpace < 0){
                    lastSpace = spaceIndex;
                }
                subString = text.substring(0, lastSpace);
                lines.add(subString);
                text = text.substring(lastSpace).trim();
                lastSpace = -1;
            } else if (spaceIndex == text.length()) {
                lines.add(text);
                text = "";
            } else {
                lastSpace = spaceIndex;
            }
        }
        return lines;
    }
}
