package projeto4;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Scholar extends UnicastRemoteObject implements IScholar {
	
	private ArrayList<Autor> autoresRegistados = new ArrayList<Autor>();
	private ArrayList<Publicacao> publicacoesRegistadas = new ArrayList<Publicacao>();
	private Autor autorOnline;
	
	// IMPLEMETACAO E LEITURA DA BASE DE DADOS (CASO JA EXISTA)
	public Scholar() throws RemoteException {
		try 
		{
			FileInputStream fis1 = new FileInputStream("AutoresRegistados.txt");
			ObjectInputStream ois1 = new ObjectInputStream(fis1);
			this.autoresRegistados = (ArrayList<Autor>) ois1.readObject();
			ois1.close();
			fis1.close();
			
			FileInputStream fis2 = new FileInputStream("PublicacoesRegistadas.txt");
			ObjectInputStream ois2 = new ObjectInputStream(fis2);
			this.publicacoesRegistadas = (ArrayList<Publicacao>) ois2.readObject();
			ois2.close();
			fis2.close();
			
			System.out.println("\nListas carregadas com sucesso!");
		}
		catch (Exception e) {
			System.out.println("\nFalha a carregar listas!");
		}	
	}
	
	// GUARDAR LISTA DOS AUTORES REGISTADOS NO DISCO
	public void guardarAutores() {
		try {
			FileOutputStream fos = new FileOutputStream("AutoresRegistados.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.autoresRegistados);
			fos.close();
			oos.close();
			System.out.println("\nLista dos autores guardada com sucesso!");
		} 
		catch (Exception e) {
			System.out.println("\nFalha a guardar lista dos autores.");
		}
	}
	
	// GUARDAR LISTA DAS PUBLICACOES DO SISTEMA NO DISCO
	public void guardarPublicacoes() {
		try {
			FileOutputStream fos = new FileOutputStream("PublicacoesRegistadas.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.publicacoesRegistadas);
			fos.close();
			oos.close();
			System.out.println("\nLista das publicacoes guardada com sucesso!");

		} 
		catch (Exception e) {
			System.out.println("\nFalha a guardar lista das publicacoes.");
		}
	}
	
	// VALIDAR NUMERO INTEIRO E POSITIVO
	public boolean isInt(String pedido) throws RemoteException {
		boolean valido = true;
		try {
			int n = Integer.parseInt(pedido);
			if (n < 0) {
				valido = false;
			}
		} 
		catch (NumberFormatException e) {
			valido = false;
		}
		return valido;
	}
	
	// RESGISTAR NOVO AUTOR
	public boolean registarAutor(Autor novoautor) throws RemoteException {
		boolean valido = true;
		if (autoresRegistados.size() > 0) { 
			for (Autor autor: autoresRegistados) {
				if (autor.getEmail().equals(novoautor.getEmail())) { // verificar se o autor ja esta registado
					valido = false; 
				}
			}
		}	
		if (valido == true) { // registar o autor na lista e guardar na memoria
			this.autoresRegistados.add(novoautor);
			System.out.println("\nRegisto realizado!");
			guardarAutores();
		}	
		else { 
			System.out.println("\nRegisto invalido!"); 
		}
		return valido;
	}
	
	// VALIDAR LOGIN
	public String verificarLogin(String email, String password) throws RemoteException {
		String valido = "false1"; // email nao registado
		
		for (Autor autor: autoresRegistados) {
			if (autor.getEmail().equals(email)) {
				valido = "false2"; // email registado e password errada		
				if (autor.getPassword().equals(password)) {
					valido = "true"; // dados validos
				}
			}
		}
		if (valido.equals("true")) {
			System.out.println("\nSessao iniciada!");
		}
		else {
			System.out.println("\nDados invalidos!");
		}	
		return valido;
	}
	
	// NOME DO AUTOR 
	public String getAutor(String email) throws RemoteException {
		String nome = "";
		for (Autor autor: autoresRegistados) {
			if (autor.getEmail().equals(email)) {
				autorOnline = autor;
			}
		}
		nome = autorOnline.getNomeproprio() + " " + autorOnline.getApelido(); 
		return nome; // devolve o nome proprio e o apelido do autor online
	}

	// ORDENAR LISTA DE PUBLICACOES DO AUTOR
	public ArrayList<Publicacao> ordenar(String ordem) throws RemoteException 
	{	
		Collections.sort(autorOnline.getMinhasPub(), new Comparator<Publicacao>() 
		{		
			public int compare(Publicacao p1, Publicacao p2) {
				int m = 0;
				if (ordem.equals("ano")) {
					m = Integer.valueOf(p2.getAno()).compareTo(p1.getAno()); // ordenar por ano
				}
				else if (ordem.equals("citacao")) {
					m = Integer.valueOf(p2.getNcitacoes()).compareTo(p1.getNcitacoes()); // ordenar por numero de citacoes
				}
				return m;
			}
		});
		
		return autorOnline.getMinhasPub(); // devolve a lista ordenada
	}
	
	// VALIDAR DOI
	public boolean validarDOI (String doi) throws RemoteException 
	{
		boolean valido = true;
		for (Publicacao publicacao: publicacoesRegistadas) { // verifica se a publicacao ja existe no sistema 
			if (publicacao.getDoi().equals(doi)) {
				valido = false;	
			}	
		}
		return valido;
	}
	
	// VALIDAR LISTA DE AUTORES
	public boolean validarAutores (ArrayList<Autor> listaAutores) throws RemoteException 
	{
		boolean valido = false;
		for (Autor autor: listaAutores) {
			if (autor.getNomeproprio().equals(autorOnline.getNomeproprio()) && autor.getApelido().equals(autorOnline.getApelido())) {
				valido = true;	// Verificar se o autor online esta incluido na lista de autores da publicacao
			}	
		}
		return valido;
	}

	// ADICIONAR PUBLICACAO
	public void adicionarPub(Publicacao publicacao) throws RemoteException
	{
		publicacoesRegistadas.add(publicacao); // adiciona a publicacao ao sistema
		guardarPublicacoes(); // guarda na memoria
		associarPub(publicacao); // a publicacao fica automaticamente registada na lista do autor online
	}
	
	// ASSOCIAR PUBLICACAO
	public void associarPub(Publicacao publicacao) throws RemoteException
	{
		for (Autor autorRegistado: autoresRegistados) {
			if (autorOnline.getEmail().equals(autorRegistado.getEmail())) {
				autorRegistado.getMinhasPub().add(publicacao); // adicionar publicacao a lista do autor online
				autorOnline = autorRegistado;
			}
		}
		guardarAutores(); // guardar na memoria
	}
		
	// PUBLICACOES CANDIDATAS
	public ArrayList<Publicacao> pubCandidatas() throws RemoteException
	{
		ArrayList<Publicacao> publicacoesCandidatas = new ArrayList<Publicacao>();
		
		for (Publicacao pubSistema : publicacoesRegistadas) {
			for (Autor autor : pubSistema.getListaAutores()) {
				if (autor.getNomeproprio().equals(autorOnline.getNomeproprio()) && autor.getApelido().equals(autorOnline.getApelido())) {
					boolean repetida = false;
					for (Publicacao pubAutor : autorOnline.getMinhasPub()) {
						if (pubSistema.getDoi().equals(pubAutor.getDoi()))  {
							repetida = true; 
						}
					}
					if (repetida == false) {
						publicacoesCandidatas.add(pubSistema); 
						// percorre as publicacoes do sistema e adiciona aquelas que tem o nome do autor online que nao estao na lista dele		
					}
				}
			}
		}
		return publicacoesCandidatas;
	}

	// REMOVER PUBLICACAO
	public void removerPub(Publicacao publicacao) throws RemoteException {
		
		for (Autor autorRegistado: autoresRegistados) {
			if (autorOnline.getEmail().equals(autorRegistado.getEmail())) {
				for (int i = 0; i < autorRegistado.getMinhasPub().size(); i++) {
					if (autorRegistado.getMinhasPub().get(i).getDoi().equals(publicacao.getDoi())) 
					{
						autorRegistado.getMinhasPub().remove(i); 
						autorOnline = autorRegistado;
						guardarAutores(); // percorre a lista de publicacoes do autor, remove a publicacao escolhida e guarda a lista
					}
				}
			}
		}
		if (publicacao.getListaAutores().size() == 1) { // se a publicacao pertencer apenas ao autor tambem e removida do sistema
			for (int i = 0; i < publicacoesRegistadas.size(); i++) {
				if (publicacoesRegistadas.get(i).getDoi().equals(publicacao.getDoi())) 
				{
					publicacoesRegistadas.remove(i);
				}
			}
		}
	}	

	// ESTATISTICAS DO AUTOR
	public String estatisticas() throws RemoteException {		
		String estatisticas = "";
		int total = 0;
		int i10 = 0;
		int H = 0;
		
		for (Publicacao p: autorOnline.getMinhasPub()) {
			total = total + p.getNcitacoes(); // total de citacoes de todos os artigos do autor
			if (p.getNcitacoes() >= 10) {
				i10 = i10 + 1; // numero de artigos com mais de 10 citacoes
			}
		}	
		ordenar("citacao");	// comecamos por ordenar a lista de publicacoes do autor pelo numero de citacoes
		for (int i = 0; i < autorOnline.getMinhasPub().size(); i++) {
			if (autorOnline.getMinhasPub().get(i).getNcitacoes() >= i+1) {
				H = i+1; // O indice H e do numero de artigos com citacoes maiores ou iguais ao numero do indice na lista ordenada 
			}
		}	
		estatisticas = "Total de citacoes = " + total + "\nIndice H = " + H + "\nIndice i10 = " + i10 + "\n";
		return estatisticas;
	}

}
