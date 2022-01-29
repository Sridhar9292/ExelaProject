package com.exelatech.ecx.backend.domain;

public class JobResponse {
    private Body sid;
    public Body getSid() {
        return sid;
    }

    public void setSid(Body sid) {
        this.sid = sid;
    }


    public class Body {
        private String sid;

        public String getSid() {
            return sid;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }

        public Body() {
        }
    }

    public JobResponse() {
    }

   
}