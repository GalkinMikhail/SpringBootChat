package chat.exceptions;

public class ResourceNotFoundException extends AbstractException {

    public ResourceNotFoundException(String msg,String techInfo) {
        super(msg,techInfo);
    }
}
