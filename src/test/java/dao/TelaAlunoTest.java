package dao;

import model.dao.AlunoDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;
import static org.junit.Assert.*;
import model.beans.Aluno;
import model.dao.ConnectionFactory;

/**
 *
 * @author D-JTP,Otaciliob
 */
public class TelaAlunoTest {

    static Connection conexao;
    private static final String url = "jdbc:mysql://localhost:3306/teste";
    AlunoDao dao = new AlunoDao(url);
    
    //410670327 RG correto
    @Test
    public void testRGCorreto(){
        conexao = ConnectionFactory.getConnection(url);
        Aluno lei = new Aluno(
                410670327,
                "'weslem'",
                "e-mail@mail.com");
        if (dao.validarRG(410670327)) {
            dao.create(lei);
        }
        for (Aluno l : dao.readFor(410670327)) {
            assertEquals(l.getRg(), 410670327);
        }
        dao.delete(lei);
    }

    @Test
    public void testRGIncorreto(){
        conexao = ConnectionFactory.getConnection(url);
        if (dao.validarRG(410670320)) {
            dao.create(new Aluno(
                410670320,
                "'weslem'",
                "e-mail@mail.com")
            );
        }
        for (Aluno l : dao.readFor(410670320)) {
            assertNotEquals(l.getRg(), 410670320);
        }
    }

    @Test
    public void testEmailCorreto() throws SQLException{
        conexao = ConnectionFactory.getConnection(url);
        if (dao.emailCheck("e-mail@e-mail.com")) {
            dao.create(new Aluno(
                1,
                "'João'",
                "e-mail@e-mail.com")
            );
        }
        for (Aluno l : dao.readFor(1)) {
            assertEquals(l.getRg(), 1);
        }

        String sqldeletelei = "DELETE FROM leitor WHERE leitor_rg = 1";
        PreparedStatement ps = conexao.prepareStatement(sqldeletelei);
        ps.executeUpdate(sqldeletelei);
    }
    
    @Test
    public void testEmailIncorreto() throws SQLException{
        conexao = ConnectionFactory.getConnection(url);
        if (dao.emailCheck("e-mail2")) {
            dao.create(new Aluno(
                410670327,
                "'Maria'",
                "e-mail2")
            );
        }
        for (Aluno l : dao.readFor(1)) {
            assertNotEquals(l.getRg(), 2);
        }
    }
}
