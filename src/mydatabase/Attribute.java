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
public class Attribute implements Serializable{

    private String name;
    private DataType dataType;
    private Integer size;

    public Attribute(){
    }

    public Attribute(String name, DataType dataType, Integer size) {
        this.name = name;
        this.dataType = dataType;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

}
