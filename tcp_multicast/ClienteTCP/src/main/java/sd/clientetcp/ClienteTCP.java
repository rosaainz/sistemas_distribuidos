/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package sd.clientetcp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
/**
 *
 * @author rosasainz
 */
public class ClienteTCP extends JFrame{
    private JLabel label;
    private JButton button;
    private JFileChooser fileChooser;
    private Socket socket;
    private DataOutputStream outputStream;

    public ClienteTCP() {
        setTitle("Cliente TCP");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        label = new JLabel("Selecciona un archivo:");
        add(label);

        button = new JButton("Seleccionar archivo");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarArchivo();
            }
        });
        add(button);

        fileChooser = new JFileChooser();
    }
    
    private void seleccionarArchivo() {
        int returnVal = fileChooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            enviarArchivo(file);
        }
    }
    
    private void enviarArchivo(File file) {
        try {
            socket = new Socket("localhost", 7896); // Cambia la dirección y el puerto según tu configuración
            outputStream = new DataOutputStream(socket.getOutputStream());

            // Envía el nombre del archivo
            outputStream.writeUTF(file.getName());

            // Envía el contenido del archivo
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            fileInputStream.close();
            outputStream.close();
            socket.close();

            JOptionPane.showMessageDialog(this, "Archivo enviado con éxito.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al enviar el archivo: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClienteTCP cliente = new ClienteTCP();
                cliente.setVisible(true);
            }
        });
    }
}
