package dao;

import dto.EventBackup;
import util.utildemo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventBackupDaoImp implements EventBackupDao{


    public List<EventBackup> backup() {
        Connection conn = null;
        List<EventBackup> backupList = new ArrayList<>();

        try {
            conn = utildemo.getConnection();
            CallableStatement cs = conn.prepareCall("{call getBackup_procedure()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                EventBackup eventBackup = new EventBackup();
                eventBackup.setBackup_id(rs.getInt("backup_id"));
                eventBackup.setTable_name(rs.getString("table_name"));
                eventBackup.setEvent_type(rs.getString("event_type"));
                eventBackup.setBefore_change(rs.getString("before_change"));
                eventBackup.setAfter_change(rs.getString("after_change"));
                eventBackup.setEvent_time(rs.getDate("event_time"));
                backupList.add(eventBackup);
            }
            for (EventBackup eventBackup : backupList) System.out.println(eventBackup);
            if (rs != null) rs.close();
            if (cs != null) cs.close();
            if (conn != null) conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return backupList;
    }
}
