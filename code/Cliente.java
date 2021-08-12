package projeto4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

public class Cliente {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		boolean conectado = true;
		int contador = 1;
		
		while (conectado == true) {
			
			try 
			{
				Registry registry = LocateRegistry.getRegistry("localhost", 8000);
				IScholar myScholar = (IScholar) registry.lookup("Scholar");
				contador = 1;
				
				System.out.println("================================== SCHOLAR =================================");
				
				boolean menu = true;
				boolean valido = false;
				String comando = "";
				
				while (menu == true) {
					
					System.out.println("\n******************************* MENU INICIAL *******************************");
					System.out.println("\n1- Novo registo \n2- Login \n3- Sair \nEscolha uma opcao:");
					comando = scan.nextLine();
					
					if (comando.equals("1"))
					{
						System.out.println("\n=============================== Novo registo ===============================");
						
						String nomeproprio = "";
						while (nomeproprio.isBlank()) {
							System.out.println("\nNome proprio: ");		
							nomeproprio = scan.nextLine();
							if (nomeproprio.isBlank()) {
								System.out.println("ERRO: Tem que introduzir algo!");
							} 
						}

						String apelido = "";
						while (apelido.isBlank()) {
							System.out.println("\nApelido: ");		
							apelido = scan.nextLine();
							if (apelido.isBlank()) {
								System.out.println("ERRO: Tem que introduzir algo!");
							} 
						}
						
						String afiliacao = "";
						while (afiliacao.isBlank()) {
							System.out.println("\nAfiliacao: ");		
							afiliacao = scan.nextLine();
							if (afiliacao.isBlank()) {
								System.out.println("ERRO: Tem que introduzir algo!");
							}  
						}
						
						String email = "";
						while (email.isBlank() || email.length() < 4) {
							System.out.println("\nEmail: ");		
							email = scan.nextLine();
							if (email.isBlank()) {
								System.out.println("ERRO: Tem que introduzir algo!");
							}  
							else if (email.length() < 4) {
								System.out.println("ERRO: O formato introduzido nao e valido!");
							}
						}
						
						String password = "";
						while (password.isBlank() || password.length() < 4) {
							System.out.println("\nPalavra-passe: ");		
							password = scan.nextLine();
							if (password.isBlank()) {
								System.out.println("ERRO: Tem que introduzir algo!");
							}  
							else if (password.length() < 4) {
								System.out.println("ERRO: A password tem que ter pelo menos 4 caracteres!");
							}
						}
						
						boolean registo = myScholar.registarAutor(new Autor(nomeproprio, apelido, email, password, afiliacao));			
						if (registo == false) {
							System.out.println("\nERRO: O email introduzido ja esta a ser utilizado!");
						}
						else {
							System.out.println("\nRegisto efetuado com sucesso!\n");
						}
					}
					
					else if (comando.equals("2"))
					{
						System.out.println("\n=================================== Login ==================================");
						
						System.out.println("\nEmail: ");
						String email = scan.nextLine();
						
						System.out.println("\nPalavra-passe: ");
						String password = scan.nextLine();
						
						String login = myScholar.verificarLogin(email, password);
						
						if (login.equals("false1")) {	
							System.out.println("\nERRO: O email inserido nao corresponde a nenhum dos autores registados!");
						}
						else if (login.equals("false2")) {	
							System.out.println("\nERRO: A password inserida nao corresponde ao email introduzido!");
						}
						
						else if (login.equals("true")) {
							System.out.println("\nLogin efetuado com sucesso! Bem-vindo/a, " + myScholar.getAutor(email) + "!\n");	
							
							boolean sessaoIniciada = true;
							while(sessaoIniciada == true) // ciclo da sessao: termina quando o utilizador escolher a opcao 7
							{
								System.out.println("\n****************************** MENU PRINCIPAL ******************************\n");
								
								System.out.println("1- Listar publicacoes por ano (mais recentes primeiro) "
										+ "\n2- Listar publicacoes por citacoes (mais citadas primeiro) "
										+ "\n3- Adicionar publicacao \n4- Ver publicacoes candidatas \n5- Remover publicacao "
										+ "\n6- Mostrar estatisticas \n7- Terminar sessao \nEscolha uma opcao:");
								comando = scan.nextLine();
								
								if (comando.equals("1")) // mostrar publicacoes ordenadas por ano
								{
									System.out.println("\n=========================== AS SUAS PUBLICACOES ============================");
									System.out.println("----------------------- Ordem: mais recentes primeiro ----------------------\n");
									
									ArrayList<Publicacao> listaOrdenada = myScholar.ordenar("ano");	
									System.out.println(printLista(listaOrdenada));	
								}
								
								else if (comando.equals("2")) // mostrar publicacoes ordenadas por citacao
								{	
									System.out.println("\n=========================== AS SUAS PUBLICACOES ============================");
									System.out.println("----------------------- Ordem: mais citadas primeiro -----------------------\n");
									
									ArrayList<Publicacao> listaOrdenada = myScholar.ordenar("citacao");	
									System.out.println(printLista(listaOrdenada));		
								
								}
								
								else if (comando.equals("3")) { 
									
									System.out.println("\n=========================== ADICIONAR PUBLICACAO ===========================");
									
									String doi = "";
									while (doi.isBlank()) {
										System.out.println("\nInsira o DOI da publicacao que quer adicionar: ");		
										doi = scan.nextLine();
										if (doi.isBlank()) {
											System.out.println("ERRO: Tem que introduzir algo!");
										} 
									}
									valido = myScholar.validarDOI(doi); // verifica se a publicacao com o DOI inserido ja esta registada no sistema
									
									if (valido == false) {
										System.out.println("ERRO: A publicacao com o DOI inserido ja esta registada no sistema!");
									}
									
									else {
										System.out.println("\nAutores: ");
										ArrayList<Autor> listaAutores = new ArrayList<Autor>();
										
										int i = 1;
										boolean novoautor = true; 
										while (novoautor == true) { // ciclo para pedir o(s) autor(es) de uma publicacao
											System.out.println("\nAutor " + i + ": ");
	
											String nomeproprio = "";
											while (nomeproprio.isBlank()) {
												System.out.println("\nNome proprio: ");		
												nomeproprio = scan.nextLine();
												if (nomeproprio.isBlank()) {
													System.out.println("ERRO: Tem que introduzir algo!");
												} 
											}

											String apelido = "";
											while (apelido.isBlank()) {
												System.out.println("\nApelido: ");		
												apelido = scan.nextLine();
												if (apelido.isBlank()) {
													System.out.println("ERRO: Tem que introduzir algo!");
												} 
											}
						
											listaAutores.add(new Autor(nomeproprio, apelido));
											
											comando = "";
											while (!comando.equalsIgnoreCase("S") && !comando.equalsIgnoreCase("N")) {
												System.out.println("\nQuer adicionar outro autor? [S/N]");
												comando = scan.nextLine();
												if (comando.equalsIgnoreCase("N")) {
													novoautor = false;
												}
												else if (comando.equalsIgnoreCase("S")) {
													i++;
												}
												else {
													System.out.println("ERRO: Opcao invalida! Deve introduzir S ou N.");
												}	
											}
										}
										
										valido = myScholar.validarAutores(listaAutores); // verifica se o utilizador faz parte da lista
										
										if (valido == true) { // se o utlizador estiver na lista sao pedidos os restantes dados
											
											String titulo = "";
											while (titulo.isBlank()) {
												System.out.println("\nTitulo da publicacao: ");		
												titulo = scan.nextLine();
												if (titulo.isBlank()) {
													System.out.println("ERRO: Tem que introduzir algo!");
												} 
											}
											
											String revista = "";
											while (revista.isBlank()) {
												System.out.println("\nRevista: ");		
												revista = scan.nextLine();
												if (revista.isBlank()) {
													System.out.println("ERRO: Tem que introduzir algo!");
												} 
											}
											
											int ano = 0;
											valido = false;
											while (valido == false) {
												System.out.println("\nAno: ");
												comando = scan.nextLine();
												valido = myScholar.isInt(comando);
												if (valido == false) {
													System.out.println("ERRO: O ano tem que ser um numero inteiro positivo!");
												} 
												else {
													ano = Integer.parseInt(comando);
													if (ano > 2020) {
														valido = false;
														System.out.println("ERRO: Ano invalido!");
													}
												}
											}
											
											int volume = 0;
											valido = false;
											while (valido == false) {
												System.out.println("\nVolume: ");
												comando = scan.nextLine();
												valido = myScholar.isInt(comando);
												if (valido == false) {
													System.out.println("ERRO: O volume tem que ser um numero inteiro positivo!");
												} 
												else {
													volume = Integer.parseInt(comando);
												}
											}
											
											int numero = 0;
											valido = false;
											while (valido == false) {
												System.out.println("\nNumero: ");
												comando = scan.nextLine();
												valido = myScholar.isInt(comando);
												if (valido == false) {
													System.out.println("ERRO: O numero tem que ser um numero inteiro positivo!");
												} 
												else {
													numero = Integer.parseInt(comando);
												}
											}
											
											int pagI = 0;
											valido = false;
											while (valido == false) {
												System.out.println("\nPagina inicial: ");
												comando = scan.nextLine();
												valido = myScholar.isInt(comando);
												if (valido == false) {
													System.out.println("ERRO: A pagina inicial tem que ser um numero inteiro positivo!");
												} 
												else {
													pagI = Integer.parseInt(comando);
												}
											}
											
											int pagF = 0;
											valido = false;
											while (valido == false) {
												System.out.println("\nPagina final: ");
												comando = scan.nextLine();
												valido = myScholar.isInt(comando);
												if (valido == false) {
													System.out.println("ERRO: A pagina final tem que ser um numero inteiro positivo!");
												} 
												else {
													pagF = Integer.parseInt(comando);
													if (pagF <= pagI) {
														valido = false;
														System.out.println("ERRO: A pagina final tem que ser maior que a pagina inicial!");
													}
												}
											}
											
											int ncitacoes = 0;
											valido = false;
											while (valido == false) {
												System.out.println("\nNumero de citacoes: ");
												comando = scan.nextLine();
												valido = myScholar.isInt(comando);
												if (valido == false) {
													System.out.println("ERRO: O numero de citacoes tem que ser um numero inteiro positivo!");
												} 
												else {
													ncitacoes = Integer.parseInt(comando);
												}
											}						
											
											Publicacao novapublicacao = new Publicacao(titulo, revista, doi, ano, volume, numero, pagI, pagF, ncitacoes, listaAutores);			
											myScholar.adicionarPub(novapublicacao);
											System.out.println("\nPublicacao adicionada com sucesso!");			
										}
										
										else {
											System.out.println("\nERRO: Nao pode adicionar uma publicacao que nao seja da sua autoria!");
										}	
									}
								}
								
								else if (comando.equals("4")) { // mostrar as publicacoes candidatas 
									System.out.println("\n========================== PUBLICACOES CANDIDATAS ==========================\n");
									
									ArrayList<Publicacao> listaCandidatas = myScholar.pubCandidatas();
									
									if (listaCandidatas.size() == 0) {
										System.out.println("Nao existem publicacoes candidatas para adicionar a sua lista!\n");
									}
									else {
										System.out.println(printLista(listaCandidatas));
										
										while (!comando.equalsIgnoreCase("S") && !comando.equalsIgnoreCase("N")) {
											System.out.println("Quer adicionar alguma das publicacoes acima a sua lista? [S/N]");
											comando = scan.nextLine();
											if (!comando.equalsIgnoreCase("S") && !comando.equalsIgnoreCase("N")) {
												System.out.println("ERRO: Opcao invalida! Deve introduzir S ou N.\n");
											}
											else if (comando.equalsIgnoreCase("S")) {
												System.out.println("\nInsira as publicacoes que quer adicionar separadas por ;");
												String pedido = scan.nextLine();
												
												String[] opcoes = pedido.split(";"); // separa as publicacoes
												String numeros = "";
												boolean adicionada = false;
												
												for (int i = 0; i < opcoes.length; i++) {
													if (myScholar.isInt(opcoes[i])) { // verifica se e um inteiro
														int n = Integer.parseInt(opcoes[i]);
														if ((n-1) >= 0 && (n-1) <= listaCandidatas.size()) { // verifica se e uma das publicacoes apresentadas
															myScholar.associarPub(listaCandidatas.get(n-1));
															numeros = numeros + n + ", ";
															adicionada = true;
														}
													}
												}
												if (adicionada == true) {
													System.out.println("\nPublicacoes adicionadas: " + numeros.substring(0, numeros.length()-2) + ".\n");
												}
												else {
													System.out.println("\nFormato invalido! Nao foi adicionada nenhuma publicacao.\n");
												}
											}
										}
									}
								}
								
								else if (comando.equals("5")) {
									System.out.println("\n============================ REMOVER PUBLICACAO ============================\n");
									
									ArrayList<Publicacao> listaAutor = myScholar.ordenar("ano");	
									
									if (listaAutor.size() == 0) {
										System.out.println("Lista vazia! Nao tem publicacoes para remover.\n");
									}
									else {
										System.out.println(printLista(listaAutor));
										
										while (!comando.equalsIgnoreCase("S") && !comando.equalsIgnoreCase("N")) {
											System.out.println("Quer remover alguma das publicacoes da sua lista? [S/N]");
											comando = scan.nextLine();
											
											if (!comando.equalsIgnoreCase("S") && !comando.equalsIgnoreCase("N")) {
												System.out.println("ERRO: Opcao invalida! Deve introduzir S ou N.\n");
											}
											else if (comando.equalsIgnoreCase("S")) {
												System.out.println("\nInsira as publicacoes que quer remover separadas por ;");
												String pedido = scan.nextLine();
												
												String[] opcoes = pedido.split(";"); // separa as publicacoes
												String numeros = "";
												boolean removida = false;
												
												for (int i = 0; i < opcoes.length; i++) {
													if (myScholar.isInt(opcoes[i])) { // verifica se e um inteiro
														int n = Integer.parseInt(opcoes[i]);
														if ((n-1) >= 0 && (n-1) <= listaAutor.size()) {  // verifica se e uma das publicacoes apresentadas
															myScholar.removerPub(listaAutor.get(n-1));
															numeros = numeros + n + ", ";
															removida = true;
														}
													}
												}
												if (removida == true) {
													System.out.println("\nPublicacoes removidas: " + numeros.substring(0, numeros.length()-2) + ".\n");
												}
												else {
													System.out.println("\nFormato invalido! Nao foi adicionada nenhuma publicacao.\n");
												}
											}
										}
									}
								}

								else if (comando.equals("6")) { // mostrar estatisticas do autor
									System.out.println("\n=========================== AS SUAS ESTATISTICAS ===========================\n");
									String estatisticas = myScholar.estatisticas();
									System.out.println(estatisticas);
								}

								else if (comando.equals("7")) { // terminar sessao 
									System.out.println("\n----------------------------- Sessao terminada -----------------------------\n");
									sessaoIniciada = false;
								}
						
								else {
									System.out.println("Opcao invalida!");
								}

							}
						}
					}
					
					else if (comando.equals("3")) {
						System.out.println("\n----------------------------------- Saiu -----------------------------------");
						conectado = false;
						menu = false;
					}
					
					else {
						System.out.println("ERRO: Opcao invalida! Deve introduzir 1, 2 ou 3.");
					}
				}

			}
			
			catch (Exception e) 
			{					
				if (contador <= 10) { // 10 tentativas para estabelecer a ligacao
					System.out.println("ERRO! Nova tentativa dentro de 3 segundos! (" + contador + " tentativa)");
					contador++;
					try 
					{
						Thread.sleep(3000); // em caso de falha a aplicacao espera 3 segundos e tenta novamente
					}
					catch (InterruptedException e1) 
					{
						// nao faz nada
					}
				}
				else 
				{
					conectado = false; // se ao fim de 10 tentativas ainda não existir comunicação com o servidor, a aplicação cliente termina	
					System.out.println("\nConexao perdida!");
				}
			}
		}
		scan.close();
	}

	// PRINT DA LISTA DE PUBLICACOES
	private static String printLista(ArrayList<Publicacao> listaPublicacoes){
		String lista = "";
		
		if (listaPublicacoes.size() == 0) {
			System.out.println("Sem publicacoes!");
		}
		else {
			int i = 1;
			for (Publicacao publicacao : listaPublicacoes) {
				lista = lista + "" + i + ": " + publicacao.printPub() + "\n";
				i++;
			}
		}	
		return lista;
	}

}
		