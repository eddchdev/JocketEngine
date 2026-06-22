# Contribuindo com a JocketEngine

Obrigado pelo interesse em contribuir! Este guia resume o necessário para
colaborar de forma tranquila.

## Como rodar localmente

```bash
git clone https://github.com/eddchdev/JocketEngine.git
cd JocketEngine
mvn verify          # compila e roda os testes
mvn compile exec:java   # roda o demo
```

Requisitos: **JDK 17+** e **Maven 3.8+**. Não há dependências nativas.

## Fluxo de trabalho

1. Faça um fork e crie um branch a partir de `main`:
   `git checkout -b feat/minha-melhoria`
2. Faça as alterações, com commits pequenos e descritivos.
3. Garanta que o build passa: `mvn verify`.
4. Abra um Pull Request descrevendo o que mudou e por quê.

## Padrões de código

- **Java 17**, indentação de 4 espaços.
- Mantenha o estilo existente: classes coesas, uma responsabilidade por classe.
- Escreva **JavaDoc** em português para tipos e métodos públicos.
- Prefira nomes claros a comentários explicando código confuso.
- Evite adicionar dependências de runtime — a engine é, por princípio,
  **sem dependências externas** (apenas o JDK). Bibliotecas em escopo de teste
  são aceitáveis quando justificadas.

## Testes

- Toda lógica nova (não gráfica) deve vir acompanhada de testes JUnit 5.
- Os testes rodam em ambiente **headless** (sem janela); não dependa de um
  display real nas suas asserções.
- Rode `mvn test` antes de abrir o PR.

## Reportando bugs

Abra uma _issue_ incluindo:

- O que você esperava que acontecesse e o que aconteceu.
- Passos para reproduzir.
- Sistema operacional e versão do Java (`java -version`).

## Licença

Ao contribuir, você concorda que sua contribuição seja licenciada sob a
[Licença MIT](LICENSE), como o restante do projeto.
