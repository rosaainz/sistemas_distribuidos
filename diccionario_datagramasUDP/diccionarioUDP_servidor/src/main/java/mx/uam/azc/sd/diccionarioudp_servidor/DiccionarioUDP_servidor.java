/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.uam.azc.sd.diccionarioudp_servidor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author rosasainz
 */
public class DiccionarioUDP_servidor {

    public static void main(String[] args) {
        try {
            // Crear un socket UDP en el puerto 9876
            DatagramSocket socket = new DatagramSocket(9876);
            
            System.out.println("Servidor corriendo");

            while (true) {
                // Recibir la solicitud del cliente
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                
                // Obtener la palabra a buscar
                String palabraBuscar = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Palabra a buscar: "+palabraBuscar);

                // Buscar la definición de la plabra que recibimos
                String definicion = buscarDefinicion(palabraBuscar);
                System.out.println("Definicion de "+palabraBuscar+" : "+definicion);

                // Enviar la definición al cliente
                byte[] respuesta = definicion.getBytes();
                packet.setData(respuesta);
                packet.setLength(respuesta.length);
                socket.send(packet);
                
                System.out.println("Definición de '" + palabraBuscar + "' enviada al cliente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método que busca la definición de una palabra en el diccionario.txt
    private static String buscarDefinicion(String palabra) {
        String definicion = "Definicion no encontrada";
       
        try(BufferedReader br = new BufferedReader(new FileReader("diccionario.txt"))){
            String linea;
            
            while((linea = br.readLine()) != null ){
                String[] partes = linea.split(": ");
                if( partes[0].equalsIgnoreCase(palabra)){
                    definicion = partes[1];
                    break;
                }
            }
            
        }catch(IOException e) {
            e.printStackTrace();
        }
//        switch (palabra.toLowerCase()) {
//            case "amor":
//                return "Sentimiento de afecto, inclinación y entrega hacia otra persona.";
//            case "barco":
//                return "Embarcación de gran tamaño que se utiliza para navegar por el mar.";
//            default:
//                return "Definición no encontrada para '" + palabra + "'.";
//        }
    return definicion;
    }

}
