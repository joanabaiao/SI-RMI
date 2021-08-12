package projeto4;

import java.io.Serializable;
import java.util.ArrayList;

public class Publicacao implements Serializable {
	
	private String titulo;
	private String revista;
	private String doi;
	private int ano;
	private int volume;
	private int numero;
	private int pagI;
	private int pagF;
	private int ncitacoes;
	private ArrayList<Autor> listaAutores;
	
	public Publicacao(String titulo, String revista, String doi, int ano, int volume, int numero, int pagI, int pagF, int ncitacoes, ArrayList<Autor> listaAutores) {
		this.titulo = titulo;
		this.revista = revista;
		this.doi = doi;
		this.ano = ano;
		this.volume = volume;
		this.numero = numero;
		this.pagI = pagI;
		this.pagF = pagF;
		this.ncitacoes = ncitacoes;
		this.listaAutores = listaAutores;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getRevista() {
		return revista;
	}

	public void setRevista(String revista) {
		this.revista = revista;
	}

	public String getDoi() {
		return doi;
	}

	public void setDoi(String doi) {
		this.doi = doi;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public int getPagI() {
		return pagI;
	}

	public void setPagI(int pagI) {
		this.pagI = pagI;
	}

	public int getPagF() {
		return pagF;
	}

	public void setPagF(int pagF) {
		this.pagF = pagF;
	}

	public int getNcitacoes() {
		return ncitacoes;
	}

	public void setNcitacoes(int ncitacoes) {
		this.ncitacoes = ncitacoes;
	}

	public ArrayList<Autor> getListaAutores() {
		return listaAutores;
	}

	public void setListaAutores(ArrayList<Autor> listaAutores) {
		this.listaAutores = listaAutores;
	}

	public String printPub() {
		String publicacao = "";
		for (Autor autor : listaAutores) {
			publicacao = publicacao + autor.getApelido() + ", " + autor.getNomeproprio() + "; ";
		}
		publicacao = publicacao + titulo + ", " + revista + ", " + volume + "(" + numero + "), " 
		+ pagI + "-" + pagF + ", " + ano + ", " + doi + " (Citado " + ncitacoes + " vezes).";
		
		return publicacao;
	}

}

