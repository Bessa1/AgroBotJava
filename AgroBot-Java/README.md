🌱 AgroBot (Java Edition) - Monitoramento IoT

Status: ✅ Concluído | Versão: 2.0 (Java Migration)

📖 Sobre o Projeto

O AgroBot é um sistema de monitoramento agrícola baseado em IoT. Esta versão foi migrada para Java (Spring Boot) para atender aos requisitos de robustez de Sistemas Distribuídos corporativos.

O sistema coleta dados simulados de temperatura/umidade e, através de uma API REST, analisa riscos para culturas sensíveis. Se detectar anomalias (Geada ou Calor Extremo), notifica o agrônomo via Telegram.

🚀 Tecnologias

Java 17+: Linguagem Core.

Spring Boot 3: Framework para criação da API REST.

Maven: Gerenciador de dependências e build.

Java HttpClient: Cliente HTTP nativo para o simulador.

Telegram Bot API: Integração mobile.

⚙️ Arquitetura

AgroBotSimulator (Client): App Java console que gera JSON e envia via POST.

AgroBotServer (Server): API Spring Boot rodando no Tomcat (Porta 8080).

Telegram: Interface Mobile para alertas push.

🛠️ Como Executar

1️⃣ Preparação

No VS Code ou Codespaces, certifique-se de que a extensão "Extension Pack for Java" está instalada (o Codespaces geralmente já tem).

2️⃣ Terminal 1: O Servidor (Spring Boot)

Para rodar a API, use o Maven:

mvn spring-boot:run


Aguarde aparecer o logo do Spring e a mensagem "Started AgroBotServer in..."

3️⃣ Terminal 2: O Simulador (Client)

Abra um novo terminal e rode o script do sensor:

java AgroBotSimulator.java


(Nota: Não precisa compilar, o Java 11+ roda arquivos únicos diretamente).

🎉 Pronto!

Terminal 2: 📡 Sensor enviando: Café (36°C)...

Terminal 1: 📥 Recebido...

Celular: 🚨 Notificação chegando!

📊 Regras de Negócio

Condição

Regra

Ação

Crítico (Calor)

Temp > 32°C e Umid < 40%

🚨 Alerta Telegram

Crítico (Frio)

Temp < 10°C

❄️ Alerta Telegram

Atenção

Temp 30-32°C ou Umid 40-50%

⚠️ Alerta Silencioso

Normal

Outros casos

✅ Apenas Log

Developed for Sistemas Distribuídos e Mobile ☕