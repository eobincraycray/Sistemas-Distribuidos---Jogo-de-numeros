package client;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        
       Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um IP  Válido - Atenção aos pontos:");
        String ip = scanner.nextLine();
        System.out.println("Digite uma porta - Apenas números:");
        int porta = scanner.nextInt();
        
            try {     
                Socket socket = new Socket( ip , porta);

                BufferedReader leitor = new BufferedReader (new InputStreamReader (socket.getInputStream()));
                PrintWriter escritor = new PrintWriter( socket.getOutputStream(), true); 

                escritor.println("O cliente esteve aqui!");
                String mensagemRecebida = leitor.readLine();
                System.out.println(mensagemRecebida);

                socket.close();
            
            
            
            } catch (IOException e) {
                e.printStackTrace(); 
        
            }  
    } 

}
