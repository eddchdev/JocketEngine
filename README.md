<h1 align="center">🚀 JocketEngine</h1>

<p align="center">
  <a href="https://github.com/eddchdev/JocketEngine/actions" target="_blank">
    <img src="https://img.shields.io/github/actions/workflow/status/eddchdev/JocketEngine/maven.yml?branch=main&style=for-the-badge" alt="Build Status" />
  </a>
  <a href="https://github.com/eddchdev/JocketEngine/releases" target="_blank">
    <img src="https://img.shields.io/github/v/release/eddchdev/JocketEngine?style=for-the-badge" alt="Release" />
  </a>
  <a href="https://opensource.org/licenses/MIT" target="_blank">
    <img src="https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge" alt="License MIT" />
  </a>
  <a href="https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html" target="_blank">
    <img src="https://img.shields.io/badge/Java-17+-brightgreen?style=for-the-badge&logo=java" alt="Java 17+" />
  </a>
  <a href="https://github.com/eddchdev/JocketEngine" target="_blank">
    <img src="https://img.shields.io/github/repo-size/eddchdev/JocketEngine?style=for-the-badge&color=informational" alt="Repo Size" />
  </a>
  <a href="https://github.com/eddchdev/JocketEngine/commits" target="_blank">
    <img src="https://img.shields.io/github/last-commit/eddchdev/JocketEngine?style=for-the-badge" alt="Last Commit" />
  </a>
  <a href="https://github.com/eddchdev/JocketEngine/stargazers" target="_blank">
    <img src="https://img.shields.io/github/stars/eddchdev/JocketEngine?style=for-the-badge" alt="Stars" />
  </a>
  <a href="https://github.com/eddchdev/JocketEngine/issues" target="_blank">
    <img src="https://img.shields.io/github/issues/eddchdev/JocketEngine?style=for-the-badge" alt="Open Issues" />
  </a>
  <a href="https://github.com/eddchdev/JocketEngine/network/members" target="_blank">
    <img src="https://img.shields.io/github/forks/eddchdev/JocketEngine?style=for-the-badge" alt="Forks" />
  </a>
</p>

---

<p align="center">
  <img src="https://raw.githubusercontent.com/github/explore/main/topics/java/java.png" width="100" alt="Java Logo"/>
</p>

<p align="center">
  <b>Engine Java 2D modular, extensível, elegante e poderosa.</b><br>
  <i>Ideal para quem deseja criar jogos com arquitetura limpa, UI moderna e total controle sobre o ciclo do jogo.</i>
</p>

---

> **JocketEngine** — Motor de jogos 2D em Java, concebido para desenvolvedores exigentes que buscam aliar estética pixel art de altíssima qualidade a uma arquitetura robusta, modular e extensível. Inspirado pela excelência do PocketMine, porém com foco exclusivo em jogos elegantes, fluidos e de performance superior.

---

## ✨ Visão Geral

JocketEngine oferece uma plataforma de desenvolvimento completa para criação de jogos 2D que unem beleza visual e engenharia de software de ponta. Com um conjunto integrado de ferramentas e APIs refinadas, a engine possibilita:

- 🎨 **Gráficos pixel art meticulosamente trabalhados**, com animações suaves e responsivas
- 🎬 **Gerenciamento inteligente de cenas**, promovendo transições e estados de jogo organizados
- 🧱 **Arquitetura baseada em entidades e componentes**, flexível e reutilizável
- 🔔 **Sistema de eventos sofisticado**, para comunicação desacoplada e extensível
- ⚙️ **Mecanismos físicos realistas**, com colisões, gravidade e atrito
- 🖥️ **Interface gráfica customizável**, para menus, HUDs e painéis interativos
- 🚀 **Renderização acelerada**, com alto desempenho
- 🔌 **Estrutura modular para plugins**, pronta para extensões externas
- 📚 **Código limpo e bem documentado**, ideal para desenvolvedores profissionais
<p>
  <img src="resources/skull.gif" width="200" alt="Pixel Art Skeleton Warrior" />
</p>

---

## 🗂 Estrutura do Projeto

```bash
jocketengine/
├── assets/                 🖼️ Gerenciamento e carregamento de recursos visuais e sonoros
├── collision/              💥 Sistema robusto de detecção e resolução de colisões
├── entities/               👾 Modelagem de entidades do jogo e seus componentes
├── events/                 🔔 Infraestrutura para eventos e comunicação interna desacoplada
├── graphics/               🎞️ Controle e reprodução de animações e sprites
├── input/                  🎮 Captura e processamento de entradas do usuário
├── physics/                ⚙️ Simulação de física: gravidade, atrito e movimento
├── scene/                  🖼️ Gerenciamento de cenas e estados do ciclo do jogo
├── ui/                     🖥️ Sistema completo para interface gráfica customizada
├── utils/                  🛠️ Utilitários matemáticos e auxiliares genéricos
└── Game.java               🚦 Ponto de entrada e controlador principal da engine

```

## 🚀 Guia Rápido de Início

### Pré-requisitos

- ☕ Java Development Kit (JDK) versão 17 ou superior  
- 🛠️ Apache Maven 3.8+ instalado e configurado  
- 🎨 Ambiente gráfico compatível com OpenGL via LWJGL  

### Como compilar e executar

Clone o repositório:

```bash
git clone https://github.com/eddchdev/JocketEngine.git
cd JocketEngine
```

Compile e execute via Maven:

```bash
mvn clean compile exec:java
```

---

## 📖 Documentação

A JocketEngine dispõe de documentação completa em JavaDoc, proporcionando fácil navegação pelas APIs e facilitando a compreensão de suas funcionalidades:

- `Game.java`: Inicialização, ciclo de execução e gerenciamento global  
- `SceneManager.java`: Controle sofisticado da troca e ciclo de vida das cenas  
- `Entity` & `Component`: Bases para modelagem de objetos dinâmicos e extensíveis  
- `EventManager`: Gerenciamento flexível e eficiente de eventos  
- `UIManager`: Coordenação da interface gráfica, com suporte a múltiplos elementos  

---

## 🛠 Roadmap de Desenvolvimento

- 🧩 Implementação de sistema de plugins completo e seguro  
- 🛠 Ferramentas visuais para edição de níveis e interfaces  
- 🌐 Funcionalidades básicas para multiplayer e sincronização  
- 🛠 Ferramentas avançadas para debug, profiling e otimização  

---

## 🤝 Contribuição

Contribuições são recebidas com entusiasmo! Por favor, abra issues para reportar bugs, sugerir melhorias ou implementar funcionalidades. Respeite as diretrizes de estilo e documentação para garantir a qualidade e consistência do código.

---

## 📜 Licença

Este projeto está licenciado sob a [Licença MIT](https://opensource.org/licenses/MIT).

---

Seja bem-vindo à comunidade JocketEngine! Desenvolva com paixão, código limpo e muita criatividade. 🚀
