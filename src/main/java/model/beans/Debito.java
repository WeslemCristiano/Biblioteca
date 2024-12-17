package model.beans;

public class Debito {
    int codigoAluno;
    public Debito(int aluno){
        this.codigoAluno =aluno;
    }

    public boolean verificaDebito()
    {
        //codigo aleatorio para definir se o aluno tem debito
        //eh necessario fazer a verificao de forma persistente
        if(this.codigoAluno == 4)
            return false;
        else
            return true;
    }
}