package ITVitae.PMT.miscellaneous;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ErrorHandler {
    public static <T> T throwError(Constants.Errors message){
        return actuallyThrowError(message.toString(), message);
    }

    public static <T> T throwError(String prefix, Constants.Errors message){
        return actuallyThrowError(prefix + message.toString(), message);
    }

    private static <T> T actuallyThrowError(String message, Constants.Errors type) {
        throw new ResponseStatusException(status(type), message);
        //throw new RuntimeException(message);
    }

    private static HttpStatus status(Constants.Errors type) {
        switch (type) {
            case NOT_FOUND:
                return HttpStatus.NOT_FOUND;
            case WRONG_PASSWORD:
            case DENIED:
            case ALREADY_EXISTS:
                return HttpStatus.FORBIDDEN;
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
