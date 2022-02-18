package chat.exceptions;

public class PersistenceLayerException extends AbstractException{

    public PersistenceLayerException(String msg, String techInfo){
        super(msg,techInfo);
    }
}
