package med.prometheus.api.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratarErros {

    @ExceptionHandler( EntityNotFoundException.class )
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler( MethodArgumentNotValidException.class )
    public ResponseEntity traterErro400( MethodArgumentNotValidException ex ) {
        var erros = ex.getFieldErrors();

        return  ResponseEntity.badRequest().body( erros.stream().map( DadosErroValitation::new).toList() );
    }

    private record DadosErroValitation ( String campo, String message ) {
        public DadosErroValitation(FieldError error)
        {
            this( error.getField(), error.getDefaultMessage() );
        }
    }
}
