import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class MainClass {
    private JFrame frame;
    private JTable table;
    private JTextField nameField, scoreField;
    private JPanel centerPanel;

    public MainClass() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Invention Expo");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Handle window close event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(frame, "Thank for using Invention Expo.\nDeveloped by Muhammad Asim (BC210200948)");
                System.exit(0);
            }
        });

        // Left Panel (Icon and Main Menu buttons)
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(150, 0));

        // Icon
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon("assets/medal_icon.png")); // Provide the correct path to your image
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(iconLabel, BorderLayout.NORTH);

        // Buttons
        JPanel menuPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        JButton loadButton = new JButton("Load Data");
        JButton deleteButton = new JButton("Delete All");
        JButton winnerButton = new JButton("View Winner");

        loadButton.addActionListener(e -> loadInventors());
        deleteButton.addActionListener(e -> deleteAllInventors());
        winnerButton.addActionListener(e -> loadTopWinners());

        menuPanel.add(loadButton);
        menuPanel.add(deleteButton);
        menuPanel.add(winnerButton);
        leftPanel.add(menuPanel, BorderLayout.CENTER);

        frame.add(leftPanel, BorderLayout.WEST);

        // Top Panel (Input Fields)
        JPanel topPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JLabel scoreLabel = new JLabel("Score:");
        nameField = new JTextField();
        scoreField = new JTextField("0");
        JButton addButton = new JButton("Add Inventor");

        addButton.addActionListener(e -> {
            try {
                addInventor();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE);
            }
        });

        topPanel.add(nameLabel);
        topPanel.add(nameField);
        topPanel.add(scoreLabel);
        topPanel.add(scoreField);
        topPanel.add(new JLabel()); // Empty placeholder
        topPanel.add(addButton);

        frame.add(topPanel, BorderLayout.NORTH);

        // Center Panel (Table)
        DefaultTableModel model = new DefaultTableModel(new Object[]{"No", "Name", "Score"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder(""));
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        frame.add(centerPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void addInventor() throws SQLException {
        String name = nameField.getText().trim();
        String scoreStr = scoreField.getText().trim();
        if (name.isEmpty() || scoreStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Fields cannot be empty!", "Validation Message", JOptionPane.WARNING_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
            return;
        }
        name = capitalizeFirstLetter(name);

        try {
            int score = Integer.parseInt(scoreStr);
            if (score < 1 || score > 100) {
                JOptionPane.showMessageDialog(frame, "Score must be between 1 and 100!", "Validation Message", JOptionPane.WARNING_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
                return;
            }

            if (DbHelper.addInventor(name, score)) {
                JOptionPane.showMessageDialog(frame, "Inventor added successfully!");
                nameField.setText("");
                scoreField.setText("0");

                loadInventors();
            }
            // JOptionPane.showMessageDialog(frame, "Inventor already exists!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Score must be a valid number!", "Validation Message", JOptionPane.WARNING_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
        }
    }

    private void loadInventors() {
        try {
            centerPanel.setBorder(BorderFactory.createTitledBorder("All Inventors!"));
            List<Inventor> inventors = DbHelper.getInventors();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            if (!inventors.isEmpty()) {
                int rowNum = 1;
                for (Inventor inventor : inventors) {
                    model.addRow(new Object[]{rowNum++, inventor.getName(), inventor.getScore()});
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No inventors found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
        }
    }

    private void deleteAllInventors() {
        int confirm = JOptionPane.showConfirmDialog(frame,
                "Do you want to remove all inventors' data from the database?",
                "Select an Option...",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                DbHelper.deleteAllInventors();
                JOptionPane.showMessageDialog(frame, "All inventors deleted successfully!");
                loadInventors();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
            }
        }
    }

    private void loadTopWinners() {
        try {
            centerPanel.setBorder(BorderFactory.createTitledBorder("Winners List!"));
            List<Inventor> winners = DbHelper.getTopWinners();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            if (!winners.isEmpty()) {
                int rowNum = 1;
                for (Inventor winner : winners) {
                    model.addRow(new Object[]{rowNum++, winner.getName(), winner.getScore()});
                }
                JOptionPane.showMessageDialog(frame, "Winners loaded successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "No winner found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error: " + e.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
        }
    }

    // Helper method to capitalize the first letter
    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainClass::new);
    }
}
