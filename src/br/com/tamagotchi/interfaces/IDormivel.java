package br.com.tamagotchi.interfaces;

import br.com.tamagotchi.exceptions.AcaoNaoPermitidaException;

public interface IDormivel {
    void dormir() throws AcaoNaoPermitidaException;
}