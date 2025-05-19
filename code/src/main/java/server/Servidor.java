import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {

       Scanner scanner = new Scanner(System.in);
        System.out.println("Digite uma porta - Apenas números:");
        int porta = scanner.nextInt();  // Pegando a porta digitada
        
        try {     
            ServerSocket servidor = new ServerSocket(porta);
            System.out.println("Servidor iniciado na porta " + porta);
            System.out.println("Aguardando conexão do cliente...");

            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

        } catch (IOException e) {
            e.printStackTrace(); 
        
    }   

}

}
