import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AgroBotSimulator {

    // Endereço da API Java (Spring Boot roda na porta 8080 por padrão)
    private static final String URL_API = "http://localhost:8080/monitorar_safra";
    
    private static final String[] CULTURAS = {"Soja", "Milho", "Café Especial", "Algodão"};
    private static final String[] SETORES = {"Estufa A", "Setor Norte", "Setor Sul"};

    public static void main(String[] args) throws Exception {
        System.out.println("--- INICIANDO SIMULAÇÃO DE SENSORES IOT (JAVA) ---");
        System.out.println("Pressione CTRL+C para parar.");

        HttpClient client = HttpClient.newHttpClient();
        Random random = new Random();

        while (true) {
            // 1. Gera dados aleatórios
            String cultura = CULTURAS[random.nextInt(CULTURAS.length)];
            String setor = SETORES[random.nextInt(SETORES.length)];
            int temp = 15 + random.nextInt(26); // 15 a 40
            int umidade = 20 + random.nextInt(71); // 20 a 90

            // Monta o JSON manualmente (para não precisar de bibliotecas externas aqui)
            String json = String.format(
                "{\"setor\":\"%s\", \"cultura\":\"%s\", \"temp\":%d, \"umidade\":%d}",
                setor, cultura, temp, umidade
            );

            // 2. Mostra no terminal
            System.out.printf("\n📡 Sensor enviando: %s (%d°C / %d%%) ...%n", cultura, temp, umidade);

            // 3. Envia para a API
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(URL_API))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    System.out.println("✅ Sucesso: " + response.body());
                } else {
                    System.out.println("❌ Erro na API: " + response.statusCode());
                }
            } catch (Exception e) {
                System.out.println("❌ Erro de Conexão: O servidor Java está rodando?");
            }

            // Espera 5 segundos
            TimeUnit.SECONDS.sleep(5);
        }
    }
}