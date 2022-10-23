package com.example.Personal_Trainer;


import com.android.volley.AuthFailureError;

import com.android.volley.Response;

import com.android.volley.toolbox.StringRequest;


import java.util.HashMap;

import java.util.Map;

/**
 * Created by kch on 2018. 5. 14..
 */


public class ValidateRequest extends StringRequest {

    final static private String URL = "http://ec2-3-38-94-242.ap-northeast-2.compute.amazonaws.com/checkID.php";
    private Map<String, String> parameters;

    public ValidateRequest(String userID, Response.Listener<String> listener){
    super(Method.POST, URL, listener, null);//해당 URL에 POST방식으로 파마미터들을 전송함

        parameters = new HashMap<>();
        parameters.put("userID", userID);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
