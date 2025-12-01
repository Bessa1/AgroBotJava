package com.agrobot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class AgroBotServer {

    // --- CONFIGURAÇÕES ---
    private static final String TELEGRAM_TOKEN = "8108541302:AAGLfN26dC2i41ooAKLGtLOwbECg1Vv2OfI";
    private static final String CHAT_ID = "1543925291";

    public static void main(String[] args) {
        SpringApplication.run(AgroBotServer.class, args);
        System.out.println("🚀 AgroBot Server rodando em http://localhost:8080");
    }

    @PostMapping("/monitorar_safra")
    public Map<String, Object> monitorarSafra(@RequestBody SensorData dados) {
        // 1. Recebe os dados
        System.out.printf("📥 Recebido: %s | %.1f°C | %.1f%%%n", 
            dados.cultura, dados.temp, dados.umidade);

        String statusResposta = "NORMAL - Condições Ideais";
        String msgTelegram = null;
        boolean alerta = false;

        // 2. Regras de Negócio
        
        // Regra 1: Calor Extremo (Soja/Café)
        if (dados.temp > 32 && dados.umidade < 40) {
            alerta = true;
            msgTelegram = String.format("""
                🔥 *ALERTA CRÍTICO: ESTRESSE TÉRMICO* 🔥
                --------------------------------
                📍 *Setor:* %s
                🌱 *Cultura:* %s
                🌡️ *Temperatura:* %.1f°C (Muito Alta)
                💧 *Umidade:* %.1f%% (Muito Baixa)
                ⚠️ *Ação Automática:* Irrigação ligada!
                """, dados.setor, dados.cultura, dados.temp, dados.umidade);
            statusResposta = "CRÍTICO - Irrigação Ativada";
        }
        
        // Regra 2: Frio Extremo
        else if (dados.temp < 10) {
            alerta = true;
            msgTelegram = String.format("""
                ❄️ *ALERTA DE GEADA* ❄️
                --------------------------------
                📍 *Setor:* %s
                🌱 *Cultura:* %s
                🌡️ *Temperatura:* %.1f°C
                ⚠️ *Ação Automática:* Aquecedores ligados!
                """, dados.setor, dados.cultura, dados.temp);
            statusResposta = "CRÍTICO - Aquecimento Ativado";
        }
        
        // Regra 3: Alerta Silencioso (Atenção)
        else if ((dados.temp >= 30 && dados.temp <= 32) || (dados.umidade >= 40 && dados.umidade <= 50)) {
            alerta = true;
            msgTelegram = String.format("""
                ⚠️ *ALERTA SILENCIOSO - Atenção*
                --------------------------------
                📍 *Setor:* %s
                🌱 *Cultura:* %s
                🌡️ *Temperatura:* %.1f°C
                💧 *Umidade:* %.1f%%
                ℹ️ Nenhuma ação automática tomada.
                """, dados.setor, dados.cultura, dados.temp, dados.umidade);
            statusResposta = "ATENÇÃO - Alerta Silencioso";
        }

        // 3. Envia para o Telegram
        if (alerta && msgTelegram != null) {
            enviarTelegram(msgTelegram);
        }

        // Retorna JSON para o simulador
        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", statusResposta);
        resposta.put("temp_atual", dados.temp);
        return resposta;
    }

    private void enviarTelegram(String mensagem) {
        try {
            String url = "https://api.telegram.org/bot" + TELEGRAM_TOKEN + "/sendMessage";
            
            // Monta o payload simples para o Telegram
            Map<String, String> payload = new HashMap<>();
            payload.put("chat_id", CHAT_ID);
            payload.put("text", mensagem);
            payload.put("parse_mode", "Markdown");

            new RestTemplate().postForObject(url, payload, String.class);
            System.out.println("📤 Notificação enviada para o Telegram.");
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar Telegram: " + e.getMessage());
        }
    }

    // Classe auxiliar para representar o JSON (DTO)
    public static class SensorData {
        public String setor;
        public String cultura;
        public double temp;
        public double umidade;
    }
}