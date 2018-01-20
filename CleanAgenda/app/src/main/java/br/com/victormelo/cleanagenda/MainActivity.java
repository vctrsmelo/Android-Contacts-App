package br.com.victormelo.cleanagenda;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Observer {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static final int NEW_CONTACT_REQUEST_CODE = 1;
    public static final int EDIT_CONTACT_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addContact = (FloatingActionButton) findViewById(R.id.buttonAddContact);
        addContact.setOnClickListener(this);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        ContactsManager.getInstance().ctsDb = getSharedPreferences(ContactsManager.getInstance().CONTACTS_FILE,0);

//        SharedPreferences.Editor editor = ContactsManager.getInstance().ctsDb.edit();
//        editor.clear();
//        editor.commit();

        ContactsManager.getInstance().loadContacts();

        // specify an adapter (see also next example)
        mAdapter = new ContactsAdapter(ContactsManager.getInstance().getContacts());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        ContactsManager.getInstance().addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof ContactsManager) {
                mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

        switch(view.getId()) {
            case R.id.buttonAddContact:
                Intent it = new Intent(this,EditContactActivity.class);
                startActivityForResult(it,NEW_CONTACT_REQUEST_CODE);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case(NEW_CONTACT_REQUEST_CODE) : {
                String newName = data.getStringExtra("name");
                String newPhone = data.getStringExtra("phone");
                ContactsManager.getInstance().addContact(newName,newPhone);

            }
        }
    }

}
