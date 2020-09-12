import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServidorImple implements InterfaceRemotaOla {

	public static HashMap<String, Integer> contador = new HashMap<String, Integer>();
	public static ArrayList<Integer> idEleitor = new ArrayList<Integer>();
	public static ArrayList<String> eleitor = new ArrayList<String>(); // para colocar o nome do eleitor
	public static ArrayList<String> votos = new ArrayList<String>(); // lista para verificar se o eleitor já votou ou não
																		

	public static ArrayList<String> temps = new ArrayList<String>();

	public ServidorImple() {

	}

	public String comunica() {
		return "Olá mundo!";
	}

	private int geradorID() {

		Random ran = new Random();

		int nxt = ran.nextInt(22222222 - 10000000) + 10000000; // Gera identificação do eleitor com até 8 digiítos e
																// único

		int tamanho = String.valueOf(nxt).length();

		String string = Integer.toString(nxt);
		int[] ints = new int[string.length()];

		for (int i = 0; i < string.length(); i++) {
			ints[i] = Integer.parseInt(string.substring(i, i + 1));
		}

		for (int i = ints.length - 2; i >= 0; i = i - 2) {
			int j = ints[i];
			j = j * 2;
			if (j > 9) {
				j = j % 10 + 1;
			}
			ints[i] = j;
		}

		int sum = 0;

		for (int i = 0; i < ints.length; i++) {
			sum += ints[i];
		}

		if (sum % 2 == 0) {

			return nxt;

		} else {

			return nxt + 1;
		}
	}

	public int registraEleitores(String nome) throws RemoteException {

		eleitor.add(nome);
		int x = geradorID();

		idEleitor.add(x);
		votos.add(null);

		System.out.print("----------");
		System.out.println("Os eleitores registrados são: \n");

		for (int i = 0; i < eleitor.size(); i++) {
			System.out.print(eleitor.get(i));
			System.out.print(":");
			System.out.print(idEleitor.get(i));
			System.out.print("|");

		}

		System.out.println(" ");
		return x;
	}

	public ArrayList<String> Candidatos() throws RemoteException {

		return temps;
	}

	public String VerificaEleitores(int id) throws RemoteException {

		int index = idEleitor.indexOf(id);
		if (index != -1) {

			String nome = eleitor.get(index);

			return nome;
		}

		return "Eleitor não verificado";

	}

	public String Votos(int id, int candidatoIndex) throws RemoteException {
		int index = idEleitor.indexOf(id);

		if (index == -1) {
			return "Não existe o ID desse eleitor";
		}

		if (votos.get(index) != null) {
			return "Você já votou antes!!";
		}

		String candidato1 = temps.get(candidatoIndex);

		votos.set(index, candidato1);
		contador.put(candidato1, contador.get(candidato1) + 1);

		return "Voto efetuado com sucesso!!!";
	}

	public HashMap<String, Integer> tally() throws RemoteException {

		return contador;
	}

	public String Vencedor() throws RemoteException {

		int maxVotos = 0;

		ArrayList<String> listaVencedores = new ArrayList<String>();

		String vencedor = temps.get(0);
		int count = 1;
		listaVencedores.add(vencedor);

		for (HashMap.Entry<String, Integer> entrada : contador.entrySet()) {

			String chave = entrada.getKey();
			Integer value = entrada.getValue();

			if (value > maxVotos) {
				listaVencedores = new ArrayList<String>();
				maxVotos = value;
				vencedor = chave;
				count = 1;
				listaVencedores.add(vencedor);
			} else if (value == maxVotos) {
				count += 1;
				vencedor = chave;
				listaVencedores.add(vencedor);
			} else {

			}
		}

		if (maxVotos == 0) {
			return "Ninguém votou. Sem dados para mostrar.";
		}

		if (count == 1) {
			return vencedor + "Ganhou a eleição: " + maxVotos + " votos!!";
		}

		return "Candidatos com o mesmo numero de votos. Empate!!" + listaVencedores.toString();

	}

	public static void main(String args[]) throws IOException {

		try {

			ServidorImple obj = new ServidorImple();

			InterfaceRemotaOla stub = (InterfaceRemotaOla) UnicastRemoteObject.exportObject(obj, 0);

			Registry registro = LocateRegistry.getRegistry();
			registro.bind("Olá", stub);
			System.err.println("Servidor pronto");

			String token1 = "";

			Scanner entrada1 = new Scanner(new File("nome.txt")).useDelimiter(",\\s*");
			
			// List<String> temps = new LinkedList<String>();
			// ArrayList<String> temps = new ArrayList<String>();
			
			while (entrada1.hasNext()) {

				token1 = entrada1.next();
				temps.add(token1);
			}
			entrada1.close();

			String[] tempsArray = temps.toArray(new String[0]);

			System.out.println(temps);
			System.out.println(" A lista de candidatos é: ");
			System.out.println("---------");

			for (String s : tempsArray) {
				System.out.println(s);
				contador.put(s, 0);
			}

			System.out.println("--------");
			System.out.println("Grupo de votos atuais dos candidatos: ");
			System.out.println(" ");
			System.out.println(contador);

		} catch (Exception e) {
			System.err.println("Exceção do servidor: " + e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public int calculate(int n) {
		
		return 0;
	}
}
