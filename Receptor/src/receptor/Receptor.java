package receptor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * @author Maria Luiza Theisges
 */
public class Receptor {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {

        Socket receptor = new Socket("127.0.0.1", 1234); //cliente cria socket de conexao
        System.out.println("\nConexão Estabelecida.\n");

        try {

            ObjectInputStream in = new ObjectInputStream(receptor.getInputStream()); //objeto criado para entrada de dados dessa classe

            String messagem = (String) in.readObject(); //armazena a mensagem vinda do Transmissor na variável "mensagem"

            String tamanho = messagem;
            //System.out.println("Tamanho do arquivo recebido: " + messagem + " Bytes.\n");

            messagem = (String) in.readObject();

            OutputStream arquivo = new FileOutputStream("/home/maria/Downloads/" + messagem);//escrita do arquivo recebido

            System.out.println("Recebendo...\n");
            TimeUnit.SECONDS.sleep(2); //espera dois segundos para receber o arquivo
            
            byte[] buffer = new byte[Integer.parseInt(tamanho)];//array de bytes no tamanho do arquivo a ser recebido

            while (true) {
//loop de recebimento do arquivo               
//enquanto o valor da variável "tamanho" não for -1 significa que o recebimento ainda não terminou
                int len = in.read(buffer);
                if (len == -1) break;
                arquivo.write(buffer, 0, len);
            }

            arquivo.close();
            
            System.out.println("Tamanho do arquivo recebido: " + tamanho + " Bytes.\n");
            
            System.out.println("O arquivo está diponível na pasta Downloads.\n");
            
            System.out.println("Conexão com o Transmissor encerrada.\n");

        } catch (IOException e) {
            System.out.println("Houve algum erro. Não foi possível estabelecer a conexão e/ou transferir o arquivo.\n");
        }
           
        receptor.close();
    }
}
