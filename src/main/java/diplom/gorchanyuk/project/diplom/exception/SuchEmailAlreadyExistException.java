package diplom.gorchanyuk.project.diplom.exception;

public class SuchEmailAlreadyExistException extends RuntimeException{

    public SuchEmailAlreadyExistException(String message){
        super(message);
    }
}
