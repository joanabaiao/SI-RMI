package projeto4;

import java.io.Serializable;
import java.util.ArrayList;

public class Autor implements Serializable {
	
	private String email;
	private String password;
	private String nomeproprio;
	private String apelido;
	private String afiliacao;
	private ArrayList<Publicacao> minhasPub;  
	
	public Autor(String nomeproprio, String apelido, String email, String password, String afiliacao) 
	{
		this.nomeproprio = nomeproprio;
		this.apelido = apelido;
		this.email = email;
		this.password = password;
		this.afiliacao = afiliacao;
		this.minhasPub = new ArrayList<Publicacao>();
	}
	
	public Autor(String nomeproprio, String apelido) 
	{
		this.nomeproprio = nomeproprio;
		this.apelido = apelido;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNomeproprio() {
		return nomeproprio;
	}

	public void setNomeproprio(String nomeproprio) {
		this.nomeproprio = nomeproprio;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getAfiliacao() {
		return afiliacao;
	}

	public void setAfiliacao(String afiliacao) {
		this.afiliacao = afiliacao;
	}

	public ArrayList<Publicacao> getMinhasPub() {
		return minhasPub;
	}

	public void setMinhasPub(ArrayList<Publicacao> minhasPub) {
		this.minhasPub = minhasPub;
	}	
	
}
