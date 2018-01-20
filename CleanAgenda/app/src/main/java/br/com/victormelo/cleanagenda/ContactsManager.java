package br.com.victormelo.cleanagenda;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Created by victorsmelo on 20/01/18.
 */

public class ContactsManager extends Observable {


    private static ContactsManager singleton;

    private ArrayList<Contact> contacts;

    private Boolean isLoaded = false;

    public final String CONTACTS_FILE = "MyContactsFile";
    public final String NEXT_ID = "NextId";

    public SharedPreferences ctsDb;

    private ContactsManager() {

    }

    public static synchronized ContactsManager getInstance() {

        if(singleton == null) {
            singleton = new ContactsManager();
        }

        return singleton;

    }


    public ArrayList<Contact> getContacts() {

        if(!isLoaded) {
            loadContacts();
        }

        return contacts;
    }

    public void addContact(String name, String phone) {

        int contactId = ctsDb.getInt(NEXT_ID,0);

        Contact contact = new Contact(contactId,name,phone);

        contacts.add(contact);

        //database changes
        SharedPreferences.Editor editor = ctsDb.edit();
        editor.putString("name_"+contactId,contact.getName());
        editor.putString("phone_"+contactId,contact.getPhoneNumber());

        //increase next index
        editor.putInt(NEXT_ID,contactId+1);

        editor.commit();

        setChanged();
        notifyObservers();

    }

    public void loadContacts() {

        Integer nextId = ctsDb.getInt(NEXT_ID,0);

        contacts = new ArrayList<Contact>(nextId);

        for(int i=0;i<nextId;i++){

            String name = ctsDb.getString("name_"+i,"");
            String number = ctsDb.getString("phone_"+i,"");

            if(!name.equals("")) {

                Contact cont = new Contact(i, name, number);
                contacts.add(cont);

            }

        }

        isLoaded = true;

        setChanged();
        notifyObservers();

    }

    public void delete(String name) {

        for(Contact c : contacts) {
            if(c.getName().equals(name)){
                contacts.remove(c);
                deleteFromDb(c);

                setChanged();
                notifyObservers();

                break;
            }
        }

    }

    private void deleteFromDb(Contact c) {

        SharedPreferences.Editor editor = ctsDb.edit();
        editor.remove("name_"+c.getId());
        editor.remove("phone_"+c.getId());

        editor.commit();

    }

}
