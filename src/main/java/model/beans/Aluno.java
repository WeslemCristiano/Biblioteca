package model.beans;

public class Aluno  {

	private String nome;
	private String email;
	private String livro_autor;
	private int ra;

	public Aluno() {
	}

	public Aluno(int ra, String nome, String email) {
		this.ra = ra;
		this.nome = nome;
		this.email = email;
	}

	public int getRg() {
		return ra;
	}

	public String getNome() {
		return nome;
	}
	
	public String getLivro_autor() {
		return livro_autor;
	}

	public String getEmail() {
		return email;
	}

	public void setRg(int ra) {
		this.ra = ra;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setLivro_autor(String livro_autor) {
		this.livro_autor = livro_autor;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
