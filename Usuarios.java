package br.com.uva;

import java.io.File;

public class Usuarios {
	String usuario;
	String senha;
	File db;
	int id = 1;
	String destinatario, data;
	double valor;

	Usuarios(String usuario, String senha) {
		this.usuario = usuario;
		this.senha = senha;
	}

	Usuarios() {

	}

	public void acrementaId(){
		this.id = id++;
	}

	public void setDestinatario(String nome) {
		this.destinatario = nome;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDestinatario() {
		return this.destinatario;
	}

	public String getData() {
		return this.data;
	}

	public double getValor() {
		return this.valor;
	}

	public String getNome() {
		return this.usuario;
	}

	public String getSenha() {
		return this.senha;
	}

	public void createFile() {
		String fl = this.getNome() + ".txt";
		try {
			this.db = new File(fl);
			if (db.createNewFile()) {
				System.out.println("Arquivo criado: " + db.getName());
			} else {
				System.out.println("Arquivo existente.");
			}
		} catch (Exception e) {
			System.out.println("Um erro ocorreu.");
			e.printStackTrace();
		}
	}

	public File getFile() {
		return this.db;
	}

}