package frame;

import helpers.koneksi;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class kotainputframe extends JFrame {
    private JTextField idTextField;
    private JTextField namaTextField;
    private JTextField luasTextField;
    private JButton simpanButton;
    private JButton batalButton;
    private JPanel mainPanel;

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public void Isikomponen() {
        Connection c = koneksi.getConnection();
        String findSQL = "SELECT * FROM kota WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = c.prepareStatement(findSQL);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idTextField.setText(String.valueOf(rs.getInt("id")));
                namaTextField.setText(rs.getString("nama"));
                luasTextField.setText(rs.getString("luas"));
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public kotainputframe() {
        batalButton.addActionListener(e -> {
            dispose();
        });
        simpanButton.addActionListener(e -> {
            String nama = namaTextField.getText();
            String luas = luasTextField.getText();
            Connection c = koneksi.getConnection();
            PreparedStatement ps;
            try{
                if (id == 0) {
                        String insertSQL = "INSERT INTO kota VALUES (NULL, ?, ?)";
                        ps = c.prepareStatement(insertSQL);
                        ps.setString(1, nama);
                        ps.setString(2, luas);
                        ps.executeUpdate();
                        dispose();
                } else {
                    String updateSQL = "UPDATE kota SET nama = ?, luas = ? WHERE id = ?";
                    ps = c.prepareStatement(updateSQL);
                    ps.setString(1, nama);
                    ps.setString(2, luas);
                    ps.setInt(3, id);
                    ps.executeUpdate();
                    dispose();
                }
            }catch (SQLException ex){
                throw new RuntimeException(ex);
            }
        });
        init();
    }
    public void init() {
        setContentPane(mainPanel);
        setTitle("Input Kota");
        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

}
