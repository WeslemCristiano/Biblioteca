/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import control.Validator;

/**
 *
 * @author D-JTP
 */
public class LoginDao {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    private static final String sqllogin = "SELECT * FROM login WHERE user=? AND password=?";

    public LoginDao() {
        conexao = ConnectionFactory.getConnection();
    }
    
    public LoginDao(String teste) {
        conexao = ConnectionFactory.getConnection(teste);
    }

    public boolean login(String user, String pass) {
        try {
            pst = conexao.prepareStatement(sqllogin);
            pst.setString(1, user);
            pst.setString(2, Validator.encrypt(pass));
            rs = pst.executeQuery();
            if (rs.next()) {
                conexao.close();
                return (true);
            }
        } catch (SQLException erro) {
            System.out.println(erro);
        }
        return false;
    }

    public boolean conexao() {
        return conexao != null;
    }
}
