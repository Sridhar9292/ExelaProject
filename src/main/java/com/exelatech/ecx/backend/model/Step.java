package com.exelatech.ecx.backend.model;

import lombok.Data;

/**
 * Created by VenkataDurgavarjhula on 7/3/2016.
 */
@Data
public class Step {
    private String color;
    private String name;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Step{" +
                "color='" + color + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
