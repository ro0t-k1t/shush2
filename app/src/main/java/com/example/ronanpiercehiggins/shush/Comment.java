package com.example.ronanpiercehiggins.shush;

/**
 * Created by ronanpiercehiggins on 26/02/16.
 */
public class Comment {


    private String comment;
    private String objectId;
    private String post;
    private int likes;

    public Comment()
    {
    }




    public String getComment()
    {
        return comment;
    }

    public String getId()
    {
        return objectId;
    }

    public String getPost() { return post; }

    public void setPost( String post )
    {
        this.post = post;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public int getLikes()
    {
        return likes;
    }

    public void setLikes( int likes )
    {
        this.likes = likes;
    }





}
