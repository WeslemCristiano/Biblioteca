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
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Computador
 */
public class GenericDao {
    public static void create(PreparedStatement stmt, Connection con) throws SQLException {
        stmt.executeUpdate();

    }

    public static ResultSet read(PreparedStatement stmt, Connection con) throws SQLException {

        ResultSet rs = null;
        return rs = stmt.executeQuery();
    }

    public static ResultSet readFor(PreparedStatement stmt, Connection con) {

        ResultSet rs = null;

        try {
            rs = stmt.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }

    public static void update(PreparedStatement stmt, Connection con) {

        try {
            stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void delete(PreparedStatement stmt, Connection con) {

        try {
            stmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
