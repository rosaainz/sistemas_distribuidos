/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mx.uam.azc.sd.diccionarioudp_cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
            "Amor", "Barco", "Casa", "Delfín", "Escuela", "Fuego", "Gato", "Hogar", "Isla", "Jardín",
            "Kilómetro", "Lápiz", "Música", "Nube", "Ola", "Papel", "Queso", "Rana", "Silla", "Tiempo",
            "Uva", "Vaca", "Xilófono", "Yacimiento", "Zorro", "Elefante", "Lluvia", "Manzana", "Nariz", "Zapato"
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

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setVisible(true);
    }

    private void buscarTermino() {
        String termino = buscarField.getText();
        JOptionPane.showMessageDialog(this, "Buscando definición de: " + termino);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DiccionarioUDP_cliente();
            }
        });
    }
}
