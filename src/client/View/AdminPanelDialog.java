package client.View;

import client.Controller.ClientController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;

public class AdminPanelDialog {
    private JDialog dialog;
    private ClientController controller;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton reloadButton, createButton;

    public AdminPanelDialog(Frame owner, ClientController controller) {
        this.controller = controller;
        dialog = new JDialog(owner, "Admin Panel", true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(800, 500);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initializeUI();
        loadUserData();
    }

    private void initializeUI() {
        // Top panel for buttons
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reloadButton = new JButton("Reload");
        createButton = new JButton("Create User");
        reloadButton.addActionListener(e -> loadUserData());
        createButton.addActionListener(this::createUser);
        topPanel.add(reloadButton);
        topPanel.add(createButton);

        // Table setup
        String[] columnNames = {"ID", "Username", "Password", "Credits", "Streak", "Update", "Delete"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5 || column == 6;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setPreferredScrollableViewportSize(new Dimension(750, 300));
        userTable.setFillsViewportHeight(true);

        // Adding button renderers and editors for update and delete actions
        userTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        userTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), controller, "Update"));
        userTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        userTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), controller, "Delete"));

        JScrollPane scrollPane = new JScrollPane(userTable);
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
    }

    private void loadUserData() {
        String response = controller.sendRequest("READ_ALL_USERS");
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);  // Clear existing data
            String[] rows = response.split("\n");
            for (String row : rows) {
                String[] rowData = row.split(",");
                if (rowData.length >= 5) {
                    Object[] displayRow = {
                            rowData[0],  // ID
                            rowData[1],  // Username
                            rowData[2],  // Password (admin use case)
                            rowData[3],  // Credits
                            rowData[4],  // Streak
                            "Update",
                            "Delete"
                    };
                    tableModel.addRow(displayRow);
                }
            }
        });
    }

    private void createUser(ActionEvent e) {
        // Prompt for user details
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        JTextField creditsField = new JTextField();
        JTextField streakField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Credits:"));
        panel.add(creditsField);
        panel.add(new JLabel("Streak:"));
        panel.add(streakField);

        int result = JOptionPane.showConfirmDialog(dialog, panel, "Enter New User Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String credits = creditsField.getText().trim();
            String streak = streakField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || credits.isEmpty() || streak.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "All fields must be filled out", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String userData = String.join(",", username, password, credits, streak);

            // Send data to the server
            String response = controller.sendRequest("CREATE_USER:" + userData);
            JOptionPane.showMessageDialog(dialog, response);
            loadUserData();  // Reload user data to reflect changes
        }
    }

    public void show() {
        dialog.setVisible(true);
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private boolean isPushed;
        private int row;
        private String actionType;
        private ClientController controller;

        public ButtonEditor(JCheckBox checkBox, ClientController controller, String actionType) {
            super(checkBox);
            this.controller = controller;
            this.actionType = actionType;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            button.setText((value == null) ? "" : value.toString());
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if ("Update".equals(actionType)) {
                    showUpdateDialog(row);
                } else if ("Delete".equals(actionType)) {
                    deleteUserData(row);
                }
            }
            isPushed = false;
            return button.getText();
        }

        private void showUpdateDialog(int row) {
            JTextField usernameField = new JTextField((String) tableModel.getValueAt(row, 1));
            JTextField passwordField = new JPasswordField();
            JTextField creditsField = new JTextField((String) tableModel.getValueAt(row, 3));
            JTextField streakField = new JTextField((String) tableModel.getValueAt(row, 4));

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Username (leave blank if no change):"));
            panel.add(usernameField);
            panel.add(new JLabel("New Password (leave blank if no change):"));
            panel.add(passwordField);
            panel.add(new JLabel("Credits (leave blank if no change):"));
            panel.add(creditsField);
            panel.add(new JLabel("Streak (leave blank if no change):"));
            panel.add(streakField);

            int result = JOptionPane.showConfirmDialog(dialog, panel, "Update User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                String userId = (String) tableModel.getValueAt(row, 0);
                String[] data = {
                        usernameField.getText().trim(),
                        passwordField.getText().trim(),
                        creditsField.getText().trim(),
                        streakField.getText().trim(),
                        userId
                };

                String userData = String.join(",", data);
                String response = controller.sendRequest("UPDATE_USER:" + userData);
                JOptionPane.showMessageDialog(dialog, response);
                loadUserData();
            }
        }

        private void deleteUserData(int row) {
            int confirm = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to delete this user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String userId = (String) tableModel.getValueAt(row, 0);
                String response = controller.sendRequest("DELETE_USER:" + userId);  // Send delete request
                JOptionPane.showMessageDialog(dialog, response);
                loadUserData();  // Reload user data to reflect the changes
            }
        }
    }
}
