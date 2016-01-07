package com.vaiuu.androidtrello.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vaiuu.androidtrello.R;
import com.vaiuu.androidtrello.trello.AddCardActivity;
import com.vaiuu.androidtrello.trello.TrelloUtils;
import com.vaiuu.androidtrello.trello.adapter.BoardAdapter;
import com.vaiuu.androidtrello.trello.holder.AllTrelloBoardHolder;
import com.vaiuu.androidtrello.trello.model.BoardModel;
import com.vaiuu.androidtrello.trello.parser.BoardListParser;
import com.vaiuu.androidtrello.trello.parser.CardListParser;
import com.vaiuu.androidtrello.util.Constant;
import com.vaiuu.androidtrello.util.IVolleyRespose;
import com.vaiuu.androidtrello.util.SharedPreferencesHelper;
import com.vaiuu.androidtrello.util.ToastUtil;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;

public class TrelloMainActivity extends BaseActivity implements IVolleyRespose {
    private String TAG = "TrelloMainActivity";
    private Context context;
    private SharedPreferencesHelper sharedPreferencesHelper;
    private ProgressDialog progressDialog;
    private BoardAdapter boardAdapter;
    private String respones_results;
    private ToastUtil toastUtil;

    private WebView trello_webview;
    private ListView trello_board_list;

