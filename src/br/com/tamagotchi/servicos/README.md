# Pacote: Serviços (Controller / Business Logic)

Este pacote atua como a camada de *Controle* e *Regras de Negócio*. Ele serve como uma ponte entre a Interface Gráfica (View) e as Entidades (Model), além de gerenciar operações complexas como I/O (Entrada e Saída).

## Principais Responsabilidades
* *Orquestração:* O TamagotchiService recebe os comandos da GUI e decide o que fazer com a entidade.
* *Gerenciamento de Estado:* Controla a evolução (polimorfismo) e a verificação de morte.
* *Persistência:* O PersistenciaService isola a complexidade de salvar e carregar arquivos binários (.dat).

## Classes
* *TamagotchiService:* Controlador principal do jogo.
* *PersistenciaService:* Gerenciador de arquivos (Serialização).