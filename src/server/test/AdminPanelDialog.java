package server.test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class AdminPanelDialog {
    private JDialog dialog;
    private TestClient client;
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton reloadButton, createButton;

    public AdminPanelDialog(Frame owner, TestClient client) {
        this.client = client;
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
        String[] columnNames = {"ID", "Username", "Credits", "Streak", "Actions"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // make table cells non-editable except for the action buttons
                return column == 4;
            }
        };
        userTable = new JTable(tableModel);
        userTable.setPreferredScrollableViewportSize(new Dimension(750, 300));
        userTable.setFillsViewportHeight(true);

        // Add action buttons to each row for Update and Delete
        userTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        userTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), client));

        JScrollPane scrollPane = new JScrollPane(userTable);
        dialog.add(topPanel, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);
    }

    private void loadUserData() {
        String response = client.sendRequest("ADMIN_CRUD:READ_ALL");
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0); // Clear existing data
            Arrays.stream(response.split("\n")).forEach(row -> {
                String[] rowData = row.split(",");
                Object[] displayRow = {rowData[0], rowData[1], rowData[3], rowData[4], "Update/Delete"};
                tableModel.addRow(displayRow);
            });
        });
    }

    private void createUser(ActionEvent e) {
        String newUser = JOptionPane.showInputDialog(dialog, "Enter user details as id,username,password,credits,streak");
        if (newUser != null && !newUser.trim().isEmpty()) {
            String response = client.sendRequest("ADMIN_CRUD:CREATE_USER:" + newUser);
            JOptionPane.showMessageDialog(dialog, response);
            loadUserData();
        }
    }

    public void show() {
        dialog.setVisible(true);
    }

    // Mock-up implementations for the button renderer and editor for update/delete actions
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                setText(value.toString());
            }
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox, TestClient client) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (value != null) {
                button.setText(value.toString());
            }
            this.label = (value == null) ? "" : value.toString();
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                JOptionPane.showMessageDialog(button, label + ": Ouch!");
                // Perform function depending on whether it's an update or delete
            }
            isPushed = false;
            return label;
        }
    }
}
