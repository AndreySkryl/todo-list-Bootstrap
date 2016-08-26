package database.todoList.model;

import database.todoList.utils.UtilForWorkWithMedia;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

@Component
public class User implements Serializable {
    public static final String GUID_OF_USER = "guidOfUser";

    String guid;
    String login;
    String lastName;
    String firstName;
    String password;
    String eMail;
    String pathToPhotoOfUser;
    Timestamp createTime;
    Timestamp updateTime;

    public User() {}

    public User(String login, String password, String eMail) {
        this.login = login;
        this.password = password;
        this.eMail = eMail;
    }

    public User(String login, String lastName, String firstName, String password, String eMail) {
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.password = password;
        this.eMail = eMail;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPathToPhotoOfUser() {
        return pathToPhotoOfUser;
    }

    public void setPathToPhotoOfUser(String pathToPhotoOfUser) {
        this.pathToPhotoOfUser = pathToPhotoOfUser;
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
    public boolean equals(Object obj) {
        /*if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;*/

        User other = (User) obj;
        return guid.equals(other.guid) &&
                login.equals(other.login) &&
                lastName.equals(other.lastName) &&
                firstName.equals(other.firstName) &&
                password.equals(other.password) &&
                eMail.equals(other.eMail) &&
                createTime.equals(other.createTime) &&
                updateTime.equals(other.updateTime);
    }

    @Override
    public String toString() {
        return "User{" +
                "guid='" + guid + '\'' +
                ", login='" + login + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", password='" + password + '\'' +
                ", eMail='" + eMail + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public static void convertFROMPathToPhotoOfUserTOBase64StringFromImage(User user) throws IOException {
        String base64StringFromImage = UtilForWorkWithMedia.getBASE64StringFromImage(user.getPathToPhotoOfUser());
        user.setPathToPhotoOfUser(base64StringFromImage);
    }

    public static void convertFROMPathToPhotoOfUserTOBase64StringFromImage(Collection<User> users) throws IOException {
        for (User user : users) {
            String base64StringFromImage = UtilForWorkWithMedia.getBASE64StringFromImage(user.getPathToPhotoOfUser());
            user.setPathToPhotoOfUser(base64StringFromImage);
        }
    }
}
