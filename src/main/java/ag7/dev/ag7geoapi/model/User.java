package ag7.dev.ag7geoapi.model;

import javax.persistence.*;

import java.util.List;
import java.util.Arrays;
import java.util.Date;
import java.util.ArrayList;

/**
 * User Entity
 * @ag7-alexis
 */
@Entity(name = "UserAccess")
@Table(name = "UserAccess", indexes = {@Index(name = "index_username_UserAccess", columnList = "username", unique = true)})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private int active = 1;

    private String apiKey;

    private Date dateEndApiKay;

    private String roles = "";

    private String permissions = "";

    public User() {
    }

    public User(long id, String username, String password, int active, String apiKey, String roles,
            String permissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.active = active;
        this.apiKey = apiKey;
        this.roles = roles;
        this.permissions = permissions;
    }

    public User(String username, String password, int active, String apiKey, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.apiKey = apiKey;
        this.roles = roles;
        this.permissions = permissions;
    }

    public User(String username, String password, String apiKey) {
        this.username = username;
        this.password = password;
        this.apiKey = apiKey;
    }
    
    public User(String username, String password, String roles, String permissions) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.permissions = permissions;
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Permet d'obtenir la liste des rôles car ils sont regroupés dans une seule chaine de caractère et séparés par des ','
     *
     * @return List<String>
     */

    public List<String> getRoleList() {
        if (this.roles.length() > 0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    /**
     * Permet d'obtenir la liste des permissions car elles sont regroupées dans une seule chaine de caractère et séparées par des ','
     *
     * @return List<String>
     */

    public List<String> getPermissionList() {
        if (this.permissions.length() > 0) {
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Date getDateEndApiKay() {
        return dateEndApiKay;
    }

    public void setDateEndApiKay(Date dateEndApiKay) {
        this.dateEndApiKay = dateEndApiKay;
    }


}
