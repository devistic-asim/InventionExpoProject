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
                JOptionPane.showMessageDialog(frame, "Thank for using Invention Expo.\nDeveloped by Muhammad Asim (BC210200948)");
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
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error Message", JOptionPane.ERROR_MESSAGE, UIManager.getIcon("OptionPane.errorIcon"));
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
        DefaultTableModel model = new DefaultTableModel(new Object[]{"#", "Name", "Score"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
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
                loadInventors();
                nameField.setText("");
                scoreField.setText("0");
            }
            // JOptionPane.showMessageDialog(frame, "Inventor already exists!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Score must be a valid number!", "Validation Message", JOptionPane.WARNING_MESSAGE, UIManager.getIcon("OptionPane.warningIcon"));
        }
    }

    private void loadInventors() {
        try {
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
                "Confirm Delete",
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
