# Pacote: Exceções (Error Handling)

Este pacote contém as exceções personalizadas do projeto. O objetivo é permitir um tratamento de erros refinado e específico para as regras de negócio do jogo.

## Conteúdo
* **AcaoNaoPermitidaException:** Uma *Checked Exception* lançada quando o usuário tenta realizar uma ação inválida (ex: Alimentar um Digimon cheio). Isso força a camada de interface a capturar o erro e exibir um aviso amigável.
