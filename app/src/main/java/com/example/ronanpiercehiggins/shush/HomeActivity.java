package com.example.ronanpiercehiggins.shush;


import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;


import java.util.ArrayList;
import java.util.Iterator;


public class HomeActivity extends AppCompatActivity {

    ArrayList<String> emails;
    ArrayList<Integer> likes;
    ArrayList<String> ObjectId;


    TextView submitButton;
    EditText postStatus;

    //TextView delete;

    /*public void delete(View view) {

        Log.i("info", "worked");

        Intent i = new Intent(getApplicationContext(), ProfileActivity.class );
        i.putExtra("email", "worked");
        startActivity(i);

    }*/






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle(Backendless.UserService.CurrentUser().getEmail());

        emails = new ArrayList<String>();
        likes = new ArrayList<Integer>();
        ObjectId = new ArrayList<String>();

        final CustomList adapter = new CustomList(HomeActivity.this, emails, likes, ObjectId);
        final ListView emailList = (ListView) findViewById(R.id.email_list);


        postStatus = (EditText) findViewById(R.id.poststatus);
        submitButton = (TextView) findViewById(R.id.submit);
        //delete = (TextView) findViewById(R.id.comments);

        /*delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("info", "delete worked");

            }
        });

        /*delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.i("info", "delete worked");

            }

        });*/


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.i("info", "it worked mother fuckers!!!");

                Post post = new Post();
                post.setContent(String.valueOf(postStatus.getText()));
                post.setName(Backendless.UserService.CurrentUser().getEmail());
                post.setLikes(0);


                Backendless.Persistence.of( Post.class ).save( post, new AsyncCallback<Post>()
                {
                    @Override
                    public void handleResponse( Post post1 )
                    {



                        emails.add(0, post1.getContent());

                        likes.add(0, post1.getLikes());

                        ObjectId.add(0, post1.getId());

                        adapter.notifyDataSetChanged();




                    }

                    @Override
                    public void handleFault( BackendlessFault backendlessFault )
                    {
                    }
                } );

            }
        });







        String clause = "";
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


                    emails.add(post.getContent());

                    likes.add(post.getLikes());

                    ObjectId.add(post.getId());

                    Log.i("info", "ObjectId");

                    Log.i("info", post.getId());




                }


                emailList.setAdapter(adapter);

                emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(final AdapterView<?> parent, final View view,
                                            int position, long id) {


                        Log.i("info", ObjectId.get(position));

                        Intent i = new Intent(getApplicationContext(), ProfileActivity.class );
                        i.putExtra("postId", ObjectId.get(position));
                        startActivity(i);



                    }
                });
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.logout) {

            Log.i("info", "action button tapped");

            Backendless.UserService.logout(new AsyncCallback<Void>()
            {
                public void handleResponse( Void response )
                {
                    Log.i("info", "Logout successful");

                    Intent i = new Intent(getApplicationContext(), MainActivity.class );
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                }

                public void handleFault( BackendlessFault fault )
                {
                    Log.i("info", "Logout failed");
                    Toast.makeText(getApplicationContext(), "Logout unsuccessful", Toast.LENGTH_SHORT).show();
                }
            });

            return true;
        }

        return super.onOptionsItemSelected(item);


    }


}
