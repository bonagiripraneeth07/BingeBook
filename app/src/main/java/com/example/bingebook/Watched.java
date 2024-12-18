package com.example.bingebook;

public class Watched {
    private int userid;
    private int  watched;
    private int rating;

    public Watched(int userid, int watched, int rating, String comment) {
        this.userid = userid;
        this.watched = watched;
        this.rating = rating;
        this.comment = comment;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;
}
