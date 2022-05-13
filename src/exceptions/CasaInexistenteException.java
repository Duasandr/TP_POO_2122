package exceptions;

public class CasaInexistenteException extends Exception{
    public CasaInexistenteException(){
        super("Casa não existe.");
    }
}
