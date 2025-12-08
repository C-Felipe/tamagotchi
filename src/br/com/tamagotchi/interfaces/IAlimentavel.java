package br.com.tamagotchi.interfaces;

import br.com.tamagotchi.exceptions.AcaoNaoPermitidaException;

public interface IAlimentavel {
    void alimentar() throws AcaoNaoPermitidaException;
}