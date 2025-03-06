package dto;

import lombok.Data;

import java.util.Date;
@Data
public class EventBackup {
    private int backup_id;
    private  String table_name;
    private String event_type;
    private String before_change;
    private String after_change;
    private Date event_time;

    @Override
    public String toString() {
        return "backup_id : " + backup_id +
                ", table_name :'" + table_name + '\'' +
                ", event_type : '" + event_type + '\'' +
                ", before_change :'" + before_change + '\'' +
                ", after_change :'" + after_change + '\'' +
                ", event_time : " + event_time;
    }
}