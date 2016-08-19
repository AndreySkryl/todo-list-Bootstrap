package database.todoList.model;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Colleague {
    public static final String GUID_OF_COLLEAGUE = "guidOfColleague";

    String userGuid;
    String colleagueGuid;
    Timestamp createTime;
    Timestamp updateTime;

    public Colleague() {}

    public Colleague(String userGuid, String colleagueGuid) {
        this.userGuid = userGuid;
        this.colleagueGuid = colleagueGuid;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getColleagueGuid() {
        return colleagueGuid;
    }

    public void setColleagueGuid(String colleagueGuid) {
        this.colleagueGuid = colleagueGuid;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Colleague{" +
                "userGuid='" + userGuid + '\'' +
                ", colleagueGuid='" + colleagueGuid + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}