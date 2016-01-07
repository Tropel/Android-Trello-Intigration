package com.vaiuu.androidtrello.trello;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.vaiuu.androidtrello.R;
import com.vaiuu.androidtrello.trello.holder.AllTrelloCardHolder;
import com.vaiuu.androidtrello.trello.model.CardModel;
import com.vaiuu.androidtrello.util.SharedPreferencesHelper;
import com.vaiuu.androidtrello.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddCardActivity extends FragmentActivity implements View.OnClickListener {
    private String TAG = "AddCardActivity";
    private Context context;
    List<String> cardNameList = new ArrayList<String>();
    private SharedPreferencesHelper sharedPreferencesHelper;
    private String boardListId;
    private ProgressDialog progressDialog;
    private String response = "";
    private ToastUtil toastUtil;

    private Spinner card_spinner;
    private EditText card_subject;
    private EditText trello_engg_name;
    private EditText trello_desription;
    private Button btn_addcard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addcard_layout);
        context = this;
        toastUtil=new ToastUtil(AddCardActivity.this);
        sharedPreferencesHelper = new SharedPreferencesHelper();
        for (int i = 0; i < AllTrelloCardHolder.getAllCardlist().size(); i++) {
            CardModel query = AllTrelloCardHolder.getAllCardlist().elementAt(i);
            cardNameList.add(query.getName());
        }
        initUI();

    }

    public void initUI() {
        card_spinner = (Spinner) findViewById(R.id.card_spinner);
        card_subject = (EditText) findViewById(R.id.card_subject);
        trello_engg_name=(EditText)findViewById(R.id.trello_engg_name);
        trello_desription=(EditText)findViewById(R.id.trello_desription);
        btn_addcard = (Button) findViewById(R.id.btn_addcard);
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardNameList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        card_spinner.setAdapter(dataAdapter);
        card_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0,View arg1, int position, long arg3) {
                CardModel _query = AllTrelloCardHolder.getAllCardlist().elementAt(position);
                boardListId = _query.getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        btn_addcard.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addcard:
                if(card_subject.getText().toString().equalsIgnoreCase("")){
                    toastUtil.appSuccessMsg(context, "Please Enter Description");
                    card_subject.setError("Please Enter Subject");
                }else if(boardListId.equalsIgnoreCase("")){
                    toastUtil.appSuccessMsg(context, "Please select from list");
                }else{
                    if (!sharedPreferencesHelper.isOnline(context)) {
                        toastUtil.appSuccessMsg(context, TrelloUtils.INTERNET_ERROR);
                    } else {
                        addCard(TrelloUtils.APPLICATION_ID, sharedPreferencesHelper.getTrelloAuth(context),
                                card_subject.getText().toString(), boardListId,
                                trello_engg_name.getText().toString(),trello_desription.getText().toString());
                    }
                }


                break;
        }
    }

    private void addCard(final String key,final String token,final String desc,final String boardListID,final String enggName,final String desC){
        if (!sharedPreferencesHelper.isOnline(context)) {
            toastUtil.appSuccessMsg(context, TrelloUtils.INTERNET_ERROR);
        }
        progressDialog = new ProgressDialog(AddCardActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(TrelloUtils.TRELLO_SUBMIT_CARD);
        progressDialog.show();
        (new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected Object doInBackground(Object... params) {
                    /*HTTPPostHelper url = AllURL.getAddCard(key,token,desc, boardListID,enggName,desC);
                    response = HTTPHandler.GetPostDataFromURL(url);*/
                return null;
            }
            @Override
            protected void onPostExecute(Object result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if(jsonObject.getString("id").equalsIgnoreCase("")){
                    }else {
                        toastUtil.appSuccessMsg(context, TrelloUtils.TRELLO_SUCCESS);
                        AddCardActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    toastUtil.appSuccessMsg(context, response);
                }
                Log.i(TAG,"response are "+response);
            }
        }).execute();
    }
    public void BACK(View view) {
        AddCardActivity.this.finish();
    }


}
