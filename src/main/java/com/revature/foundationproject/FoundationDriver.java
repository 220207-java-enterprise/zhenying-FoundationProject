package com.revature.foundationproject;

import com.revature.foundationproject.util.AppState;
import com.revature.foundationproject.util.ConnectionFactory;

import java.sql.*;

public class FoundationDriver {


    public static void main(String[] args) {
        /*try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ers_users");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String outMessage = " " + rs.getString("username") +
                        " " + rs.getString("email") +
                        " " + rs.getString("password") +
                        " " + rs.getString("given_name");
                System.out.println(outMessage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        AppState app = new AppState();
        app.startup();

    }
}
