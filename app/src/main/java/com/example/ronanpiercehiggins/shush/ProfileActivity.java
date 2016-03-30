package com.example.ronanpiercehiggins.shush;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.Iterator;


public class ProfileActivity extends AppCompatActivity {

    ArrayList<String> emails;
    ArrayList<Integer> likes;
    ArrayList<String> ObjectId;

    ArrayList<String> emails2;
    ArrayList<Integer> likes2;
    ArrayList<String> ObjectId2;

    Button submitButton2;
    EditText postComment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        Intent i = getIntent();
        final String activeEmail = i.getStringExtra("postId");

        //setTitle(activeEmail);

        Log.i("info", activeEmail);

        /*Comment comment = new Comment();
        comment.setComment( "The comment worked" );
        comment.setPost(activeEmail);


        Backendless.Persistence.of( Comment.class ).save( comment, new AsyncCallback<Comment>()
        {
            @Override
            public void handleResponse( Comment comment )
            {


            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {
            }
        } );*/

        emails = new ArrayList<String>();
        likes = new ArrayList<Integer>();
        ObjectId = new ArrayList<String>();

        final CustomList adapter = new CustomList(ProfileActivity.this, emails, likes, ObjectId);
        final ListView emailList = (ListView) findViewById(R.id.postView);


        emails2 = new ArrayList<String>();
        likes2 = new ArrayList<Integer>();
        ObjectId2 = new ArrayList<String>();

        final CustomList adapter2 = new CustomList(ProfileActivity.this, emails2, likes2, ObjectId2);
        final ListView emailList2 = (ListView) findViewById(R.id.commentView);


        postComment = (EditText) findViewById(R.id.postComment);
        submitButton2 = (Button) findViewById(R.id.comment);

        submitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("info", "post comment worked!");
                Log.i("info", String.valueOf(postComment.getText()));

                Comment comment = new Comment();
                comment.setComment(String.valueOf(postComment.getText()));
                comment.setPost(activeEmail);
                comment.setLikes(0);


                Backendless.Persistence.of( Comment.class ).save( comment, new AsyncCallback<Comment>()
                {
                    @Override
                    public void handleResponse( Comment comment )
                    {



                        emails2.add(0, comment.getComment());

                        likes2.add(0, comment.getLikes());

                        ObjectId.add(0, comment.getId());

                        adapter2.notifyDataSetChanged();




                    }

                    @Override
                    public void handleFault( BackendlessFault backendlessFault )
                    {
                    }
                } );



            }
        });

        String clause = "objectId = " + "'" + activeEmail + "'";
        Log.i("info", clause);
        BackendlessDataQuery dataQuery = new BackendlessDataQuery();
        dataQuery.setWhereClause(clause);


        Backendless.Data.of( Post.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<Post>>()
        {
            @Override
            public void handleResponse( BackendlessCollection<Post> nycPeople )
            {


                Iterator<Post> iterator = nycPeople.getCurrentPage().iterator();





                while( iterator.hasNext() )
                {

                    Post post = iterator.next();

                    setTitle(post.getName());

                    emails.add(post.getContent());

                    likes.add(post.getLikes());

                    ObjectId.add(post.getId());



                }

                emailList.setAdapter(adapter);


                Log.i("info", "success");

            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {

                Log.i("info", "failed");

            }
        } );


        String clause2 = "post = " + "'" + activeEmail + "'";
        Log.i("info", clause2);
        BackendlessDataQuery dataQuery2 = new BackendlessDataQuery();
        dataQuery2.setWhereClause(clause2);


        Backendless.Data.of( Comment.class ).find( dataQuery2, new AsyncCallback<BackendlessCollection<Comment>>()
        {
            @Override
            public void handleResponse( BackendlessCollection<Comment> nycPeople )
            {


                Iterator<Comment> iterator = nycPeople.getCurrentPage().iterator();





                while( iterator.hasNext() )
                {

                    Comment comment = iterator.next();


                    emails2.add(comment.getComment());

                    likes2.add(comment.getLikes());

                    ObjectId2.add(comment.getId());



                }

                emailList2.setAdapter(adapter2);


                Log.i("info", "success");

            }

            @Override
            public void handleFault( BackendlessFault backendlessFault )
            {

                Log.i("info", "failed");

            }
        } );



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profilemenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.back) {


            /*Intent i = new Intent(getApplicationContext(), HomeActivity.class );
            startActivity(i);*/

            Intent i = getIntent();
            String activeEmail = i.getStringExtra("postId");

            String clause = "objectId = " + "'" + activeEmail + "'";
            Log.i("info", clause);
            BackendlessDataQuery dataQuery = new BackendlessDataQuery();
            dataQuery.setWhereClause(clause);


            Backendless.Data.of( Post.class ).find( dataQuery, new AsyncCallback<BackendlessCollection<Post>>()
            {
                @Override
                public void handleResponse( BackendlessCollection<Post> nycPeople )
                {


                    Iterator<Post> iterator = nycPeople.getCurrentPage().iterator();


                    while( iterator.hasNext() )
                    {

                        Post post = iterator.next();

                        Backendless.Persistence.of( Post.class ).remove(post, new AsyncCallback<Long>() {

                            public void handleResponse( Long response )
                            {

                                Log.i("info", "instance deleted");
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class );
                                startActivity(i);
                            }
                            public void handleFault( BackendlessFault backendlessFault )
                            {
                            }


                        });





                    }




                    Log.i("info", "success");

                }

                @Override
                public void handleFault( BackendlessFault backendlessFault )
                {

                    Log.i("info", "failed");

                }
            } );


            return true;
        }

        return super.onOptionsItemSelected(item);


    }
}
