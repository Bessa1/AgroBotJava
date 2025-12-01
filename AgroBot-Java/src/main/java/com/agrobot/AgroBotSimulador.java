package br.com.agrobot;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Random;

public class AgroBotSimulador {

    private static final String URL_API = "http://localhost:8080/monitorar_safra";
    private static final String[] CULTURAS = {"Soja", "Milho", "Café Especial", "Algodão"};
    private static final String[] SETORES = {"Estufa A", "Setor Norte", "Setor Sul"};

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        ObjectMapper mapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();

        System.out.println("--- INICIANDO SIMULAÇÃO DE SENSORES IOT (JAVA) ---");
        System.out.println("Pressione CTRL+C para parar.");

        while (true) {
            try {
                // 1. Gera dados aleatórios
                String cultura = CULTURAS[random.nextInt(CULTURAS.length)];
                String setor = SETORES[random.nextInt(SETORES.length)];
                int temp = random.nextInt(26) + 15; // 15 a 40 (aprox)
                int umidade = random.nextInt(71) + 20; // 20 a 90

                // Cria mapa de dados
                Map<String, Object> payload = Map.of(
                    "setor", setor,
                    "cultura", cultura,
                    "temp", temp,
                    "umidade", umidade
                );

                String jsonBody = mapper.writeValueAsString(payload);

                // 2. Mostra no terminal
                System.out.printf("%n📡 Sensor enviando: %s (%d°C / %d%%) ...%n", cultura, temp, umidade);

                // 3. Envia para a API
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(URL_API))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    System.out.println("✅ Resposta da API: " + response.body());
                } else {
                    System.out.println("❌ Erro na API: " + response.statusCode());
                }

            } catch (Exception e) {
                System.out.println("⚠️ Erro de conexão: A API está rodando? (" + e.getMessage() + ")");
            }

            // Espera 5 segundos
            Thread.sleep(5000);
        }
    }
}