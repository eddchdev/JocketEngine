# Changelog

Todas as mudanças relevantes deste projeto são documentadas aqui.
O formato segue, em linhas gerais, [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/).

## [1.1.0]

Expansão para uma engine 2D modular e completa.

### Adicionado
- **Câmera 2D** (`Camera2D`): zoom, seguir alvo com suavização e limites de mundo.
- **Tilemap** (`TileMap`): mapas em grade com colisão integrada (`getSolidBounds`).
- **Partículas** (`ParticleSystem`): explosões e efeitos com gravidade e desbotamento.
- **Tweening** (`Tween` + `Easing`): interpolação animada com várias curvas (linear, quad, sine, back, bounce).
- **Áudio** (`Audio` + `Sfx`): SFX **procedural** sintetizado em código, robusto à ausência de dispositivo de som.
- **Input por ações** (`InputMap`): mapeamento remapeável de teclas para ações nomeadas.
- **Assets** unificado (`Assets`): acesso único a texturas, sons e fontes com cache.
- **Math** (`MathUtils`) e `Vector2` expandido (length, dot, normalize, lerp, distance).
- **Utilitários**: `Logger` com níveis e `Preferences` (salvar/carregar progresso).
- Personagem com **ciclo de caminhada** (2 quadros) e som de pulo.
- Demo ampliado: mundo em tilemap com **câmera rolando**, partículas e som ao coletar moedas, e vitória animada com tween.
- Cobertura de testes cresceu de 35 para **88**.

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
- **Sprite** em pixel art a partir de mapa de caracteres (`Sprite.fromRows`), com
  espelhamento horizontal.
- **Personagem padrão** em pixel art: um pequeno cavaleiro de máscara e chifres
  (inspirado em Hollow Knight), com direção (flip) conforme o movimento.
- **Tipografia limpa**: HUD e UI são desenhados em resolução nativa com
  antialiasing, separados do mundo em pixel art; botões com cantos arredondados.

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
