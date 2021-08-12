package projeto4;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IScholar extends Remote {
	
	public boolean isInt(String pedido) throws RemoteException;
	
	public boolean validarDOI (String doi) throws RemoteException;
	
	public boolean registarAutor(Autor autor) throws RemoteException;
	
	public String verificarLogin(String email, String password) throws RemoteException;
	
	public String getAutor(String email) throws RemoteException;
	
	public ArrayList<Publicacao> ordenar(String ordem) throws RemoteException;
	
	public void adicionarPub (Publicacao publicacao) throws RemoteException;
	
	public boolean validarAutores (ArrayList<Autor> listaAutores) throws RemoteException;
	
	public void associarPub (Publicacao publicacao) throws RemoteException;
	
	public ArrayList<Publicacao> pubCandidatas() throws RemoteException;

	public void removerPub (Publicacao publicacao) throws RemoteException;
	
	public String estatisticas() throws RemoteException;
	
}
