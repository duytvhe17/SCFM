/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author sonnt
 */
public class Role {
    private int roleId;
    private String name;
    private List<User> users = new ArrayList<>();
    private List<Feature> features = new ArrayList<>();

    public Role() {
    }

    public Role(int roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }


    

    public int getId() {
        return roleId;
    }

    public void setId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Feature> features) {
        this.features = features;
    }

    @Override
    public String toString() {
        return "Role{" + "roleId=" + roleId + ", name=" + name + ", users=" + users + ", features=" + features + '}';
    }
    
}
