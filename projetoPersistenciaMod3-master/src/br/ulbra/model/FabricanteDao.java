/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ulbra.model;

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
public class FabricanteDao {
    Connection con;
    
    public FabricanteDao() throws SQLException{
        con = ConnectionFactory.getConnection();
    }
    
   
    
     //listagem de usuarios na tabela do formulario   ---   R
    
    public ArrayList<Fabricante> read(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Fabricante> Fabricante = new ArrayList<Fabricante>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbfabricante ORDER BY marca ASC");
            rs = stmt.executeQuery();
            while(rs.next()){
                Fabricante f = new Fabricante();
                f.setId(rs.getInt("id"));
                f.setMarca(rs.getString("marca"));
                f.setTelefone(rs.getString("telefone"));
                f.setEmail(rs.getString("email"));
                f.setSite(rs.getString("site"));
                Fabricante.add(f);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro:"+e.getMessage());
        } finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Fabricante>) Fabricante;
    }
    
     public ArrayList<Fabricante> readPesq(){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Fabricante> fabricante = new ArrayList<Fabricante>();
        try {
            stmt = con.prepareStatement("SELECT * FROM tbfabricante WHERE marca LIKE ?");
            rs = stmt.executeQuery();
            while(rs.next()){
                Fabricante f = new Fabricante();
                f.setId(rs.getInt("id"));
                f.setMarca(rs.getString("marca"));
                f.settelefone(rs.getString("telefone"));
                f.setsite(rs.getInt("site"));
                f.setEmail(rs.getString("e-mail"));
     
               fabricante.add(f);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Erro:"+e.getMessage());
        } finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return (ArrayList<Fabricante>) fabricante;
    }
    
    
   // SALVA O USUARIO NO BANCO DE DADOS   ---- C
    public void create(Fabricante f){
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO tbfabricante (marca,"
                    + "telefone) VALUE (?,?,?,?)");
            stmt.setString(1, f.getMarca());
            stmt.setString(2, f.getTelefone());
            stmt.setString(3, f.getEmail());
            stmt.setString(4, f.getSite());
            
          
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "O "+f.getMarca()
                    +" Salvo com Sucesso!!");
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    //ALTERAR O USUARIO NO BANCO DE DADOS   -- U 
    public void update(Fabricante f){
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("UPDATE tbfabricante SET modelo = ?,"
                    + "telefone= ?, modelo = ? ,site = ? WHERE id = ?");
           stmt.setString(1, f.getModelo());
            stmt.setString(2, f.getTelefone());
            stmt.setString(3, f.getSite());
            stmt.setInt(4, f.getId());
            
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario "+f.getModelo()
                    +" Modificado com Sucesso!!");
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    //excluir do banco de dados   --- D
    public void delete(Fabricante f){
            PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("DELETE FROM tbfabricante WHERE id = ?");
           
            stmt.setInt   (1, f.getId());
            
            if (JOptionPane.showConfirmDialog(null, "Tem certeza que"
                    + " deseja excluir este Fabricante","Exclusão",
                    JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
                JOptionPane.showMessageDialog(null, "Fabricante excluído com Sucesso!!");
                stmt.executeUpdate();
            }else{
                JOptionPane.showMessageDialog(null, "A exclusão do Fabricante Cancelado(a)com Sucesso!!");
            }
   
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro:"+e.getMessage());
        } finally{
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    private void mail(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}

    

