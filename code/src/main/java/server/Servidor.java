import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {

    private static final int NUM_JOGADORES = 3;
    private static final int META_PONTOS = 3;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite uma porta - Apenas números:");
        int porta = scanner.nextInt();

        ServerSocket servidor = new ServerSocket(porta);
        System.out.println("Servidor iniciado em " + InetAddress.getLocalHost().getHostAddress() + " na porta " + porta);
        System.out.println("Aguardando conexão de " + NUM_JOGADORES + " jogadores...");

        Socket[] jogadores = new Socket[NUM_JOGADORES];
        PrintWriter[] escritores = new PrintWriter[NUM_JOGADORES];
        BufferedReader[] leitores = new BufferedReader[NUM_JOGADORES];
        int[] pontos = new int[NUM_JOGADORES];

        // Conectar os jogadores
        for (int i = 0; i < NUM_JOGADORES; i++) {
            jogadores[i] = servidor.accept();
            escritores[i] = new PrintWriter(jogadores[i].getOutputStream(), true);
            leitores[i] = new BufferedReader(new InputStreamReader(jogadores[i].getInputStream()));
            System.out.println("Jogador " + (i + 1) + " conectado: " + jogadores[i].getInetAddress().getHostAddress());
        }

        boolean jogoAtivo = true;

        while (jogoAtivo) {
            // Enviar mensagem de regras
            for (int i = 0; i < NUM_JOGADORES; i++) {
                escritores[i].println("Regras do Jogo: Envie um número entre 0 e 100. Quem chegar mais próximo de 80% da média dos valores vence a rodada!");
            }

            System.out.println("Mensagem cortesia enviada. Aguardando jogadores...");

            int[] numeros = new int[NUM_JOGADORES];
            double soma = 0;

            // Receber os números dos jogadores
            for (int i = 0; i < NUM_JOGADORES; i++) {
                try {
                    String recebido = leitores[i].readLine();
                    numeros[i] = Integer.parseInt(recebido);
                    soma += numeros[i];
                } catch (Exception e) {
                    escritores[i].println("Erro ao receber número. Encerrando.");
                    jogadores[i].close();
                    return;
                }
            }

            double media = soma / NUM_JOGADORES;
            double alvo = media * 0.8;

            System.out.println("Média: " + media + ", Alvo (80%): " + alvo);

            // Determinar quem ficou mais próximo
            double menorDiferenca = Double.MAX_VALUE;
            int vencedorRodada = -1;
            for (int i = 0; i < NUM_JOGADORES; i++) {
                double diferenca = Math.abs(numeros[i] - alvo);
                if (diferenca < menorDiferenca) {
                    menorDiferenca = diferenca;
                    vencedorRodada = i;
                }
            }

            pontos[vencedorRodada]++;
            System.out.println("Jogador " + (vencedorRodada + 1) + " venceu a rodada e agora tem " + pontos[vencedorRodada] + " ponto(s).");

            // Enviar pontuação aos jogadores
            for (int i = 0; i < NUM_JOGADORES; i++) {
                escritores[i].println("Resultado da rodada: Jogador " + (vencedorRodada + 1) + " venceu. Sua pontuação: " + pontos[i]);
            }

            // Verificar fim de jogo
            if (pontos[vencedorRodada] >= META_PONTOS) {
                for (int i = 0; i < NUM_JOGADORES; i++) {
                    escritores[i].println("FIM DE JOGO! Jogador " + (vencedorRodada + 1) + " venceu com " + pontos[vencedorRodada] + " pontos.");
                    jogadores[i].close();
                }
                jogoAtivo = false;
            }
        }

        servidor.close();
    }
}
