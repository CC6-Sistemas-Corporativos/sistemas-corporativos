# Como Contribuir para o Projeto Sistemas Corporativos

Olá! Ficamos muito felizes com seu interesse em contribuir com nosso projeto. Toda contribuição é bem-vinda, seja um relatório de bug, uma sugestão de melhoria ou um novo recurso.

Para garantir que o projeto se mantenha organizado e com qualidade, pedimos que siga algumas diretrizes descritas abaixo.

## Código de Conduta

Adotamos um Código de Conduta para garantir que nossa comunidade seja aberta e inclusiva. Por favor, leia e siga o nosso [Código de Conduta](CODE_OF_CONDUCT.md) em todas as suas interações com o projeto.

## Como Reportar um Bug

Se você encontrar um bug, por favor, certifique-se de que ele ainda não foi reportado criando uma [nova Issue](https://github.com/CC6-Sistemas-Corporativos/sistemas-corporativos/issues/new/choose).

Ao criar um relatório de bug, inclua o máximo de detalhes possível:
* **Versão do Projeto:** Qual versão você estava usando?
* **Passos para Reproduzir:** Um passo a passo claro de como o bug aconteceu.
* **Comportamento Esperado:** O que você esperava que acontecesse?
* **Comportamento Atual:** O que de fato aconteceu? (Inclua screenshots, se aplicável).
* **Contexto Adicional:** Informações sobre seu ambiente (sistema operacional, navegador, etc.).

## Sugerindo Melhorias ou Novos Recursos

Para sugerir uma melhoria ou um novo recurso, crie uma [nova Issue](https://github.com/CC6-Sistemas-Corporativos/sistemas-corporativos/issues/new/choose) descrevendo sua ideia:
* Use um título claro e descritivo.
* Explique em detalhes o problema que sua sugestão resolve.
* Descreva a solução que você tem em mente.

## Sua Primeira Contribuição de Código

Pronto para escrever código? Ótimo! Siga este fluxo de trabalho para garantir que sua contribuição seja integrada da melhor forma.

### 1. Configure o Ambiente
* Faça um **Fork** deste repositório para a sua própria conta do GitHub.
* Clone o seu fork para a sua máquina local:
    ```bash
    git clone [https://github.com/SEU-USUARIO/sistemas-corporativos.git](https://github.com/SEU-USUARIO/sistemas-corporativos.git)
    ```
* Adicione o repositório original como um "remote" para poder sincronizar as alterações:
    ```bash
    git remote add upstream [https://github.com/CC6-Sistemas-Corporativos/sistemas-corporativos.git](https://github.com/CC6-Sistemas-Corporativos/sistemas-corporativos.git)
    ```

### 2. Crie uma Nova Branch
Nunca trabalhe diretamente na branch `main`. Crie uma branch específica para sua tarefa:
```bash
# Sincronize seu fork com o repositório original antes de criar a branch
git fetch upstream
git checkout -b nome-descritivo-da-sua-branch upstream/main
```
Escolha um nome descritivo, como `feature/tela-de-login` ou `fix/bug-no-relatorio`.

### 3. Escreva seu Código
* Faça as alterações desejadas no código.
* Siga as convenções de estilo de código do projeto.
* Faça commits pequenos e com mensagens claras e descritivas. Lembre-se que todos os commits precisam ser **assinados**.

### 4. Envie suas Alterações (Pull Request)
1.  Envie sua branch para o seu fork no GitHub:
    ```bash
    git push origin nome-descritivo-da-sua-branch
    ```
2.  No site do GitHub, vá para o seu fork e clique no botão **"Compare & pull request"**.
3.  Abra um **Pull Request (PR)** para a branch `main` do repositório original.
4.  Preencha o template do PR:
    * Dê um título claro ao seu PR.
    * Descreva as alterações que você fez.
    * Se o PR resolve uma Issue existente, mencione-a usando `Closes #123`.
5.  Aguarde a revisão do código. Pelo menos um outro membro da equipe precisará aprovar seu PR.
6.  Fique atento aos comentários e faça as alterações solicitadas.
7.  Assim que seu PR for aprovado e passar em todas as verificações, ele será mesclado na branch `main`.

Obrigado pela sua contribuição!