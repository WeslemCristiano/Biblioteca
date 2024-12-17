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

import model.beans.Livros;

public class LivrosDao {

    Connection con = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;

    private static final String sqlinsert = "INSERT INTO livro(livro_nome, livro_autor, livro_ano, livro_unidades) VALUES(?,?,?,?)";
    private static final String sqlselect = "SELECT * FROM livro";
    private static final String sqlselectfrom = "SELECT * FROM livro WHERE livro_nome LIKE ?";
    private static final String sqlselectfrom2 = "SELECT * FROM livro WHERE livro_id = ?";
    private static final String sqlupdate = "UPDATE livro SET livro_nome = ?, livro_autor = ?, livro_ano = ?, livro_unidades = ? WHERE livro_id = ?";
    private static final String sqldelete = "DELETE FROM livro WHERE livro_id = ?";
    private static final String sqlemprestado = "UPDATE livro SET livro_unidades = livro_unidades - 1 WHERE livro_id = ?";
    private static final String sqldevolvido = "UPDATE livro SET livro_unidades = livro_unidades + 1 WHERE livro_id = ?";

    public LivrosDao(String teste) {
        con = ConnectionFactory.getConnection(teste);
    }

    public LivrosDao() {
        con = ConnectionFactory.getConnection();
    }

    public int consultaUnidades(int id) {
        try {
            stmt = con.prepareStatement("SELECT livro_unidades FROM livro WHERE livro_id = ?");
            stmt.setInt(1, id);

            rs = GenericDao.read(stmt, con);
            int test = 0;
            while (rs.next()) {
                test = rs.getInt("livro_unidades");
            }
            if (test > 0) {
                return test;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public boolean emprestimo(int id, int funcao) {
        try {
            if (funcao > 0) {
                stmt = con.prepareStatement(sqlemprestado);
                stmt.setInt(1, id);
            } else {
                stmt = con.prepareStatement(sqldevolvido);
                stmt.setInt(1, id);
            }
            GenericDao.update(stmt, con);
        } catch (SQLException ex) {
            Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public boolean insert(Livros liv) {
        if (liv.getAno() < 0 || liv.getUnidades() < 0) {
            return false;
        } else {
            try {
                stmt = con.prepareStatement(sqlinsert);
                stmt.setString(1, liv.getNome());
                stmt.setString(2, liv.getAutor());
                stmt.setInt(3, liv.getAno());
                stmt.setInt(4, liv.getUnidades());
                GenericDao.create(stmt, con);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
                return false;
            }
        }
    }

    public List<Livros> select() {
        List<Livros> book = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlselect);
            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Livros l = new Livros(
                        rs.getInt("livro_id"),
                        rs.getString("livro_nome"),
                        rs.getString("livro_autor"),
                        rs.getInt("livro_ano"),
                        rs.getInt("livro_unidades")
                );
                book.add(l);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }

    public List<Livros> selectFrom(String titulo) {
        List<Livros> book = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlselectfrom);
            stmt.setString(1, "%" + titulo + "%");

            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Livros l = new Livros(
                        rs.getInt("livro_id"),
                        rs.getString("livro_nome"),
                        rs.getString("livro_autor"),
                        rs.getInt("livro_ano"),
                        rs.getInt("livro_unidades")
                );
                book.add(l);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }
    
    public List<Livros> selectFrom(int id) {
        List<Livros> book = new ArrayList<>();

        try {
            stmt = con.prepareStatement(sqlselectfrom2);
            stmt.setInt(1, id);

            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Livros l = new Livros(
                        rs.getInt("livro_id"),
                        rs.getString("livro_nome"),
                        rs.getString("livro_autor"),
                        rs.getInt("livro_ano"),
                        rs.getInt("livro_unidades")
                );
                book.add(l);
            }

        } catch (SQLException ex) {
            Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return book;
    }

    public boolean update(Livros liv, int id) {
        if (liv != null) {
            if (liv.getAno() < 0 || liv.getUnidades() < 0) {
                return false;
            } else {
                try {
                    stmt = con.prepareStatement(sqlupdate);
                    stmt.setString(1, liv.getNome());
                    stmt.setString(2, liv.getAutor());
                    stmt.setInt(3, liv.getAno());
                    stmt.setInt(4, liv.getUnidades());
                    stmt.setInt(5, id);

                    GenericDao.update(stmt, con);
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }
        return false;
    }

    public boolean delete(Livros liv) {
        if (liv != null) {
            if (liv.getAno() < 0 || liv.getUnidades() < 0) {
                return false;
            } else {
                try {
                    stmt = con.prepareStatement(sqldelete);
                    stmt.setInt(1, liv.getId());
                    GenericDao.update(stmt, con);
                    return true;
                } catch (SQLException ex) {
                    Logger.getLogger(LivrosDao.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }
        return false;
    }
}