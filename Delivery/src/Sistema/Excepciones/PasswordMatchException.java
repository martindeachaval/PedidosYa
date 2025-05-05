package Sistema.Excepciones;

public class PasswordMatchException extends Exception{
    public PasswordMatchException(String message){
        super(message);
    }
}
