package com.darkjeff.jaxb.model.animal;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Dog extends Animal {

    private Integer age;
    private Owner owner;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
