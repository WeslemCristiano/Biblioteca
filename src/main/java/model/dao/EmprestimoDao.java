/*
h * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import model.beans.Emprestimo;
import control.Validator;

/**
 *
 * @author Computador
 */
public class EmprestimoDao {

    /* private static final String restricao1 = "SELECT count(leitor_rg) FROM emprestimo WHERE leitor_rg = ?";
    String para ser usa no stmt da ISSUE #7, count nao pode ser maior igual a 3
     */
    private Connection con = null;
    
    public EmprestimoDao(String teste) {
        con = ConnectionFactory.getConnection(teste);
    }

    public EmprestimoDao() {
        con = ConnectionFactory.getConnection();
    }
    public boolean create(Emprestimo l) {
        PreparedStatement stmt = null;

        if (restricao1(l.getLeitor_ra())) {
            try {
                stmt = con.prepareStatement("INSERT INTO emprestimo(leitor_rg,livro_id,data_devolucao) VALUES(?,?,?) ");
                stmt.setInt(1, l.getLeitor_ra());
                stmt.setInt(2, l.getLivro_id());
                stmt.setDate(3, l.getData_devolucao());
                GenericDao.create(stmt, con);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
            return false;
        }
    }

    public List<Emprestimo> read() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Emprestimo> emprestado = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM emprestimo");
            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Emprestimo l = new Emprestimo();
                l.setLeitor_ra(rs.getInt("leitor_rg"));
                l.setLivro_id(rs.getInt("livro_id"));
                l.setData_devolucao(rs.getDate("data_devolucao"));
                emprestado.add(l);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
        }
        return emprestado;
    }

    public List<Emprestimo> readFor(int rg) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Emprestimo> emprestado = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM emprestimo WHERE leitor_rg LIKE ?");
            stmt.setInt(1, rg);

            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                Emprestimo l = new Emprestimo();
                l.setLeitor_ra(rs.getInt("leitor_rg"));
                l.setLivro_id(rs.getInt("livro_id"));
                l.setData_devolucao(rs.getDate("data_devolucao"));
                emprestado.add(l);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Erro ao salvar " + ex);
        }
        return emprestado;
    }

    public boolean update(Emprestimo l, int rg, int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "UPDATE emprestimo SET data_devolucao = ? WHERE leitor_rg = ? and livro_id = ?"
            );
            stmt.setDate(1, l.getData_devolucao());
            stmt.setInt(2, rg);
            stmt.setInt(3, id);

            GenericDao.update(stmt, con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean delete(Emprestimo l) {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.prepareStatement(
                    "DELETE FROM emprestimo WHERE livro_id = ? AND leitor_rg = ?"
            );
            stmt.setInt(1, l.getLivro_id());
            stmt.setInt(2, l.getLeitor_ra());
            GenericDao.update(stmt, con);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean restricao1(int leitor_rg) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean pode = false;
        try {
            stmt = con.prepareStatement("SELECT count(leitor_rg) AS qtd FROM emprestimo WHERE leitor_rg = ?");
            stmt.setInt(1, leitor_rg);
            rs = GenericDao.read(stmt, con);
            while (rs.next()) {
                if (rs.getInt("qtd") < 3) {
                    pode = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoDao.class.getName()).log(Level.SEVERE, null, ex);
            pode = false;
        }
        return pode;
    }
    
    /* recebe um RG via paramentro
        procura por todos os emprestimos que possuem este RG
        verifica se a data de um dos emprestimos registrados esta atrasada
    */
    public boolean restricao2(int rg){
        //Impedir que leitores com empréstimos atrasados façam novos empréstimos
        boolean pode = true;
        for (Emprestimo emp : readFor(rg)) {
            if(!Validator.validateDate(emp.getData_devolucao().toLocalDate())){
                pode = false;
            }
        }
        return pode;
    }

    public boolean validarData(String data){
        return Validator.validateDate(data);
    }
    
}