    String finalURL=Constant.WEBSERVICE_URL+"members/me/boards/";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.trello_home);
        context = this;
        toastUtil = new ToastUtil(TrelloMainActivity.this);
        sharedPreferencesHelper = new SharedPreferencesHelper();
        initUI();
    }

    @SuppressWarnings("static-access")
    private void initUI() {
        trello_webview = (WebView) findViewById(R.id.trello_webview);
        trello_board_list = (ListView) findViewById(R.id.trello_board_list);
        trello_board_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final BoardModel query = AllTrelloBoardHolder.getAllBoardlist().elementAt(position);
                if (!sharedPreferencesHelper.isOnline(context)) {
                    toastUtil.appSuccessMsg(context, TrelloUtils.INTERNET_ERROR);
                } else {
                    getTrelloBoard(finalURL,sharedPreferencesHelper.getTrelloAuth(context),TrelloUtils.APPLICATION_ID);
                   // new LoadTrelloCardAsyncTask().execute(TrelloUtils.getListsByBoard(sharedPreferencesHelper.getTrelloAuth(context), query.getId()));
                }

            }
        });
        if (!sharedPreferencesHelper.getTrelloAuth(context).equalsIgnoreCase("")) {
            trello_webview.setVisibility(View.GONE);
            Log.i(TAG, "Url are " + TrelloUtils.getAllBoardsUrl(sharedPreferencesHelper.getTrelloAuth(context)));
            if (!sharedPreferencesHelper.isOnline(context)) {
                toastUtil.appSuccessMsg(context, TrelloUtils.INTERNET_ERROR);
            } else {
                getTrelloBoard(finalURL,sharedPreferencesHelper.getTrelloAuth(context),TrelloUtils.APPLICATION_ID);
               // new LoadTrelloBoardAsyncTask().execute(TrelloUtils.getAllBoardsUrl(sharedPreferencesHelper.getTrelloAuth(context)));
            }

        } else {
            if (!sharedPreferencesHelper.isOnline(context)) {
                toastUtil.appSuccessMsg(context, TrelloUtils.INTERNET_ERROR);
            } else {
                trelloWebView();
            }

        }

    }


    private void trelloWebView() {
        trello_webview.setVisibility(View.VISIBLE);
        trello_board_list.setVisibility(View.GONE);
        trello_webview.getSettings().setJavaScriptEnabled(true);
        trello_webview.setScrollbarFadingEnabled(false);
        trello_webview.getSettings().setBuiltInZoomControls(true);
        trello_webview.getSettings().setUseWideViewPort(true);
        trello_webview.getSettings().setPluginState(PluginState.ON);
        trello_webview.getSettings().setLoadWithOverviewMode(true);
        trello_webview.getSettings().setAllowFileAccess(true);
        trello_webview.getSettings().setLoadWithOverviewMode(true);
        trello_webview.getSettings().setUseWideViewPort(true);
        trello_webview.loadUrl(TrelloUtils.TRELLO_AUTH_URL);
        trello_webview.setWebViewClient(new WebViewClient() {
            @SuppressWarnings("static-access")
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // do your handling codes here, which url is the requested url
                // probably you need to open that url rather than redirect:
                if (url.contains("http://127.0.0.1:8080/")) {
                    //Toast.makeText(context, "Url Are " + url, Toast.LENGTH_LONG).show();
                    trello_webview.setVisibility(View.GONE);
                    trello_board_list.setVisibility(View.VISIBLE);
                    sharedPreferencesHelper.setTrelloAuth(context, url.substring(29, url.length()));

                    getTrelloBoard(finalURL,sharedPreferencesHelper.getTrelloAuth(context),TrelloUtils.APPLICATION_ID);
                   // new LoadTrelloBoardAsyncTask().execute(TrelloUtils.getAllBoardsUrl(sharedPreferencesHelper.getTrelloAuth(context)));
                }
                view.loadUrl(url);
                return false; // then it is not handled by default action
            }
        });
    }

    public void BACK(View view) {
        TrelloMainActivity.this.finish();
    }

    /**
     * Used For History
     */
    private void getTrelloBoard(String finalURL,String trelloAuth,String appID) {
        HashMap<String, String> mMap = new HashMap<String, String>();
        String keys[] = {"key","token"};
        String values[] = {appID,trelloAuth};
        String finalWebURL = prepareWebserviceRequestHtml(finalURL,Constant.TRELLO_BOARD, keys, values);
        Log.i("Web URl Are ", finalWebURL);
        callVolley(this, Constant.GET_REQUEST, Constant.TRELLO_BOARD, finalWebURL, mMap,
                Constant.CALL_TIME_OUT_LONG, false, Constant.VOLLEY_RETRY_COUNT, true, false, this);

    }
    @Override
    public void onVolleyResponse(int responseCode, String mRes, String ResponseTag) {
        if (mRes != null) {
            try {
                if (ResponseTag.equals(Constant.TRELLO_BOARD)) {
                    {
                        try {

                                try {
                                    if (BoardListParser.connect(context, mRes)) {
                                        boardAdapter = new BoardAdapter(context, AllTrelloBoardHolder.getAllBoardlist());
                                        trello_board_list.setAdapter(boardAdapter);
                                        boardAdapter.notifyDataSetChanged();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                        } catch (Exception e) {
                            e.printStackTrace();
                            //printToastAlert(getApplicationContext(),""+e.getMessage());
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onVolleyError(int Code, String mError, String ResponseTag) {

    }

    class LoadTrelloBoardAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TrelloMainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(TrelloUtils.TRELLO_LOADING_BOARDS);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
            /*try {
                //respones_results = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(aurl[0]));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }*/
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(String getResult) {
            progressDialog.dismiss();
            Log.i(TAG, "Response ARe " + respones_results);
            try {
                if (BoardListParser.connect(context, respones_results)) {
                    boardAdapter = new BoardAdapter(context, AllTrelloBoardHolder.getAllBoardlist());
                    trello_board_list.setAdapter(boardAdapter);
                    boardAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    class LoadTrelloCardAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(TrelloMainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(TrelloUtils.TRELLO_LOADING_CARDS);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... aurl) {
          /*  try {
                respones_results = HttpRequest.GetText(HttpRequest.getInputStreamForGetRequest(aurl[0]));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }*/
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);
        }

        @SuppressLint("NewApi")
        protected void onPostExecute(String getResult) {
            progressDialog.dismiss();
            Log.i(TAG, "Card Response are " + respones_results);
            try {
                if (CardListParser.connect(context, respones_results)) {
                    Intent intent = new Intent(TrelloMainActivity.this, AddCardActivity.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

}
