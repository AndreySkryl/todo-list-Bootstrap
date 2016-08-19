package database.todoList.model;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Task {
    public static final String GUID_OF_TASK = "guidOfTask";

    String listOfTasksGuid;
    String guid;
    Status status;
    String description;
    Timestamp createTime;
    Timestamp updateTime;

    public Task() {}

    public Task(String listOfTasksGuid, String guid, Status status, String description) {
        this.listOfTasksGuid = listOfTasksGuid;
        this.guid = guid;
        this.status = status;
        this.description = description;
    }

    public String getListOfTasksGuid() {
        return listOfTasksGuid;
    }

    public void setListOfTasksGuid(String listOfTasksGuid) {
        this.listOfTasksGuid = listOfTasksGuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
		this.guid = guid;
	}

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return "Task{" +
                "listOfTasksGuid='" + listOfTasksGuid + '\'' +
                ", guid='" + guid + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
