

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


public class frmDSMuonTra extends javax.swing.JFrame {

    public frmDSMuonTra() {
                
        initComponents();
        setupUI();
        // can giua cho form
        
//        this.setSize(1099,691);
        this.setLocationRelativeTo(null);
        // tao vien cho khung panel
        Border panelHeaderBorder = BorderFactory.createMatteBorder(4, 4, 4, 4, new Color(74,98,138));
        
        jPanel_Theloaisach.setBorder(panelHeaderBorder);
     
        controllers.Func_Class func = new controllers.Func_Class();
        func.displayImage(60,60,"/image/books-1977235.png", lblTieudeTL );
    
    }
    private void setupUI(){
      
        
        // thiet ke cho hang tren dau cua bang
        tblSachmuon.getTableHeader().setBackground(new Color(74,98,138)); 
        tblSachmuon.getTableHeader().setForeground(Color.white);
        tblSachmuon.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        tblSachmuon.getTableHeader().setOpaque(false);

        tblSachmuon.getTableHeader().repaint();
       
//        tblSachmuon.getColumnModel().getColumn(0).setPreferredWidth(20);
//        tblSachmuon.getColumnModel().getColumn(4).setPreferredWidth(40);
//        tblSachmuon.getColumnModel().getColumn(5).setPreferredWidth(40);
        

      
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Theloaisach = new javax.swing.JPanel();
        lblTieudeTL = new javax.swing.JLabel();
        lblMinus = new javax.swing.JLabel();
        lbl_Close = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Ngaymuon = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        Ngaytra = new com.toedter.calendar.JDateChooser();
        btnTim = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSachmuon = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel_Theloaisach.setBackground(new java.awt.Color(148, 170, 214));
        jPanel_Theloaisach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(74, 98, 138)));
        jPanel_Theloaisach.setPreferredSize(new java.awt.Dimension(1105, 636));
        jPanel_Theloaisach.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTieudeTL.setBackground(new java.awt.Color(74, 98, 138));
        lblTieudeTL.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTieudeTL.setForeground(new java.awt.Color(255, 255, 255));
        lblTieudeTL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieudeTL.setText("        Danh sách sách mượn");
        lblTieudeTL.setOpaque(true);
        jPanel_Theloaisach.add(lblTieudeTL, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 57));

        lblMinus.setBackground(new java.awt.Color(74, 98, 138));
        lblMinus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ic_minus.png"))); // NOI18N
        lblMinus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinus.setOpaque(true);
        lblMinus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinusMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMinusMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMinusMouseExited(evt);
            }
        });
        jPanel_Theloaisach.add(lblMinus, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 0, 60, 57));

        lbl_Close.setBackground(new java.awt.Color(74, 98, 138));
        lbl_Close.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl_Close.setForeground(new java.awt.Color(255, 255, 255));
        lbl_Close.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_Close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ic_close24.png"))); // NOI18N
        lbl_Close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_Close.setOpaque(true);
        lbl_Close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_CloseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_CloseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_CloseMouseExited(evt);
            }
        });
        jPanel_Theloaisach.add(lbl_Close, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 0, 60, 57));

        jPanel1.setBackground(new java.awt.Color(191, 202, 230));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setText("Ngày mượn:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 27, 102, -1));
        jPanel1.add(Ngaymuon, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 23, 173, -1));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel2.setText("Ngày trả:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(422, 27, 90, -1));
        jPanel1.add(Ngaytra, new org.netbeans.lib.awtextra.AbsoluteConstraints(518, 23, 164, -1));

        btnTim.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnTim.setText("Tìm");
        jPanel1.add(btnTim, new org.netbeans.lib.awtextra.AbsoluteConstraints(772, 23, 91, -1));

        jPanel_Theloaisach.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 65, 919, 70));

        tblSachmuon.setBackground(new java.awt.Color(191, 202, 230));
        tblSachmuon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã sách", "Tên sách", "Tên độc giả", "Ngày mượn", "Ngày trả", "Trạng thái"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblSachmuon.setSelectionBackground(new java.awt.Color(191, 202, 230));
        jScrollPane1.setViewportView(tblSachmuon);

        jPanel_Theloaisach.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 145, 900, -1));

        getContentPane().add(jPanel_Theloaisach, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_CloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_CloseMouseClicked
        // TODO add your handling code here:
        // Dong form the loai sach
        this.dispose();
    }//GEN-LAST:event_lbl_CloseMouseClicked

    private void lblMinusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinusMouseClicked
        // TODO add your handling code here:
        // Thu nhỏ form lại
        this.setState(javax.swing.JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMinusMouseClicked

    private void lblMinusMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinusMouseEntered
        // TODO add your handling code here:
        lblMinus.setBackground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_lblMinusMouseEntered

    private void lblMinusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinusMouseExited
        // TODO add your handling code here:
        lblMinus.setBackground(new java.awt.Color(74,98,138));
    }//GEN-LAST:event_lblMinusMouseExited

    private void lbl_CloseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_CloseMouseEntered
        // TODO add your handling code here:
        lbl_Close.setBackground(Color.red);
    }//GEN-LAST:event_lbl_CloseMouseEntered

    private void lbl_CloseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_CloseMouseExited
        // TODO add your handling code here:
        lbl_Close.setBackground(new java.awt.Color(74,98,138));
    }//GEN-LAST:event_lbl_CloseMouseExited

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmDSMuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmDSMuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmDSMuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmDSMuonTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmDSMuonTra().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser Ngaymuon;
    private com.toedter.calendar.JDateChooser Ngaytra;
    private javax.swing.JButton btnTim;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_Theloaisach;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMinus;
    private javax.swing.JLabel lblTieudeTL;
    private javax.swing.JLabel lbl_Close;
    private javax.swing.JTable tblSachmuon;
    // End of variables declaration//GEN-END:variables
}
