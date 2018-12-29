/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydatabase;

import java.io.Serializable;

/**
 *
 * @author nikolaos
 */
public class DataType implements Serializable{

    private String name; // Number, Varchar, Boolean, Date, other...

    public DataType() {
    }

    public DataType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
