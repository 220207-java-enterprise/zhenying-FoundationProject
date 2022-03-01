package com.revature.foundationproject.daos;

import com.revature.foundationproject.models.ErsUser;
import com.revature.foundationproject.models.ErsUserRole;
import com.revature.foundationproject.util.ConnectionFactory;
import com.revature.foundationproject.util.exceptions.DataSourceException;
import com.revature.foundationproject.util.exceptions.ResourcePersistenceException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CrudDAO<ErsUser>{
    private  final String rootSelect = "SELECT * FROM ers_users eu " +
                                        "JOIN ers_user_roles eur " +
                                        "ON eu.role_id = eur.role_id ";

    public ErsUser findUserByUsername(String username){
        ErsUser ersUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(
                    rootSelect + "WHERE username = ?");
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ersUser = new ErsUser();
                ersUser.setUser_id(rs.getString("user_id"));
                ersUser.setUsername(rs.getString("username"));
                ersUser.setEmail(rs.getString("email"));
                ersUser.setPassword(rs.getString("password"));
                ersUser.setGiven_name(rs.getString("given_name"));
                ersUser.setSurname(rs.getString("surname"));
                ersUser.setIs_active(rs.getBoolean("is_active"));
                ersUser.setRole(new ErsUserRole(rs.getString("role_id"), rs.getString("role")));
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersUser;
    }

    public ErsUser findUserByEmail(String email){
        ErsUser ersUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(
                    rootSelect + "WHERE email = ?");
            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ersUser = new ErsUser();
                ersUser.setUser_id(rs.getString("user_id"));
                ersUser.setUsername(rs.getString("username"));
                ersUser.setEmail(rs.getString("email"));
                ersUser.setPassword(rs.getString("password"));
                ersUser.setGiven_name(rs.getString("given_name"));
                ersUser.setSurname(rs.getString("surname"));
                ersUser.setIs_active(rs.getBoolean("is_active"));
                ersUser.setRole(new ErsUserRole(rs.getString("role_id"), rs.getString("role")));
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersUser;
    }

    public ErsUser findUserByUsernameAndPassword(String username, String password){
        ErsUser ersUser = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(
                    rootSelect + "WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                ersUser = new ErsUser();
                ersUser.setUser_id(rs.getString("user_id"));
                ersUser.setUsername(rs.getString("username"));
                ersUser.setEmail(rs.getString("email"));
                ersUser.setPassword(rs.getString("password"));
                ersUser.setGiven_name(rs.getString("given_name"));
                ersUser.setSurname(rs.getString("surname"));
                ersUser.setIs_active(rs.getBoolean("is_active"));
                ersUser.setRole(new ErsUserRole(rs.getString("role_id"), rs.getString("role")));
            }

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersUser;
    }

    //For Admin
    public void updateUserPassword (ErsUser ersUser, String password){
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_users eu "+
                                                                "SET password = ? "+
                                                                "WHERE eu.user_id = ?");
            pstmt.setString(1,password);
            pstmt.setString(2,ersUser.getUser_id());

            int passwordUpdate = pstmt.executeUpdate();
            if(passwordUpdate != 1){
                conn.rollback();
                throw new ResourcePersistenceException("Failed to update password");
            }
            conn.commit();
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    public void deactivateUserAccount (ErsUser ersUser){
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_users eu "+
                                                                "SET is_active = ? "+
                                                                "WHERE eu.user_id = ?");
            pstmt.setBoolean(1,false);
            pstmt.setString(2,ersUser.getUser_id());
            int deactivate = pstmt.executeUpdate();
            if(deactivate != 1){
                conn.rollback();
                throw new ResourcePersistenceException("Failed to deactivate user account");
            }

            conn.commit();
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    public void reactivateUserAccount (ErsUser ersUser){
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_users eu "+
                                                                "SET is_active = ? "+
                                                                "WHERE eu.user_id = ?");
            pstmt.setBoolean(1,true);
            pstmt.setString(2,ersUser.getUser_id());
            int reactivate = pstmt.executeUpdate();
            if(reactivate != 1){
                conn.rollback();
                throw new ResourcePersistenceException("Failed to reactivate user account");
            }

            conn.commit();
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }


    //helper function
    public ErsUserRole findUserRoleByRoleName(String role_name){
        ErsUserRole userRole = null;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_user_roles "+
                                                                "WHERE role = ?");
            pstmt.setString(1, role_name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                userRole = new ErsUserRole();
                userRole.setRole_id(rs.getString("role_id"));
                userRole.setRole_name(rs.getString("role"));
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return userRole;
    }

    @Override
    public void save(ErsUser newErsUser) {
        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_users VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, newErsUser.getUser_id());
            pstmt.setString(2, newErsUser.getUsername());
            pstmt.setString(3, newErsUser.getEmail());
            pstmt.setString(4, newErsUser.getPassword());
            pstmt.setString(5, newErsUser.getGiven_name());
            pstmt.setString(6, newErsUser.getSurname());
            pstmt.setBoolean(7, newErsUser.getIs_active());
            pstmt.setString(8, newErsUser.getRole().getRole_id());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user to data source");
            }

            conn.commit();
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public ErsUser getById(String id) {
        ErsUser ersUser = null;

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(
                    rootSelect + "WHERE user_id = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                ersUser = new ErsUser();
                ersUser.setUser_id(rs.getString("user_id"));
                ersUser.setUsername(rs.getString("username"));
                ersUser.setEmail(rs.getString("email"));
                ersUser.setPassword(rs.getString("password"));
                ersUser.setGiven_name(rs.getString("given_name"));
                ersUser.setSurname(rs.getString("surname"));
                ersUser.setIs_active(rs.getBoolean("is_active"));
                ersUser.setRole(new ErsUserRole(rs.getString("role_id"), rs.getString("role")));
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return ersUser;
    }

    @Override
    public List<ErsUser> getAll() {
        List<ErsUser> ersUsers= new ArrayList<>();

        try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery(rootSelect);

            while (rs.next()){
                ErsUser ersUser = new ErsUser();
                ersUser.setUser_id(rs.getString("user_id"));
                ersUser.setUsername(rs.getString("username"));
                ersUser.setEmail(rs.getString("email"));
                ersUser.setPassword(rs.getString("password"));
                ersUser.setGiven_name(rs.getString("given_name"));
                ersUser.setSurname(rs.getString("surname"));
                ersUser.setIs_active(rs.getBoolean("is_active"));
                ersUser.setRole(new ErsUserRole(rs.getString("role_id"), rs.getString("role")));
                ersUsers.add(ersUser);
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return ersUsers;
    }

    @Override
    public void update(ErsUser updateErsUser) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_users " +
                                                            "SET username = ?, " +
                                                            "email = ?, " +
                                                            "password = ?, " +
                                                            "given_name = ?, " +
                                                            "surname = ?, " +
                                                            "is_active = ?, " +
                                                            "role_id = ? " +
                                                            "WHERE user_id = ?");

            pstmt.setString(1, updateErsUser.getSurname());
            pstmt.setString(2, updateErsUser.getEmail());
            pstmt.setString(3, updateErsUser.getPassword());
            pstmt.setString(4, updateErsUser.getGiven_name());
            pstmt.setString(5, updateErsUser.getSurname());
            pstmt.setBoolean(6, updateErsUser.getIs_active());
            pstmt.setString(6, updateErsUser.getRole().getRole_id());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new ResourcePersistenceException("Failed to update user data within datasource.");
            }

            conn.commit();

        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    @Override
    public void deleteById(String id) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ers_users WHERE user_id = ?");
            pstmt.setString(1, id);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to delete user from data source");
            }

            conn.commit();
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }
}
