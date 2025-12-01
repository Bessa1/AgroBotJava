🌱 AgroBot Java - Monitoramento IoT Inteligente

Status: ✅ Concluído | Versão: 2.0 (Migration Python -> Java)

📖 Sobre o Projeto

O AgroBot é um sistema de monitoramento agrícola que simula sensores IoT em plantações. Ele coleta dados de temperatura e umidade em tempo real e utiliza uma API em Java para analisar riscos (Geada ou Estresse Térmico).

Se uma condição crítica for detectada, o sistema envia alertas imediatos via Telegram para o agrônomo e aciona sistemas automáticos (Irrigação ou Aquecedores).

🚀 Por que Java (Javalin)?

Este projeto foi migrado de Python para Java visando robustez e performance em ambientes corporativos.

Característica

Escolha Técnica

Justificativa

Framework

Javalin

Extremamente leve (inicia em < 1s) e consome pouca RAM, ideal para rodar em PCs acadêmicos ou servidores limitados.

Compatibilidade

Java 11

Configuramos para rodar no Java 11 (LTS), garantindo execução em laboratórios com máquinas mais antigas sem erros.

Build

Maven

Gerenciamento padronizado de dependências e compilação.

📂 Estrutura do Projeto

A organização dos arquivos segue o padrão Maven:

/workspaces/AgroBotJava
└── AgroBot-Java/                <-- PASTA PRINCIPAL DO PROJETO
    ├── pom.xml                  <-- Configurações e Dependências
    └── src/main/java/com/agrobot/
        ├── AgroBotServer.java    <-- (API) O Cérebro do sistema
        └── AgroBotSimulador.java <-- (IoT) O Gerador de dados


🛠️ Como Executar (Guia Passo a Passo)

Como estamos usando o GitHub Codespaces, o ambiente já possui Java e Maven. Siga a ordem exata abaixo:

1️⃣ Passo Inicial: Compilação

Abra o terminal. Precisamos entrar na pasta do projeto e compilar o código.

# Entra na pasta correta (IMPORTANTE!)
cd AgroBot-Java

# Baixa as bibliotecas e compila
mvn clean compile


Aguarde aparecer a mensagem "BUILD SUCCESS".

2️⃣ Terminal 1: Ligar o Servidor (API)

Este terminal ficará travado rodando o servidor. Não o feche!

# Certifique-se de estar na pasta AgroBot-Java
cd /workspaces/AgroBotJava/AgroBot-Java

# Comando para rodar o servidor
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotServer"


👀 Sucesso: Aguarde aparecer: Javalin has started ...

3️⃣ Terminal 2: Ligar os Sensores (Simulador)

Abra um Novo Terminal (clique no + ou divida a tela).

# Entra na pasta correta novamente
cd /workspaces/AgroBotJava/AgroBot-Java

# Comando para rodar o simulador
mvn exec:java -Dexec.mainClass="com.agrobot.AgroBotSimulador"


🎉 Pronto!

No Terminal 2, você verá: 📡 Sensor enviando: Soja (35°C)...

No Terminal 1, você verá os dados chegando.

No Telegram, o alerta chegará se a temperatura for crítica!

⚠️ Solução de Problemas Comuns

1. Erro: "Goal requires a project to execute..."

Causa: Você tentou rodar o comando mvn fora da pasta AgroBot-Java.

Solução: Digite cd AgroBot-Java e tente de novo.

2. Erro: "Address already in use"

Causa: Você tentou abrir o servidor duas vezes ou o terminal antigo ainda está rodando.

Solução: Feche os terminais (no ícone de lixeira) e comece do zero.

3. Erro: "invalid target release: 17"

Causa: O PC tem Java antigo.

Solução: No pom.xml, mude a versão de <maven.compiler.source>17 para 11 ou 1.8.

👨‍💻 Autor

Desenvolvido para demonstração de arquitetura IoT com Java.
