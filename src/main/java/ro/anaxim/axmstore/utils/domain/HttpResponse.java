package ro.anaxim.axmstore.utils.domain;

import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.util.Map;
@SuperBuilder
public class HttpResponse {
    private String timeStamp;
    private int statusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;
    private String developerMessage;
    private Map<?,?>  data;
}
