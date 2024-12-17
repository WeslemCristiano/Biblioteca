package dao;

import model.dao.LivrosDao;
import model.dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import model.beans.Livros;

/**
 *
 * @author D-JTP
 */
public class TelaLivroTest {

    static Connection conexao;
    private static final String url = "jdbc:mysql://localhost:3306/teste";
    LivrosDao dao = new LivrosDao(url);

    @Before
    public void setup() throws SQLException {
        conexao = ConnectionFactory.getConnection(url);
        String sqllivroinsert = "INSERT INTO livro(livro_id, livro_nome, livro_autor, livro_ano, livro_unidades) VALUES(1, 'Java: Como Programar', 'Paul Deitel e Harvey Deitel', 2016, 36)";
        String sqllivroinsert02 = "INSERT INTO livro(livro_id, livro_nome, livro_autor, livro_ano, livro_unidades) VALUES(2, 'Código limpo: Habilidades práticas do Agile Software', 'Robert C. Martin', 2009, 21)";
        PreparedStatement ps = conexao.prepareStatement(sqllivroinsert);
        ps.executeUpdate(sqllivroinsert);
        ps.executeUpdate(sqllivroinsert02);
    }

    @Test
    public void testLivrosQuantidadeDeLivrosCadastradosCorreta() {
        List<Livros> book = dao.select();
        assertEquals(book.size(), 2);
        assertEquals(book.get(0).getId(), 1);
    }

    @Test
    public void testLivrosSelectFromCorreto() {
        List<Livros> book = dao.selectFrom("Java");

        assertEquals(book.get(0).getId(), 1);
        assertEquals(book.get(0).getNome(), "Java: Como Programar");
        assertEquals(book.get(0).getAutor(), "Paul Deitel e Harvey Deitel");
        assertEquals(book.get(0).getAno(), 2016);
        assertEquals(book.get(0).getUnidades(), 36);
    }

    @Test
    public void testLivrosSelectFromIncorreto() {
        List<Livros> book = dao.selectFrom("Código");

        assertNotEquals(book.get(0).getId(), 1);
        assertNotEquals(book.get(0).getNome(), "Java: Como Programar");
        assertNotEquals(book.get(0).getAutor(), "Paul Deitel e Harvey Deitel");
        assertNotEquals(book.get(0).getAno(), 2016);
        assertNotEquals(book.get(0).getUnidades(), 36);
    }

    @Test
    public void testLivrosDadosDoPrimeiroInsertCorretos() {
        List<Livros> book = dao.select();

        assertEquals(book.get(0).getId(), 1);
        assertEquals(book.get(0).getNome(), "Java: Como Programar");
        assertEquals(book.get(0).getAutor(), "Paul Deitel e Harvey Deitel");
        assertEquals(book.get(0).getAno(), 2016);
        assertEquals(book.get(0).getUnidades(), 36);
    }

    @Test
    public void testLivrosDadosDoPrimeiroInsertIncorretos() {
        List<Livros> book = dao.select();

        assertNotEquals(book.get(0).getId(), 2);
        assertNotEquals(book.get(0).getNome(), "Código limpo: Habilidades práticas do Agile Software");
        assertNotEquals(book.get(0).getAutor(), "Robert C. Martin");
        assertNotEquals(book.get(0).getAno(), 2009);
        assertNotEquals(book.get(0).getUnidades(), 21);
    }

    @Test
    public void testLivrosDadosDoSegundoInsertCorretos() {
        List<Livros> book = dao.select();

        assertEquals(book.get(1).getId(), 2);
        assertEquals(book.get(1).getNome(), "Código limpo: Habilidades práticas do Agile Software");
        assertEquals(book.get(1).getAutor(), "Robert C. Martin");
        assertEquals(book.get(1).getAno(), 2009);
        assertEquals(book.get(1).getUnidades(), 21);
    }

    @Test
    public void testLivrosDadosDoSegundoInsertIncorretos() {
        List<Livros> book = dao.select();

        assertNotEquals(book.get(1).getId(), 1);
        assertNotEquals(book.get(1).getNome(), "Java: Como Programar");
        assertNotEquals(book.get(1).getAutor(), "Paul Deitel e Harvey Deitel");
        assertNotEquals(book.get(1).getAno(), 2016);
        assertNotEquals(book.get(1).getUnidades(), 36);
    }
    
    @Test
    public void testLivrosDeleteCorreto() throws SQLException{
        String sqlinsert = "INSERT INTO livro(livro_id, livro_nome, livro_autor, livro_ano, livro_unidades) VALUES(3, 'Nome', 'Autor', 0, 0)";
        PreparedStatement ps = conexao.prepareStatement(sqlinsert);
        ps.executeUpdate(sqlinsert);
        List<Livros> book = dao.select();

        assertEquals(book.get(2).getId(), 3);

        String sqldelete = "DELETE FROM livro WHERE livro_id = 3";
        ps.executeUpdate(sqldelete);
        book = dao.selectFrom("Nome");
        assertEquals(book.size(), 0);
    }
    
    @Test
    public void testLivrosDeleteIncorreto() throws SQLException{
        String sqlinsert = "INSERT INTO livro(livro_id, livro_nome, livro_autor, livro_ano, livro_unidades) VALUES(3, 'Nome', 'Autor', 0, 0)";
        PreparedStatement ps = conexao.prepareStatement(sqlinsert);
        ps.executeUpdate(sqlinsert);
        List<Livros> book = dao.select();

        assertEquals(book.get(2).getId(), 3);

        String sqldelete = "DELETE FROM livro WHERE livro_id = 3";
        ps.executeUpdate(sqldelete);
        book = dao.selectFrom("Nome");
        assertNotEquals(book.size(), 1);
    }
    
    @Test
    public void testLivrosAnoInvalido() throws SQLException{
        try {
        String sqlinsert = "INSERT INTO livro(livro_id, livro_nome, livro_autor, livro_ano, livro_unidades) VALUES(3, 'Nome', 'Autor', -1, 0)";
        PreparedStatement ps = conexao.prepareStatement(sqlinsert);
        ps.executeUpdate(sqlinsert);
        List<Livros> book = dao.select();

        assertEquals(book.size(), 0);
        }catch(java.sql.DataTruncation e) {
            System.out.println(e);
        }
    }
    
    @Test
    public void testLivrosUnidadesInvalidas() throws SQLException{
        try {
        String sqlinsert = "INSERT INTO livro(livro_id, livro_nome, livro_autor, livro_ano, livro_unidades) VALUES(3, 'Nome', 'Autor', 0, -1)";
        PreparedStatement ps = conexao.prepareStatement(sqlinsert);
        ps.executeUpdate(sqlinsert);
        List<Livros> book = dao.select();

        assertEquals(book.size(), 0);
        }catch(java.sql.DataTruncation e) {
            System.out.println(e);
        }
    }

    @After
    public void after() throws SQLException {
        String sqllivrodelete = "DELETE FROM livro WHERE livro_id = 1";
        String sqllivrodelete02 = "DELETE FROM livro WHERE livro_id = 2";
        PreparedStatement ps = conexao.prepareStatement(sqllivrodelete);
        ps.executeUpdate(sqllivrodelete);
        ps.executeUpdate(sqllivrodelete02);
    }
}
