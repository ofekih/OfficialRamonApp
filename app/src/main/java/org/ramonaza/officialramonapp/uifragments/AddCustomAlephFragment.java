package org.ramonaza.officialramonapp.uifragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ramonaza.officialramonapp.R;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ConDriveDatabaseHelper;
import org.ramonaza.officialramonapp.datafiles.condrive_database.ContactInfoWrapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddCustomAlephFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddCustomAlephFragment extends Fragment {



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddCustomAlephFragment.
     */
    public static AddCustomAlephFragment newInstance() {
        AddCustomAlephFragment fragment = new AddCustomAlephFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddCustomAlephFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_add_custom_aleph, container, false);
        Button submitButton=(Button) rootView.findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(new SubmitListener(getActivity(),rootView));
        return rootView;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class SubmitListener implements View.OnClickListener{
        Context context;
        ContactInfoWrapper mContact;
        View myView;

        public SubmitListener(Context incontext, View inView){
            this.myView=inView;
            this.context=incontext;
            this.mContact=new ContactInfoWrapper();
        }

        @Override
        public void onClick(View v) {
            ConDriveDatabaseHelper dbHelper=new ConDriveDatabaseHelper(context);
            SQLiteDatabase db=dbHelper.getWritableDatabase();

            EditText nameField=(EditText) myView.findViewById(R.id.AddAlephName);
            EditText addressField=(EditText) myView.findViewById(R.id.AddAlephAddress);
            EditText phoneField=(EditText) myView.findViewById(R.id.AddAlephPhone);
            EditText schoolField=(EditText) myView.findViewById(R.id.AddAlephSchool);
            EditText emailField=(EditText) myView.findViewById(R.id.AddAlephEmail);
            EditText gradeField= (EditText) myView.findViewById(R.id.AddAlephGrade);

            String nameVal=nameField.getText().toString();
            String addressVal=addressField.getText().toString();
            String phoneVal=phoneField.getText().toString();
            String schoolVal=schoolField.getText().toString();
            String emailVal=emailField.getText().toString();
            String gradeVal=gradeField.getText().toString();
            Set<String> valArray=new HashSet<String>(Arrays.asList(nameVal,addressVal,phoneVal,schoolVal,emailVal,gradeVal));
            if(valArray.contains(null)|| valArray.contains("")){
                Toast.makeText(context,R.string.error_blank_responce,Toast.LENGTH_SHORT).show();
                return;
            }

            mContact.setName(nameVal);
            mContact.setAddress(addressVal);
            mContact.setPhoneNumber(phoneVal);
            mContact.setSchool(schoolVal);
            mContact.setEmail(emailVal);
            int grade=Integer.parseInt(gradeVal);
            mContact.setGrade(grade);
            mContact.setPresent(true);

            try {
                dbHelper.addContact(mContact, db);
                getActivity().finish();
            } catch (ConDriveDatabaseHelper.ContactCSVReadError contactCSVReadError) {
                contactCSVReadError.printStackTrace();
            }
        }
    }


}
