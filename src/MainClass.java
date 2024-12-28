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

    public MainClass() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Invention Expo");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Handle window close event
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(frame, "Student ID: 12345\nName: John Doe");
                System.exit(0);
            }
        });

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        nameField = new JTextField();
        scoreField = new JTextField("0");

        JButton addButton = new JButton("Add Inventor");
        addButton.addActionListener(e -> {
            try {
                addInventor();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
            }
        });

        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Score:"));
        inputPanel.add(scoreField);
        inputPanel.add(new JLabel());
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Table
        // Create table with initial model
        DefaultTableModel model = new DefaultTableModel(new Object[]{"#", "Name", "Score"}, 0);
        table = new JTable(model);
        // Make all cells non-editable
        model = new DefaultTableModel(new Object[]{"#", "Name", "Score"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };
        table.setModel(model);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = getjPanel();

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private JPanel getjPanel() {
        JPanel buttonPanel = new JPanel();
        JButton loadButton = new JButton("Load Data");
        JButton deleteButton = new JButton("Delete All");
        JButton winnerButton = new JButton("View Winners");

        loadButton.addActionListener(e -> loadInventors());
        deleteButton.addActionListener(e -> deleteAllInventors());
        winnerButton.addActionListener(e -> loadTopWinners());

        buttonPanel.add(loadButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(winnerButton);
        return buttonPanel;
    }

    private void addInventor() throws SQLException {
        String name = nameField.getText().trim();
        String scoreStr = scoreField.getText().trim();
        if (name.isEmpty() || scoreStr.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Fields cannot be empty!");
            return;
        }
        name = capitalizeFirstLetter(name);

        try {
            int score = Integer.parseInt(scoreStr);
            if (score < 1 || score > 100) {
                JOptionPane.showMessageDialog(frame, "Score must be between 1 and 100!");
                return;
            }

            if (DbHelper.addInventor(name, score)) {
                JOptionPane.showMessageDialog(frame, "Inventor added successfully!");
                // JOptionPane.showMessageDialog(frame, "This is an information message", "Information", JOptionPane.INFORMATION_MESSAGE);
                loadInventors();
                nameField.setText("");
                scoreField.setText("0");
            }
            // JOptionPane.showMessageDialog(frame, "Inventor already exists!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Score must be a valid number!");
        }
    }

    private void loadInventors() {
        try {
            List<Inventor> inventors = DbHelper.getInventors();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            int rowNum = 1;
            if (!inventors.isEmpty()) {
                for (Inventor inventor : inventors) {
                    model.addRow(new Object[]{rowNum++, inventor.getName(), inventor.getScore()});
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No inventors found!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage());
        }
    }

    private void deleteAllInventors() {
        int confirm = JOptionPane.showConfirmDialog(frame,
                "Do you want to remove all inventors' data from the database?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                DbHelper.deleteAllInventors();
                JOptionPane.showMessageDialog(frame, "All inventors deleted successfully!");
                loadInventors();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Error deleting data: " + e.getMessage());
            }
        }
    }

    private void loadTopWinners() {
        try {
            List<Inventor> winners = DbHelper.getTopWinners();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            int rowNum = 1;
            for (Inventor winner : winners) {
                model.addRow(new Object[]{rowNum++, winner.getName(), winner.getScore()});
            }
            JOptionPane.showMessageDialog(frame, "Winners loaded successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error loading winners: " + e.getMessage());
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
