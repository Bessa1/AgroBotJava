🌱 AgroBot Java – Monitoramento IoT Inteligente

Status: ✔️ Concluído
Versão: 2.0 (Migração Python ➝ Java)

📖 Sobre o Projeto

O AgroBot é um sistema de monitoramento agrícola que simula sensores IoT para plantações.
Ele coleta dados de temperatura e umidade, envia para uma API em Java, e realiza a análise de risco em tempo real:

❄️ Geada Detected

🔥 Estresse Térmico

Quando uma condição crítica é encontrada, o sistema:

Envia alertas imediatos via Telegram 📲

Aciona sistemas automáticos como Irrigação 💧 ou Aquecedores 🔥

🚀 Por que Java (Javalin)?

O projeto foi migrado de Python para Java visando robustez, performace, e compatibilidade corporativa.
Aqui estão as escolhas técnicas principais:

Característica	Escolha Técnica	Justificativa
Framework	Javalin	Extremamente leve, inicia em < 1s e consome pouca RAM. Ideal para máquinas acadêmicas e servidores enxutos.
Compatibilidade	Java 11 (LTS)	Funciona em laboratórios com máquinas mais antigas, sem erros de versão.
Build	Maven	Padroniza dependências, compilação e execução via linha de comando.
📂 Estrutura do Projeto
/workspaces/AgroBotJava
└── AgroBot-Java/                 <-- Pasta Principal
    ├── pom.xml                   <-- Dependências e Configurações
    └── src/main/java/com/agrobot/
        ├── AgroBotServer.java     <-- API (cérebro do sistema)
        └── AgroBotSimulador.java  <-- Simulador IoT (gerador de dados)

🛠️ Como Executar

Importante: No Codespaces, Java + Maven já estão prontos para uso.

1️⃣ Compilação Inicial
cd AgroBot-Java
mvn clean compile


✔️ Aguarde aparecer BUILD SUCCESS.

2️⃣ Terminal 1 – Iniciar o Servidor (API)
cd /workspaces/AgroBotJava/AgroBot-Java
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotServer"


Você deverá ver:
Javalin has started ...

3️⃣ Terminal 2 – Iniciar o Simulador (Sensores)

Abra outro terminal e execute:

cd /workspaces/AgroBotJava/AgroBot-Java
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotSimulador"


📡 Sensor enviando dados...
💬 Alertas vão aparecer no Telegram em caso de risco!

⚠️ Solução de Problemas Comuns
1. "Goal requires a project to execute..."

Causa: comando mvn executado fora da pasta do projeto.
Solução:

cd AgroBot-Java

2. "Address already in use"

Causa: servidor rodando em dois terminais.
Solução: finalize os terminais ou reinicie o Codespaces.

3. "invalid target release: 17"

Causa: máquina com Java antigo.
Solução: Ajuste no pom.xml:

<maven.compiler.source>11</maven.compiler.source>
<maven.compiler.target>11</maven.compiler.target>
