package com.revature.foundationproject.daos;

import com.revature.foundationproject.models.*;
import com.revature.foundationproject.util.ConnectionFactory;
import com.revature.foundationproject.util.exceptions.DataSourceException;
import com.revature.foundationproject.util.exceptions.ResourcePersistenceException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReimbursementDAO implements CrudDAO<ErsReimbursement>{
    private final String rootSelect = "SELECT * FROM ers_reimbursements er "+
                                    "JOIN ers_reimbursement_statuses ers "+
                                    "ON er.status_id  = ers.status_id "+
                                    "JOIN ers_reimbursement_types ert "+
                                    "ON er.type_id = ert.type_id ";

    //For Employee
    public List<ErsReimbursement> findAllPendingReimbursementsByErsUser(ErsUser ersUser){
        List<ErsReimbursement> ersReimbursementArrayList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootSelect+
                                                                "WHERE er.author_id = ? and er.resolver_id is null");
            pstmt.setString(1, ersUser.getUser_id());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                ErsReimbursement reimbursement = new ErsReimbursement();
                reimbursement.setReimb_id(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getDouble("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getBytes("receipt"));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor(ersUser);
                reimbursement.setResolver(null);
                reimbursement.setErsReimbStatus(new ErsReimbStatus(rs.getString("status_id"),rs.getString("status")));
                reimbursement.setErsReimbType(new ErsReimbType(rs.getString("type_id"), rs.getString("type")));
                ersReimbursementArrayList.add(reimbursement);
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersReimbursementArrayList;
    }

    public List<ErsReimbursement> findAllSolvedReimbursementsByErsUser(ErsUser ersUser){
        List<ErsReimbursement> ersReimbursementArrayList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootSelect+
                    "JOIN ers_users eu "+
                    "ON er.resolver_id = eu.user_id "+
                    "WHERE er.author_id = ?");
            pstmt.setString(1, ersUser.getUser_id());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                ErsReimbursement reimbursement = new ErsReimbursement();
                reimbursement.setReimb_id(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getDouble("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getBytes("receipt"));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor(ersUser);
                reimbursement.setResolver(new ErsUser(rs.getString("user_id"),
                                                    rs.getString("username"),
                                                    rs.getString("email"),
                                                    rs.getString("password"),
                                                    rs.getString("given_name"),
                                                    rs.getString("surname"),
                                                    rs.getBoolean("is_active"),
                                                    new ErsUserRole(rs.getString("role_id"), "FINANCE_MANAGER")));
                reimbursement.setErsReimbStatus(new ErsReimbStatus(rs.getString("status_id"),rs.getString("status")));
                reimbursement.setErsReimbType(new ErsReimbType(rs.getString("type_id"), rs.getString("type")));
                ersReimbursementArrayList.add(reimbursement);
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersReimbursementArrayList;
    }

    public List<ErsReimbursement> findAllReimbursementsByErsUser(ErsUser ersUser){
        List<ErsReimbursement> ersReimbursementArrayList = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootSelect+
                                                            "WHERE er.author_id = ? "+
                                                            "ORDER BY submitted DESC");
            pstmt.setString(1, ersUser.getUser_id());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                ErsReimbursement reimbursement = new ErsReimbursement();
                reimbursement.setReimb_id(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getDouble("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getBytes("receipt"));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor(ersUser);
                reimbursement.setResolver(null);
                reimbursement.setErsReimbStatus(new ErsReimbStatus(rs.getString("status_id"),rs.getString("status")));
                reimbursement.setErsReimbType(new ErsReimbType(rs.getString("type_id"), rs.getString("type")));
                ersReimbursementArrayList.add(reimbursement);
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersReimbursementArrayList;
    }

    public void submitNewReimbursementRequestByErsUser(ErsReimbursement ersReimbursement){
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ers_reimbursements VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1,ersReimbursement.getReimb_id());
            pstmt.setDouble(2,ersReimbursement.getAmount());
            pstmt.setTimestamp(3,ersReimbursement.getSubmitted());
            pstmt.setTimestamp(4,ersReimbursement.getResolved());
            pstmt.setString(5,ersReimbursement.getDescription());
            pstmt.setBytes(6,ersReimbursement.getReceipt());
            pstmt.setString(7,ersReimbursement.getPayment_id());
            pstmt.setString(8,ersReimbursement.getAuthor().getUser_id());
            pstmt.setString(9,null);
            pstmt.setString(10,ersReimbursement.getErsReimbStatus().getStatus_id());
            pstmt.setString(11,ersReimbursement.getErsReimbType().getType_id());

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                conn.rollback();
                throw new ResourcePersistenceException("Failed to persist user to data source");
            }

            conn.commit();

        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }


    //For Finance Manager
    public List<ErsReimbursement> findAllPendingReimbursementsByFM (){
        List<ErsReimbursement> ersReimbursementArrayList = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootSelect+
                                                                "JOIN ers_users eu " +
                                                                "ON eu.user_id = er.author_id "+
                                                                "JOIN ers_user_roles eur "+
                                                                "ON eur.role_id = eu.role_id "+
                                                                "WHERE resolver_id IS NULL "+
                                                                "ORDER BY submitted");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                ErsReimbursement reimbursement = new ErsReimbursement();
                reimbursement.setReimb_id(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getDouble("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getBytes("receipt"));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor(new ErsUser(rs.getString("user_id"),
                                                    rs.getString("username"),
                                                    rs.getString("email"),
                                                    rs.getString("password"),
                                                    rs.getString("given_name"),
                                                    rs.getString("surname"),
                                                    rs.getBoolean("is_active"),
                                                    new ErsUserRole(rs.getString("role_id"), rs.getString("role"))));
                reimbursement.setResolver(null);
                reimbursement.setErsReimbStatus(new ErsReimbStatus(rs.getString("status_id"),rs.getString("status")));
                reimbursement.setErsReimbType(new ErsReimbType(rs.getString("type_id"), rs.getString("type")));
                ersReimbursementArrayList.add(reimbursement);
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersReimbursementArrayList;
    }

    public List<ErsReimbursement> findAllSolvedReimbursementsByFM(ErsUser ersUserFM){
        List<ErsReimbursement> ersReimbursementArrayList = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(rootSelect+
                                                                "JOIN ers_users eu " +
                                                                "ON eu.user_id = er.author_id "+
                                                                "JOIN ers_user_roles eur "+
                                                                "ON eur.role_id = eu.role_id "+
                                                                "WHERE resolver_id = ? "+
                                                                "ORDER BY submitted");
            pstmt.setString(1, ersUserFM.getUser_id());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                ErsReimbursement reimbursement = new ErsReimbursement();
                reimbursement.setReimb_id(rs.getString("reimb_id"));
                reimbursement.setAmount(rs.getDouble("amount"));
                reimbursement.setSubmitted(rs.getTimestamp("submitted"));
                reimbursement.setResolved(rs.getTimestamp("resolved"));
                reimbursement.setDescription(rs.getString("description"));
                reimbursement.setReceipt(rs.getBytes("receipt"));
                reimbursement.setPayment_id(rs.getString("payment_id"));
                reimbursement.setAuthor(new ErsUser(rs.getString("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("given_name"),
                        rs.getString("surname"),
                        rs.getBoolean("is_active"),
                        new ErsUserRole(rs.getString("role_id"), "role")));
                reimbursement.setResolver(ersUserFM);
                reimbursement.setErsReimbStatus(new ErsReimbStatus(rs.getString("status_id"),rs.getString("status")));
                reimbursement.setErsReimbType(new ErsReimbType(rs.getString("type_id"), rs.getString("type")));
                ersReimbursementArrayList.add(reimbursement);
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }

        return ersReimbursementArrayList;
    }

    public void HandleReimbursementRequestByFM(ErsUser ersUserFM, ErsReimbursement ersReimbursement, boolean Approve){
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            //update resolved time; resolver_id; status_id;
            ErsReimbStatus ersReimbStatus = null;
            if(Approve){
                ersReimbStatus = findReimbStatusByStatusName("APPROVED");
            }else{
                ersReimbStatus = findReimbStatusByStatusName("DENIED");
            }
            PreparedStatement pstmt = conn.prepareStatement("UPDATE ers_reimbursements "+
                                                                "SET resolved = ? "+
                                                                "resolver_id = ? "+
                                                                "status_id =  ?");




            conn.commit();
        } catch (SQLException e) {
            throw new DataSourceException(e);
        }
    }

    //helper function
    public ErsReimbStatus findReimbStatusByStatusName(String status_name){
        ErsReimbStatus ersReimbStatus = null;

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_reimbursement_statuses "+
                                                                "WHERE status = ?");
            pstmt.setString(1, status_name);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                ersReimbStatus = new ErsReimbStatus();
                ersReimbStatus.setStatus_id(rs.getString("status_id"));
                ersReimbStatus.setStatus(rs.getString("status"));
            }
        }catch (SQLException e) {
            throw new DataSourceException(e);
        }
        return ersReimbStatus;
    }

    @Override
    public void save(ErsReimbursement newObject) {

    }

    @Override
    public ErsReimbursement getById(String id) {
        return null;
    }

    @Override
    public List<ErsReimbursement> getAll() {
        return null;
    }

    @Override
    public void update(ErsReimbursement updatedObject) {

    }

    @Override
    public void deleteById(String id) {

    }
}
