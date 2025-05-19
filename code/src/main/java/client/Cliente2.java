import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o IP do servidor:");
        String ip = scanner.nextLine();

        System.out.println("Digite a porta:");
        int porta = scanner.nextInt();
        scanner.nextLine(); // limpar o buffer

        try {
            Socket socket = new Socket(ip, porta);
            BufferedReader leitor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);

            boolean jogoAtivo = true;

            while (jogoAtivo) {
                String msg = leitor.readLine();
                if (msg == null) break;

                System.out.println("[SERVIDOR]: " + msg);

                if (msg.startsWith("Regras do Jogo")) {
                    System.out.print("Digite um número de 0 a 100: ");
                    String numero = scanner.nextLine();
                    escritor.println(numero);
                }

                if (msg.startsWith("FIM DE JOGO")) {
                    jogoAtivo = false;
                }
            }

            socket.close();

        } catch (IOException e) {
            System.out.println("Erro ao conectar com o servidor.");
            e.printStackTrace();
        }
    }
}
