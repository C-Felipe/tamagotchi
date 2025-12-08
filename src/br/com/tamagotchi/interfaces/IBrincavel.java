package br.com.tamagotchi.interfaces;

import br.com.tamagotchi.exceptions.AcaoNaoPermitidaException;

public interface IBrincavel {
    void brincar() throws AcaoNaoPermitidaException;
}