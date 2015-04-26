package com.chodolak.gmailtesting;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.*;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button)findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Properties props = new Properties();
                        props.setProperty("mail.store.protocol", "imaps");

                        Session session = Session.getDefaultInstance(props, null);
                        IMAPStore imapStore = null;

                        try {
                            imapStore = (IMAPStore) session.getStore("imaps");
                            imapStore.connect("imap.gmail.com", "maxzoofake1@gmail.com", "5vevrt12");
                            final IMAPFolder folder = (IMAPFolder) imapStore.getFolder("Inbox");
                            folder.open(IMAPFolder.READ_WRITE);
                            Message m[] = folder.getMessages();
                            for (Message n : m) {
                                System.out.println(n.getSubject());
                            }
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }

                    }
                };
                Thread t = new Thread(r);
                t.start();
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

}


