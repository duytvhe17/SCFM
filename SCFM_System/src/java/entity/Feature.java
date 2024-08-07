/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;
import java.util.List;



public class Feature {
    private int featureId;
    private String featureName;
    private String url;
    private List<Role> roles = new ArrayList<>();

    public Feature() {
    }

    public Feature( String featureName, String url) {
       
        this.featureName = featureName;
        this.url = url;
    }
    

    public int getId() {
        return featureId;
    }

    public void setId(int featureId) {
        this.featureId = featureId;
    }

    public String getName() {
        return featureName;
    }

    public void setName(String featureName) {
        this.featureName = featureName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "Feature{" + "featureId=" + featureId + ", featureName=" + featureName + ", url=" + url + ", roles=" + roles + '}';
    }
    
}
