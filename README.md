# Panucci

![Thumbnails GitHub](https://user-images.githubusercontent.com/8989346/216600115-b19a50e0-191e-4198-8b14-8c326775e481.png)

## 🔨 Funcionalidades do projeto

Uma extensão do [App base com o Type Safety](https://github.com/alura-cursos/jetpack-compose-navigation-type-safety), com a adição da confirmação de pedido e testes automatizados para a navegação.

- Confirmação do pedido

![apresentando-snackbar](https://user-images.githubusercontent.com/8989346/216601582-ffffafce-d36f-474f-9ba3-856c34e000b2.gif)

- Testes automatizados de navegação

![rodando-teste-no-android-studio](https://user-images.githubusercontent.com/8989346/216605300-b66c62aa-3c1d-499f-92a1-9a046d68632f.gif)

## ✔️ Técnicas e tecnologias utilizadas

Para implementar o App foram utilizadas as seguintes funcionalidades e tecnologias:

- Saved State Handle
  - Salvar mensagens durante a navegação
  - Recuperar argumentos de navegação via ViewModel
- Snackbar
  - Exibir mensagem de configuração para fluxos específicos, como a realização de um pedido
- Deep Links
  - Acesso exclusivo para telas a partir de links
  - Aplicação de comportamentos específicos para links personalizados, como acessar uma tela de produto com desconto apenas com o link
- Testes para a navegação com o Compose
  - Validando destinos do App
  - Verificando a visibilidade de componentes visuais
  - Garantido a chamada de cada rota dependendo das ações do usuário
- Semantics
  - Possibilitando que os testes consigam buscar composables que não tenham textos

## 📁 Acesso ao projeto

Você pode [acessar o código fonte do projeto](https://github.com/alura-cursos/jetpack-compose-navigation-deep-links-e-testes/tree/aula-6) ou [baixá-lo](https://github.com/alura-cursos/jetpack-compose-navigation-deep-links-e-testes/archive/refs/heads/aula-6.zip).

## 🛠️ Abrir e rodar o projeto

Após baixar o projeto, você pode abrir com o Android Studio. Para isso, na tela de launcher clique em:

Open an Existing Project (ou alguma opção similar)
Procure o local onde o projeto está e o selecione (Caso o projeto seja baixado via zip, é necessário extraí-lo antes de procurá-lo)
Por fim clique em OK
O Android Studio deve executar algumas tasks do Gradle para configurar o projeto, aguarde até finalizar. Ao finalizar as tasks, você pode executar o App 🏆

<!-- ## 📚 Mais informações do curso

**Faça um CTA (_call to action_) para o curso do projeto**
