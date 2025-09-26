package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import data.DatabaseManager;

public class UserPanel extends JPanel {
    private DefaultTableModel model;
    private JTable table;

    public UserPanel() {
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
        JLabel lblTitle = new JLabel("QUẢN LÝ NGƯỜI DÙNG");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(52, 73, 94));

        // Add Button
        JButton btnAdd = new JButton("+ Thêm Người Dùng");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setPreferredSize(new Dimension(180, 40));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> openUserDialog(null));

        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(btnAdd, BorderLayout.EAST);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Create table model
        String[] columns = {"ID", "Tên đầy đủ", "Email", "Số điện thoại", "Vai trò", "Ngày tạo", "Trạng thái", "Hành động"};
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
        columnModel.getColumn(1).setPreferredWidth(150);  // Tên
        columnModel.getColumn(2).setPreferredWidth(200);  // Email
        columnModel.getColumn(3).setPreferredWidth(120);  // SĐT
        columnModel.getColumn(4).setPreferredWidth(80);   // Vai trò
        columnModel.getColumn(5).setPreferredWidth(120);  // Ngày tạo
        columnModel.getColumn(6).setPreferredWidth(80);   // Trạng thái
        columnModel.getColumn(7).setPreferredWidth(150);  // Hành động

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

        // Custom renderer for action buttons
        table.getColumnModel().getColumn(7).setCellRenderer(new ActionButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new ActionButtonEditor());

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

    // Action Button Renderer
    class ActionButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnEdit, btnDelete;

        public ActionButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            setOpaque(true);

            btnEdit = createActionButton("Sửa", new Color(243, 156, 18), Color.WHITE);
            btnDelete = createActionButton("Xóa", new Color(231, 76, 60), Color.WHITE);

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

    // Action Button Editor
    class ActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton btnEdit, btnDelete;
        private int currentRow;

        public ActionButtonEditor() {
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
            panel.setOpaque(true);

            btnEdit = createActionButton("Sửa", new Color(243, 156, 18), Color.WHITE);
            btnDelete = createActionButton("Xóa", new Color(231, 76, 60), Color.WHITE);

            btnEdit.addActionListener(e -> {
                fireEditingStopped();
                editUser(currentRow);
            });

            btnDelete.addActionListener(e -> {
                fireEditingStopped();
                deleteUser(currentRow);
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

    private JButton createActionButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 11));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(60, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openUserDialog(Integer userId) {
        UserDialog dialog = new UserDialog((JFrame) SwingUtilities.getWindowAncestor(this), userId);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            UserData userData = dialog.getUserData();
            if (userId == null) {
                // Add new user
                addUserToTable(userData);
            } else {
                // Update existing user
                updateUserInTable(userId, userData);
            }
        }
    }

    private void editUser(int row) {
        Integer userId = (Integer) model.getValueAt(row, 0);
        openUserDialog(userId);
    }

    private void deleteUser(int row) {
        String userName = (String) model.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa người dùng '" + userName + "'?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa người dùng thành công!");
        }
    }

    private void addUserToTable(UserData userData) {
        int newId = model.getRowCount() + 1;
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        
        model.addRow(new Object[]{
                newId,
                userData.getFullName(),
                userData.getEmail(),
                userData.getPhone(),
                userData.getRole(),
                currentTime,
                userData.getStatus(),
                ""
        });
    }

    private void updateUserInTable(Integer userId, UserData userData) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (userId.equals(model.getValueAt(i, 0))) {
                model.setValueAt(userData.getFullName(), i, 1);
                model.setValueAt(userData.getEmail(), i, 2);
                model.setValueAt(userData.getPhone(), i, 3);
                model.setValueAt(userData.getRole(), i, 4);
                model.setValueAt(userData.getStatus(), i, 6);
                break;
            }
        }
    }

    public void refreshData() {
        // Clear existing data
        model.setRowCount(0);
        
        // Load data from DatabaseManager
        DatabaseManager db = DatabaseManager.getInstance();
        java.util.List<DatabaseManager.User> users = db.getAllUsers();
        
        for (DatabaseManager.User user : users) {
            String status = "admin".equals(user.getRole()) ? "Hoạt động" : "Hoạt động";
            String roleDisplay = "admin".equals(user.getRole()) ? "Admin" : 
                                "customer".equals(user.getRole()) ? "Khách hàng" : "User";
            
            model.addRow(new Object[]{
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                roleDisplay,
                user.getCreatedAt().substring(0, 10), // Lấy phần ngày tháng
                status,
                ""
            });
        }
    }

    // User Data class
    static class UserData {
        private String fullName, email, phone, role, status;

        public UserData(String fullName, String email, String phone, String role, String status) {
            this.fullName = fullName;
            this.email = email;
            this.phone = phone;
            this.role = role;
            this.status = status;
        }

        // Getters
        public String getFullName() { return fullName; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getRole() { return role; }
        public String getStatus() { return status; }
    }

    // User Dialog
    class UserDialog extends JDialog {
        private JTextField txtFullName, txtEmail, txtPhone;
        private JComboBox<String> cmbRole, cmbStatus;
        private boolean confirmed = false;

        public UserDialog(JFrame parent, Integer userId) {
            super(parent, userId == null ? "Thêm Người Dùng Mới" : "Sửa Thông Tin Người Dùng", true);
            initComponents();
            if (userId != null) {
                loadUserData(userId);
            }
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setSize(500, 400);
            setLocationRelativeTo(getParent());

            // Form Panel
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // Full Name
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Tên đầy đủ:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtFullName = new JTextField(20);
            formPanel.add(txtFullName, gbc);

            // Email
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Email:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtEmail = new JTextField(20);
            formPanel.add(txtEmail, gbc);

            // Phone
            gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Số điện thoại:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtPhone = new JTextField(20);
            formPanel.add(txtPhone, gbc);

            // Role
            gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Vai trò:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            cmbRole = new JComboBox<>(new String[]{"User", "Admin", "Manager"});
            formPanel.add(cmbRole, gbc);

            // Status
            gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Trạng thái:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            cmbStatus = new JComboBox<>(new String[]{"Hoạt động", "Tạm khóa"});
            formPanel.add(cmbStatus, gbc);

            add(formPanel, BorderLayout.CENTER);

            // Button Panel
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);

            JButton btnSave = new JButton("Lưu");
            btnSave.setBackground(new Color(46, 204, 113));
            btnSave.setForeground(Color.WHITE);
            btnSave.setFocusPainted(false);
            btnSave.addActionListener(e -> saveUser());

            JButton btnCancel = new JButton("Hủy");
            btnCancel.setBackground(new Color(231, 76, 60));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFocusPainted(false);
            btnCancel.addActionListener(e -> dispose());

            buttonPanel.add(btnSave);
            buttonPanel.add(btnCancel);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void loadUserData(Integer userId) {
            for (int i = 0; i < model.getRowCount(); i++) {
                if (userId.equals(model.getValueAt(i, 0))) {
                    txtFullName.setText((String) model.getValueAt(i, 1));
                    txtEmail.setText((String) model.getValueAt(i, 2));
                    txtPhone.setText((String) model.getValueAt(i, 3));
                    cmbRole.setSelectedItem(model.getValueAt(i, 4));
                    cmbStatus.setSelectedItem(model.getValueAt(i, 6));
                    break;
                }
            }
        }

        private void saveUser() {
            if (validateInput()) {
                confirmed = true;
                dispose();
            }
        }

        private boolean validateInput() {
            if (txtFullName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên đầy đủ!");
                return false;
            }
            if (txtEmail.getText().trim().isEmpty() || !txtEmail.getText().contains("@")) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập email hợp lệ!");
                return false;
            }
            if (txtPhone.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!");
                return false;
            }
            return true;
        }

        public boolean isConfirmed() {
            return confirmed;
        }

        public UserData getUserData() {
            return new UserData(
                    txtFullName.getText().trim(),
                    txtEmail.getText().trim(),
                    txtPhone.getText().trim(),
                    (String) cmbRole.getSelectedItem(),
                    (String) cmbStatus.getSelectedItem()
            );
        }
    }
}