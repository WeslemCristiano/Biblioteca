package model.beans;

public class Livros extends Autor {

    private int id;
    private String nome;
    private String autor;
    private int ano;
    private int unidades;

    public Livros() {
    }

    public Livros(int id, String nome, String autor, int ano, int unidades) {
        this.id = id;
        this.nome = nome;
        this.autor = autor;
        this.ano = ano;
        this.unidades = unidades;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getAutor() {
        return autor;
    }
    
    public int getAno() {
        return ano;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setId(int id) {
        this.id = id;
    }
        
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }   

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }
}