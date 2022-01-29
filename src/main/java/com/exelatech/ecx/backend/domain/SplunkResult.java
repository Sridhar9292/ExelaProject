package com.exelatech.ecx.backend.domain;

import java.util.ArrayList;

public class SplunkResult<T> {

    private ArrayList<Messages> messages;

    private ArrayList<Fields> fields;

    private ArrayList<T> results = new ArrayList<T>();

    public ArrayList<T> getResults() {
        return results;
    }

    public ArrayList<Messages> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Messages> messages) {
        this.messages = messages;
    }

    public ArrayList<Fields> getFields() {
        return fields;
    }

    public void setFields(ArrayList<Fields> fields) {
        this.fields = fields;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }

    public class Fields {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Fields() {
        }

        public Fields(String name) {
            this.name = name;
        }
    }

    public class Messages {
        private String type;
        private String text;

        public String getType() {
            return type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Messages() {
        }

        public Messages(String type, String text) {
            this.type = type;
            this.text = text;
        }
    }

    public SplunkResult(ArrayList<Messages> messages, ArrayList<Fields> fields, ArrayList<T> results) {
        this.messages = messages;
        this.fields = fields;
        this.results = results;
    }

    public SplunkResult() {
    }

}
