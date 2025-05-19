import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TCPPlayerClass {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Digite a porta do Servidor:");
        int serverPort = scan.nextInt();
        scan.nextLine();

        System.out.println("Digite o IP do Servidor:");
        String ip = scan.nextLine();

        try (
                Socket socket = new Socket(ip, serverPort);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String msgServer;

            while ((msgServer = in.readLine()) != null) {
                System.out.println("[Servidor]: " + msgServer);

                if (msgServer.contains("Digite um valor")) {
                    int valor;
                    do {
                        System.out.println("Digite um valor de 0 - 100");
                        valor = scan.nextInt();
                        scan.nextLine();
                    } while (valor < 0 || valor > 100);

                    out.println(valor);
                }

                if (msgServer.contains("Você venceu!") || msgServer.contains("Você foi eliminado!")) {
                    System.out.println("Fim de jogo. Encerrando conexão...");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
        }

        scan.close();
    }
}
