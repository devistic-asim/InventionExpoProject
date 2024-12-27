import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class InventionExpo {

    public static void main(String[] args) {
        new InventionExpo().init();
    }

    // Method to initialize the program
    private void init() {
        try {
            // Prompt the user to enter the number of inventors with a question icon
            String numInventorsStr = showInputDialog(
                    "INVENTION EXPO COMPETITION!",
                    "WELCOME TO THE INVENTION EXPO COMPETITION!\nPlease enter the number of inventors:",
                    UIManager.getIcon("OptionPane.questionIcon")
            );
            int numInventors = Integer.parseInt(numInventorsStr);
            if (numInventors <= 0) {
                showMessageDialog("Error", "Number of inventors must be greater than zero.", Color.RED, UIManager.getIcon("OptionPane.errorIcon"));
                return;
            }

            // List to store all inventors
            ArrayList<Inventor> inventors = new ArrayList<>();

            // Collect information for each inventor
            for (int i = 0; i < numInventors; i++) {
                String name = showInputDialog(
                        "INVENTION EXPO - Inventor #" + (i + 1),
                        "Please enter the name of inventor:",
                        UIManager.getIcon("OptionPane.questionIcon")
                );
                if (name == null || name.trim().isEmpty()) {
                    showMessageDialog("Error", "Name cannot be empty.", Color.RED, UIManager.getIcon("OptionPane.errorIcon"));
                    return;
                }

                int score;
                try {
                    String scoreStr = showInputDialog(
                            "INVENTION EXPO - Score Entry for Inventor #" + (i + 1),
                            "Please enter the score (1-100) for the invention of inventor " + name + ":",
                            UIManager.getIcon("OptionPane.questionIcon")
                    );
                    score = Integer.parseInt(scoreStr);
                    if (score < 1 || score > 100) {
                        showMessageDialog("Error", "Score must be between 1 and 100.", Color.RED, UIManager.getIcon("OptionPane.errorIcon"));
                        return;
                    }
                } catch (NumberFormatException e) {
                    showMessageDialog("Error", "Invalid input for score. Please enter a number between 1 and 100.", Color.RED, UIManager.getIcon("OptionPane.errorIcon"));
                    return;
                }

                // Add inventor to the list
                inventors.add(new Inventor(name, score));
            }

            // Find and display the top inventor
            Inventor topInventor = findTopInventor(inventors);
            if (topInventor != null) {
                showMessageDialog(
                        "INVENTION EXPO - Top Inventor",
                        "The top inventor is " + topInventor.name() + " with an invention score of " + topInventor.score() + " out of 100.",
                        Color.GREEN,
                        UIManager.getIcon("OptionPane.informationIcon")
                );
            } else {
                showMessageDialog("Error", "No valid inventors found.", Color.RED, UIManager.getIcon("OptionPane.errorIcon"));
            }

            // Display developer information
            showMessageDialog(
                    "INVENTION EXPO - Developer Info",
                    """
                            Thanks for participating in the INVENTION EXPO COMPETITION!
                            Developed by BC210200948 - Muhammad Asim
                            .NET Developer at Jaffer Business Systems""",
                    Color.BLUE,
                    UIManager.getIcon("OptionPane.informationIcon")
            );

        } catch (NumberFormatException e) {
            showMessageDialog("Error", "Invalid input. Program will exit.", Color.RED, UIManager.getIcon("OptionPane.errorIcon"));
        }
    }

    // Method to find the top inventor based on the highest score
    private Inventor findTopInventor(ArrayList<Inventor> inventors) {
        if (inventors.isEmpty()) return null;

        Inventor topInventor = inventors.getFirst();
        for (Inventor inventor : inventors) {
            if (inventor.score() > topInventor.score()) {
                topInventor = inventor;
            }
        }
        return topInventor;
    }

    // Inner class Inventor
    private record Inventor(String name, int score) {
        // Parameterized constructor is implicitly defined by record class
    }

    // Custom method for input dialog with title and icon
    private String showInputDialog(String title, String message, Icon icon) {
        return (String) JOptionPane.showInputDialog(null, message, title, JOptionPane.PLAIN_MESSAGE, icon, null, "");
    }

    // Custom method for showing message dialog with colored border, title, and icon
    private void showMessageDialog(String title, String message, Color borderColor, Icon icon) {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(borderColor, 0, true));

        // Create a JLabel to support multiline text in JOptionPane
        JLabel label = new JLabel("<html><body style='text-align: center;'>" + message.replace("\n", "<br>") + "</body></html>");
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label);
        JOptionPane.showMessageDialog(null, panel, title, JOptionPane.PLAIN_MESSAGE, icon);
    }
}
