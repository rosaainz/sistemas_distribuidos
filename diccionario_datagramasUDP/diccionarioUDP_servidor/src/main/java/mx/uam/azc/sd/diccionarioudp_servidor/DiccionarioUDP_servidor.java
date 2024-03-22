/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.uam.azc.sd.diccionarioudp_servidor;

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
            
            System.out.println("Servidor UDP iniciado en el puerto 9876...");

            while (true) {
                // Preparar el paquete para recibir la solicitud del cliente
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                // Recibir la solicitud del cliente
                socket.receive(packet);
                
                // Obtener el término de búsqueda del cliente
                String termino = new String(packet.getData(), 0, packet.getLength());

                // Buscar la definición del término
                String definicion = buscarDefinicion(termino);

                // Preparar el paquete para enviar la definición al cliente
                byte[] respuesta = definicion.getBytes();
                packet.setData(respuesta);
                packet.setLength(respuesta.length);

                // Enviar la definición al cliente
                socket.send(packet);
                
                System.out.println("Definición de '" + termino + "' enviada al cliente");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para buscar la definición de un término en el diccionario
    private static String buscarDefinicion(String termino) {
        // Simplemente un ejemplo, deberías implementar tu propia lógica de búsqueda
        switch (termino.toLowerCase()) {
            case "amor":
                return "Sentimiento de afecto, inclinación y entrega hacia otra persona.";
            case "barco":
                return "Embarcación de gran tamaño que se utiliza para navegar por el mar.";
            // Añadir más definiciones aquí...
            default:
                return "Definición no encontrada para '" + termino + "'.";
        }
    }

}
