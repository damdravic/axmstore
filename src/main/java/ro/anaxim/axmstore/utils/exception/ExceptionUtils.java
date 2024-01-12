package ro.anaxim.axmstore.utils.exception;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import ro.anaxim.axmstore.exception.ApiException;
import ro.anaxim.axmstore.utils.domain.HttpResponse;

import java.io.InvalidClassException;
import java.io.OutputStream;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class ExceptionUtils {


    public static void processError(HttpServletRequest request, HttpServletResponse response,Exception exception){

        if(exception instanceof ApiException || exception  instanceof DisabledException || exception instanceof LockedException ||
                exception instanceof InvalidClassException  || exception instanceof TokenExpiredException || exception instanceof BadCredentialsException){
            HttpResponse httpResponse = getHttpResponse(response,exception.getMessage(),BAD_REQUEST);
            writeResponse(response, httpResponse);
        }else {
            HttpResponse httpResponse = getHttpResponse(response,"An error occurred.Please try again",INTERNAL_SERVER_ERROR);
            writeResponse(response, httpResponse);
        }
   log.error(exception.getMessage());
    }

    private static void writeResponse(HttpServletResponse response, HttpResponse httpResponse) {
        OutputStream out;
        try{

            out =response.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(out,httpResponse);
            out.flush();

        }catch(Exception exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private static HttpResponse getHttpResponse(HttpServletResponse response, String message, HttpStatus httpStatus){
        HttpResponse httpResponse = HttpResponse.builder()
                .timeStamp(now().toString())
                .reason("You need to log in to access this resource")
                .httpStatus(httpStatus)
                .statusCode(httpStatus.value())
                .build();

        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
/*
        OutputStream out =response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out,httpResponse);
        out.flush();*/

        return httpResponse;

    }
    }




