/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ui_library;

import controllers.TacGiaController;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.TacGiaModel;

/**
 *
 * @author admin
 */
public final class DSTacGia extends javax.swing.JDialog {

    /**
     * Creates new form DSTacGia
     */
    private final QLSach parent;
    // Khai báo tableModel
    private final DefaultTableModel tableModel;
    private final TacGiaController dstacgia;
    public DSTacGia(QLSach parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        this.parent = parent; // Gán form cha
        // can giua cho form
        this.setLocationRelativeTo(null);
        //set UI
        setupUI();
   
        // Cấu hình bảng và controller
        tableModel = (DefaultTableModel) tblTacgia.getModel();
        dstacgia = new TacGiaController();
        // Khởi tạo các components trên JFrame
        String[] colsName = {"Mã", "Tên tác giả", "Chuyên môn", "Giới thiệu"};
        // đặt tiêu đề cột cho tableModel
        tableModel.setColumnIdentifiers(colsName);
        // Kết nối JTable với tableModel
        tblTacgia.setModel(tableModel);
        // Gọi hàm showDataOnTable() để hiển thị dữ liệu lên bảng
        ShowData();
        
    }
    
    private void setupUI(){
        tblTacgia.getTableHeader().setBackground(new java.awt.Color(255, 255, 255));
        tblTacgia.getTableHeader().setForeground(new java.awt.Color(90, 34, 139));
        tblTacgia.getTableHeader().setFont(new java.awt.Font("Roboto", 1, 16));
        tblTacgia.getTableHeader().setOpaque(false);
        
       
    }
    
    public void ShowData() throws SQLException{ 
        List<TacGiaModel> lst = dstacgia.getdsTacGia();
        // Duyệt qua ArrayList data
        Object[] rows = new Object[4];
        for(int i =0; i< lst.size() ; i++){
            rows[0] = lst.get(i).getMaTacgia();
            rows[1] = lst.get(i).getTenTacgia();
            rows[2] = lst.get(i).getChuyenmon();
            rows[3] = lst.get(i).getGioithieu();
            // Đưa dòng dữ liệu vào tableModel
            tableModel.addRow(rows);
        }   
    }
    
    //Ham xoa du lieu trong tableModel 
    public void ClearData() throws SQLException{ 
        int rowCount = tableModel.getRowCount(); // Lấy số lượng dòng hiện tại
        for (int i = rowCount - 1; i >= 0; i--) {
            tableModel.removeRow(i); // Xóa từng dòng
        }
    } 

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTieude = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        scrollInfomation = new javax.swing.JScrollPane();
        tblTacgia = new javax.swing.JTable();
        btnChon_Tacgia = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(5, 12, 156));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTieude.setBackground(new java.awt.Color(241, 231, 254));
        lblTieude.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTieude.setForeground(new java.awt.Color(255, 255, 255));
        lblTieude.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTieude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ic_tacgia64.png"))); // NOI18N
        lblTieude.setText("   Danh sách tác giả");
        lblTieude.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        pnlHeader.add(lblTieude, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 0, 310, 57));

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, -1));

        jPanel1.setBackground(new java.awt.Color(167, 230, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(90, 34, 139)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        scrollInfomation.setBackground(new java.awt.Color(213, 184, 255));
        scrollInfomation.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tblTacgia.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        tblTacgia.setForeground(new java.awt.Color(90, 34, 139));
        tblTacgia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã ", "Tên tác giả", "Chuyên môn", "Giới thiệu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblTacgia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblTacgia.setFocusable(false);
        tblTacgia.setGridColor(new java.awt.Color(102, 102, 102));
        tblTacgia.setRowHeight(25);
        tblTacgia.setSelectionBackground(new java.awt.Color(241, 231, 254));
        tblTacgia.setSelectionForeground(new java.awt.Color(90, 34, 139));
        tblTacgia.setShowGrid(true);
        tblTacgia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTacgiaMouseClicked(evt);
            }
        });
        scrollInfomation.setViewportView(tblTacgia);

        jPanel1.add(scrollInfomation, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, 490, 450));

        btnChon_Tacgia.setFont(new java.awt.Font("Verdana", 1, 20)); // NOI18N
        btnChon_Tacgia.setForeground(new java.awt.Color(5, 12, 156));
        btnChon_Tacgia.setText("Chọn tác giả");
        btnChon_Tacgia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChon_TacgiaActionPerformed(evt);
            }
        });
        jPanel1.add(btnChon_Tacgia, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 550, 200, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblTacgiaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTacgiaMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblTacgiaMouseClicked

    private void btnChon_TacgiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChon_TacgiaActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblTacgia.getSelectedRow();

        // Kiểm tra xem có hàng nào được chọn không
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tác giả!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy dữ liệu từ bảng
        Object maTacgiaObj = tblTacgia.getValueAt(selectedRow, 0);
        Object tenTacgiaObj = tblTacgia.getValueAt(selectedRow, 1);

        // Đảm bảo kiểu dữ liệu đúng
        String maTacgia = maTacgiaObj != null ? maTacgiaObj.toString() : "";
        String tenTacgia = tenTacgiaObj != null ? tenTacgiaObj.toString() : "";

        // Gửi dữ liệu về form cha
        // Gửi dữ liệu về form cha
        if (parent != null) {
            parent.AddtgData(tenTacgia, maTacgia);
        }

        // Đóng form hiện tại
        this.dispose();
    }//GEN-LAST:event_btnChon_TacgiaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    //javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSTacGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                // Tạo một đối tượng QLSach giả lập để truyền vào DSTacGia
                QLSach parent = new QLSach(new javax.swing.JFrame(), true);

                // Khởi tạo DSTacGia với form cha là QLSach
                DSTacGia dialog = new DSTacGia(parent, true);

                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });

                dialog.setVisible(true);
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(DSTacGia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChon_Tacgia;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTieude;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JScrollPane scrollInfomation;
    public static javax.swing.JTable tblTacgia;
    // End of variables declaration//GEN-END:variables
}
