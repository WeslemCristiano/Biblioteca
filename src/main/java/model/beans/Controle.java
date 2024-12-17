package model.beans;

import java.util.ArrayList;
import java.util.List;
public class Controle {
    //int[] codigos = new int[10];

    public boolean emprestar(String aluno, int[] codigos)
    {

        boolean retorno=true;
        /*Aqui voce deve instaciar um objeto aluno*/
        Aluno a = new Aluno(aluno);

        //Verifica se o aluno existe
        if (!a.verficaAluno())
        {
            System.out.println("modelo.Aluno Inexistente");
            retorno = false;
        }

        //Verifica se o aluno possui algum modelo.Debito
        if (!a.verificaDebito())
        {
            System.out.println("modelo.Aluno em modelo.Debito");
            retorno = false;
        }

        //Caso o aluno nao tenha dedbitos e exista
        if(retorno)
        {
            //Cria um conjunto de livros
            List<Livro> livros = new ArrayList<Livro>();

		  /*Para cada livro verifica  se e exemplar da biblioteca
                   e so deixar emprestar os livros que nao sao */
            for(int i=0; i< codigos.length;i++)
            {   Livro l = new Livro(codigos[i]);
                //caso o livro n�o seja exemplar da biblioteca permite emprestar
                if (!l.verificaLivro())
                    livros.add(l);
            }

            /*Chama o metodo delegado do aluno de emprestar cliente, passando o conjunto de livros como parametro caso tenha pelo menos um livro a ser emprestado*/
            if (livros.size() > 0 )
            {
                retorno = a.emprestar(livros);
                return retorno;
            }
            else
                return false;

        }
        else
            return retorno;
    }

    public boolean devolver(String name, int[] codigos) {
        Aluno aluno = new Aluno(name);

        if(!aluno.verficaAluno()){
            System.out.println("modelo.Aluno inexistente");
            return false;
        }

        // verifica se livro pertence ao aluno

        // receber os livros do banco de dados
        //aluno.devolver(livros);

        return true;
    }

}