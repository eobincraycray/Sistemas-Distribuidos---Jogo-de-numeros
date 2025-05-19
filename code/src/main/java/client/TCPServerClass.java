package client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPServerClass {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        System.out.println("Digite a porta do Servidor:");
        int serverPort = scan.nextInt();

        int[] pontuacao = {0, 0, 0};

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            String ipServidor = InetAddress.getLocalHost().getHostAddress();
            System.out.println("Servidor iniciado em " + ipServidor + " na porta " + porta);
            System.out.println("Servidor aguardando jogadores ...");

            Socket[] players = new Socket[3];
            BufferedReader[] entries = new BufferedReader[3];
            PrintWriter[] exits = new PrintWriter[3];

            for (int i = 0; i < 3; i++) {
                players[i] = serverSocket.accept();
                entries[i] = new BufferedReader(new InputStreamReader(players[i].getInputStream()));
                exits[i] = new PrintWriter(players[i].getOutputStream(), true);
                exits[i].println("Conectando com o jogador...");
                System.out.println("Jogador " + (i + 1) + " Conectado!");
            }

            do {
                int[] values = new int[3];
                for (int i = 0; i < 3; i++) {
                    System.out.println("Digite um valor de 0 a 100:");
                    values[i] = Integer.parseInt(entries[i].readLine());
                }
                double media = (values[0] + values[1] + values[2]) / 3.0;
                double meta = media * 0.8;

                double[] distancia = {
                        Math.abs(values[0] - meta),
                        Math.abs(values[1] - meta),
                        Math.abs(values[2] - meta)
                };

                //Teste de condições (alteração no código depois)
                if (distancia[0] < distancia[1] && distancia[0] < distancia[2]) {
                    pontuacao[0] += 0;
                } else if (distancia[0] > distancia[1] && distancia[0] > distancia[2]) {
                    pontuacao[0] -= 2;
                } else {
                    pontuacao[0] -= 1;
                }

                if (distancia[1] < distancia[0] && distancia[1] < distancia[2]) {
                    pontuacao[1] += 0;
                } else if (distancia[1] > distancia[0] && distancia[1] > distancia[2]) {
                    pontuacao[1] -= 2;
                } else {
                    pontuacao[1] -= 1;
                }

                if (distancia[2] < distancia[0] && distancia[2] < distancia[1]) {
                    pontuacao[2] += 0;
                } else if (distancia[2] > distancia[0] && distancia[2] > distancia[1]) {
                    pontuacao[2] -= 2;
                } else {
                    pontuacao[2] -= 1;
                }

                for (int i = 0; i < 3; i++) {
                    exits[i].println("Meta: " + meta);
                    exits[i].println("Seu valor:" + values[i]);
                    exits[i].println("Pontuação Atual:" + pontuacao[i]);
                }
            } while (
                    (pontuacao[0] > -6 && pontuacao[1] > -6) ||
                            (pontuacao[0] > -6 && pontuacao[2] > -6) ||
                            (pontuacao[1] > -6 && pontuacao[2] > -6)
            );

            for (int i = 0; i < 3; i++) {
                if (pontuacao[i] <= -6) {
                    exits[i].println("Você foi eliminado!\n");
                } else {
                    exits[i].println("Você venceu!\n");
                }
                players[i].close();
            }
        } catch(IOException e){
                System.out.println("Erro ao conectar com Servidor: "+ e.getMessage());
        }catch (NumberFormatException e){
            System.out.println("Erro ao ler o numero: "+ e.getMessage());
        }
    }
}
