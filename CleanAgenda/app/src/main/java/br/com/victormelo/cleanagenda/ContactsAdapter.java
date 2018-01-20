package br.com.victormelo.cleanagenda;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by victorsmelo on 19/01/18.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>{

    private ArrayList<Contact> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public View mView;

        public ViewHolder(View v) {
            super(v);
            mView = v;

            mView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(v.getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(v.getContext());
            }

            CharSequence colors[] = new CharSequence[] {"Delete"};

            final TextView nameTextView = (TextView) v.findViewById(R.id.contactNameTextView);
            builder.setTitle(nameTextView.getText().toString());
            builder.setItems(colors, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // the user clicked on colors[which]
                    Log.i("Selected",which+"");
                    switch(which){
                        case 0:
                            //delete
                            ContactsManager.getInstance().delete(nameTextView.getText().toString());
                    }
                }

            }).show();
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsAdapter(ArrayList<Contact> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);

        // set the view's size, margins, paddings and layout parameters ...
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        TextView nameTextView = (TextView) holder.mView.getRootView().findViewById(R.id.contactNameTextView);
        TextView phoneTextView = (TextView) holder.mView.getRootView().findViewById(R.id.contactPhoneNumberTextView);

        nameTextView.setText(mDataset.get(position).getName());
        phoneTextView.setText(mDataset.get(position).getPhoneNumber());




    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
