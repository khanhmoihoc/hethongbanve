package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.UIManager.LookAndFeelInfo;

public class Main extends JFrame {
    private JPanel contentPanel;
    private UserPanel userPanel;
    private SuKienPanel eventPanel;
    private VenuePanel venuePanel;

    public Main() {
        setTitle("Hệ Thống Quản Lý Bán Vé - Admin Panel");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        
        initializeComponents();
        setupLayout();
        
        // Show events panel by default
        showEventPanel();
    }

    private void initializeComponents() {
        // Initialize panels
        userPanel = new UserPanel();
        eventPanel = new SuKienPanel();
        venuePanel = new VenuePanel();
        
        // Content panel to switch between different views
        contentPanel = new JPanel(new BorderLayout());
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Create menu bar
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        
        // Create sidebar
        JPanel sidebar = createSidebar();
        add(sidebar, BorderLayout.WEST);
        
        // Add content panel
        add(contentPanel, BorderLayout.CENTER);
        
        // Create status bar
        JPanel statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(44, 62, 80));
        
        // File Menu
        JMenu fileMenu = new JMenu("Tệp");
        fileMenu.setForeground(Color.WHITE);
        fileMenu.add(createMenuItem("Đăng xuất", e -> System.exit(0)));
        fileMenu.addSeparator();
        fileMenu.add(createMenuItem("Thoát", e -> System.exit(0)));
        
        // View Menu
        JMenu viewMenu = new JMenu("Xem");
        viewMenu.setForeground(Color.WHITE);
        viewMenu.add(createMenuItem("Người dùng", e -> showUserPanel()));
        viewMenu.add(createMenuItem("Sự kiện", e -> showEventPanel()));
        viewMenu.add(createMenuItem("Địa điểm", e -> showVenuePanel()));
        
