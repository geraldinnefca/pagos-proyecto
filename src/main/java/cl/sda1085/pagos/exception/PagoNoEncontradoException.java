package cl.sda1085.pagos.exception;

public class PagoNoEncontradoException extends  RuntimeException{
    public PagoNoEncontradoException(Long id) {
        super("No se pudo encontrar el registro de pago con el ID: " + id);
    }
}
