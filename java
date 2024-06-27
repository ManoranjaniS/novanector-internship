public class PercentageCalculator extends JFrame {
    private JComboBox<String> calculationTypeCombo;
    private JTextField value1Field, value2Field, resultField;
    private JLabel value1Label, value2Label, resultLabel;
    private JButton calculateButton;

    public PercentageCalculator() {
        setTitle("Percentage Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        // Create UI components
        String[] calculationTypes = {
            "Calculate Percentage",
            "Percentage Increase",
            "Percentage Decrease",
            "Find Whole from Part and Percentage"
        };
        
        calculationTypeCombo = new JComboBox<>(calculationTypes);
        value1Field = new JTextField(10);
        value2Field = new JTextField(10);
        resultField = new JTextField(10);
        resultField.setEditable(false);
        
        value1Label = new JLabel("Value 1:");
        value2Label = new JLabel("Value 2:");
        resultLabel = new JLabel("Result:");
        
        calculateButton = new JButton("Calculate");

        // Set up the layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Calculation Type:"), gbc);
        
        gbc.gridx = 1;
        add(calculationTypeCombo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(value1Label, gbc);
        
        gbc.gridx = 1;
        add(value1Field, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(value2Label, gbc);
        
        gbc.gridx = 1;
        add(value2Field, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(resultLabel, gbc);
        
        gbc.gridx = 1;
        add(resultField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(calculateButton, gbc);

        // Add action listener to the button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCalculation();
            }
        });

        // Set visibility
        setVisible(true);
    }

    private void performCalculation() {
        try {
            String selectedCalculation = (String) calculationTypeCombo.getSelectedItem();
            double value1 = Double.parseDouble(value1Field.getText());
            double value2 = Double.parseDouble(value2Field.getText());
            double result = 0;

            switch (selectedCalculation) {
                case "Calculate Percentage":
                    result = (value1 / value2) * 100;
                    break;
                case "Percentage Increase":
                    result = ((value2 - value1) / value1) * 100;
                    break;
                case "Percentage Decrease":
                    result = ((value1 - value2) / value1) * 100;
                    break;
                case "Find Whole from Part and Percentage":
                    result = (value1 / value2) * 100;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid calculation type");
            }

            resultField.setText(String.format("%.2f", result));
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numeric values.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PercentageCalculator());
    }
}