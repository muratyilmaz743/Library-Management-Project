package org.Library.util;

import java.sql.SQLException;
import java.sql.Statement;

public class Member extends Account {
    @Override
    public String getID() {
        return super.getID();
    }

    public static void blocked(String memberID, Statement statement){
        try {
            String SQL = "UPDATE library.member SET status = '"+MemberStatus.BLOCKED+"' WHERE (`memberID` = "+memberID +")";

            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void unblocked(String memberID, Statement statement) {
        try {
            String SQL = "UPDATE library.member SET status = '"+MemberStatus.ACTIVE+"' WHERE (`memberID` = "+memberID +")";

            statement.executeUpdate(SQL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
