package com.darkjeff.jaxb.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {


    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @XmlAttribute
    private String name;

    @XmlAttribute
    private int age;

}
