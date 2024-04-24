package server.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServerModel extends BaseServerModel {
    public String createUser(String userData) {
        String[] data = userData.split(",");
        String username = data[0];
        String password = data[1];
        int credits = Integer.parseInt(data[2]);
        int streak = Integer.parseInt(data[3]);

        String sql = "INSERT INTO users (username, password, credits, streak) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, credits);
            pstmt.setInt(4, streak);
            return "User created successfully.";
        } catch (SQLException e) {
            return "Error creating user: " + e.getMessage();
        }
    }


    // Probably need to change this to allow for credits to be updated.
    public String updateUser(String userData) {
        String[] data = userData.split(",");
        String username = data[0];
        String password = data[1];
        int credits = Integer.parseInt(data[2]);
        int streak = Integer.parseInt(data[3]);

        String sql = "UPDATE users SET password = ?, credits = ?, streak = ? WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, password);
            pstmt.setInt(2, credits);
            pstmt.setInt(3, streak);
            pstmt.setString(4, username);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return "User updated successfully.";
            } else {
                return "No such user found.";
            }
        } catch (SQLException e) {
            return "Error updating user: " + e.getMessage();
        }
    }

    public String deleteUser(String username) {
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
            return "Error deleting user: " + e.getMessage();
        }
    }

    public String readUser(String username) {
        String sql = "SELECT id, username, password, credits, streak FROM users WHERE username = ?";

        try(PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return "User: " + rs.getString("username")
                        + ", Credits: " + rs.getInt("credits")
                        + ", Streak: " + rs.getInt("streak");
            } else {
                return "User not found.";
            }
        }
        catch (SQLException e) {
            return "Error reading user data: " + e.getMessage();
        }
    }

    public String processRequest(String requestType, String requestData) {
        switch (requestType) {
            case "CREATE":
                return createUser(requestData);
            case "UPDATE":
                return updateUser(requestData);
            case "DELETE":
                return deleteUser(requestData);
            case "READ":
                return readUser(requestData);
            default:
                return "Invalid request type";
        }
    }
}