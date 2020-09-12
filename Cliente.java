import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.*;

public class Cliente {
	
	public static ArrayList<String> Eleitores= new ArrayList<String>();

	private Cliente() {
	}

	public static void main(String[] args)

	{

		int flag = 0;

		Scanner s = new Scanner(System.in);
		String host = (args.length < 1) ? null : args[0];

		try {
			Registry registro = LocateRegistry.getRegistry(host);
			InterfaceRemotaOla stub = (InterfaceRemotaOla) registro.lookup("Ola");

			String resposta = stub.comunica();
			System.out.println("Resposta: " + resposta);

			while (flag == 0) {
				
				System.out.println("Escolha uma das opções abaixo ou aperte 0 para sair:\n");
				System.out.println("1. Registrar voto");
				System.out.println("2. Verificar eleitor");
				System.out.println("3. Votos");
				System.out.println("4. Resultado dos votos");
				System.out.println("5. Mostrar vencedor");
				System.out.println("0. Sair");
				System.out.println("-----------");

				int escolha = s.nextInt();

				{
					switch (escolha) {

					case 1:

						System.out.print("Qual o seu nome? ");
						String nome = s.next();

						System.out.println("Eleitor registrado com o ID: ");
						int id = stub.registraEleitores(nome);
						System.out.println(id);
						System.out.println("-----------");

						break;

					case 2:
						System.out.println("-----------");
						System.out.println("Digite seu ID para entrar: ");
						int x = s.nextInt();
						System.out.println(stub.VerificaEleitores(x));
						System.out.println("-----------");

						break;

					case 3:
						
						System.out.println("-----------");
						System.out.println("Lista de candidatos:\n");

						ArrayList<String> Candidatos = stub.Candidatos();

						for (int i = 0; i < Candidatos.size(); i++) {
							System.out.print(i);
							System.out.print(". ");
							System.out.println(Candidatos.get(i));
						}

						System.out.println("-----------");
						System.out.println("Digite seu ID de eleitor para começar a votar: ");
						int id1 = s.nextInt();

						System.out.println("Digite o número do candidato que você vai votar: ");
						int numCandidato = s.nextInt();

						System.out.println(stub.Votos(id1, numCandidato));
						// System.out.println(stub.VerificaEleitores(x));
						System.out.println("-----------");
						break;

					case 4:
						
						System.out.println("-----------");
						System.out.println("Os votos que cada um dos candidatos recebeu são: ");
						System.out.println(" ");
						System.out.println(stub.tally());
						System.out.println("-----------");
						break;

					case 5:
						
						System.out.println(stub.Vencedor());
						break;
					case 0:
						flag++;
					}

				}
				System.out.println("-----------");
				System.out.println("Votação está encerrada");
				System.out.println("-----------");
			}

			s.close();
		} catch (Exception e) {
			System.err.println("Excecao do cliente: " + e.toString());
			e.printStackTrace();
		}
	}
}