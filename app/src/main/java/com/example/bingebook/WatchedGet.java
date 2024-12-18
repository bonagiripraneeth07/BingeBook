package com.example.bingebook;

public class WatchedGet {

    String comment;
    int rating;
    int watched;

    public WatchedGet(String comment, int rating, int watched) {
        this.comment = comment;
        this.rating = rating;
        this.watched = watched;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }
    public String toString() {
        return "WatchedGet{" +
                "comment='" + comment + '\'' +
                ", rating=" + rating +
                ", watched='" + watched + '\'' +
                '}';
    }
}
