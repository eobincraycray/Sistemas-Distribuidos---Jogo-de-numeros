import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Servidor {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite uma porta - Apenas números:");
        int porta = scanner.nextInt();  // Pegando a porta digitada
        
        try (ServerSocket servidor = new ServerSocket(porta);) {   

            while (true) {
                String ipServidor = InetAddress.getLocalHost().getHostAddress();
                System.out.println("Servidor iniciado em " + ipServidor + " na porta " + porta);
                System.out.println("Aguardando conexão do cliente...");

                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

                BufferedReader leitor = new BufferedReader (new InputStreamReader (cliente.getInputStream()));
                PrintWriter escritor = new PrintWriter( cliente.getOutputStream(), true); 

                String mensagem = leitor.readLine();
                System.out.println("cliente"+ mensagem);

                escritor.println(" Sua mensagem foi recebida");
                cliente.close();
                    
            }

        } catch (IOException exception) {
            exception.printStackTrace(); 
        
    }   

}

}

// 1 - Fase: Digite um numero de 0 á 100 ( Eu tiro a media, multiplico por 0.8 e quem tira o resultado mais proximo da meta fica com (0) o segundo fica com (-1) e o ultimo (-2))
// 2 - Fase: 
