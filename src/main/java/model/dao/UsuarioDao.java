package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import model.beans.Usuario;
import control.Validator;

public class UsuarioDao {

    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    private static final String sqlinsert = "INSERT INTO login(user, password) VALUES(?,?)";
    private static final String sqlselect = "SELECT * FROM login";
    private static final String sqlselectfrom = "SELECT * FROM login WHERE user LIKE ?";
    private static final String sqlupdate = "UPDATE login SET password = ? WHERE user = ?";
    private static final String sqldelete = "DELETE FROM login WHERE user = ?";

    public UsuarioDao(String teste) {
        con = ConnectionFactory.getConnection(teste);
    }

    public UsuarioDao() {
        con = ConnectionFactory.getConnection();
    }

    public boolean insert(Usuario usu) {
        if (usu.validatePassword(usu.getPassword())) {
            try {
                stmt = con.prepareStatement(sqlinsert);
                stmt.setString(1, usu.getUser());
                stmt.setString(2, Validator.encrypt(usu.getPassword()));
                GenericDao.create(stmt, con);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Senha muito fraca ");
            return false;
        }

    }

    public List<Usuario> select() {
        List<Usuario> users = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlselect);
            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("user"),
                        Validator.decrypt(rs.getString("password"))
                );
                users.add(u);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public List<Usuario> selectFrom(String usuario) {
        List<Usuario> users = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlselectfrom);
            stmt.setString(1, "%" + usuario + "%");

            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getString("user"),
                        Validator.decrypt(rs.getString("password"))
                );
                users.add(u);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }

    public boolean update(Usuario usu) {
        if (usu != null) {
            try {
                stmt = con.prepareStatement(sqlupdate);
                stmt.setString(1, Validator.encrypt(usu.getPassword()));
                stmt.setString(2, usu.getUser());

                GenericDao.update(stmt, con);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        return false;
    }

    public boolean delete(Usuario usu) {
        if (usu != null) {
            try {
                stmt = con.prepareStatement(sqldelete);
                stmt.setString(1, usu.getUser());
                GenericDao.update(stmt, con);
                return true;

            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class
                        .getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

        return false;
    }
}
