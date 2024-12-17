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
import model.beans.Aluno;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import control.Validator;

public class AlunoDao extends GenericDao {

    private Connection con = null;

    public AlunoDao(String teste) {
        con = ConnectionFactory.getConnection(teste);
    }

    public AlunoDao() {
        con = ConnectionFactory.getConnection();
    }

    public boolean emailCheck(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static boolean validarRG(int rg) {
        return Validator.validateRG(String.valueOf(rg));
    }

    public static boolean validarRG(String rg) {
        return Validator.validateRG(rg);
    }

    public void create(Aluno l) {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO Leitor(leitor_rg,leitor_nome,leitor_email) VALUES(?,?,?) ");
            stmt.setInt(1, l.getRg());
            stmt.setString(2, l.getNome());
            stmt.setString(3, l.getEmail());
            GenericDao.create(stmt, con);

        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
        }

    }

    public List<Aluno> read() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> leitores = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM leitor");
            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Aluno l = new Aluno();
                l.setRg(rs.getInt("leitor_rg"));
                l.setNome(rs.getString("leitor_nome"));
                l.setEmail(rs.getString("leitor_email"));
                leitores.add(l);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
        }
        return leitores;
    }

    public List<Aluno> readFor(String nome) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> leitores = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM leitor WHERE leitor_nome LIKE '%?%'");
            stmt.setString(1, nome);

            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Aluno l = new Aluno();
                l.setRg(rs.getInt("leitor_rg"));
                l.setNome(rs.getString("leitor_nome"));
                l.setEmail(rs.getString("leitor_email"));
                leitores.add(l);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
        }
        return leitores;
    }

    public List<Aluno> readFor(int rg) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Aluno> leitores = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM leitor WHERE leitor_rg = ?");
            stmt.setInt(1, rg);

            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Aluno l = new Aluno();
                l.setRg(rs.getInt("leitor_rg"));
                l.setNome(rs.getString("leitor_nome"));
                l.setEmail(rs.getString("leitor_email"));
                leitores.add(l);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
        }
        return leitores;
    }

    public void update(Aluno l, int rg) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE leitor SET leitor_rg = ?,leitor_nome = ?,leitor_email = ? WHERE leitor_rg = ?"
            );
            stmt.setInt(1, l.getRg());
            stmt.setString(2, l.getNome());
            stmt.setString(3, l.getEmail());
            stmt.setInt(4, rg);

            GenericDao.update(stmt, con);
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
        }

    }

    public void delete(Aluno l) {
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement(
                    "DELETE FROM leitor WHERE leitor_rg = ?"
            );
            stmt.setInt(1, l.getRg());
            GenericDao.update(stmt, con);
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
        } catch (SQLException ex) {
            Logger.getLogger(AlunoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao excluir " + ex);
        }

    }

}
