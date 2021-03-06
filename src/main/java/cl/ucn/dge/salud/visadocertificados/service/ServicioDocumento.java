package cl.ucn.dge.salud.visadocertificados.service;

import cl.ucn.dge.salud.visadocertificados.model.Documento;
import cl.ucn.dge.salud.visadocertificados.repository.RepositorioDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioDocumento {

    @Autowired
    private final RepositorioDocumento repositorioDocumento;

    public ServicioDocumento(RepositorioDocumento repositorioDocumento) {
        this.repositorioDocumento = repositorioDocumento;
    }

    @Transactional
    public List<Documento> guardarDocumento(MultipartFile[] certificado,
                                            MultipartFile[] evidencia) throws IOException {

        List<Documento> documentos = new ArrayList<Documento>();
        for (MultipartFile archivo : certificado) {

            String tipoArchivo = archivo.getContentType();
            String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());

            if(tipoArchivo != null && !nombreArchivo.equals("")){
                Documento documento = new Documento(archivo.getBytes(),
                        Documento.posiblesTiposDocumentos.CERTIFICADO,
                        tipoArchivo, nombreArchivo);
                documentos.add(documento);
            }
        }

        for (MultipartFile archivo : evidencia) {
            String tipoArchivo = archivo.getContentType();
            String nombreArchivo = StringUtils.cleanPath(archivo.getOriginalFilename());

            if(tipoArchivo != null && !nombreArchivo.equals("")) {
                Documento documento = new Documento(archivo.getBytes(),
                        Documento.posiblesTiposDocumentos.RESPALDO,
                        tipoArchivo, nombreArchivo);
                documentos.add(documento);
            }
        }


        return documentos;
    }
    public Documento getDocumentoPorId(String id){
        return this.repositorioDocumento.getDocumentoById(id);

    }

    @Transactional
    public void eliminarDocumento(Documento documento){
        this.repositorioDocumento.deleteById(documento.getId());
    }

    public boolean estaPorId(String id){
        return this.repositorioDocumento.existsById(id);
    }
}
