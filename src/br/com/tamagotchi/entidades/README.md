# Pacote: Entidades (Model)

Este pacote contém as classes que representam o **Modelo de Domínio** do sistema. Aqui estão definidos os objetos que possuem estado e comportamento, mas não possuem lógica de interface gráfica ou persistência direta.

## Principais Responsabilidades
* **Encapsulamento:** Todos os atributos (fome, energia, etc.) são privados.
* **Lógica de Negócio Interna:** Regras de como comer, dormir e envelhecer.
* **Herança:** A classe abstrata `Tamagotchi` define a base, e as subclasses (`Agumon`, `Gabumon`, etc.) definem as especializações.

## Classes
* **Tamagotchi (Abstract):** A superclasse base.
* **Subclasses:** Implementações concretas das espécies de Digimon.