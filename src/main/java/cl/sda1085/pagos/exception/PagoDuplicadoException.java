package cl.sda1085.pagos.exception;

public class PagoDuplicadoException extends RuntimeException{
    public PagoDuplicadoException(Long idSubasta){
        super("La subasta con ID " + idSubasta + " ya tiene un pago registrado y no puede procesarse otro.");
    }
}
