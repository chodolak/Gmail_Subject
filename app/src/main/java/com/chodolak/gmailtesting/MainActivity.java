package com.chodolak.gmailtesting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.*;

public class MainActivity extends ActionBarActivity {

    ArrayList<String> subjectList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.button);

        subjectList = new ArrayList<String>();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subjectList.clear();

                final EditText email = (EditText)findViewById(R.id.editText);
                final EditText pass = (EditText)findViewById(R.id.passField);
                System.out.println(email.getText());
                System.out.println(pass.getText());

                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Properties props = new Properties();
                        props.setProperty("mail.store.protocol", "imaps");

                        Session session = Session.getDefaultInstance(props, null);
                        IMAPStore imapStore = null;

                        try {
                            imapStore = (IMAPStore) session.getStore("imaps");
                            imapStore.connect("imap.gmail.com", email.getText().toString(), pass.getText().toString());
                            final IMAPFolder folder = (IMAPFolder) imapStore.getFolder("Inbox");
                            folder.open(IMAPFolder.READ_WRITE);
                            Message m[] = folder.getMessages();
                            for (Message n : m) {
                                subjectList.add(n.getSubject());
                                System.out.println(n.getSubject());

                            }
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }

                    }

                };
                Thread t = new Thread(r);
                t.start();
                updateList();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateList(){
        ListView animalList = (ListView)findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, subjectList);
        animalList.setAdapter(arrayAdapter);
    }


}


