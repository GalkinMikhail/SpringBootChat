package chat.exceptions;

public class AccessDeniedException extends AbstractException{
    public AccessDeniedException(String msg,String techInfo) {
        super(msg,techInfo);
    }
}
