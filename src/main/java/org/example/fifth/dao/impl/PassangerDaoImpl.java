package org.example.fifth.dao.impl;

import org.example.fifth.config.DatabaseConnection;
import org.example.fifth.dao.PassangerDao;
import org.example.fifth.domain.Passanger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PassangerDaoImpl implements PassangerDao {
    private final Connection con = DatabaseConnection.getConnection();
    private static final Logger logger = LoggerFactory.getLogger(PassangerDaoImpl.class);

    @Override
    public Passanger login(String email, String password) {
        String query = "SELECT * FROM passanger WHERE email = ? AND password = ?";
        Passanger passanger = null;
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int clientId = resultSet.getInt("id");
                updateLoginStatus(clientId);
                logger.info("Login successful");
                passanger = new Passanger(clientId, resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6), resultSet.getBoolean(7), resultSet.getBoolean(8));
            } else {
                throw new RuntimeException();
            }
        } catch (SQLException e) {
            logger.error("Error during login: {}", e.getMessage());
        }
        return passanger;
    }

    private void updateLoginStatus(int clientId) {
        String query = "UPDATE passanger SET isLogin = ? WHERE id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setBoolean(1, true);
            statement.setInt(2, clientId);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Error updating login status: {}", e.getMessage());
        }
    }

    @Override
    public void register(String firstname, String lastname, String contactNumber, String email, String password) {
        String query = "INSERT INTO passanger (first_name, last_name, contact_number, email, password,islogin,is_admin) " +
                "VALUES (?, ?, ?, ?, ?, ? ,?)";
        try (PreparedStatement statement = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstname);
            statement.setString(2, lastname);
            statement.setString(3, contactNumber);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setBoolean(6, false);
            statement.setBoolean(7, false);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 1) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int clientId = generatedKeys.getInt(1);
                    logger.info("Registration successful. Unique user ID: {}", clientId);
                }
            } else {
                logger.error("Failed to register user.");
            }
        } catch (SQLException e) {
            logger.error("Error during registration: {}", e.getMessage());
        }
    }
}

