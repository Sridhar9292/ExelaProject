package com.exelatech.ecxapi.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class LabelValue implements Serializable {
    private static final long serialVersionUID = 3689355407466181430L;
    private String label;
    private String value;
    /**
     * Construct an instance with the supplied property values.
     *
     * @param label The label to be displayed to the user.
     * @param value The value to be returned to the server.
     */
    public LabelValue(final String label, final String value) {
        this.label = label;
        this.value = value;
    }
}