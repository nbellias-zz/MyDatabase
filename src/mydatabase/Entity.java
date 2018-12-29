/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mydatabase;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author nikolaos
 */
public class Entity implements Serializable {

    private String name;
    private List<Attribute> attributes;

    public Entity() {
    }

    public Entity(String name, List<Attribute> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

}
