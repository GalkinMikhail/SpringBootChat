package SimbirSoftProject.exceptions;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class BaseControllerAdvice {
    Date date = new Date();

    @ExceptionHandler(ResourceNotFoundException.class)
    public Object resourceNotFoundException(ResourceNotFoundException ex){
        return response(HttpStatus.NOT_FOUND,ex);
    }

    @ExceptionHandler(IllegalDataException.class)
    public Object illegalDataException(IllegalDataException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR, ex);
    }

    @ExceptionHandler(PersistenceLayerException.class)
    public Object persistenceLayerException(PersistenceLayerException ex){
        return response(HttpStatus.INTERNAL_SERVER_ERROR,ex);
    }
    @ExceptionHandler(UserBlockedException.class)
    public Object userBlockedException(UserBlockedException ex){
        return response(HttpStatus.CONFLICT,ex);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public Object accessDeniedException(AccessDeniedException ex){
        return response(HttpStatus.CONFLICT,ex);
    }


    private Object response(HttpStatus status, AbstractException ex){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String,String> body = new HashMap<>();
        body.put("message",ex.getMessage());
        body.put("techInfo", ex.getTechInfo());
        body.put("status",status.toString());
        body.put("timestamp",String.valueOf(date));
        return new ResponseEntity<>(body,headers,status);
    }

}
