package cliente;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import interfaces.Inscripcion;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author beatriz
 */
public class ClienteSaludo extends JFrame {
    private JLabel labelMatricula;
    private JTextField textFieldMatricula;
    private JButton buttonEnviar;
    private JTextArea textAreaMaterias;
    private Inscripcion servicio;

    public ClienteSaludo() {
        super("Inscripción");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        labelMatricula = new JLabel("Matrícula:");
        textFieldMatricula = new JTextField(10);
        buttonEnviar = new JButton("Enviar");
        textAreaMaterias = new JTextArea(10, 30);
        textAreaMaterias.setEditable(false);

        add(labelMatricula);
        add(textFieldMatricula);
        add(buttonEnviar);
        add(textAreaMaterias);

        buttonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarMatricula();
            }
        });

        try {
            String nombreServicio = "Inscripcion";
            Registry registry = LocateRegistry.getRegistry();
            servicio = (Inscripcion) registry.lookup(nombreServicio);
        } catch (Exception e) {
            System.err.println("Error en el cliente");
            e.printStackTrace();
        }
    }

    private void enviarMatricula() {
        String matricula = textFieldMatricula.getText();

        try {
            // Llamar al método remoto del servidor
            System.out.println("Matricula a buscar: "+matricula);
            List<String> materias = servicio.obtenerMaterias(matricula);
            System.out.println(materias.size());

            if (materias != null) {
                // Mostrar la respuesta del servidor en el JTextArea
                textAreaMaterias.setText("Materias disponibles:\n");
                for (String materia : materias) {
                    textAreaMaterias.append(materia + "\n");
                    System.out.println(materia);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ClienteSaludo cliente = new ClienteSaludo();
                cliente.setVisible(true);
            }
        });
    }
}