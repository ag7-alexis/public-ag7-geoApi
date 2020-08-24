package ag7.dev.ag7geoapi.controller.viewmodel;

import java.util.Date;

public class APIKeyViewModel {
    private String token;
    private Date dateEndToken;
    private boolean isUpdate;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDateEndToken() {
        return dateEndToken;
    }

    public void setDateEndToken(Date dateEndToken) {
        this.dateEndToken = dateEndToken;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    
    
}