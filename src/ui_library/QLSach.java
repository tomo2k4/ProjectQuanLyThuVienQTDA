/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package ui_library;

import controllers.SachController;
import controllers.TacGiaController;
import controllers.TheLoaiController;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import models.SachModel;


/**
 *
 * @author admin
 */
public final class QLSach extends javax.swing.JDialog {

    /**
     * Creates new form QLSach
     */
    //private Object filename;
    String filename =null;
    byte[] book_img = null;
    private final TheLoaiController theLoai; // Đối tượng để kết nối dữ liệu thể loại
    private final TacGiaController tacGia;
    private final HashMap<String, String> tacGiaMap; // Map lưu trữ dữ liệu mã và tên tác giả
    private HashMap<String, Integer> theLoaiMap; // Lưu trữ dữ liệu thể loại
    // Khai báo tableModel
    private final DefaultTableModel tableModel;
    private final SachController dssach;
    private final boolean cothem = true;
    
    public QLSach(java.awt.Frame parent, boolean modal) throws SQLException {
        super(parent, modal);
        initComponents();
        // can giua cho form     
        this.setLocationRelativeTo(null);
        setupUI();
        // Cấu hình bảng và controller
        tableModel = (DefaultTableModel) tblsach.getModel();
        dssach = new SachController();
        tacGia = new TacGiaController();
        // Tải dữ liệu tác giả vào Map
        tacGiaMap = tacGia.getTacGiaMap();
        // Khởi tạo các components trên JFrameif
        String[] colsName = {"Mã", "Tên", "Tác giả", "Thể loại", "Nhà xuất bản", "Số lượng", "Giá", "Ngày nhập","Mô tả", "Ảnh"};
        // đặt tiêu đề cột cho tableModel
        tableModel.setColumnIdentifiers(colsName);
        // Kết nối JTable với tableModel
        tblsach.setModel(tableModel);
        // Gọi hàm showDataOnTable() để hiển thị dữ liệu lên bảng
        ShowData();
        theLoai = new TheLoaiController(); // Khởi tạo đối tượng controller
        insertCmbTheLoai();
        this.txtMasach. setEnabled (false);

        lblWarn.setVisible(false);
        lblWarn1.setVisible(false);
        lblWarn2.setVisible(false);
        lblWarn3.setVisible(false);
        lblWarn4.setVisible(false);
        lblWarn5.setVisible(false);
    }
    private void setupUI(){
        //this.setSize(1400,725);
        // tao vien xam cho khung panel
        Border panelHeaderBorder = BorderFactory.createMatteBorder(3, 3, 3, 3, new Color(255, 255, 255));
        
        // thiet ke cho hang tren dau cua bang
        tblsach.getTableHeader().setBackground(new Color(242, 120, 75)); 
        tblsach.getTableHeader().setForeground(Color.white);
        tblsach.getTableHeader().setFont(new Font("Verdana", Font.BOLD, 12));
        tblsach.getTableHeader().setOpaque(false);

        tblsach.getTableHeader().repaint();
       
        tblsach.getColumnModel().getColumn(0).setPreferredWidth(20);
        tblsach.getColumnModel().getColumn(4).setPreferredWidth(40);
        tblsach.getColumnModel().getColumn(5).setPreferredWidth(40);
        
        txtmota.setLineWrap(true); // Tự động xuống dòng
        txtmota.setWrapStyleWord(true); // Ngắt từ hợp lý
        txtmota.setMargin(new Insets(10, 10, 10, 5)); // Khoảng cách nội dung với biên

    }
    public void ShowData() throws SQLException{ 
        List<SachModel> lst = dssach.getdsSach();
        // Duyệt qua ArrayList data
        Object[] rows = new Object[10];
        for(int i =0; i< lst.size() ; i++){
            rows[0] = lst.get(i).getIdSach();
            rows[1] = lst.get(i).getTenSach();
            rows[2] = lst.get(i).getIdTacGia();
            rows[3] = lst.get(i).getIdTheLoai();
            rows[4] = lst.get(i).getNhaXuatBan();
            rows[5] = lst.get(i).getSoLuong();
            rows[6] = lst.get(i).getGiaSach();
            rows[7] = lst.get(i).getNgayNhan();
            rows[8] = lst.get(i).getMoTa();
            rows[9] = lst.get(i).getAnhSach();
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
    
    // Phương thức tải dữ liệu thể loại vào ComboBox
    public void insertCmbTheLoai() {
        cmbTheloai.removeAllItems(); // Xóa dữ liệu cũ
        cmbTheloai.addItem("Chọn thể loại"); // Thêm mục mặc định

        // Gọi phương thức từ controller để lấy danh sách thể loại
        theLoaiMap = theLoai.GetTheloaiMap();
        for (String tenTheLoai : theLoaiMap.keySet()) {
            cmbTheloai.addItem(tenTheLoai);
        }
    }
    
    public void AddtgData(String tenTg, String maTg){
        txtTenTacGia.setText(tenTg);
        Matg.setText(maTg);
        // Kiểm tra lại trạng thái của lblWarn1 sau khi điền thông tin
        if (tenTg != null && !tenTg.isEmpty()) {
            lblWarn1.setVisible(false);  // Ẩn thông báo nếu đã có tên tác giả
        } else {
            lblWarn1.setVisible(true);   // Hiển thị thông báo nếu chưa có tên tác giả
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
        jPanel_Theloaisach = new javax.swing.JPanel();
        lblMasach = new javax.swing.JLabel();
        lblTensach = new javax.swing.JLabel();
        lblTacgia = new javax.swing.JLabel();
        lblTheloai = new javax.swing.JLabel();
        lblSoluong = new javax.swing.JLabel();
        lblGia = new javax.swing.JLabel();
        txtMasach = new javax.swing.JTextField();
        txtTenSach = new javax.swing.JTextField();
        txtTenTacGia = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtmota = new javax.swing.JTextArea();
        lblAnh = new javax.swing.JLabel();
        cmbTheloai = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNhaxuatban = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblsach = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnThem = new javax.swing.JButton();
        btnHuy = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        NgayNhap = new com.toedter.calendar.JDateChooser();
        spnSoluong = new javax.swing.JSpinner();
        btnChonanhbia = new javax.swing.JButton();
        Matg = new javax.swing.JTextField();
        Matl = new javax.swing.JTextField();
        btnChon_Tacgia = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        lblWarn = new javax.swing.JLabel();
        lblWarn1 = new javax.swing.JLabel();
        lblWarn2 = new javax.swing.JLabel();
        lblWarn3 = new javax.swing.JLabel();
        lblWarn4 = new javax.swing.JLabel();
        lblWarn5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(5, 12, 156));
        pnlHeader.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTieude.setBackground(new java.awt.Color(241, 231, 254));
        lblTieude.setFont(new java.awt.Font("Roboto", 1, 24)); // NOI18N
        lblTieude.setForeground(new java.awt.Color(255, 255, 255));
        lblTieude.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTieude.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/ic_Books_color.png"))); // NOI18N
        lblTieude.setText("   Quản lý sách");
        lblTieude.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        pnlHeader.add(lblTieude, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 270, 60));

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 60));

