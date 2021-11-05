package cl.ucn.dge.salud.visadocertificados.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ExcepcionPesoLimiteArchivo extends ResponseEntityExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Archivo muy pesado!");
    }
    @ExceptionHandler(value = MultipartException.class)
    public ResponseEntity<String> handleFileUploadingError(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Eroor al subir archivo");
    }
}
