package database.todoList.model;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class ListOfTasks {
    public static final String GUID_OF_LIST_Of_TASKS = "guidOfListOfTasks";

    String guid;
    String userGuid;
    Integer favourites;
    String name;
    String description;
    Timestamp createTime;
    Timestamp updateTime;

    public ListOfTasks() {}

    public ListOfTasks(String guid, String userGuid, int favourites, String name) {
        this.guid = guid;
        this.userGuid = userGuid;
        this.favourites = favourites;
        this.name = name;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

	public Integer getFavourites() {
		return favourites;
	}

	public void setFavourites(int favourites) {
		this.favourites = favourites;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ListOfTasks{" +
                "guid='" + guid + '\'' +
                ", userGuid='" + userGuid + '\'' +
                ", favourites=" + favourites +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
