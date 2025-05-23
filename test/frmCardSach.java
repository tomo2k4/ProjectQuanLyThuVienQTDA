/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


/**
 *
 * @author ADMIN
 */
public class frmCardSach extends javax.swing.JFrame {

    /**
     * Creates new form frmCardSach
     */
    public frmCardSach() {
        initComponents();
        // can giua cho form
        this.setLocationRelativeTo(null);
        
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
        lblSystemIC = new javax.swing.JLabel();
        lblTieude = new javax.swing.JLabel();
        lblSystemIC1 = new javax.swing.JLabel();
        pnlReview = new javax.swing.JPanel();
        lblAnh = new javax.swing.JLabel();
        lblNgaynhan = new javax.swing.JLabel();
        lblMasach = new javax.swing.JLabel();
        lblTensach = new javax.swing.JLabel();
        lblTacgia = new javax.swing.JLabel();
        lblTheloai = new javax.swing.JLabel();
        lblnxb = new javax.swing.JLabel();
        lblGia = new javax.swing.JLabel();
        lblSoluong = new javax.swing.JLabel();
        scrollDescrip = new javax.swing.JScrollPane();
        txtAreaMota = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(4, 147, 114));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSystemIC.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSystemIC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ic_close24.png"))); // NOI18N
        lblSystemIC.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSystemICMouseClicked(evt);
            }
        });
        pnlHeader.add(lblSystemIC, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 0, 60, 57));

        lblTieude.setBackground(new java.awt.Color(241, 231, 254));
        lblTieude.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTieude.setForeground(new java.awt.Color(247, 239, 232));
        lblTieude.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTieude.setText("Tóm tắt sách");
        lblTieude.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        pnlHeader.add(lblTieude, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 170, 57));

        lblSystemIC1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSystemIC1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ic_tacgia64.png"))); // NOI18N
        pnlHeader.add(lblSystemIC1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 60, 57));

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, -1));

        pnlReview.setBackground(new java.awt.Color(200, 247, 197));
        pnlReview.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(4, 147, 114)));
        pnlReview.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(4, 147, 114)));
        pnlReview.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 140, 210));

        lblNgaynhan.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblNgaynhan.setForeground(new java.awt.Color(4, 147, 114));
        lblNgaynhan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNgaynhan.setText("Ngày nhận:");
        pnlReview.add(lblNgaynhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 165, 25));

        lblMasach.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblMasach.setForeground(new java.awt.Color(4, 147, 114));
        lblMasach.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblMasach.setText("Mã sách:");
        pnlReview.add(lblMasach, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 80, 165, 25));

        lblTensach.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblTensach.setForeground(new java.awt.Color(4, 147, 114));
        lblTensach.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTensach.setText("Tên sách:");
        pnlReview.add(lblTensach, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 110, 165, 25));

        lblTacgia.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblTacgia.setForeground(new java.awt.Color(4, 147, 114));
        lblTacgia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTacgia.setText("Tác giả:");
        pnlReview.add(lblTacgia, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 165, 25));

        lblTheloai.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblTheloai.setForeground(new java.awt.Color(4, 147, 114));
        lblTheloai.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTheloai.setText("Thể loại:");
        pnlReview.add(lblTheloai, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 165, 25));

        lblnxb.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblnxb.setForeground(new java.awt.Color(4, 147, 114));
        lblnxb.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblnxb.setText("Nhà xuất bản:");
        pnlReview.add(lblnxb, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 200, 165, 25));

        lblGia.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblGia.setForeground(new java.awt.Color(4, 147, 114));
        lblGia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblGia.setText("Giá:");
        pnlReview.add(lblGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 230, 165, 25));

        lblSoluong.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        lblSoluong.setForeground(new java.awt.Color(4, 147, 114));
        lblSoluong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblSoluong.setText("Số lượng:");
        pnlReview.add(lblSoluong, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 260, 165, 25));

        txtAreaMota.setColumns(20);
        txtAreaMota.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        txtAreaMota.setRows(5);
        scrollDescrip.setViewportView(txtAreaMota);

        pnlReview.add(scrollDescrip, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 370, 140));

        getContentPane().add(pnlReview, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 500));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSystemICMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSystemICMouseClicked
        // TODO add your handling code here:
        // Dong form
        this.dispose();
    }//GEN-LAST:event_lblSystemICMouseClicked

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
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmCardSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCardSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCardSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCardSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCardSach().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblMasach;
    private javax.swing.JLabel lblNgaynhan;
    private javax.swing.JLabel lblSoluong;
    private javax.swing.JLabel lblSystemIC;
    private javax.swing.JLabel lblSystemIC1;
    private javax.swing.JLabel lblTacgia;
    private javax.swing.JLabel lblTensach;
    private javax.swing.JLabel lblTheloai;
    private javax.swing.JLabel lblTieude;
    private javax.swing.JLabel lblnxb;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlReview;
    private javax.swing.JScrollPane scrollDescrip;
    private javax.swing.JTextArea txtAreaMota;
    // End of variables declaration//GEN-END:variables
}