        // Help Menu
        JMenu helpMenu = new JMenu("Trợ giúp");
        helpMenu.setForeground(Color.WHITE);
        helpMenu.add(createMenuItem("Hướng dẫn", e -> showHelp()));
        helpMenu.add(createMenuItem("Về chúng tôi", e -> showAbout()));
        
        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        
        return menuBar;
    }

    private JMenuItem createMenuItem(String text, java.awt.event.ActionListener action) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(action);
        return item;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(52, 73, 94));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Logo/Title
        JLabel logo = new JLabel("ADMIN PANEL", SwingConstants.CENTER);
        logo.setFont(new Font("Arial", Font.BOLD, 16));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        // Navigation buttons
        JButton btnUsers = createSidebarButton("👥 Người dùng", e -> showUserPanel());
        JButton btnEvents = createSidebarButton("🎭 Sự kiện", e -> showEventPanel());
        JButton btnVenues = createSidebarButton("🏢 Địa điểm", e -> showVenuePanel());
        
        // Spacer
        Component spacer = Box.createVerticalGlue();
        
        // Logout button
        JButton btnLogout = createSidebarButton("🚪 Đăng xuất", e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác nhận đăng xuất",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        btnLogout.setBackground(new Color(231, 76, 60));

        sidebar.add(logo);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnUsers);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnEvents);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(btnVenues);
        sidebar.add(spacer);
        sidebar.add(btnLogout);

        return sidebar;
    }

    private JButton createSidebarButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(41, 128, 185));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(180, 40));
        button.setPreferredSize(new Dimension(180, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (!button.getBackground().equals(new Color(231, 76, 60))) {
                    button.setBackground(new Color(52, 152, 219));
                }
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (!button.getBackground().equals(new Color(231, 76, 60))) {
                    button.setBackground(new Color(41, 128, 185));
                }
            }
        });
        
        return button;
    }

    private JPanel createStatusBar() {
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(236, 240, 241));
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusBar.setPreferredSize(new Dimension(0, 25));

        JLabel statusLabel = new JLabel("Sẵn sàng");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        JLabel timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        
        // Update time every second
        Timer timer = new Timer(1000, e -> {
            timeLabel.setText(java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        });
        timer.start();

        statusBar.add(statusLabel, BorderLayout.WEST);
        statusBar.add(timeLabel, BorderLayout.EAST);

        return statusBar;
    }

    private void showUserPanel() {
        contentPanel.removeAll();
        contentPanel.add(userPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        setTitle("Hệ Thống Quản Lý Bán Vé - Quản lý Người dùng");
    }

    private void showEventPanel() {
        contentPanel.removeAll();
        contentPanel.add(eventPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        setTitle("Hệ Thống Quản Lý Bán Vé - Quản lý Sự kiện");
    }

    private void showVenuePanel() {
        contentPanel.removeAll();
        contentPanel.add(venuePanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        setTitle("Hệ Thống Quản Lý Bán Vé - Quản lý Địa điểm");
    }

    private void showHelp() {
        JOptionPane.showMessageDialog(
            this,
            "Hướng dẫn sử dụng:\n\n" +
            "1. Quản lý Người dùng: Thêm, sửa, xóa thông tin người dùng\n" +
            "2. Quản lý Sự kiện: Tạo và quản lý các sự kiện, vé và chỗ ngồi\n" +
            "3. Quản lý Địa điểm: Quản lý các địa điểm tổ chức sự kiện\n\n" +
            "Sử dụng menu bên trái để chuyển đổi giữa các chức năng.",
            "Hướng dẫn sử dụng",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showAbout() {
        JOptionPane.showMessageDialog(
            this,
            "Hệ Thống Quản Lý Bán Vé\n" +
            "Phiên bản 1.0.0\n\n" +
            "Được phát triển để quản lý việc bán vé sự kiện\n" +
            "© 2025 - Tất cả quyền được bảo lưu",
            "Về chúng tôi",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Main entry point for the Ticket Management System
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=== TICKET MANAGEMENT SYSTEM ===");
        System.out.println("Initializing application...");
        
        // Set system properties for better rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        System.setProperty("swing.plaf.metal.controlFont", "Dialog-12");
        System.setProperty("swing.plaf.metal.systemFont", "Dialog-12");
        
        // Set look and feel
        setLookAndFeel();

        // Create and show GUI on EDT
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }
    
    /**
     * Set the best available Look and Feel
     */
    private static void setLookAndFeel() {
        try {
            // Try to use Nimbus Look and Feel (modern and cross-platform)
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    System.out.println("Using Nimbus Look and Feel");
                    return;
                }
            }
            // Fallback to cross-platform look and feel
            System.out.println("Using default Metal Look and Feel");
        } catch (Exception e) {
            System.out.println("Using default Metal Look and Feel");
        }
    }
    
    /**
     * Create and display the main application window
     */
    private static void createAndShowGUI() {
        try {
            System.out.println("Creating main application window...");
            
            // Create main window
            Main mainWindow = new Main();
            
            // Make sure the application exits when window is closed
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // Show the window
            mainWindow.setVisible(true);
            
            System.out.println("✓ Application started successfully!");
            System.out.println("Window size: " + mainWindow.getWidth() + "x" + mainWindow.getHeight());
            
        } catch (Exception e) {
            System.err.println("✗ Failed to start application: " + e.getMessage());
            e.printStackTrace();
            
            // Show error dialog
            JOptionPane.showMessageDialog(null, 
                "<html><body style='width: 300px'>" +
                "<h3>Lỗi khởi động ứng dụng</h3>" +
                "<p>Không thể khởi động Hệ thống Quản lý Bán vé.</p>" +
                "<p><b>Chi tiết lỗi:</b><br/>" + e.getMessage() + "</p>" +
                "<p>Vui lòng kiểm tra lại Java và thử lại.</p>" +
                "</body></html>", 
                "Ticket Management System - Error", 
                JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
        }
    }
}