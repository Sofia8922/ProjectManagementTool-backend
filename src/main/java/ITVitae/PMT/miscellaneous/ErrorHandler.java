package ITVitae.PMT.miscellaneous;

public class ErrorHandler {
    public static <T> T throwError(Constants.Errors message){
        return actuallyThrowError(message.toString());
    }

    public static <T> T throwError(String prefix, Constants.Errors message){
        return actuallyThrowError(prefix + message.toString());
    }

    private static <T> T actuallyThrowError(String message) {
        throw new RuntimeException(message);
    }
}
