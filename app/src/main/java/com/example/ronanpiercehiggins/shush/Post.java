package com.example.ronanpiercehiggins.shush;


public class Post {

    private String content;
    private String objectId;
    private String name;
    private int likes;

    public Post()
    {
    }




    public String getContent()
    {
        return content;
    }

    public String getId()
    {
        return objectId;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }


    public void setContent( String content )
    {
        this.content = content;
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
