/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.uam.azc.sd.diccionarioudp_cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

/**
 *
 * @author rosasainz
 */
public class DiccionarioUDP_cliente extends JFrame{
   private JLabel titleLabel;
    private JPanel palabrasPanel;
    private JPanel buscarPanel;
    private JButton buscarButton;
    private JTextField buscarField;
    
    private DatagramSocket socket;
    private InetAddress dirServidor;
    private int puertoServidor = 9876;
    
    

    public DiccionarioUDP_cliente() {
        super("Diccionario");
        setLayout(new BorderLayout());

        // Título
        titleLabel = new JLabel("Significados disponibles");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Panel de palabras
        palabrasPanel = new JPanel(new GridLayout(3, 10));
        String[] palabras = {
            "Amor", "Barco", "Casa", "Delfin", "Escuela", "Fuego", "Gato", "Hogar", "Isla", "Jardin",
            "Kilometro", "Lapiz", "Musica", "Nube", "Ola", "Papel", "Queso", "Rana", "Silla", "Tiempo",
            "Uva", "Vaca", "Xilofono", "Yacimiento", "Zorro", "Elefante", "Lluvia", "Manzana", "Nariz", "Zapato"
        };
        for (String palabra : palabras) {
            palabrasPanel.add(new JLabel(palabra));
        }
        add(palabrasPanel, BorderLayout.CENTER);

        // Panel de búsqueda
        buscarPanel = new JPanel(new FlowLayout());
        buscarPanel.add(new JLabel("Palabra a buscar: "));
        buscarField = new JTextField(15);
        buscarPanel.add(buscarField);
        buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarTermino();
            }
        });
        buscarPanel.add(buscarButton);
        add(buscarPanel, BorderLayout.SOUTH);

        try {
            socket = new DatagramSocket();
            dirServidor = InetAddress.getLocalHost();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setVisible(true);
    }

    private void buscarTermino() {
        String termino = buscarField.getText();
        String significado = obtenerSignificado(termino);
        JOptionPane.showMessageDialog(this, "Significado de " + termino + ": " + significado);
    }

    public String obtenerSignificado(String termino){
        try {
            // Enviar el término al servidor
            byte[] sendData = termino.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, dirServidor, puertoServidor);
            socket.send(sendPacket);

            // Recibir la respuesta del servidor
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);
            return new String(receivePacket.getData()).trim();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Error al obtener el significado.";
        }
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DiccionarioUDP_cliente();
            }
        });
    }
}
