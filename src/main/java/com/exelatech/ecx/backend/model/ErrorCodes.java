package com.exelatech.ecx.backend.model;

import java.io.Serializable;

/**
 * Created by VenkataDurgavarjhula on 12/10/2015.
 */
public class ErrorCodes extends BaseObject implements Serializable {
    private String errorCode;
    private String errorDesc;
    private String errorCause;
    private String errorResolution;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getErrorCause() {
        return errorCause;
    }

    public void setErrorCause(String errorCause) {
        this.errorCause = errorCause;
    }

    public String getErrorResolution() {
        return errorResolution;
    }

    public void setErrorResolution(String errorResolution) {
        this.errorResolution = errorResolution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorCodes that = (ErrorCodes) o;

        return errorCode.equals(that.errorCode);

    }

    @Override
    public int hashCode() {
        return errorCode.hashCode();
    }

    @Override
    public String toString() {
        return "ErrorCodes{" +
                "errorCode='" + errorCode + '\'' +
                ", errorDesc='" + errorDesc + '\'' +
                ", errorCause='" + errorCause + '\'' +
                ", errorResolution='" + errorResolution + '\'' +
                '}';
    }
}
