package database.todoList.utils;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UtilForManageResources implements Serializable {
	// { table | model } "User"
	public static final String PATH_TO_RESOURCES_OF_TABLE_USER = "resources/user/";
	public static final String PATH_TO_DEFAULT_PHOTO_OF_USER = "resources/user/img/photo_of_user.png";
	public static final String DEFAULT_NAME_OF_FILE_OF_PHOTO_OF_USER = "photoOfUser";
}
