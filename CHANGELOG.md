# Changelog

Todas as mudanças relevantes deste projeto são documentadas aqui.
O formato segue, em linhas gerais, [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/).

## [1.0.0]

Primeira versão funcional e jogável da engine.

### Adicionado
- Núcleo `Engine` com janela, **loop de tempo fixo** e escala de pixel art
  (renderização em back buffer ampliada por vizinho-mais-próximo).
- `GameConfig` com API fluente para configurar título, resolução e FPS.
- Sistema de **entidades + componentes** (`Entity`, `Component`,
  `EntityManager`), com `PhysicsComponent`, `BobComponent` e `AnimationPlayer`.
- Detecção de **colisão AABB** (`CollisionSystem`) baseada nos limites das entidades.
- **Demo jogável**: menu principal, platformer com moedas colecionáveis e tela de pausa.
- Suíte de **testes JUnit 5** cobrindo a lógica central (32 testes).
- **Integração contínua** via GitHub Actions (`maven.yml`).
- Geração de **JAR executável** (Maven Shade) e execução via `exec:java`.
- `CONTRIBUTING.md`, `CHANGELOG.md` e `.gitignore`.

### Corrigido
- Entrada (`Input`): estados de "pressionado neste passo" agora são limpos no
  **fim** do quadro, eliminando cliques e teclas perdidos; coordenadas do mouse
  convertidas para o espaço lógico; fim do registro duplicado de listeners.
- `EventManager`: ordem de prioridade corrigida — listeners de prioridade mais
  alta executam primeiro (antes era o inverso).
- `PhysicsComponent`: a aceleração agora é de fato aplicada à velocidade
  (antes o resultado era descartado).
- Coordenadas das cenas migradas para o **espaço lógico**, eliminando valores
  fixos inconsistentes (`960` vs `1440`, chão em `720` vs `730`).
- Pacote dos elementos de UI alinhado ao diretório (`ui/elements/`).

### Removido
- Janela OpenGL/LWJGL vazia e a dependência do LWJGL (com nativos só de Windows).
  A engine passa a ser **Java2D puro, multiplataforma e sem dependências nativas**.
- `PhysicsSystem` e `Collider` redundantes (substituídos por componentes e
  pelos limites das entidades).
- Artefatos de build (`target/`) e arquivos de IDE (`.idea/`) do controle de versão.
