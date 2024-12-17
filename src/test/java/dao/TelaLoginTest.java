package dao;

import model.dao.LoginDao;
import model.dao.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TelaLoginTest {

    static Connection conexao;
    private final static String user = "login";
    private final static String pass = "senha";
    private static final String url ="jdbc:mysql://localhost:3306/teste";
    LoginDao dao = new LoginDao(url);
    
    @Before
    public void setup() throws SQLException {
        conexao = ConnectionFactory.getConnection(url);
        String sqllogininsert = "INSERT INTO login VALUES ('login','senha')";
        PreparedStatement ps = conexao.prepareStatement(sqllogininsert);
        ps.executeUpdate(sqllogininsert);
    }

    @Test
    public void testLoginCorreto() {
        assertEquals(true, dao.login(user, pass));
    }

    @Test
    public void testLoginUsuarioESenhaIncorretos() {
        assertEquals(false, dao.login("no", "no"));
    }

    @Test
    public void testLoginUsuarioVazioSenhaCorreta() {
        assertEquals(false, dao.login("", pass));
    }

    @Test
    public void testLoginUsuarioCorretoSenhaVazia() {
        assertEquals(false, dao.login(user, ""));
    }

    @Test
    public void testLoginCorreto02() {
        assertEquals(true, dao.login("login", "senha"));
    }

    @Test
    public void testLoginUsuarioIncorretoESenhaCorreta() {
        assertEquals(false, dao.login("user", "senha"));
    }

    @Test
    public void testLoginCorretoESenhaIncorreta() {
        assertEquals(false, dao.login("login", "123456"));
    }

    @Test
    public void testLoginComNovoCadastroDeNovoUsuarioESenhaCorreto() throws SQLException {
        String sqllogininsert = "INSERT INTO login VALUES ('teste','123')";
        PreparedStatement ps = conexao.prepareStatement(sqllogininsert);
        ps.executeUpdate(sqllogininsert);

        assertEquals(true, dao.login("teste", "123"));

        String sqllogindelete = "DELETE FROM login WHERE user = 'teste'";
        ps = conexao.prepareStatement(sqllogindelete);
        ps.executeUpdate(sqllogindelete);
    }

    @Test
    public void testLoginComUsuarioCorretoESenhaVazia() {
        assertEquals(false, dao.login("login", ""));
    }

    @Test
    public void testLoginComNovoCadastroDeNovoUsuarioESenhaIncorreto() throws SQLException {
        String sqllogininsert = "INSERT INTO login VALUES ('teste','123')";
        PreparedStatement ps = conexao.prepareStatement(sqllogininsert);
        ps.executeUpdate(sqllogininsert);

        assertEquals(false, dao.login("false", "123"));

        String sqllogindelete = "DELETE FROM login WHERE user = 'teste'";
        ps = conexao.prepareStatement(sqllogindelete);
        ps.executeUpdate(sqllogindelete);
    }

    @After
    public void after() throws SQLException {
        String sqllogindelete = "DELETE FROM login WHERE user = 'login'";
        PreparedStatement ps = conexao.prepareStatement(sqllogindelete);
        ps.executeUpdate(sqllogindelete);
    }
}
