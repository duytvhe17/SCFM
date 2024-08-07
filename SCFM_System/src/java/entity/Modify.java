/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

/**
 *
 * @author nguye
 */
public class Modify {
    private int modifyId;
    private String modifyName;

    public Modify() {
    }

    public Modify(String modifyName) {
        this.modifyName = modifyName;
    }

    public int getModifyId() {
        return modifyId;
    }

    public void setModifyId(int modifyId) {
        this.modifyId = modifyId;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    @Override
    public String toString() {
        return "Modify{" + "modifyId=" + modifyId + ", modifyName=" + modifyName + '}';
    }
    
    
}
