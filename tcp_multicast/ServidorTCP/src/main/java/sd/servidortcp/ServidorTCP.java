/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package sd.servidortcp;
import java.io.*;
import java.net.*;
/**
 *
 * @author rosasainz
 */
public class ServidorTCP {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream inputStream = null;

        try {
            serverSocket = new ServerSocket(12345); // Puerto donde el servidor escucha
            System.out.println("Servidor TCP iniciado. Esperando conexiones...");

            socket = serverSocket.accept(); // Espera a que un cliente se conecte
            System.out.println("Cliente conectado desde: " + socket.getInetAddress());

            inputStream = new DataInputStream(socket.getInputStream());

            // Lee el nombre del archivo enviado por el cliente
            String nombreArchivo = inputStream.readUTF();
            System.out.println("Archivo recibido: " + nombreArchivo);

            // Lee el contenido del archivo
            FileOutputStream fileOutputStream = new FileOutputStream("archivos_recibidos/" + nombreArchivo);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }

            fileOutputStream.close();
            inputStream.close();
            socket.close();
            serverSocket.close();
            System.out.println("Archivo guardado en archivos_recibidos/" + nombreArchivo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
