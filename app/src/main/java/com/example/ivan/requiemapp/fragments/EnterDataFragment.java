package com.example.ivan.requiemapp.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.ivan.requiemapp.R;
import com.example.ivan.requiemapp.models.PlacesModel;
import com.example.ivan.requiemapp.models.ServiceModel;
import com.example.ivan.requiemapp.models.SupportModel;
import com.example.ivan.requiemapp.models.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class EnterDataFragment extends Fragment {
    private EditText edt_mjesto, edt_usluga, edt_vrijeme, edt_datum, edt_dodatno;
    private CheckBox chk_voznja;
    private FloatingActionButton fab;
    private PlacesModel placesModel;
    private ServiceModel serviceModel;
    private SupportModel supportModel;
    private UserModel userModel;
    private DatabaseReference databaseReference;
    private FirebaseUser userId;
    private String mjestaID, uslugaID, dodatnoID;
    private Map<String, Object> data1 = new HashMap<String, Object>();


    public EnterDataFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enter_data, container, false);

        edt_mjesto = (EditText)view.findViewById(R.id.edt_place);
        edt_usluga = (EditText)view.findViewById(R.id.edt_usluga);
        edt_datum = (EditText)view.findViewById(R.id.edt_datum);
        edt_vrijeme = (EditText)view.findViewById(R.id.edt_vrijeme);
        chk_voznja = (CheckBox)view.findViewById(R.id.chk_voznja);
        edt_dodatno = (EditText)view.findViewById(R.id.edt_dodatno);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser();
       // databaseReference.child(userId.getUid()).setValue(user);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placesModel = new PlacesModel(edt_mjesto.getText().toString().trim());
                serviceModel = new ServiceModel(edt_usluga.getText().toString().trim(),
                        "1300 kn", edt_vrijeme.getText().toString().trim(),
                        edt_datum.getText().toString().trim(),
                        7);
                supportModel = null;

                saveDodatno(supportModel);
                savePlaces(placesModel);
                saveService(serviceModel);
                updateUser();

                databaseReference.child("mjesta").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot da:dataSnapshot.getChildren()) {
                            PlacesModel pm = da.getValue(PlacesModel.class);

                            Log.v("MJESTO", pm.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

        return view;
    }


    /**
     * Metoda za provjeru texta i checkbox statusa te spremanje tog objekta u firebase bazu
     * @param supportModel objekt tipa SupportModel
     */
    public void saveDodatno(SupportModel supportModel){

        if(chk_voznja.isChecked() && !TextUtils.isEmpty(edt_dodatno.getText().toString().trim())){
            supportModel = new SupportModel(true, edt_dodatno.getText().toString().trim());
        }else if(chk_voznja.isChecked() && TextUtils.isEmpty(edt_dodatno.getText().toString().trim())){
            supportModel = new SupportModel(true, edt_dodatno.getText().toString().trim());
        }else if(!chk_voznja.isChecked() && !TextUtils.isEmpty(edt_dodatno.getText().toString().trim())){
            supportModel = new SupportModel(false, edt_dodatno.getText().toString().trim());
        }
        if(supportModel != null){
            dodatnoID = databaseReference.child("dodatno").push().getKey();
            databaseReference.child("dodatno").push().setValue(supportModel);
        }

    }

    /**
     * Metoda za spremanje modela PlacesModel
     * @param placesModel objekt tipa PlacesModel
     */
    public void savePlaces(PlacesModel placesModel){
        mjestaID = databaseReference.child("mjesta").push().getKey();

        databaseReference.child("mjesta").push().setValue(placesModel);

    }

    /**
     * Metoda za spremanje objekta tipa ServiceModel
     * @param serviceModel objekt tipa ServiceModel
     */
    public void saveService(ServiceModel serviceModel){
        uslugaID = databaseReference.child("usluga").push().getKey();

        databaseReference.child("usluga/"+uslugaID).setValue(serviceModel);
        if(!TextUtils.isEmpty(dodatnoID)) {
            databaseReference.child("usluga/" + uslugaID + "/dodatno/dodatnoID").child(dodatnoID).setValue(true);
        }
    }

    /**
     * Metoda za update podataka autentificiranog korisnika
     */
    public void updateUser(){
        data1.put("uslugaID/" + uslugaID, true);
        data1.put("mjestaID/" + mjestaID, true);
        data1.put("uslugaID/" + uslugaID, true);

        databaseReference.child("users").child(userId.getUid()).updateChildren(data1);

    }

}
