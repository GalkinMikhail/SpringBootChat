package chat.exceptions;

public class IllegalDataException extends AbstractException{
    public IllegalDataException(String msg, String techInfo){
        super(msg,techInfo);
    }
}
