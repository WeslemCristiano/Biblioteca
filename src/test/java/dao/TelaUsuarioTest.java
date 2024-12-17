package dao;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import model.beans.Usuario;
import model.dao.UsuarioDao;

/**
 *
 * @author D-JTP
 */
public class TelaUsuarioTest {

    private static final String url = "jdbc:mysql://localhost:3306/teste";
    UsuarioDao dao = new UsuarioDao(url);

    @Before
    public void setup() throws SQLException {
        String usuario = "A";
        String senha = "789!";
        Usuario usu = new Usuario(usuario, senha);
        dao.insert(usu);
        usuario = "B";
        senha = "123!";
        usu = new Usuario(usuario, senha);
        dao.insert(usu);
    }

    @Test
    public void testValidatePassword_WithSpecialChars() {
        String password = "MinhaSenha@123";
        Usuario usu = new Usuario();
        boolean result = usu.validatePassword(password);
        assertTrue(result);
    }

    @Test
    public void testValidatePassword_WithoutSpecialChars() {
        String password = "MinhaSenha123";
        Usuario usu = new Usuario();
        boolean result = usu.validatePassword(password);
        assertFalse(result);
    }

    @Test
    public void testUsuarioQuantidadeDeUsuariosCadastradosCorreta() {
        List<Usuario> user = dao.select();
        assertEquals(user.size(), 2);
        assertEquals(user.get(0).getUser(), "A");
    }

    @Test
    public void testUsuarioSelectFromCorreto() {
        List<Usuario> user = dao.selectFrom("A");

        assertEquals(user.get(0).getUser(), "A");
        assertEquals(user.get(0).getPassword(), "789!");
    }

    @Test
    public void testUsuarioSelectFromIncorreto() {
        List<Usuario> user = dao.selectFrom("B");

        assertNotEquals(user.get(0).getUser(), "A");
        assertNotEquals(user.get(0).getPassword(), "789!");
    }

    @Test
    public void testUsuarioDadosDoPrimeiroInsertCorretos() {
        List<Usuario> user = dao.select();
        System.out.println(user.get(0).getUser());
        assertEquals(user.get(0).getUser(), "A");
        assertEquals(user.get(0).getPassword(), "789!");
    }

    @Test
    public void testUsuarioDadosDoPrimeiroInsertIncorretos() {
        List<Usuario> user = dao.select();

        assertNotEquals(user.get(0).getUser(), "B");
        assertNotEquals(user.get(0).getPassword(), "123!");
    }

    @Test
    public void testUsuarioDadosDoSegundoInsertCorretos() {
        List<Usuario> user = dao.select();

        assertEquals(user.get(1).getUser(), "B");
        assertEquals(user.get(1).getPassword(), "123!");
    }

    @Test
    public void testLivrosDadosDoSegundoInsertIncorretos() {
        List<Usuario> user = dao.select();

        assertNotEquals(user.get(1).getUser(), "A");
        assertNotEquals(user.get(1).getPassword(), "789!");
    }

    @Test
    public void testUsuarioDeleteCorreto() throws SQLException {
        String usuario = "C";
        String senha = "789!";
        Usuario usu = new Usuario(usuario, senha);
        dao.insert(usu);
        List<Usuario> user = dao.select();

        assertEquals(user.get(2).getUser(), "C");

        String usuario2 = "C";
        String senha2 = "789!";
        Usuario usu2 = new Usuario(usuario2, senha2);
        dao.delete(usu2);
        user = dao.selectFrom("C");
        assertEquals(user.size(), 0);
    }

    @Test
    public void testUsuarioDeleteIncorreto() throws SQLException {
        String usuario = "C";
        String senha = "789!";
        Usuario usu = new Usuario(usuario, senha);
        dao.insert(usu);
        List<Usuario> user = dao.select();

        assertEquals(user.get(2).getUser(), "C");

        String usuario2 = "C";
        String senha2 = "789!";
        Usuario usu2 = new Usuario(usuario2, senha2);
        dao.delete(usu2);
        user = dao.selectFrom("C");
        assertNotEquals(user.size(), 1);
    }

    @After
    public void after() throws SQLException {
        String usuario2 = "A";
        String senha2 = "789!";
        Usuario usu2 = new Usuario(usuario2, senha2);
        dao.delete(usu2);
        usuario2 = "B";
        senha2 = "123!";
        usu2 = new Usuario(usuario2, senha2);
        dao.delete(usu2);
    }
}
