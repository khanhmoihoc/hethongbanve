package ui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import data.DatabaseManager;

public class SuKienPanel extends JPanel {
    private DefaultTableModel model;
    private JTable table;

    public SuKienPanel() {
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
        JLabel lblTitle = new JLabel("QUẢN LÝ SỰ KIỆN");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(52, 73, 94));

        // Add Button
        JButton btnAdd = new JButton("+ Thêm Sự Kiện");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBackground(new Color(46, 204, 113));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setBorderPainted(false);
        btnAdd.setPreferredSize(new Dimension(150, 40));
        btnAdd.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAdd.addActionListener(e -> openEventDialog(null));

        panel.add(lblTitle, BorderLayout.WEST);
        panel.add(btnAdd, BorderLayout.EAST);
        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Create table model
        String[] columns = {"ID", "Tên sự kiện", "Mô tả", "Địa điểm", "Ngày bắt đầu", "Giá vé (VNĐ)", "Trạng thái", "Hành động"};
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
        columnModel.getColumn(2).setPreferredWidth(250);  // Mô tả
        columnModel.getColumn(3).setPreferredWidth(150);  // Địa điểm
        columnModel.getColumn(4).setPreferredWidth(120);  // Ngày
        columnModel.getColumn(5).setPreferredWidth(100);  // Giá vé
        columnModel.getColumn(6).setPreferredWidth(80);   // Trạng thái
        columnModel.getColumn(7).setPreferredWidth(80);   // Hành động (giảm từ 200 xuống 80)

