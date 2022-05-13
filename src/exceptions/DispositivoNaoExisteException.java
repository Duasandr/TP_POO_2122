package exceptions;

public class DispositivoNaoExisteException extends Exception{
    public DispositivoNaoExisteException(){
        super("Dispositivo não existe.");
    }
}