        jPanel_Theloaisach.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_Theloaisach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 3, 3, 3, new java.awt.Color(5, 12, 156)));
        jPanel_Theloaisach.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMasach.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        lblMasach.setForeground(new java.awt.Color(5, 12, 156));
        lblMasach.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMasach.setText("ID sách:   ");
        jPanel_Theloaisach.add(lblMasach, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 120, 30));

        lblTensach.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        lblTensach.setForeground(new java.awt.Color(5, 12, 156));
        lblTensach.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTensach.setText("Tên sách:   ");
        jPanel_Theloaisach.add(lblTensach, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, 120, 30));

        lblTacgia.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        lblTacgia.setForeground(new java.awt.Color(5, 12, 156));
        lblTacgia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTacgia.setText("Tác giả:   ");
        jPanel_Theloaisach.add(lblTacgia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 120, 30));

        lblTheloai.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        lblTheloai.setForeground(new java.awt.Color(5, 12, 156));
        lblTheloai.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTheloai.setText("Thể loại:   ");
        jPanel_Theloaisach.add(lblTheloai, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 235, 120, 30));

        lblSoluong.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        lblSoluong.setForeground(new java.awt.Color(5, 12, 156));
        lblSoluong.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSoluong.setText("Số Lượng:   ");
        jPanel_Theloaisach.add(lblSoluong, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 120, 30));

        lblGia.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        lblGia.setForeground(new java.awt.Color(5, 12, 156));
        lblGia.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblGia.setText("Giá:   ");
        jPanel_Theloaisach.add(lblGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 120, 30));

        txtMasach.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtMasach.setForeground(new java.awt.Color(255, 102, 51));
        txtMasach.setEnabled(false);
        jPanel_Theloaisach.add(txtMasach, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 170, 32));

        txtTenSach.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtTenSach.setForeground(new java.awt.Color(255, 102, 51));
        txtTenSach.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTenSachKeyReleased(evt);
            }
        });
        jPanel_Theloaisach.add(txtTenSach, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 430, 32));

        txtTenTacGia.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtTenTacGia.setForeground(new java.awt.Color(255, 102, 51));
        txtTenTacGia.setEnabled(false);
        jPanel_Theloaisach.add(txtTenTacGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 182, 250, 32));

        txtGia.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtGia.setForeground(new java.awt.Color(255, 102, 51));
        txtGia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGiaKeyReleased(evt);
            }
        });
        jPanel_Theloaisach.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 450, 250, 32));

        txtmota.setColumns(20);
        txtmota.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtmota.setForeground(new java.awt.Color(255, 102, 51));
        txtmota.setRows(5);
        jScrollPane2.setViewportView(txtmota);

        jPanel_Theloaisach.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 505, 250, 115));

        lblAnh.setBackground(new java.awt.Color(167, 230, 255));
        lblAnh.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        lblAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAnh.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lblAnh.setOpaque(true);
        jPanel_Theloaisach.add(lblAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 320, 150, 200));

        cmbTheloai.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        cmbTheloai.setForeground(new java.awt.Color(255, 102, 51));
        cmbTheloai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTheloaiActionPerformed(evt);
            }
        });
        jPanel_Theloaisach.add(cmbTheloai, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 235, 250, 32));

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(5, 12, 156));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Ngày nhập:   ");
        jPanel_Theloaisach.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 120, 30));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(5, 12, 156));
        jLabel3.setText("Nhà xuất bản:   ");
        jPanel_Theloaisach.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 395, 150, 30));

        txtNhaxuatban.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        txtNhaxuatban.setForeground(new java.awt.Color(255, 102, 51));
        txtNhaxuatban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNhaxuatbanKeyReleased(evt);
            }
        });
        jPanel_Theloaisach.add(txtNhaxuatban, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 395, 250, 32));

        tblsach.setBackground(new java.awt.Color(167, 230, 255));
        tblsach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblsach.setForeground(new java.awt.Color(5, 12, 156));
        tblsach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tên", "Tác giả", "Thể loại", "Số lượng", "Giá", "Ngày nhập", "Nhà xuất bản", "Mô tả", "Ảnh"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblsach.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblsach.setFocusable(false);
        tblsach.setGridColor(new java.awt.Color(5, 12, 156));
        tblsach.setRowHeight(25);
        tblsach.setSelectionBackground(new java.awt.Color(137, 196, 244));
        tblsach.setShowGrid(true);
        tblsach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblsachMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblsach);

        jPanel_Theloaisach.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 125, 570, 495));

        btnXoa.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(5, 12, 156));
        btnXoa.setText("Xóa");
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel_Theloaisach.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 640, 110, 32));

        btnSua.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnSua.setForeground(new java.awt.Color(5, 12, 156));
        btnSua.setText("Sửa");
        btnSua.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        jPanel_Theloaisach.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 640, 110, 32));

        jLabel4.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(5, 12, 156));
        jLabel4.setText("Bảng thông tin sách");
        jPanel_Theloaisach.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 60, 290, 50));

        btnThem.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(5, 12, 156));
        btnThem.setText("Thêm ");
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel_Theloaisach.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 640, 110, 32));

        btnHuy.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(5, 12, 156));
        btnHuy.setText("Hủy bỏ");
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel_Theloaisach.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 640, 110, 32));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(5, 12, 156));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Mô tả:   ");
        jPanel_Theloaisach.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 505, 120, 30));

        NgayNhap.setForeground(new java.awt.Color(255, 102, 51));
        NgayNhap.setDateFormatString("yyyy-MM-dd");
        NgayNhap.setFont(new java.awt.Font("Verdana", 0, 13)); // NOI18N
        NgayNhap.setMaxSelectableDate(null);
        jPanel_Theloaisach.add(NgayNhap, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 250, 32));

        spnSoluong.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jPanel_Theloaisach.add(spnSoluong, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 80, 32));

        btnChonanhbia.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        btnChonanhbia.setForeground(new java.awt.Color(5, 12, 156));
        btnChonanhbia.setText("Chọn ảnh bìa");
        btnChonanhbia.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(5, 12, 156)));
        btnChonanhbia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChonanhbia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChonanhbiaActionPerformed(evt);
            }
        });
        jPanel_Theloaisach.add(btnChonanhbia, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 530, 150, 30));

        Matg.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        Matg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Matg.setAutoscrolls(false);
        Matg.setEnabled(false);
        jPanel_Theloaisach.add(Matg, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 182, 35, 32));

        Matl.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        Matl.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Matl.setEnabled(false);
        Matl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                MatlKeyReleased(evt);
            }
        });
        jPanel_Theloaisach.add(Matl, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 235, 35, 32));

        btnChon_Tacgia.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        btnChon_Tacgia.setForeground(new java.awt.Color(5, 12, 156));
        btnChon_Tacgia.setText("Chọn tác giả");
        btnChon_Tacgia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChon_TacgiaActionPerformed(evt);
            }
        });
        jPanel_Theloaisach.add(btnChon_Tacgia, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 182, 138, 30));

        jLabel7.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(5, 12, 156));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Ảnh bìa:");
        jPanel_Theloaisach.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 290, 120, 30));

        lblWarn.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblWarn.setForeground(new java.awt.Color(255, 102, 102));
        lblWarn.setText("* Tên sách không được để trống");
        jPanel_Theloaisach.add(lblWarn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 162, 240, 20));

        lblWarn1.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblWarn1.setForeground(new java.awt.Color(255, 102, 102));
        lblWarn1.setText("* Hãy chọn một tác giả");
        jPanel_Theloaisach.add(lblWarn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 215, 240, 20));

        lblWarn2.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblWarn2.setForeground(new java.awt.Color(255, 102, 102));
        lblWarn2.setText("* Hãy chọn một thể loại");
        jPanel_Theloaisach.add(lblWarn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 268, 240, 20));

        lblWarn3.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblWarn3.setForeground(new java.awt.Color(255, 102, 102));
        lblWarn3.setText("* Nhà xuất bản không được để trống");
        jPanel_Theloaisach.add(lblWarn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 428, 240, 20));

        lblWarn4.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblWarn4.setForeground(new java.awt.Color(255, 102, 102));
        lblWarn4.setText("* Giá sách không được để trống");
        jPanel_Theloaisach.add(lblWarn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 482, 240, 20));

        lblWarn5.setFont(new java.awt.Font("Roboto", 0, 12)); // NOI18N
        lblWarn5.setForeground(new java.awt.Color(255, 102, 102));
        lblWarn5.setText("* Giá sách không hợp lệ");
        jPanel_Theloaisach.add(lblWarn5, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 482, 240, 20));

        getContentPane().add(jPanel_Theloaisach, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenSachKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTenSachKeyReleased
        // TODO add your handling code here:
        String tensach = txtTenSach.getText();
        if (!tensach.isEmpty()) {
            lblWarn.setVisible(false);

        } else {
            lblWarn.setVisible(true);
        }
    }//GEN-LAST:event_txtTenSachKeyReleased

    private void txtGiaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGiaKeyReleased
        // TODO add your handling code here:
        String giaSachText = txtGia.getText().trim();

        // Nếu giá sách hợp lệ, ẩn lblWarn4
        if (!giaSachText.isEmpty()) {
            try {
                double giasach = Double.parseDouble(giaSachText);
                if (giasach > 0) {
                    lblWarn5.setVisible(false);  // Ẩn thông báo lỗi nếu giá sách hợp lệ
                    lblWarn4.setVisible(false);
                } else {
                    lblWarn4.setVisible(false);  // Ẩn lblWarn4 nếu giá sách hợp lệ
                    lblWarn5.setVisible(true);  // Hiển thị thông báo lỗi nếu giá sách không hợp lệ (<= 0)
                }
            } catch (NumberFormatException ex) {
                lblWarn4.setVisible(false);  // Ẩn lblWarn4 nếu giá sách hợp lệ
                lblWarn5.setVisible(true);  // Hiển thị thông báo lỗi nếu giá sách không phải là số
            }
        } else {
            lblWarn4.setVisible(true);  // Hiển thị thông báo lỗi nếu giá sách rỗng
            lblWarn5.setVisible(false);  // Ẩn lblWarn4 nếu giá sách hợp lệ

        }
    }//GEN-LAST:event_txtGiaKeyReleased

    private void cmbTheloaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTheloaiActionPerformed
        // TODO add your handling code here:
        String selectedTheLoai = (String) cmbTheloai.getSelectedItem();
        if (!"Chọn thể loại".equals(selectedTheLoai)) {
            int maTheLoai = theLoaiMap.get(selectedTheLoai); // Lấy mã thể loại
            //System.out.println("Thể loại đã chọn: " + selectedTheLoai + " - Mã: " + maTheLoai);
            Matl.setText(String.valueOf(maTheLoai));
        }
    }//GEN-LAST:event_cmbTheloaiActionPerformed

    private void txtNhaxuatbanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNhaxuatbanKeyReleased
        // TODO add your handling code here:
        String nhaxuatban = txtNhaxuatban.getText();
        if (!nhaxuatban.isEmpty()) {
            lblWarn3.setVisible(false);

        } else {
            lblWarn3.setVisible(true);
        }

    }//GEN-LAST:event_txtNhaxuatbanKeyReleased

    private void tblsachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblsachMouseClicked
        // TODO add your handling code here:
        // Lấy hàng được chọn trong bảng
        int selectedRow = tblsach.getSelectedRow();

        // Kiểm tra xem hàng có được chọn không
        if (selectedRow >= 0) {
            // Lấy model của bảng
            DefaultTableModel model = (DefaultTableModel) tblsach.getModel();

            try {
                // Gán dữ liệu từ bảng vào các trường
                txtMasach.setText(model.getValueAt(selectedRow, 0).toString());   // Cột 0: ID
                txtTenSach.setText(model.getValueAt(selectedRow, 1).toString()); // Cột 1: Tên sách

                //txtTenTacGia.setText(model.getValueAt(selectedRow, 2).toString()); // Cột 2: Tác giả
                //Matg.setText(model.getValueAt(selectedRow, 2).toString());        // Gán lại tác giả vào label/field riêng
                // Lấy mã tác giả và tra cứu tên tác giả
                String maTacGiaStr = model.getValueAt(selectedRow, 2).toString(); // Cột 2: Mã tác giả
                Matg.setText(maTacGiaStr); // Gán mã tác giả vào Matg
                // Tìm tên tác giả từ Map
                String tenTacGia = tacGiaMap.getOrDefault(maTacGiaStr, "Không tìm thấy tác giả");
                txtTenTacGia.setText(tenTacGia); // Hiển thị tên tác giả

                //cmbTheloai.setSelectedItem(model.getValueAt(selectedRow, 3));     // Cột 3: Thể loại
                //String theLoai = model.getValueAt(selectedRow, 3).toString();
                // Lấy mã thể loại (cột 3) và tìm tên thể loại tương ứng
                String maTheLoaiStr = model.getValueAt(selectedRow, 3).toString();
                int maTheLoai = Integer.parseInt(maTheLoaiStr); // Chuyển đổi mã thể loại từ String sang int
                Matl.setText(maTheLoaiStr);
                // Tìm tên thể loại tương ứng trong theLoaiMap
                String tenTheLoai = theLoaiMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == maTheLoai)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse("Chọn thể loại");

                // Đặt giá trị vào ComboBox
                cmbTheloai.setSelectedItem(tenTheLoai);

                //Matl.setText(model.getValueAt(selectedRow, 3).toString());        // Gán thể loại (nếu cần vào label khác)

                // Gán Nhà xuất bản (cột 4)
                txtNhaxuatban.setText(model.getValueAt(selectedRow, 4).toString());

                // Chuyển đổi Số lượng (cột 5)
                String soLuongStr = model.getValueAt(selectedRow, 5).toString();
                if (soLuongStr.matches("\\d+")) { // Kiểm tra số nguyên dương
                    spnSoluong.setValue(Integer.valueOf(soLuongStr));
                } else {
                    throw new NumberFormatException("Số lượng không hợp lệ: " + soLuongStr);
                }

                // Gán Giá (cột 6)
                txtGia.setText(model.getValueAt(selectedRow, 6).toString());

                // Gán Ngày nhập (cột 7)
                String ngayNhapStr = model.getValueAt(selectedRow, 7).toString();
                ((JTextField) NgayNhap.getDateEditor().getUiComponent()).setText(ngayNhapStr);
                txtmota.setText(model.getValueAt(selectedRow, 8).toString());

                // Lấy đường dẫn ảnh từ cột ảnh
                byte[] imagePath = (byte[]) model.getValueAt(selectedRow, 9);
                if (imagePath != null) {
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imagePath).getImage()
                        .getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), Image.SCALE_SMOOTH));
                    lblAnh.setIcon(imgIcon);
                } else {
                    lblAnh.setIcon(null);
                    JOptionPane.showMessageDialog(this, "Ảnh không xác định.", "Thông báo", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ: " + e.getMessage(),
                    "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_tblsachMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        // Kiểm tra trường mã sách
        String masachStr = txtMasach.getText();
        String tensach = txtTenSach.getText();

        if (masachStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một cuốn sách để xóa.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Xác nhận xóa
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa cuốn "+ tensach +" không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Chuyển đổi mã sách sang kiểu Integer
                Integer masach = Integer.valueOf(masachStr);

                // Gọi phương thức DeleteData
                boolean isDeleted = dssach.DeleteData(masach);

                if (isDeleted) {
                    JOptionPane.showMessageDialog(this,
                        "Xóa thành công cuốn sách "+ tensach +".",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);

                    // Làm mới dữ liệu hiển thị
                    ClearData();
                    ShowData();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xóa sách. Vui lòng thử lại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Mã sách không hợp lệ.","Lỗi", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải lại dữ liệu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(QLSach.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        try {
            String masach = txtMasach.getText().trim();
            String tensach = txtTenSach.getText();
            if (masach.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một cuốn sách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tenTacGia = txtTenTacGia.getText();
            if (tenTacGia.isEmpty()) {
                lblWarn1.setVisible(true);
                return;
            }

            int matacgia;
            try {
                matacgia = Integer.parseInt(Matg.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã tác giả phải là số!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int matheloai;
            try {
                matheloai = Integer.parseInt(Matl.getText().trim());
                if (matheloai <= 0) {
                    lblWarn2.setVisible(true);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã thể loại phải là số!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int soluong;
            try {
                soluong = Integer.parseInt(spnSoluong.getValue().toString());
                if (soluong < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng không được âm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (NgayNhap.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Ngày nhập không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngaynhap = dateFormat.format(NgayNhap.getDate());

            String nhaxuatban = txtNhaxuatban.getText();
            if (nhaxuatban.isEmpty()) {
                lblWarn3.setVisible(true);
                return;
            }

            double giasach;
            try {
                giasach = Double.parseDouble(txtGia.getText().trim());
                if (giasach <= 0) {
                    lblWarn4.setVisible(true);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Giá sách phải là số!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String mota = txtmota.getText();

            SachModel sach = new SachModel();
            sach.setIdSach(masach);
            sach.setTenSach(tensach);
            sach.setIdTacGia(matacgia);
            sach.setIdTheLoai(matheloai);
            sach.setNhaXuatBan(nhaxuatban);
            sach.setGiaSach(giasach);
            sach.setSoLuong(soluong);
            sach.setNgayNhan(ngaynhap);
            sach.setMoTa(mota);
            sach.setAnhSach(book_img);

            if (!dssach.EditData(sach)) {
                JOptionPane.showMessageDialog(this, "Sửa thất bại!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sách đã chỉnh sửa thành công!", "Info", JOptionPane.INFORMATION_MESSAGE);
                ClearData();
                ShowData();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(QLSach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        try {
            // TODO add your handling code here:
            //Them sach moi
            //Lay gia tri
            String masach = txtMasach.getText();
            String tensach = txtTenSach.getText();
            if (tensach.isEmpty()) {
                lblWarn.setVisible(true);
                return;
            }
            // Kiểm tra tên  đã tồn tại
            if (dssach.IsDuplicateBook(tensach)) {
                JOptionPane.showMessageDialog(null, "Sách này đã tồn tại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                txtMasach.setText("");
                txtTenSach.setText("");
                Matg.setText("ID");
                txtTenTacGia.setText("");
                Matl.setText("ID");
                cmbTheloai.setSelectedIndex(0);
                spnSoluong.setValue(0);
                NgayNhap.setDate(null);
                txtNhaxuatban.setText("");
                txtGia.setText("");
                txtmota.setText("");
                lblAnh.setIcon(null);  // Gán null cho icon của lblAnh

                return; // Dừng thực hiện
            }
            // Kiểm tra Tên Tác Giả
            String tenTacGia = txtTenTacGia.getText();
            if (tenTacGia.isEmpty()) {
                lblWarn1.setVisible(true);  // Hiển thị thông báo cảnh báo
                return;
            } else {
                lblWarn1.setVisible(false);  // Ẩn thông báo nếu trường không rỗng
            }
            //int matacgia = Integer.parseInt(Matg.getText());
            int matacgia;
            try {
                matacgia = Integer.parseInt(Matg.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Mã tác giả phải là một số và không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            /*int matheloai = Integer.parseInt(Matl.getText());
            // Kiểm tra thể loại
            if (matheloai <= 0) {
                lblWarn2.setVisible(true);
                return;
            }*/
            int matheloai;
            String matheloaiText = Matl.getText().trim();
            // Kiểm tra nếu mã thể loại rỗng
            if (matheloaiText.isEmpty()) {
                lblWarn2.setVisible(true);  // Hiển thị thông báo lỗi nếu mã thể loại rỗng
                return;
            }

            try {
                matheloai = Integer.parseInt(matheloaiText);  // Chuyển đổi mã thể loại thành số nguyên
                lblWarn2.setVisible(false);  // Ẩn thông báo lỗi nếu mã thể loại hợp lệ
                // Tiến hành các bước tiếp theo với mã thể loại matheloai
            } catch (NumberFormatException e) {
                lblWarn2.setVisible(true);  // Hiển thị thông báo lỗi nếu không thể chuyển thành số
                return;
            }

            //int soluong= Integer.parseInt(spnSoluong.getValue().toString());
            int soluong;
            try {
                soluong = Integer.parseInt(spnSoluong.getValue().toString());
                if (soluong < 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng không được âm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là một số nguyên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Kiểm tra ngày nhập
            if (NgayNhap.getDate() == null) {
                JOptionPane.showMessageDialog(this, "Ngày nhập không được để trống!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngaynhap = dateFormat.format(NgayNhap.getDate());

            String nhaxuatban = txtNhaxuatban.getText();
            if (nhaxuatban.isEmpty()) {
                lblWarn3.setVisible(true);
                return;
            }
            //double giasach = Double.parseDouble(txtGia.getText());
            double giasach;

            // Kiểm tra nếu giá sách rỗng
            String giaSachText = txtGia.getText().trim();
            if (giaSachText.isEmpty()) {
                lblWarn4.setVisible(true);  // Hiển thị thông báo lỗi nếu giá sách rỗng
                return;
            } else {
                try {
                    // Chuyển giá sách thành số thập phân (double)
                    giasach = Double.parseDouble(giaSachText);

                    // Kiểm tra nếu giá sách <= 0
                    if (giasach <= 0) {
                        lblWarn5.setVisible(true);  // Hiển thị thông báo lỗi nếu giá sách không hợp lệ (<= 0)
                        return;
                    } else {
                        lblWarn4.setVisible(false);  // Ẩn lblWarn4 nếu giá sách hợp lệ
                        lblWarn5.setVisible(false);  // Ẩn lblWarn5 nếu giá sách hợp lệ

                    }
                } catch (NumberFormatException e) {
                    // Nếu không phải là số, hiển thị thông báo lỗi
                    lblWarn5.setVisible(true);
                    lblWarn4.setVisible(false);
                    return;
                }
            }

            String mota = txtmota.getText();

            if (book_img == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh bìa sách!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Tạo đối tượng SachModel
            SachModel sach = new SachModel();
            sach.setTenSach(tensach);
            sach.setIdTacGia(matacgia);
            sach.setIdTheLoai(matheloai);
            sach.setNhaXuatBan(nhaxuatban);
            sach.setGiaSach(giasach);
            sach.setSoLuong(soluong);
            sach.setNgayNhan(ngaynhap);
            sach.setMoTa(mota);
            sach.setAnhSach(book_img);

            if (cothem) {
                try {
                    // Thêm sách vào cơ sở dữ liệu
                    if (dssach.InsertBook(sach)) {
                        try {
                            JOptionPane.showMessageDialog(this, "Thêm sách thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                            // Reset form hoặc cập nhật lại danh sách sách hiển thị
                            ClearData();
                            ShowData();
                        } catch (SQLException ex) {
                            Logger.getLogger(QLSach.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm sách thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(QLSach.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(QLSach.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        txtMasach.setText("");
        txtTenSach.setText("");
        Matg.setText("ID");
        txtTenTacGia.setText("");
        Matl.setText("ID");
        cmbTheloai.setSelectedIndex(0);
        spnSoluong.setValue(0);
        NgayNhap.setDate(null);
        txtNhaxuatban.setText("");
        txtGia.setText("");
        txtmota.setText("");
        lblAnh.setIcon(null);  // Gán null cho icon của lblAnh

        // Reset lại các biến liên quan đến ảnh
        book_img = null;  // Đặt lại biến ảnh về null
        filename = null;  // Đặt lại biến filename về null
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnChonanhbiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChonanhbiaActionPerformed
        // TODO add your handling code here:
        // Tạo JFileChooser để chọn tệp ảnh
        JFileChooser choose = new JFileChooser();
        choose.setDialogTitle("Chọn ảnh bìa sách");
        choose.showOpenDialog(null);
        File f = choose.getSelectedFile();
        filename = f.getAbsolutePath();
        // Hiển thị ảnh trong lblAnh
        ImageIcon imgIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblAnh.getWidth(), lblAnh.getHeight(), Image.SCALE_SMOOTH));
        lblAnh.setIcon(imgIcon); // Gắn hình ảnh vào JLabel
        try{
            File anh = new File(filename);
            FileInputStream fis = new FileInputStream(anh);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            for(int readNum; (readNum=fis.read(buf))!= -1;){
                bos.write(buf,0,readNum);
            }
            book_img = bos.toByteArray() ;
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,e);
        }
    }//GEN-LAST:event_btnChonanhbiaActionPerformed

    private void MatlKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MatlKeyReleased
        // TODO add your handling code here:
        int matheloai = Integer.parseInt(Matl.getText());
        // Kiểm tra thể loại
        if (matheloai > 0) {
            lblWarn2.setVisible(false);
        } else{
            lblWarn2.setVisible(true);
        }
    }//GEN-LAST:event_MatlKeyReleased

    private void btnChon_TacgiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChon_TacgiaActionPerformed
        try {
            DSTacGia authors = new DSTacGia(this, true); // Truyền chính form QLSach
            authors.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mở danh sách tác giả: " + e.getMessage(),
                                          "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
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
            java.util.logging.Logger.getLogger(QLSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                QLSach dialog = new QLSach(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(QLSach.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Matg;
    private javax.swing.JTextField Matl;
    private com.toedter.calendar.JDateChooser NgayNhap;
    private javax.swing.JButton btnChon_Tacgia;
    private javax.swing.JButton btnChonanhbia;
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cmbTheloai;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel_Theloaisach;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAnh;
    private javax.swing.JLabel lblGia;
    private javax.swing.JLabel lblMasach;
    private javax.swing.JLabel lblSoluong;
    private javax.swing.JLabel lblTacgia;
    private javax.swing.JLabel lblTensach;
    private javax.swing.JLabel lblTheloai;
    private javax.swing.JLabel lblTieude;
    private javax.swing.JLabel lblWarn;
    private javax.swing.JLabel lblWarn1;
    private javax.swing.JLabel lblWarn2;
    private javax.swing.JLabel lblWarn3;
    private javax.swing.JLabel lblWarn4;
    private javax.swing.JLabel lblWarn5;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JSpinner spnSoluong;
    private javax.swing.JTable tblsach;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMasach;
    private javax.swing.JTextField txtNhaxuatban;
    private javax.swing.JTextField txtTenSach;
    private static javax.swing.JTextField txtTenTacGia;
    private javax.swing.JTextArea txtmota;
    // End of variables declaration//GEN-END:variables
}
