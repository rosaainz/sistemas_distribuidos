package servidor;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import interfaces.Inscripcion;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 *
 * @author beatriz
 */
public class ServidorSaludo extends UnicastRemoteObject implements Inscripcion {
    public Map<String, List<String>> estudiantes;

    public ServidorSaludo() throws RemoteException {
        super();
        this.estudiantes = new HashMap<>();
        cargarDatosEstudiantes();
    }
    
    private void cargarDatosEstudiantes(){
        try (BufferedReader br = new BufferedReader(new FileReader("estudiantes.txt"))){
            String line;
            while((line = br.readLine()) != null){
                String[] parts = line.split(":");
                if(parts.length == 2){
                    String matricula = parts[0].trim();
                    //System.out.println(matricula);
                    String[] materiasArray = parts[1].trim().split(",");
                    List<String> materias = new ArrayList<>();
                    for(String materia: materiasArray){
                        //System.out.println(materia);
                        materias.add(materia.trim());
                    }
                    estudiantes.put(matricula, materias);
                }
            }
        }catch(Exception e){
            System.err.println("Error al crgar");
        }
        
    }
    
    @Override
    public List<String> obtenerMaterias(String matricula) throws RemoteException {
        List<String> materias = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader("estudiantes.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            System.out.println("parts[0]: "+parts[0]);
            if (parts.length == 2 && parts[0].trim().equals(matricula.trim())) {
                System.out.println("dentro del if");
                String[] materiasArray = parts[1].trim().split(",");
                for (String materia : materiasArray) {
                    System.out.println("matricula: "+matricula+" ,Materia: "+materia);
                    materias.add(materia.trim());
                }
                break;  // Termina la búsqueda una vez que se encuentra la matrícula
            }
        }
    } catch (Exception e) {
        System.err.println("Error al leer el archivo: " + e.getMessage());
    }

    System.out.println("Tamaño materias: "+materias.size());
    return materias;
    }


    public static void main(String[] args) {

        try {
            String nombreServicio = "Inscripcion";
            Inscripcion servicio;
            Registry registroRMI;

            registroRMI = LocateRegistry.createRegistry(1099);

            servicio = new ServidorSaludo();
            registroRMI.rebind(nombreServicio, servicio);
            System.out.println("Servidor funcionando");
        } catch (Exception e) {
            System.err.println("Error en el servidor");
            e.printStackTrace();
        }
    }
}
