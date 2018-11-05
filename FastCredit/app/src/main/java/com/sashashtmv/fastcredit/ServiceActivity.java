package com.sashashtmv.fastcredit;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ServiceActivity extends SingleFragmentActivity {

//    private String req = null;
//    final ArrayList prodList = new ArrayList();
//    final ArrayList prodId = new ArrayList();
//    private String strJson = null;
//    private String url_api = "http://gertek-parts.ru/api.php?name=";

    public static Intent newIntent(Context packageContext){
        Intent intent = new Intent(packageContext, ServiceActivity.class);

        return intent;
    }
    @Override
    protected Fragment createFragment() {
        return ServicesFragment.newInstance();
    }

//    private class ParseTask extends AsyncTask<Void, Void, String> {
//
//        HttpURLConnection uC;
//        BufferedReader br;
//        String resultJoin;
//
//        InputStream inpStr;
//        StringBuffer sb;
//
//        URL url;
//        @Override
//        protected String doInBackground(Void... voids) {
//            try{
//                url = new URL(url_api+req);
//
//                uC = (HttpURLConnection) url.openConnection();
//                uC.setRequestMethod("GET");
//                uC.connect();
//
//                inpStr = uC.getInputStream();
//                sb = new StringBuffer();
//                br = new BufferedReader(new InputStreamReader(inpStr));
//
//                String line;
//                while ((line = br.readLine()) != null) {
//                    sb.append(line);
//                }
//                resultJoin = sb.toString();
//            }catch (Exception ex){
//
//            }
//            return resultJoin;
//        }

//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            JSONObject dJO ;
//            JSONArray products;
//
//            ListView lw;
//
//            final ArrayAdapter<String> adapter;
//
//            try {
//
//                dJO = new JSONObject(s);
//
//                products = dJO.getJSONArray("products");
//
//                for (int i = 0; i < products.length(); i++) {
//
//                    JSONObject prodInfo = products.getJSONObject(i);
//
//                    prodList.add(prodInfo.getString("name"));
//
//                    prodId.add(prodInfo.getString("id"));
//
//                }
//
//                lw = (ListView) findViewById(R.id.lists);
//
//                adapter = new ArrayAdapter<String>(MainActivity.this,
//                        android.R.layout.simple_list_item_1, prodList);
//
//                lw.setAdapter(adapter);
//
//                lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position,
//                                            long id) {
//
//                        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
//
//                        intent.putExtra("ProdInf", prodId.get(position).toString());
//
//                        startActivity(intent);
//
//                    }
//                });
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
