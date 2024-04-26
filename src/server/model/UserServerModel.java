/*
package server.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServerModel extends BaseServerModel {

    public String createUser(String userData) throws UserOperationException {
        String[] data = parseUserData(userData);
        if (data == null) {
            throw new UserOperationException("Invalid user data provided.");
        }

        String sql = "INSERT INTO users (username, password, credits, streak) VALUES (?, ?, ?, ?)";
        return executeUpdate(sql, data);
    }

    public String updateUser(String userData) throws UserOperationException {
        String[] data = parseUserData(userData);
        if (data == null) {
            throw new UserOperationException("Invalid user data provided.");
        }

        String sql = "UPDATE users SET password = ?, credits = ?, streak = ? WHERE username = ?";
        return executeUpdate(sql, data);
    }

    public String deleteUser(String username) throws UserOperationException {
        if (username == null || username.trim().isEmpty()) {
            throw new UserOperationException("Invalid username provided.");
        }

        String sql = "DELETE FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "User deleted successfully.";
            } else {
                return "No such user found.";
            }
        } catch (SQLException e) {
            throw new UserOperationException("Error deleting user: " + e.getMessage(), e);
        }
    }

    public String readUser(String username) throws UserOperationException {
        if (username == null || username.trim().isEmpty()) {
            throw new UserOperationException("Invalid username provided.");
        }

        String sql = "SELECT id, username, password, credits, streak FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return "User: " + rs.getString("username")
                        + ", Credits: " + rs.getInt("credits")
                        + ", Streak: " + rs.getInt("streak");
            } else {
                return "User not found.";
            }
        } catch (SQLException e) {
            throw new UserOperationException("Error reading user data: " + e.getMessage(), e);
        }
    }

    private String[] parseUserData(String userData) {
        if (userData == null || userData.split(",").length != 4) {
            return null;
        }
        return userData.split(",");
    }

    private String executeUpdate(String sql, String[] data) throws UserOperationException {
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < data.length; i++) {
                pstmt.setString(i + 1, data[i]);
            }
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return "Operation successful.";
            } else {
                return "No rows affected.";
            }
        } catch (SQLException e) {
            throw new UserOperationException("Database operation failed.", e);
        }
    }

    public String handleLogin(String userData) {
        String[] credentials = userData.split(",");
        if (credentials.length != 2) {
            return "Invalid login data";
        }
        String username = credentials[0];
        String password = credentials[1]; // Consider hashing the password before checking

        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(password)) { // Replace with password hash check in production
                    return "Login successful";
                } else {
                    return "Invalid password";
                }
            }
            return "User not found";
        } catch (SQLException e) {
            return "Login error: " + e.getMessage();
        }
    }

    public String registerUser(String userData) {
        String[] details = userData.split(",");
        if (details.length != 4) {
            return "Invalid registration data";
        }
        String username = details[0].trim();
        String password = details[1]; // Directly use the plain password string
        int credits = Integer.parseInt(details[2]);
        int streak = Integer.parseInt(details[3]);

        String checkUserSql = "SELECT id FROM users WHERE username = ?";
        String insertSql = "INSERT INTO users (username, password, credits, streak) VALUES (?, ?, ?, ?)";

        try (PreparedStatement checkStmt = connection.prepareStatement(checkUserSql)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return "Username already exists";
            }
        } catch (SQLException e) {
            return "Error checking username: " + e.getMessage();
        }

        try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
            insertStmt.setString(1, username);
            insertStmt.setString(2, password); // Store the plain password
            insertStmt.setInt(3, credits);
            insertStmt.setInt(4, streak);
            int affectedRows = insertStmt.executeUpdate();
            if (affectedRows > 0) {
                return "User registered successfully";
            } else {
                return "Registration failed";
            }
        } catch (SQLException e) {
            return "Registration error: " + e.getMessage();
        }
    }
}*/
