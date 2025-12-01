# 🌱 AgroBot Java - Monitoramento IoT Inteligente

**Status:** ✅ Concluído  
**Versão:** 2.0 *(Migração Python → Java)*

---

## 📖 Sobre o Projeto

O **AgroBot** é um sistema de monitoramento agrícola que simula sensores IoT em plantações. Ele coleta dados de **temperatura** e **umidade** em tempo real e utiliza uma API em Java para analisar riscos como **Geada** ou **Estresse Térmico**.

Se uma condição crítica for detectada, o sistema:

- Envia alertas imediatos via **Telegram** 📩  
- Aciona sistemas automáticos de **Irrigação 💧** ou **Aquecimento 🔥**

---

## 🚀 Por que Java (Javalin)?

Este projeto foi migrado de Python para Java visando **robustez** e **performance** em ambientes corporativos.

### 🧠 Escolhas Técnicas

| Característica     | Escolha Técnica | Justificativa                                                                 |
|--------------------|------------------|-------------------------------------------------------------------------------|
| Framework          | Javalin          | Extremamente leve (inicia em < 1s), ideal para PCs acadêmicos ou servidores  |
| Compatibilidade    | Java 11 (LTS)    | Garante execução em máquinas antigas sem erros de versão                     |
| Build              | Maven            | Gerenciamento padronizado de dependências e compilação                       |

---

## 📂 Estrutura do Projeto
/workspaces/AgroBotJava └── AgroBot-Java/ ├── pom.xml └── src/main/java/com/agrobot/ ├── AgroBotServer.java      // API - O cérebro do sistema └── AgroBotSimulador.java   // IoT - Gerador de dados


---

## 🛠️ Como Executar (Guia Passo a Passo)

> Requisitos: Ambiente GitHub Codespaces com Java e Maven já instalado.

### 1️⃣ Compilação

```bash
cd AgroBot-Java
mvn clean compile

Aguarde a mensagem: BUILD SUCCESS.


```

Terminal 1 - Servidor (API)
cd /workspaces/AgroBotJava/AgroBot-Java
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotServer"

```
cd /workspaces/AgroBotJava/AgroBot-Java
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotServer"

👀 Aguarde: Javalin has started ...
```

Terminal 2 - Sensores (Simulador
```
cd /workspaces/AgroBotJava/AgroBot-Java
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotSimulador"
```

🎉 Pronto

- Terminal 2: 📡 Sensor enviando: Soja (35°C)...
- Terminal 1: Dados recebidos
- Telegram: Alerta enviado se temperatura for crítica

