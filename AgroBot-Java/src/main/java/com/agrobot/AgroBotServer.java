package com.agrobot;

import io.javalin.Javalin;
import io.javalin.http.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class AgroBotServer {

    // --- SUAS CONFIGURAÇÕES ---
    private static final String TELEGRAM_TOKEN = "8108541302:AAGLfN26dC2i41ooAKLGtLOwbECg1Vv2OfI";
    private static final String CHAT_ID = "1543925291";
    
    // Objeto para lidar com JSON
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static void main(String[] args) {
        // Inicia o servidor na porta 8080
        Javalin app = Javalin.create().start(8080);

        System.out.println("--- AGROBOT SERVER RODANDO EM JAVA ---");

        // Define a rota POST
        app.post("/monitorar_safra", ctx -> monitorarSafra(ctx));
    }

    private static void monitorarSafra(Context ctx) {
        try {
            // 1. Recebe os dados do "Sensor"
            DadosSensor dados = ctx.bodyAsClass(DadosSensor.class);
            
            System.out.printf("📥 Recebido: %s | %.1f°C | %.1f%%%n", 
                dados.cultura, dados.temp, dados.umidade);

            // 2. Lógica Agronômica
            boolean alerta = false;
            String msgTelegram = "";
            String statusResposta;

            // Regra: Calor extremo e Seco
            if (dados.temp > 32 && dados.umidade < 40) {
                alerta = true;
                msgTelegram = String.format(
                    "🚨 **ALERTA CRÍTICO: ESTRESSE TÉRMICO**\n" +
                    "--------------------------------\n" +
                    "📍 **Setor:** %s\n" +
                    "🌱 **Cultura:** %s\n" +
                    "🔥 **Temperatura:** %.1f°C (Muito Alta)\n" +
                    "💧 **Umidade:** %.1f%% (Muito Baixa)\n" +
                    "⚙️ **Ação Automática:** Irrigação ligada!",
                    dados.setor, dados.cultura, dados.temp, dados.umidade
                );
                statusResposta = "CRÍTICO - Irrigação Ativada";
            } 
            // Regra: Frio extremo (Geada)
            else if (dados.temp < 10) {
                alerta = true;
                msgTelegram = String.format(
                    "❄️ **ALERTA DE GEADA**\n" +
                    "--------------------------------\n" +
                    "📍 **Setor:** %s\n" +
                    "🌱 **Cultura:** %s\n" +
                    "🥶 **Temperatura:** %.1f°C\n" +
                    "⚙️ **Ação Automática:** Aquecedores ligados!",
                    dados.setor, dados.cultura, dados.temp
                );
                statusResposta = "CRÍTICO - Aquecimento Ativado";
            } else {
                statusResposta = "NORMAL - Condições Ideais";
            }

            // 3. Envia o alerta se necessário
            if (alerta) {
                enviarTelegram(msgTelegram);
            }

            // Retorna JSON para o simulador
            ctx.json(Map.of("status", statusResposta, "temp_atual", dados.temp));

        } catch (Exception e) {
            e.printStackTrace();
            ctx.status(500).result("Erro interno no servidor");
        }
    }

    private static void enviarTelegram(String mensagem) {
        try {
            String url = "https://api.telegram.org/bot" + TELEGRAM_TOKEN + "/sendMessage";
            
            String jsonBody = jsonMapper.writeValueAsString(Map.of(
                "chat_id", CHAT_ID,
                "text", mensagem
            ));

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            
        } catch (Exception e) {
            System.err.println("Erro ao enviar Telegram: " + e.getMessage());
        }
    }

    // Classe auxiliar para mapear o JSON
    public static class DadosSensor {
        public String setor;
        public String cultura;
        public double temp;
        public double umidade;
    }

}
