package interfaces;

import java.rmi.*;
import java.util.List;
/**
 *
 * @author Rosa Isela Garcia Sainz
 */
public interface Inscripcion extends Remote {
     List<String> obtenerMaterias(String matricula) throws RemoteException;
}
