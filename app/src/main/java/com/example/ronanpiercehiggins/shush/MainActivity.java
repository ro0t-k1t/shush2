package com.example.ronanpiercehiggins.shush;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.async.callback.AsyncCallback;



public class MainActivity extends AppCompatActivity implements OnClickListener {


    EditText emailField;
    EditText passwordField;
    TextView login;
    TextView signup;
    private ProgressBar spinner;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner=(ProgressBar)findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);


        String appVersion = "v1";
        Backendless.initApp(this, "A0819152-C875-C222-FF18-0516AB9ACC00", "94E2E030-C1B8-0F27-FFEE-CD829BAE3400", appVersion);
        Log.i("info", "backendless success");


        emailField = (EditText) findViewById(R.id.email);
        passwordField = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);
        signup = (TextView) findViewById(R.id.signup);
        login.setOnClickListener(this);
        signup.setOnClickListener(this);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.login) {

            spinner.setVisibility(View.VISIBLE);


            Backendless.UserService.login( String.valueOf(emailField.getText()), String.valueOf(passwordField.getText()), new AsyncCallback<BackendlessUser>()
            {
                public void handleResponse( BackendlessUser user )
                {
                    Log.i("info", user.getEmail() + " is logged in!");
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class );
                    startActivity(i);
                    spinner.setVisibility(View.GONE);
                }

                public void handleFault( BackendlessFault fault )
                {
                    Log.i("info", "user login failed");

                    Log.i("info", fault.toString());
                    spinner.setVisibility(View.GONE);
                    passwordField.setError("Invalid login or password");
                }
            });


        } else if (v.getId() == R.id.signup) {

            spinner.setVisibility(View.VISIBLE);

            BackendlessUser user = new BackendlessUser();
            user.setEmail(String.valueOf(emailField.getText()));
            user.setPassword(String.valueOf(passwordField.getText()));


            try {

                Backendless.UserService.register( user, new BackendlessCallback<BackendlessUser>()
                {

                    public void handleResponse( BackendlessUser backendlessUser )
                    {
                        Log.i( "info", backendlessUser.getEmail() + " successfully registered" );
                        Toast.makeText(getApplicationContext(), "You have successfully registered!", Toast.LENGTH_SHORT).show();


                        Backendless.UserService.login( String.valueOf(emailField.getText()), String.valueOf(passwordField.getText()), new AsyncCallback<BackendlessUser>()
                        {
                            public void handleResponse( BackendlessUser user )
                            {
                                Log.i("info", user.getEmail() + " is logged in!");
                                Intent i = new Intent(getApplicationContext(), HomeActivity.class );
                                startActivity(i);
                                spinner.setVisibility(View.GONE);
                            }

                            public void handleFault( BackendlessFault fault )
                            {
                                Log.i("info", "user login failed");
                                spinner.setVisibility(View.GONE);
                                emailField.setError("Please try again");
                            }
                        });


                    }
                    public void handleFault( BackendlessFault fault )
                    {
                        Log.i( "info", fault.getCode());

                        spinner.setVisibility(View.GONE);

                        emailField.setError("User already exists");
                    }

                } );



            } catch (Exception e) {

            }

        }

    }


}
