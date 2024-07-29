package br.com.autbank.exceptions;

public class ConexaoAPIRemessaException extends RuntimeException {

    public ConexaoAPIRemessaException(String message) {
        super(message);
    }

    public ConexaoAPIRemessaException(String message, Throwable cause) {
        super(message, cause);
    }
}
