package diplom.gorchanyuk.project.diplom.exception;

public class SuchUsernameAlreadyExistException extends RuntimeException{

    public SuchUsernameAlreadyExistException(String message){
        super(message);
    }
}
