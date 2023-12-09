package gm.inventarios.excepcion;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus se pone porque es un restcontroller
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecursoNoEncontradoExcepcion extends RuntimeException {

    public RecursoNoEncontradoExcepcion(String mensaje){
        super(mensaje);
    }
}
