package br.com.tamagotchi.exceptions;

public class AcaoNaoPermitidaException extends Exception {
    public AcaoNaoPermitidaException(String mensagem) {
        super(mensagem);
    }
}