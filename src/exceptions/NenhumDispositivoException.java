package exceptions;

public class NenhumDispositivoException extends Exception{
    public NenhumDispositivoException(String local){
        super(local + " não tem nenhum dispositivo.");
    }
}
