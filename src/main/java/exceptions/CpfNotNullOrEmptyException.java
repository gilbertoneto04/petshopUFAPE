package exceptions;

public class CpfNotNullOrEmptyException extends RuntimeException {
    public CpfNotNullOrEmptyException(String message) {
        super(message);
    }

    public CpfNotNullOrEmptyException() {
        super("O CPF não pode ser nulo ou vazio.");
    }
}
