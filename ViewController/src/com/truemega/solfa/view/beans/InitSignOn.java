package com.truemega.solfa.view.beans;

import com.truemega.solfa.view.utils.JSFUtils;

import java.util.List;

import javax.faces.context.FacesContext;

import com.asset.encryption.Encryption;

import com.truemega.ldap.models.UserModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.truemega.solfa.model.util.ApplicationLogger;

import java.io.Serializable;

public class InitSignOn implements Serializable {
    @SuppressWarnings("compatibility:3929281663680298505")
    private static final long serialVersionUID = -1954303297925103117L;

    public InitSignOn() {
    }
    private UserModel usermodel;

    public String initSignOn() {
        // Add event code here...
        String userInfo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("user");
        String jsonString = Encryption.decryptString(userInfo);
        Gson gson = new GsonBuilder().create();
        usermodel = gson.fromJson(jsonString, UserModel.class);
        String userName = usermodel.getNtAccount();
        String password = usermodel.getPassword();
        JSFUtils.setExpressionValue("#{sessionScope.user}", userName);
        JSFUtils.setExpressionValue("#{sessionScope.password}", password);
        JSFUtils.setExpressionValue("#{sessionScope.usermodel}", usermodel);
        if (userName == null || userName.length() <= 0)
            return null;
        else
            ApplicationLogger.log("-----** ------ [ Logged User : ]"+userName+" ----- **-------",ApplicationLogger.INFO);
            return "home";
    }

    public void setUsermodel(UserModel usermodel) {
        this.usermodel = usermodel;
    }

    public UserModel getUsermodel() {
        return usermodel;
    }
}
