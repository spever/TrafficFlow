package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Contact;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hasee on 2016/3/24.
 */
public class ContactResponse implements Serializable{

    public boolean success;

    public int lenght;

    public ArrayList<Contact> data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getLenght() {
        return lenght;
    }

    public void setLenght(int lenght) {
        this.lenght = lenght;
    }

    public ArrayList<Contact> getData() {
        return data;
    }

    public void setData(ArrayList<Contact> data) {
        this.data = data;
    }
}
