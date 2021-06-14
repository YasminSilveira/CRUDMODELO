/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ulbra.model;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author s.lucas
 */
public class ModeloDao {
     Connection con;

    public ModeloDao() throws SQLException {
        con = ConnectionFactory.getConnection();
    }

    public boolean checkLogin(String email, String senha) throws NoSuchAlgorithmException {

        PreparedStatement stmt = null;
        Usuario u = new Usuario();
        ResultSet rs = null;
        boolean check = false;
        BigInteger cripto;
        cripto = u.criptografarSenha(senha);

        try {
            stmt = con.prepareStatement("SELECT * FROM tbmodelo"
                    + " where email = ? AND senha = ?");
            stmt.setString(1, email);
            stmt.setString(2, cripto.toString());
            rs = stmt.executeQuery();

            if (rs.next()) {
                check = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
        return check;
    }

    // SALVA O USUARIO NO BANCO DE DADOS   ---- C
    public void create(Modelo m) throws NoSuchAlgorithmException {
        PreparedStatement stmt = null;
        BigInteger cripto;
        try {
            stmt = con.prepareStatement("INSERT INTO tbusuario (modelo,"
                    + "marca,ano) VALUE (?,?,?)");
            stmt.setString(1, m.getModelo());
            stmt.setString(2, m.getMarca());
            stmt.setInt(3, m.getAno());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Modelo " + m.getModelo()
                    + " Salvo com Sucesso!!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    //ALTERAR O USUARIO NO BANCO DE DADOS   -- U 
    public void update(Modelo m) throws NoSuchAlgorithmException {
        PreparedStatement stmt = null;
        BigInteger cripto;
        try {
            stmt = con.prepareStatement("UPDATE tbmodelo SET modelo = ?,"
                    + "marca = ?, ano = ?");
            stmt.setString(1, m.getModelo());
            stmt.setString(2, m.getMarca());
            stmt.setInt(3, m.getAno());
            stmt.setInt(5, m.getId());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Modelo " + m.getModelo()
                    + " Modificado com Sucesso!!");

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    //listagem de usuarios na tabela do formulario   ---   R

    public ArrayList<Modelo> read() {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbmodelo ORDER BY nome ASC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Modelo modelo;
                modelo = new Modelo();
                modelo.setId(rs.getInt("id"));
                modelo.setModelo(rs.getString("modelo"));
                modelo.setMarca(rs.getString("marca"));
                modelo.setAno(rs.getInt("ano"));
                modelo.add(modelo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Modelo>) Modelo;
    }

    public ArrayList<Modelo> readPesq(String nome) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Modelo> modelos = new ArrayList<Modelo>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbmodelo WHERE nome LIKE ?");
            stmt.setString(1, "%" + nome + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Modelo modelo = new Modelo();
                modelo.setId(rs.getInt("id"));
                modelo.setMarca(rs.getString("marca"));
                modelo.setModelo(rs.getString("modelo"));
                modelo.setAno(rs.getInt("ano"));
                modelos.add(modelo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Modelo>) modelos;
    }

        
    //excluir do banco de dados   --- D
    public void delete(Modelo m) {
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbmodelo WHERE id = ?");

            stmt.setInt(1, m.getId());

            if (JOptionPane.showConfirmDialog(null, "Tem certeza que"
                    + " deseja excluir este Modelo(a)", "Exclusão",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Modelo(a) excluído(a)com Sucesso!!");
                stmt.executeUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "A exclusão do Usuario(a) Cancelado(a)com Sucesso!!");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}


