package com.trueffect.response;
import com.trueffect.util.ModelObject;

/*
 * @author santiago.mamani
 */
public class CorrectResponse {
    private int code;
    private String message;
    private ModelObject resourse;

    public CorrectResponse(int code, String message, ModelObject resourse) {
        this.code = code;
        this.message = message;
        this.resourse = resourse;
    }

    public CorrectResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ModelObject getResourse() {
        return resourse;
    }

    public void setResourse(ModelObject resourse) {
        this.resourse = resourse;
    }
    
}
