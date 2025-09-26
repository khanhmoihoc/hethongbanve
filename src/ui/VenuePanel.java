package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import data.DatabaseManager;

public class VenuePanel extends JPanel {
    private DefaultTableModel model;
    private JTable table;

    public VenuePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Load sample data
        refreshData();
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Title
        JLabel lblTitle = new JLabel("QUẢN LÝ ĐỊA ĐIỂM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(52, 73, 94));

        // Add Button
        JButton btnAdd = new JButton("+ Thêm Địa Điểm");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setPreferredSize(new Dimension(160, 40));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> openVenueDialog(null));

        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(btnAdd, BorderLayout.EAST);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Create table model
        String[] columns = {"ID", "Tên địa điểm", "Địa chỉ", "Sức chứa", "Loại địa điểm", "Ngày tạo", "Trạng thái", "Hành động"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7; // Only action column is editable
            }
        };

        table = new JTable(model);
        table.setRowHeight(50);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setSelectionBackground(new Color(230, 247, 255));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowGrid(true);

        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);   // ID
        columnModel.getColumn(1).setPreferredWidth(200);  // Tên
        columnModel.getColumn(2).setPreferredWidth(250);  // Địa chỉ
        columnModel.getColumn(3).setPreferredWidth(80);   // Sức chứa
        columnModel.getColumn(4).setPreferredWidth(120);  // Loại
        columnModel.getColumn(5).setPreferredWidth(100);  // Ngày tạo
        columnModel.getColumn(6).setPreferredWidth(80);   // Trạng thái
        columnModel.getColumn(7).setPreferredWidth(80);   // Hành động (giảm từ 180 xuống 80)

        // Custom renderer for status column
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if ("Hoạt động".equals(value)) {
                    setForeground(new Color(39, 174, 96));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setForeground(new Color(231, 76, 60));
                    setFont(getFont().deriveFont(Font.BOLD));
                }
                
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });

        // Custom renderer for capacity column
        table.getColumnModel().getColumn(3).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.CENTER);
                if (value != null) {
                    setText(String.format("%,d", (Integer)value));
                }
                return this;
            }
        });

        // Custom renderer for action buttons
        table.getColumnModel().getColumn(7).setCellRenderer(new VenueActionButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new VenueActionButtonEditor());

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 12));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 40));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Venue Action Button Renderer
    class VenueActionButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnActions;

        public VenueActionButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));
            setOpaque(true);

            // Tạo nút 3 chấm
            btnActions = new JButton("⋮");
            btnActions.setFont(new Font("Arial", Font.BOLD, 16));
            btnActions.setBackground(new Color(149, 165, 166));
            btnActions.setForeground(Color.WHITE);
            btnActions.setFocusPainted(false);
            btnActions.setBorderPainted(false);
            btnActions.setPreferredSize(new Dimension(35, 30));
            btnActions.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnActions.setToolTipText("Tùy chọn hành động");

            add(btnActions);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                setBackground(table.getBackground());
            }
            return this;
        }
    }

    // Venue Action Button Editor
    class VenueActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton btnActions;
        private int currentRow;

        public VenueActionButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
            panel.setOpaque(true);

            // Tạo nút 3 chấm với popup menu
            btnActions = new JButton("⋮");
            btnActions.setFont(new Font("Arial", Font.BOLD, 16));
            btnActions.setBackground(new Color(149, 165, 166));
            btnActions.setForeground(Color.WHITE);
            btnActions.setFocusPainted(false);
            btnActions.setBorderPainted(false);
            btnActions.setPreferredSize(new Dimension(35, 30));
            btnActions.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnActions.setToolTipText("Tùy chọn hành động");

            btnActions.addActionListener(e -> showPopupMenu(btnActions));

            panel.add(btnActions);
        }

        private void showPopupMenu(JButton source) {
            JPopupMenu popupMenu = new JPopupMenu();
            
            // Menu item Sửa
            JMenuItem editItem = new JMenuItem("✏️ Sửa địa điểm");
            editItem.setFont(new Font("Arial", Font.PLAIN, 12));
            editItem.addActionListener(e -> {
                fireEditingStopped();
                editVenue(currentRow);
            });
            
            // Menu item Quản lý chỗ ngồi
            JMenuItem seatsItem = new JMenuItem("🪑 Quản lý chỗ ngồi");
            seatsItem.setFont(new Font("Arial", Font.PLAIN, 12));
            seatsItem.setForeground(new Color(52, 152, 219));
            seatsItem.addActionListener(e -> {
                fireEditingStopped();
                openSeatManagement(currentRow);
            });
            
            // Menu item Xóa
            JMenuItem deleteItem = new JMenuItem("🗑️ Xóa địa điểm");
            deleteItem.setFont(new Font("Arial", Font.PLAIN, 12));
            deleteItem.setForeground(new Color(231, 76, 60));
            deleteItem.addActionListener(e -> {
                fireEditingStopped();
                deleteVenue(currentRow);
            });

            popupMenu.add(editItem);
            popupMenu.addSeparator();
            popupMenu.add(seatsItem);
            popupMenu.addSeparator();
            popupMenu.add(deleteItem);
            
            // Hiển thị popup menu
            popupMenu.show(source, 0, source.getHeight());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            currentRow = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return "";
        }
    }

    private JButton createActionButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 10));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(55, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openVenueDialog(Integer venueId) {
        VenueDialog dialog = new VenueDialog((JFrame) SwingUtilities.getWindowAncestor(this), venueId);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            VenueData venueData = dialog.getVenueData();
            if (venueId == null) {
                // Add new venue
                addVenueToTable(venueData);
            } else {
                // Update existing venue
                updateVenueInTable(venueId, venueData);
            }
        }
    }

    private void editVenue(int row) {
        Integer venueId = (Integer) model.getValueAt(row, 0);
        openVenueDialog(venueId);
    }

    private void deleteVenue(int row) {
        String venueName = (String) model.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa địa điểm '" + venueName + "'?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa địa điểm thành công!");
        }
    }

    private void openSeatManagement(int row) {
        Integer venueId = (Integer) model.getValueAt(row, 0);
        String venueName = (String) model.getValueAt(row, 1);
        
        SeatManagementDialog dialog = new SeatManagementDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            venueId, 
            venueName
        );
        dialog.setVisible(true);
    }

    private void addVenueToTable(VenueData venueData) {
        int newId = model.getRowCount() + 1;
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        model.addRow(new Object[]{
                newId,
                venueData.getName(),
                venueData.getAddress(),
                venueData.getCapacity(),
                venueData.getType(),
                currentTime,
                venueData.getStatus(),
                ""
        });
    }

    private void updateVenueInTable(Integer venueId, VenueData venueData) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (venueId.equals(model.getValueAt(i, 0))) {
                model.setValueAt(venueData.getName(), i, 1);
                model.setValueAt(venueData.getAddress(), i, 2);
                model.setValueAt(venueData.getCapacity(), i, 3);
                model.setValueAt(venueData.getType(), i, 4);
                model.setValueAt(venueData.getStatus(), i, 6);
                break;
            }
        }
    }

    public void refreshData() {
        // Clear existing data
        model.setRowCount(0);
        
        // Load data from DatabaseManager
        DatabaseManager db = DatabaseManager.getInstance();
        java.util.List<DatabaseManager.Venue> venues = db.getAllVenues();
        
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        for (DatabaseManager.Venue venue : venues) {
            String status = "Hoạt động"; // Default status
            String venueType = getVenueTypeFromName(venue.getName());
            
            model.addRow(new Object[]{
                venue.getId(),
                venue.getName(),
                venue.getAddress() + ", " + venue.getCity(),
                venue.getCapacity(),
                venueType,
                currentTime,
                status,
                ""
            });
        }
    }
    
    private String getVenueTypeFromName(String name) {
        name = name.toLowerCase();
        if (name.contains("rạp")) return "Rạp phim";
        if (name.contains("nhà thi đấu")) return "Nhà thi đấu";
        if (name.contains("nhà hát")) return "Nhà hát";
        if (name.contains("sân vận động")) return "Sân vận động";
        if (name.contains("bến xe")) return "Bến xe";
        if (name.contains("bảo tàng")) return "Bảo tàng";
        if (name.contains("trung tâm")) return "Trung tâm hội nghị";
        if (name.contains("quảng trường")) return "Quảng trường";
        return "Địa điểm tổ chức";
    }

    // Venue Data class
    static class VenueData {
        private String name, address, type, status;
        private Integer capacity;

        public VenueData(String name, String address, Integer capacity, String type, String status) {
            this.name = name;
            this.address = address;
            this.capacity = capacity;
            this.type = type;
            this.status = status;
        }

        // Getters
        public String getName() { return name; }
        public String getAddress() { return address; }
        public Integer getCapacity() { return capacity; }
        public String getType() { return type; }
        public String getStatus() { return status; }
    }

    // Venue Dialog
    class VenueDialog extends JDialog {
        private JTextField txtName, txtAddress, txtCapacity;
        private JComboBox<String> cmbType, cmbStatus;
        private boolean confirmed = false;

        public VenueDialog(JFrame parent, Integer venueId) {
            super(parent, venueId == null ? "Thêm Địa Điểm Mới" : "Sửa Thông Tin Địa Điểm", true);
            initComponents();
            if (venueId != null) {
                loadVenueData(venueId);
            }
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setSize(750, 600);
            setLocationRelativeTo(getParent());

            // Form Panel
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // Venue Name
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Tên địa điểm:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtName = new JTextField(25);
            formPanel.add(txtName, gbc);

            // Address
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Địa chỉ:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtAddress = new JTextField(25);
            formPanel.add(txtAddress, gbc);

            // Capacity
            gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Sức chứa:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtCapacity = new JTextField(25);
            formPanel.add(txtCapacity, gbc);

            // Type
            gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Loại địa điểm:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            cmbType = new JComboBox<>(new String[]{"Nhà hát", "Sân vận động", "Cung văn hóa", "Trung tâm hội nghị", "Khác"});
            formPanel.add(cmbType, gbc);

            // Status
            gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Trạng thái:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            cmbStatus = new JComboBox<>(new String[]{"Hoạt động", "Bảo trì", "Tạm đóng"});
            formPanel.add(cmbStatus, gbc);

            add(formPanel, BorderLayout.CENTER);

            // Button Panel
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);

            JButton btnSave = new JButton("Lưu");
            btnSave.setBackground(new Color(46, 204, 113));
            btnSave.setForeground(Color.WHITE);
            btnSave.setFocusPainted(false);
            btnSave.addActionListener(e -> saveVenue());

            JButton btnCancel = new JButton("Hủy");
            btnCancel.setBackground(new Color(231, 76, 60));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFocusPainted(false);
            btnCancel.addActionListener(e -> dispose());

            buttonPanel.add(btnSave);
            buttonPanel.add(btnCancel);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void loadVenueData(Integer venueId) {
            for (int i = 0; i < model.getRowCount(); i++) {
                if (venueId.equals(model.getValueAt(i, 0))) {
                    txtName.setText((String) model.getValueAt(i, 1));
                    txtAddress.setText((String) model.getValueAt(i, 2));
                    txtCapacity.setText(model.getValueAt(i, 3).toString());
                    cmbType.setSelectedItem(model.getValueAt(i, 4));
                    cmbStatus.setSelectedItem(model.getValueAt(i, 6));
                    break;
                }
            }
        }

        private void saveVenue() {
            if (validateInput()) {
                confirmed = true;
                dispose();
            }
        }

        private boolean validateInput() {
            if (txtName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên địa điểm!");
                return false;
            }
            if (txtAddress.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập địa chỉ!");
                return false;
            }
            try {
                Integer.parseInt(txtCapacity.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập sức chứa hợp lệ!");
                return false;
            }
            return true;
        }

        public boolean isConfirmed() {
            return confirmed;
        }

        public VenueData getVenueData() {
            return new VenueData(
                    txtName.getText().trim(),
                    txtAddress.getText().trim(),
                    Integer.parseInt(txtCapacity.getText().trim()),
                    (String) cmbType.getSelectedItem(),
                    (String) cmbStatus.getSelectedItem()
            );
        }
    }

    // Seat Management Dialog
    class SeatManagementDialog extends JDialog {
        private DefaultTableModel seatModel;
        private JTable seatTable;
        private Integer venueId;
        private String venueName;

        public SeatManagementDialog(JFrame parent, Integer venueId, String venueName) {
            super(parent, "Quản lý Chỗ ngồi - " + venueName, true);
            this.venueId = venueId;
            this.venueName = venueName;
            initComponents();
            loadSeatData();
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setSize(1100, 800);
            setLocationRelativeTo(getParent());

            // Header
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(Color.WHITE);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

            JLabel lblTitle = new JLabel("QUẢN LÝ CHỖ NGỒI");
            lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
            lblTitle.setForeground(new Color(52, 73, 94));

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.setBackground(Color.WHITE);

            JButton btnAddSection = new JButton("+ Thêm Khu vực");
            btnAddSection.setBackground(new Color(46, 204, 113));
            btnAddSection.setForeground(Color.WHITE);
            btnAddSection.setFocusPainted(false);
            btnAddSection.addActionListener(e -> addSeatSection());

            JButton btnSeatMap = new JButton("Xem Sơ đồ ghế");
            btnSeatMap.setBackground(new Color(52, 152, 219));
            btnSeatMap.setForeground(Color.WHITE);
            btnSeatMap.setFocusPainted(false);
            btnSeatMap.addActionListener(e -> showSeatMap());

            buttonPanel.add(btnAddSection);
            buttonPanel.add(btnSeatMap);

            headerPanel.add(lblTitle, BorderLayout.WEST);
            headerPanel.add(buttonPanel, BorderLayout.EAST);

            // Table
            String[] columns = {"ID", "Khu vực", "Loại ghế", "Số hàng", "Ghế/hàng", "Tổng ghế", "Giá (VNĐ)", "Hành động"};
            seatModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 7;
                }
            };

            seatTable = new JTable(seatModel);
            seatTable.setRowHeight(40);
            seatTable.setFont(new Font("Arial", Font.PLAIN, 12));

            // Column widths
            TableColumnModel columnModel = seatTable.getColumnModel();
            columnModel.getColumn(0).setPreferredWidth(50);
            columnModel.getColumn(1).setPreferredWidth(120);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(80);
            columnModel.getColumn(4).setPreferredWidth(80);
            columnModel.getColumn(5).setPreferredWidth(80);
            columnModel.getColumn(6).setPreferredWidth(100);
            columnModel.getColumn(7).setPreferredWidth(120);

            // Price column renderer
            seatTable.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, 
                        boolean isSelected, boolean hasFocus, int row, int column) {
                    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    setHorizontalAlignment(SwingConstants.RIGHT);
                    if (value != null) {
                        setText(String.format("%,d", (Integer)value));
                    }
                    return this;
                }
            });

            // Action column
            seatTable.getColumnModel().getColumn(7).setCellRenderer(new SeatActionRenderer());
            seatTable.getColumnModel().getColumn(7).setCellEditor(new SeatActionEditor());

            JScrollPane scrollPane = new JScrollPane(seatTable);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

            // Bottom panel
            JPanel bottomPanel = new JPanel(new FlowLayout());
            bottomPanel.setBackground(Color.WHITE);
            
            JButton btnClose = new JButton("Đóng");
            btnClose.setBackground(new Color(149, 165, 166));
            btnClose.setForeground(Color.WHITE);
            btnClose.setFocusPainted(false);
            btnClose.addActionListener(e -> dispose());
            bottomPanel.add(btnClose);

            add(headerPanel, BorderLayout.NORTH);
            add(scrollPane, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);
        }

        private void loadSeatData() {
            // Sample seat data
            seatModel.addRow(new Object[]{1, "Khu A - VIP", "VIP", 10, 20, 200, 500000, ""});
            seatModel.addRow(new Object[]{2, "Khu B - Thường", "Thường", 20, 25, 500, 200000, ""});
            seatModel.addRow(new Object[]{3, "Khu C - Sinh viên", "Sinh viên", 15, 20, 300, 100000, ""});
        }

        private void addSeatSection() {
            JOptionPane.showMessageDialog(this, "Chức năng thêm khu vực ghế đang được phát triển!");
        }

        private void showSeatMap() {
            SeatMapDialog mapDialog = new SeatMapDialog(this, venueName);
            mapDialog.setVisible(true);
        }

        // Seat Action Renderer
        class SeatActionRenderer extends JPanel implements TableCellRenderer {
            private JButton btnEdit, btnDelete;

            public SeatActionRenderer() {
                setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
                setOpaque(true);

                btnEdit = new JButton("Sửa");
                btnEdit.setFont(new Font("Arial", Font.BOLD, 10));
                btnEdit.setBackground(new Color(243, 156, 18));
                btnEdit.setForeground(Color.WHITE);
                btnEdit.setFocusPainted(false);
                btnEdit.setPreferredSize(new Dimension(50, 25));

                btnDelete = new JButton("Xóa");
                btnDelete.setFont(new Font("Arial", Font.BOLD, 10));
                btnDelete.setBackground(new Color(231, 76, 60));
                btnDelete.setForeground(Color.WHITE);
                btnDelete.setFocusPainted(false);
                btnDelete.setPreferredSize(new Dimension(50, 25));

                add(btnEdit);
                add(btnDelete);
            }

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (isSelected) {
                    setBackground(table.getSelectionBackground());
                } else {
                    setBackground(table.getBackground());
                }
                return this;
            }
        }

        // Seat Action Editor
        class SeatActionEditor extends AbstractCellEditor implements TableCellEditor {
            private JPanel panel;
            private int currentRow;

            public SeatActionEditor() {
                panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
                panel.setOpaque(true);

                JButton btnEdit = new JButton("Sửa");
                btnEdit.setFont(new Font("Arial", Font.BOLD, 10));
                btnEdit.setBackground(new Color(243, 156, 18));
                btnEdit.setForeground(Color.WHITE);
                btnEdit.setFocusPainted(false);
                btnEdit.setPreferredSize(new Dimension(50, 25));
                btnEdit.addActionListener(e -> {
                    fireEditingStopped();
                    editSeatSection(currentRow);
                });

                JButton btnDelete = new JButton("Xóa");
                btnDelete.setFont(new Font("Arial", Font.BOLD, 10));
                btnDelete.setBackground(new Color(231, 76, 60));
                btnDelete.setForeground(Color.WHITE);
                btnDelete.setFocusPainted(false);
                btnDelete.setPreferredSize(new Dimension(50, 25));
                btnDelete.addActionListener(e -> {
                    fireEditingStopped();
                    deleteSeatSection(currentRow);
                });

                panel.add(btnEdit);
                panel.add(btnDelete);
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                    boolean isSelected, int row, int column) {
                currentRow = row;
                return panel;
            }

            @Override
            public Object getCellEditorValue() {
                return "";
            }
        }

        private void editSeatSection(int row) {
            JOptionPane.showMessageDialog(this, "Chức năng sửa khu vực ghế đang được phát triển!");
        }

        private void deleteSeatSection(int row) {
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa khu vực ghế này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                seatModel.removeRow(row);
            }
        }
    }

    // Seat Map Dialog
    class SeatMapDialog extends JDialog {
        public SeatMapDialog(JDialog parent, String venueName) {
            super(parent, "Sơ đồ ghế - " + venueName, true);
            initComponents();
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setSize(900, 650);
            setLocationRelativeTo(getParent());

            // Main panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.setBackground(Color.WHITE);
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Stage/Screen
            JPanel stagePanel = new JPanel();
            stagePanel.setBackground(new Color(52, 73, 94));
            stagePanel.setPreferredSize(new Dimension(0, 50));
            JLabel lblStage = new JLabel("SÂN KHẤU", SwingConstants.CENTER);
            lblStage.setForeground(Color.WHITE);
            lblStage.setFont(new Font("Arial", Font.BOLD, 16));
            stagePanel.add(lblStage);

            // Seat grid
            JPanel seatGridPanel = new JPanel(new GridBagLayout());
            seatGridPanel.setBackground(Color.WHITE);

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(2, 2, 2, 2);

            // VIP Section (Khu A)
            gbc.gridx = 0; gbc.gridy = 0;
            seatGridPanel.add(createSeatSection("KHU A - VIP", 5, 10, new Color(255, 215, 0)), gbc);

            gbc.gridy = 1;
            seatGridPanel.add(Box.createVerticalStrut(20), gbc);

            // Regular Section (Khu B)
            gbc.gridy = 2;
            seatGridPanel.add(createSeatSection("KHU B - THƯỜNG", 8, 12, new Color(135, 206, 250)), gbc);

            gbc.gridy = 3;
            seatGridPanel.add(Box.createVerticalStrut(20), gbc);

            // Student Section (Khu C)
            gbc.gridy = 4;
            seatGridPanel.add(createSeatSection("KHU C - SINH VIÊN", 6, 10, new Color(144, 238, 144)), gbc);

            // Legend
            JPanel legendPanel = createLegendPanel();

            mainPanel.add(stagePanel, BorderLayout.NORTH);
            mainPanel.add(seatGridPanel, BorderLayout.CENTER);
            mainPanel.add(legendPanel, BorderLayout.SOUTH);

            add(mainPanel, BorderLayout.CENTER);

            // Bottom buttons
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);
            
            JButton btnClose = new JButton("Đóng");
            btnClose.setBackground(new Color(149, 165, 166));
            btnClose.setForeground(Color.WHITE);
            btnClose.setFocusPainted(false);
            btnClose.addActionListener(e -> dispose());
            buttonPanel.add(btnClose);

            add(buttonPanel, BorderLayout.SOUTH);
        }

        private JPanel createSeatSection(String sectionName, int rows, int seatsPerRow, Color seatColor) {
            JPanel section = new JPanel(new BorderLayout());
            section.setBackground(Color.WHITE);
            section.setBorder(BorderFactory.createTitledBorder(sectionName));

            JPanel seatGrid = new JPanel(new GridLayout(rows, seatsPerRow, 2, 2));
            seatGrid.setBackground(Color.WHITE);

            for (int i = 0; i < rows * seatsPerRow; i++) {
                JButton seat = new JButton();
                seat.setBackground(seatColor);
                seat.setPreferredSize(new Dimension(25, 25));
                seat.setBorderPainted(false);
                seat.setFocusPainted(false);
                seat.addActionListener(e -> {
                    if (seat.getBackground().equals(seatColor)) {
                        seat.setBackground(Color.RED); // Đã chọn
                    } else {
                        seat.setBackground(seatColor); // Bỏ chọn
                    }
                });
                seatGrid.add(seat);
            }

            section.add(seatGrid, BorderLayout.CENTER);
            return section;
        }

        private JPanel createLegendPanel() {
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createTitledBorder("Chú thích"));

            // VIP
            JButton vipLegend = new JButton("VIP");
            vipLegend.setBackground(new Color(255, 215, 0));
            vipLegend.setPreferredSize(new Dimension(80, 30));
            vipLegend.setEnabled(false);

            // Regular
            JButton regularLegend = new JButton("Thường");
            regularLegend.setBackground(new Color(135, 206, 250));
            regularLegend.setPreferredSize(new Dimension(80, 30));
            regularLegend.setEnabled(false);

            // Student
            JButton studentLegend = new JButton("Sinh viên");
            studentLegend.setBackground(new Color(144, 238, 144));
            studentLegend.setPreferredSize(new Dimension(80, 30));
            studentLegend.setEnabled(false);

            // Selected
            JButton selectedLegend = new JButton("Đã chọn");
            selectedLegend.setBackground(Color.RED);
            selectedLegend.setForeground(Color.WHITE);
            selectedLegend.setPreferredSize(new Dimension(80, 30));
            selectedLegend.setEnabled(false);

            panel.add(vipLegend);
            panel.add(regularLegend);
            panel.add(studentLegend);
            panel.add(selectedLegend);

            return panel;
        }
    }
}