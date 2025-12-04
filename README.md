# 🌱 AgroBot Java - Monitoramento IoT Inteligente

> **Status:** ✅ Concluído | **Versão:** 2.0 *(Migração Python → Java)*

## 📖 Sobre o Projeto

O **AgroBot** é um sistema de monitoramento agrícola que simula sensores IoT em plantações. O sistema coleta dados de **temperatura** e **umidade** em tempo real e utiliza uma API desenvolvida em **Java (Javalin)** para analisar riscos para culturas sensíveis (como Soja e Café).

Se uma condição crítica for detectada (como Geada ou Estresse Térmico), o sistema atua imediatamente:
1.  Envia alertas instantâneos via **Telegram** 📩 para o agrônomo.
2.  Simula o acionamento de sistemas de **Irrigação 💧** ou **Aquecimento 🔥**.

---

## 🚀 Tecnologias e Decisões Técnicas

Este projeto marca a migração do backend de Python para Java, visando maior robustez e tipagem estática.

* ![Java](https://img.shields.io/badge/Java-11%2B-ED8B00?style=flat&logo=openjdk&logoColor=white) **Core:** Linguagem utilizada para garantir performance e estabilidade.
* ![Javalin](https://img.shields.io/badge/Javalin-Lightweight-ff0055) **Framework Web:** Escolhido por ser extremamente leve (inicia em milissegundos), ideal para microsserviços e IoT.
* ![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=flat&logo=apachemaven&logoColor=white) **Gerenciador:** Automação de compilação e dependências.
* ![Unirest](https://img.shields.io/badge/Unirest-Http_Client-blue) **Cliente HTTP:** Para comunicação simplificada com a API do Telegram.

### 🧠 Por que essa Stack?

| Característica | Escolha Técnica | Justificativa |
| :--- | :--- | :--- |
| **Framework** | **Javalin** | Diferente do Spring Boot, o Javalin é minimalista. Ideal para rodar em hardware modesto (sensores/gateways) sem "peso" desnecessário. |
| **Compatibilidade** | **Java 11 (LTS)** | Garante execução em diversos ambientes legados e modernos sem erros de versão. |
| **Build** | **Maven** | Padronização do projeto, facilitando que qualquer desenvolvedor baixe e rode (`mvn clean install`). |

---

## 📂 Estrutura do Projeto

A organização segue o padrão Maven, separando claramente o "Cérebro" (Servidor) dos "Sensores" (Simulador).

```text
AgroBot-Java/
├── pom.xml                     # Arquivo de dependências do Maven
└── src/
    └── main/
        └── java/
            └── com/
                └── agrobot/
                    ├── AgroBotServer.java    # 🧠 API: Recebe dados e decide (Alertas)
                    └── AgroBotSimulador.java # 📡 IoT: Gera dados fake e envia via HTTP
```

## 🛠️ Como Executar (Passo a Passo)
```

Para o sistema funcionar, é necessário rodar dois processos em terminais separados: o **Servidor** (que recebe os dados) e o **Simulador** (que gera os dados).

```
### 1️⃣ Compilação (Obrigatório na primeira vez)
Abra o terminal na pasta raiz do projeto e compile o código para baixar as dependências do Maven:

```bash
cd AgroBot-Java
mvn clean compile
```
### 2️⃣ Terminal 1: Ligar o Servidor (API)
```
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotServer"
```

### 3️⃣ Terminal 2: Ligar os Sensores (Simulador)
```
cd AgroBot-Java
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotSimulador"
```

* No Telegram, você receberá os alertas quando a temperatura for crítica.

  
### 📸 Demonstração

![demonstracao](https://github.com/user-attachments/assets/469d832e-86d8-4a73-8a40-9ac13585eb3c)

