package com.subzero.trafficflow.response;

import com.subzero.trafficflow.bean.Server;

import java.util.List;

/**
 * Created by hui on 2016/3/9.
 */
public class ServerResponse  {

    /**
     * success : true
     * length : 2
     * data : [{"SERVER_NAME":"部级交调中心","SERVER_PATH":"http://219.141.223.137:8080/jddsi/rest"},{"SERVER_NAME":"广东省交调中心","SERVER_PATH":"http://219.141.223.137:8080/jddsi/rest"}]
     */
    private boolean success;
        private List<Server> data;

        public void setData(List<Server> data) {
            this.data = data;
        }
        public List<Server> getData() {
            return data;
        }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
