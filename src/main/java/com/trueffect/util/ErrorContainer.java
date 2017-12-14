package com.trueffect.util;

import com.trueffect.response.ErrorResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author santiago.mamani
 */
public class ErrorContainer {

    private List<ErrorResponse> listError;

    public ErrorContainer() {
        listError = new ArrayList<ErrorResponse>();
    }

    public List<ErrorResponse> getListError() {
        return listError;
    }

    public void addError(ErrorResponse errorResponse) {
        listError.add(errorResponse);
    }

    public int size() {
        return listError.size();
    }

    public String allMessagesError() {
        String res = "";
        for (int i = 0; i < listError.size(); i++) {
            res = res + "\n" + listError.get(i).getMessage();
        }
        return res;
    }

    public int getCodeStatusEnd() {
        return listError.get(listError.size() - 1).getCode();
    }

    public boolean isEmpty() {
        return listError.size() == 0;
    }

}
