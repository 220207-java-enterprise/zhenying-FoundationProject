package com.revature.foundationproject.daos;

import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.util.ConnectionFactory;
import com.revature.foundationproject.util.exceptions.DataSourceException;
import com.revature.foundationproject.util.exceptions.ResourcePersistenceException;

import java.sql.*;

public class UserDAO implements CrudDAO<ErsUser>{


    public ErsUser findUserByUsernameAndPassword(String username, String password){
        System.out.println("\n"+"Looking for user by username and password");

        ErsUser ersUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_users WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ersUser = new ErsUser();
                ersUser.setUser_id(rs.getString("user_id"));
                ersUser.setGiven_name(rs.getString("given_name"));
                ersUser.setEmail(rs.getString("email"));
                ersUser.setUsername(rs.getString("username"));
                ersUser.setPassword(rs.getString("password"));
                // TODO fix AppUser to include role
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersUser;
    }

    @Override
    public void save(ErsUser newObject) {

    }

    @Override
    public ErsUser getById(String id) {
        return null;
    }

    @Override
    public ErsUser[] getAll() {
        return new ErsUser[0];
    }

    @Override
    public void update(ErsUser updatedObject) {

    }

    @Override
    public void deleteById(String id) {

    }
}
