package api.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.GregorianCalendar;

/**
 * Created by charles on 24/09/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client implements Serializable {


    @JsonProperty("name")
    private String name;

    @JsonProperty("id")
    private String userId;

    @JsonProperty("email")
    private String email;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("birthday")
    private String birthday;

    @JsonProperty("picture")
    private String base64Picture;

    private GregorianCalendar birthdayGregorianCalendar;

    public Client() {
    }

    public Client(String name, String gender, String email, String birthday, String userId, String base64Picture) {
        setName(name);
        setGender(gender);
        setEmail(email);
        setBase64Picture(base64Picture);
        setBirthday(birthday);
        setUserId(userId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBase64Picture(String base64Picture) {
        this.base64Picture = base64Picture;
    }

    public String getName() {

        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {

        return email;
    }

    public String getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getBase64Picture() {
        return base64Picture;
    }

    public String getUserId() {
        return userId;
    }
}