        // Custom renderer for status column
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if ("Đang bán".equals(value)) {
                    setForeground(new Color(39, 174, 96));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else if ("Sắp diễn ra".equals(value)) {
                    setForeground(new Color(243, 156, 18));
                    setFont(getFont().deriveFont(Font.BOLD));
                } else {
                    setForeground(new Color(231, 76, 60));
                    setFont(getFont().deriveFont(Font.BOLD));
                }
                
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });

        // Custom renderer for price column
        table.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
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

        // Custom renderer for action buttons
        table.getColumnModel().getColumn(7).setCellRenderer(new EventActionButtonRenderer());
        table.getColumnModel().getColumn(7).setCellEditor(new EventActionButtonEditor());

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

    // Event Action Button Renderer
    class EventActionButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton btnActions;

        public EventActionButtonRenderer() {
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

    // Event Action Button Editor
    class EventActionButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private JPanel panel;
        private JButton btnActions;
        private int currentRow;

        public EventActionButtonEditor() {
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
            JMenuItem editItem = new JMenuItem("✏️ Sửa sự kiện");
            editItem.setFont(new Font("Arial", Font.PLAIN, 12));
            editItem.addActionListener(e -> {
                fireEditingStopped();
                editEvent(currentRow);
            });
            
            // Menu item Xóa
            JMenuItem deleteItem = new JMenuItem("🗑️ Xóa sự kiện");
            deleteItem.setFont(new Font("Arial", Font.PLAIN, 12));
            deleteItem.setForeground(new Color(231, 76, 60));
            deleteItem.addActionListener(e -> {
                fireEditingStopped();
                deleteEvent(currentRow);
            });
            
            // Menu item Quản lý vé
            JMenuItem ticketItem = new JMenuItem("🎫 Quản lý vé & ghế");
            ticketItem.setFont(new Font("Arial", Font.PLAIN, 12));
            ticketItem.setForeground(new Color(52, 152, 219));
            ticketItem.addActionListener(e -> {
                fireEditingStopped();
                openTicketManagement(currentRow);
            });

            popupMenu.add(editItem);
            popupMenu.addSeparator();
            popupMenu.add(ticketItem);
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
        button.setPreferredSize(new Dimension(60, 30));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void openEventDialog(Integer eventId) {
        EventDialog dialog = new EventDialog((JFrame) SwingUtilities.getWindowAncestor(this), eventId);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            EventData eventData = dialog.getEventData();
            if (eventId == null) {
                // Add new event
                addEventToTable(eventData);
            } else {
                // Update existing event
                updateEventInTable(eventId, eventData);
            }
        }
    }

    private void editEvent(int row) {
        Integer eventId = (Integer) model.getValueAt(row, 0);
        openEventDialog(eventId);
    }

    private void deleteEvent(int row) {
        String eventName = (String) model.getValueAt(row, 1);
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn xóa sự kiện '" + eventName + "'?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            model.removeRow(row);
            JOptionPane.showMessageDialog(this, "Đã xóa sự kiện thành công!");
        }
    }

    private void openTicketManagement(int row) {
        Integer eventId = (Integer) model.getValueAt(row, 0);
        String eventName = (String) model.getValueAt(row, 1);
        
        TicketManagementDialog dialog = new TicketManagementDialog(
            (JFrame) SwingUtilities.getWindowAncestor(this), 
            eventId, 
            eventName
        );
        dialog.setVisible(true);
    }

    private void addEventToTable(EventData eventData) {
        int newId = model.getRowCount() + 1;
        
        model.addRow(new Object[]{
                newId,
                eventData.getName(),
                eventData.getDescription(),
                eventData.getVenue(),
                eventData.getStartDate(),
                eventData.getPrice(),
                eventData.getStatus(),
                ""
        });
    }

    private void updateEventInTable(Integer eventId, EventData eventData) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if (eventId.equals(model.getValueAt(i, 0))) {
                model.setValueAt(eventData.getName(), i, 1);
                model.setValueAt(eventData.getDescription(), i, 2);
                model.setValueAt(eventData.getVenue(), i, 3);
                model.setValueAt(eventData.getStartDate(), i, 4);
                model.setValueAt(eventData.getPrice(), i, 5);
                model.setValueAt(eventData.getStatus(), i, 6);
                break;
            }
        }
    }

    public void refreshData() {
        // Clear existing data
        model.setRowCount(0);
        
        // Load data from DatabaseManager
        DatabaseManager db = DatabaseManager.getInstance();
        java.util.List<DatabaseManager.Event> events = db.getAllEvents();
        
        for (DatabaseManager.Event event : events) {
            String eventTypeDisplay = getEventTypeDisplay(event.getEventType());
            String dateDisplay = event.getStartTime().substring(0, 10); // Lấy phần ngày
            String status = "Đang bán"; // Default status
            
            // Estimate average ticket price (simplified)
            java.util.List<DatabaseManager.Ticket> tickets = db.getTicketsByEventId(event.getId());
            double avgPrice = tickets.isEmpty() ? 0 : 
                tickets.stream().mapToDouble(DatabaseManager.Ticket::getPrice).average().orElse(0);
            
            model.addRow(new Object[]{
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                dateDisplay,
                (int)avgPrice,
                status,
                ""
            });
        }
    }
    
    private String getEventTypeDisplay(String eventType) {
        switch (eventType.toLowerCase()) {
            case "concert": return "Hòa nhạc";
            case "movie": return "Phim";
            case "bus": return "Du lịch";
            case "other": return "Khác";
            default: return eventType;
        }
    }

    // Event Data class
    static class EventData {
        private String name, description, venue, startDate, status;
        private Integer price;

        public EventData(String name, String description, String venue, String startDate, Integer price, String status) {
            this.name = name;
            this.description = description;
            this.venue = venue;
            this.startDate = startDate;
            this.price = price;
            this.status = status;
        }

        // Getters
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getVenue() { return venue; }
        public String getStartDate() { return startDate; }
        public Integer getPrice() { return price; }
        public String getStatus() { return status; }
    }

    // Event Dialog
    class EventDialog extends JDialog {
        private JTextField txtName, txtDescription, txtVenue, txtStartDate, txtPrice;
        private JComboBox<String> cmbStatus;
        private boolean confirmed = false;

        public EventDialog(JFrame parent, Integer eventId) {
            super(parent, eventId == null ? "Thêm Sự Kiện Mới" : "Sửa Thông Tin Sự Kiện", true);
            initComponents();
            if (eventId != null) {
                loadEventData(eventId);
            }
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setSize(800, 650);
            setLocationRelativeTo(getParent());

            // Form Panel
            JPanel formPanel = new JPanel(new GridBagLayout());
            formPanel.setBackground(Color.WHITE);
            formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.anchor = GridBagConstraints.WEST;

            // Event Name
            gbc.gridx = 0; gbc.gridy = 0;
            formPanel.add(new JLabel("Tên sự kiện:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtName = new JTextField(25);
            formPanel.add(txtName, gbc);

            // Description
            gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Mô tả:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtDescription = new JTextField(25);
            formPanel.add(txtDescription, gbc);

            // Venue
            gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Địa điểm:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtVenue = new JTextField(25);
            formPanel.add(txtVenue, gbc);

            // Start Date
            gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Ngày bắt đầu:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtStartDate = new JTextField(25);
            txtStartDate.setToolTipText("Định dạng: dd/MM/yyyy");
            formPanel.add(txtStartDate, gbc);

            // Price
            gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Giá vé (VNĐ):"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            txtPrice = new JTextField(25);
            formPanel.add(txtPrice, gbc);

            // Status
            gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
            formPanel.add(new JLabel("Trạng thái:"), gbc);
            gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
            cmbStatus = new JComboBox<>(new String[]{"Đang bán", "Sắp diễn ra", "Hết vé", "Đã kết thúc"});
            formPanel.add(cmbStatus, gbc);

            add(formPanel, BorderLayout.CENTER);

            // Button Panel
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setBackground(Color.WHITE);

            JButton btnSave = new JButton("Lưu");
            btnSave.setBackground(new Color(46, 204, 113));
            btnSave.setForeground(Color.WHITE);
            btnSave.setFocusPainted(false);
            btnSave.addActionListener(e -> saveEvent());

            JButton btnCancel = new JButton("Hủy");
            btnCancel.setBackground(new Color(231, 76, 60));
            btnCancel.setForeground(Color.WHITE);
            btnCancel.setFocusPainted(false);
            btnCancel.addActionListener(e -> dispose());

            buttonPanel.add(btnSave);
            buttonPanel.add(btnCancel);
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private void loadEventData(Integer eventId) {
            for (int i = 0; i < model.getRowCount(); i++) {
                if (eventId.equals(model.getValueAt(i, 0))) {
                    txtName.setText((String) model.getValueAt(i, 1));
                    txtDescription.setText((String) model.getValueAt(i, 2));
                    txtVenue.setText((String) model.getValueAt(i, 3));
                    txtStartDate.setText((String) model.getValueAt(i, 4));
                    txtPrice.setText(model.getValueAt(i, 5).toString());
                    cmbStatus.setSelectedItem(model.getValueAt(i, 6));
                    break;
                }
            }
        }

        private void saveEvent() {
            if (validateInput()) {
                confirmed = true;
                dispose();
            }
        }

        private boolean validateInput() {
            if (txtName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sự kiện!");
                return false;
            }
            if (txtVenue.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập địa điểm!");
                return false;
            }
            if (txtStartDate.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày bắt đầu!");
                return false;
            }
            try {
                Integer.parseInt(txtPrice.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập giá vé hợp lệ!");
                return false;
            }
            return true;
        }

        public boolean isConfirmed() {
            return confirmed;
        }

        public EventData getEventData() {
            return new EventData(
                    txtName.getText().trim(),
                    txtDescription.getText().trim(),
                    txtVenue.getText().trim(),
                    txtStartDate.getText().trim(),
                    Integer.parseInt(txtPrice.getText().trim()),
                    (String) cmbStatus.getSelectedItem()
            );
        }
    }

    // Ticket Management Dialog
    class TicketManagementDialog extends JDialog {
        private DefaultTableModel ticketModel;
        private JTable ticketTable;
        private Integer eventId;
        private String eventName;

        public TicketManagementDialog(JFrame parent, Integer eventId, String eventName) {
            super(parent, "Quản lý Vé & Chỗ ngồi - " + eventName, true);
            this.eventId = eventId;
            this.eventName = eventName;
            initComponents();
            loadTicketData();
        }

        private void initComponents() {
            setLayout(new BorderLayout());
            setSize(1000, 750);
            setLocationRelativeTo(getParent());

            // Header
            JPanel headerPanel = new JPanel(new BorderLayout());
            headerPanel.setBackground(Color.WHITE);
            headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

            JLabel lblTitle = new JLabel("QUẢN LÝ VÉ VÀ CHỖ NGỒI");
            lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
            lblTitle.setForeground(new Color(52, 73, 94));

            JButton btnAddTicket = new JButton("+ Thêm Loại Vé");
            btnAddTicket.setBackground(new Color(46, 204, 113));
            btnAddTicket.setForeground(Color.WHITE);
            btnAddTicket.setFocusPainted(false);
            btnAddTicket.addActionListener(e -> addTicketType());

            headerPanel.add(lblTitle, BorderLayout.WEST);
            headerPanel.add(btnAddTicket, BorderLayout.EAST);

            // Table
            String[] columns = {"ID", "Loại vé", "Giá (VNĐ)", "Số lượng", "Đã bán", "Còn lại", "Hành động"};
            ticketModel = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 6;
                }
            };

            ticketTable = new JTable(ticketModel);
            ticketTable.setRowHeight(40);
            ticketTable.setFont(new Font("Arial", Font.PLAIN, 12));

            // Action column
            ticketTable.getColumnModel().getColumn(6).setCellRenderer(new TicketActionRenderer());
            ticketTable.getColumnModel().getColumn(6).setCellEditor(new TicketActionEditor());

            JScrollPane scrollPane = new JScrollPane(ticketTable);
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

        private void loadTicketData() {
            // Sample ticket data
            ticketModel.addRow(new Object[]{1, "VIP", 1000000, 100, 45, 55, ""});
            ticketModel.addRow(new Object[]{2, "Thường", 500000, 500, 320, 180, ""});
            ticketModel.addRow(new Object[]{3, "Sinh viên", 250000, 200, 150, 50, ""});
        }

        private void addTicketType() {
            JOptionPane.showMessageDialog(this, "Chức năng thêm loại vé đang được phát triển!");
        }

        // Ticket Action Renderer
        class TicketActionRenderer extends JPanel implements TableCellRenderer {
            private JButton btnEdit, btnDelete;

            public TicketActionRenderer() {
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

        // Ticket Action Editor
        class TicketActionEditor extends AbstractCellEditor implements TableCellEditor {
            private JPanel panel;
            private int currentRow;

            public TicketActionEditor() {
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
                    editTicket(currentRow);
                });

                JButton btnDelete = new JButton("Xóa");
                btnDelete.setFont(new Font("Arial", Font.BOLD, 10));
                btnDelete.setBackground(new Color(231, 76, 60));
                btnDelete.setForeground(Color.WHITE);
                btnDelete.setFocusPainted(false);
                btnDelete.setPreferredSize(new Dimension(50, 25));
                btnDelete.addActionListener(e -> {
                    fireEditingStopped();
                    deleteTicket(currentRow);
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

        private void editTicket(int row) {
            JOptionPane.showMessageDialog(this, "Chức năng sửa vé đang được phát triển!");
        }

        private void deleteTicket(int row) {
            int confirm = JOptionPane.showConfirmDialog(this, "Xóa loại vé này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ticketModel.removeRow(row);
            }
        }
    }
}