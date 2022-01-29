package com.exelatech.ecxapi.model;

import java.util.Date;

/**
 * Created by VenkataDurgavarjhula on 11/26/2015.
 */
public class Comment{
    private Date timestamp;
    private String commentedBy;
    private String remarks;

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(String commentedBy) {
        this.commentedBy = commentedBy;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (!timestamp.equals(comment.timestamp)) return false;
        if (commentedBy != null ? !commentedBy.equals(comment.commentedBy) : comment.commentedBy != null) return false;
        return !(remarks != null ? !remarks.equals(comment.remarks) : comment.remarks != null);

    }

    @Override
    public int hashCode() {
        int result = timestamp.hashCode();
        result = 31 * result + (commentedBy != null ? commentedBy.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        return result;
    }
}
