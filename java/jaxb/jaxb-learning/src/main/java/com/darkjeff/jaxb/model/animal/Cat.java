package com.darkjeff.jaxb.model.animal;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Cat extends Animal {

    private String furColor;

    public String getFurColor() {
        return furColor;
    }

    public void setFurColor(String furColor) {
        this.furColor = furColor;
    }
}
