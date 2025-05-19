import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Digite um IP válido:");
        String ip = scanner.nextLine();
        
        System.out.println("Digite uma porta - Apenas números:");
        int porta = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Socket socket = new Socket(ip, porta);

            BufferedReader leitor = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Digite um número para enviar ao servidor: ");
            String numero = scanner.nextLine();

            escritor.println(numero);

            String resposta = leitor.readLine();
            System.out.println("Resposta do servidor: " + resposta);

            socket.close();

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
