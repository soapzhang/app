package com.soap.app.entity;

public class ResponseEntity {
    private Integer status;
    private String messgae;
    private Object result;


    public static ResponseEntity ok(Object data){
        return new ResponseEntity(200,"OK",data);
    }
    public static ResponseEntity ok(){
        return new ResponseEntity(200,"OK",null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    public ResponseEntity(Integer status, String messgae, Object result) {
        this.status = status;
        this.messgae = messgae;
        this.result = result;
    }

    public ResponseEntity() {
    }

}
