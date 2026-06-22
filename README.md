<h1 align="center">🚀 JocketEngine</h1>

<p align="center">
  <a href="https://github.com/eddchdev/JocketEngine/actions/workflows/maven.yml">
    <img src="https://img.shields.io/github/actions/workflow/status/eddchdev/JocketEngine/maven.yml?branch=main&style=for-the-badge" alt="Build Status" />
  </a>
  <a href="https://opensource.org/licenses/MIT">
    <img src="https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge" alt="License MIT" />
  </a>
  <a href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html">
    <img src="https://img.shields.io/badge/Java-17+-brightgreen?style=for-the-badge&logo=java" alt="Java 17+" />
  </a>
  <img src="https://img.shields.io/badge/dependencies-zero-success?style=for-the-badge" alt="Zero dependencies" />
</p>

<p align="center">
  <b>Engine de jogos 2D em Java puro — leve, modular e sem dependências nativas.</b><br>
  <i>Clonou, rodou. Só precisa do JDK: nada de instalar bibliotecas nativas ou drivers.</i>
</p>

---

<p align="center">
  <img src="docs/screenshot-menu.png" width="48%" alt="Menu principal" />
  <img src="docs/screenshot-game.png" width="48%" alt="Gameplay de demonstração" />
</p>

> **JocketEngine** é um motor 2D feito sobre Java2D, pensado para jogos pixel art.
> A proposta é ser **fácil de entender e fácil de rodar**: a engine inteira usa
> apenas o que vem no JDK, então qualquer pessoa com Java instalado consegue
> compilar e jogar o demo em um comando.

---

## ✨ Recursos

- 🎬 **Gerenciamento de cenas em pilha** — troca de cena e sobreposições (ex.: pausa por cima do jogo).
- 🧱 **Entidades + componentes** — anexe comportamentos reutilizáveis (física, animação...) às entidades.
- 💥 **Colisão AABB** — detecção por caixa delimitadora com disparo de eventos.
- 🔔 **Sistema de eventos com prioridades** — comunicação desacoplada e cancelável.
- 🎮 **Entrada unificada** — teclado e mouse, já convertidos para o espaço lógico do jogo.
- 🖥️ **UI integrada** — botões, rótulos, painéis, sliders e campos de texto.
- 🕹️ **Loop de tempo fixo** — física determinística, independente da taxa de quadros.
- 🎨 **Renderização pixel art** — resolução lógica ampliada com vizinho-mais-próximo.
- 🧪 **Testado** — suíte JUnit cobrindo a lógica central, rodando em CI.
- 📦 **Zero dependências de runtime** e um **JAR executável** gerado pelo build.

---

## 🚀 Começando

### Pré-requisitos

- ☕ JDK 17 ou superior
- 🛠️ Apache Maven 3.8+

### Compilar e jogar

```bash
git clone https://github.com/eddchdev/JocketEngine.git
cd JocketEngine

# Opção A: rodar direto pelo Maven
mvn -q compile exec:java

# Opção B: gerar o JAR executável e rodar
mvn -q package
java -jar target/JocketEngine.jar
```

### Controles do demo

| Tecla | Ação |
|-------|------|
| `←` `→` ou `A` `D` | Mover |
| `↑` / `W` / `Espaço` | Pular |
| `ESC` | Pausar / continuar |
| Mouse | Navegar nos menus |

Objetivo: junte as 5 moedas.

---

## 🧠 Conceitos em código

**Iniciar a engine com uma cena:**

```java
GameConfig config = new GameConfig()
        .title("Meu Jogo")
        .logicalSize(480, 270)
        .scale(3)
        .targetFps(60);

Engine.start(config, new MainMenuScene());
```

**Criar uma cena:**

```java
public class MinhaCena extends Scene {
    @Override public void onLoad()  { /* inicializa */ }
    @Override public void update(float dt) { /* lógica */ }
    @Override public void render(Graphics g) { /* desenho */ }
    @Override public void onExit()  { /* limpeza */ }
}
```

**Uma entidade com componente de física:**

```java
public class Caixa extends Entity {
    public Caixa(float x, float y) {
        super(x, y, 16, 16);
        addComponent(new PhysicsComponent(this)).gravity = 800f;
    }
    @Override public void update(float dt) { }   // o componente cuida do movimento
    @Override public void render(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillRect((int) getX(), (int) getY(), getWidth(), getHeight());
    }
}
```

**Reagir a eventos:**

```java
EventManager.registerListener(CollisionEvent.class, EventPriority.NORMAL, e ->
        System.out.println("Colisão entre " + e.getEntityA() + " e " + e.getEntityB()));
```

**Adicionar UI:**

```java
Button jogar = new Button(160, 130, 160, 34, "Jogar");
jogar.setOnClick(() -> SceneManager.changeScene(new GameScene()));
UIManager.add(jogar);
```

**Ler entrada:**

```java
if (Input.isKeyDown(KeyEvent.VK_SPACE)) { /* segurando */ }
if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) { /* só neste passo */ }
```

---

## 🗂 Estrutura

```text
jocketengine/
├── core/        🚦 Engine (janela + loop de tempo fixo) e GameConfig
├── scene/       🎬 Cenas, pilha de cenas e o demo (menu, jogo, pausa)
├── entities/    👾 Entity, EntityManager e componentes (física, bob, animação)
├── events/      🔔 Eventos com prioridade e cancelamento
├── collision/   💥 Detecção de colisão AABB
├── input/       🎮 Teclado e mouse globais
├── ui/          🖥️ Elementos de interface e estilo
├── graphics/    🎞️ Animação de sprites
├── assets/      🖼️ Carregadores de imagem, som, fonte e atlas
├── utils/       🛠️ Vector2, Rectangle, Timer
└── Game.java    ▶️ Ponto de entrada do demo
```

---

## 🧪 Testes

```bash
mvn test
```

A lógica central (vetores, colisão, eventos, cenas, componentes, física,
animação) é coberta por testes JUnit 5, executados automaticamente no
[GitHub Actions](.github/workflows/maven.yml) a cada push.

---

## 🛣️ Roadmap

- [ ] Backend de renderização acelerado por hardware (opcional)
- [ ] Tilemaps e carregamento de fases
- [ ] Áudio integrado ao ciclo de jogo (já existe o carregador)
- [ ] Suporte a gamepad
- [ ] Câmera com rolagem (scrolling) e zoom
- [ ] Sistema de partículas

---

## 🤝 Contribuindo

Contribuições são bem-vindas! Veja o [CONTRIBUTING.md](CONTRIBUTING.md) para o
fluxo de trabalho, padrões de código e como rodar os testes localmente.

## 📜 Licença

Distribuído sob a [Licença MIT](LICENSE).
