package com.assignment2.ct0194173.currencyconverter;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DownloadTask extends AsyncTask<String,Void,List<Double>> {

    private List<Double> myList;
    private Document _document;
    private Context context;
    private ProgressDialog dialog;
    public static List<Double> list;


    public DownloadTask(Context context,Document document){
        _document = document;
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

            dialog = new ProgressDialog(context);
            dialog.setMessage("Please Wait...");
            dialog.show();
    }

    @Override
    protected List<Double> doInBackground(String... params) {
        String rates = request(params[0]);
        myList = new ArrayList<>();
        getRatesFromJson(myList, rates);
        return myList;
    }

    @Override
    protected void onPostExecute(List<Double> doubles) {
        super.onPostExecute(doubles);
        list.addAll(doubles);
        if(list.size() != 0){
            _document.setDocument(doubles);
            dialog.dismiss();
        }
        else{
            dialog.dismiss();
            Toast toast = Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }
    private String request(String urlString){
        StringBuilder str = new StringBuilder("");
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream stream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String JsonString="";
            while((JsonString = br.readLine()) != null){
                str.append(JsonString+"\n");
            }
            br.close();
            stream.close();
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString().trim();
    }
    private void getRatesFromJson(List<Double> myList, String str){
            double x;
            try {
                JSONObject object1 = new JSONObject(str);
                JSONObject object2 = object1.getJSONObject("rates");
                String[] currency_symbols = context.getResources().getStringArray(R.array.currency_symbols);
                for (int i = 0; i < currency_symbols.length; i++) {
                    String currency = String.valueOf(object2.get(currency_symbols[i]));
                    if(currency.length() < 5){
                        x = Double.valueOf(currency);
                    } else {
                        x = Double.valueOf((currency + "0.00000").substring(0, 5));
                    }
                    myList.add(x);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
    }

    interface Document{
        void setDocument(List<Double> list);
    }
}
