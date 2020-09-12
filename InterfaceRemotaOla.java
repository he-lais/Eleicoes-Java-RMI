import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface InterfaceRemotaOla extends Remote {

	String comunica() throws RemoteException;

	int registraEleitores(String x) throws RemoteException;

	ArrayList<String> Candidatos() throws RemoteException;

	String VerificaEleitores(int x) throws RemoteException;

	String Votos(int id, int candidatoIndex) throws RemoteException;

	HashMap<String, Integer> tally() throws RemoteException;

	String Vencedor() throws RemoteException;

	int calculate(int n);

}
