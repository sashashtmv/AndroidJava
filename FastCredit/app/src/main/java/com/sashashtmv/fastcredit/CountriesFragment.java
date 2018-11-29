package com.sashashtmv.fastcredit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sashashtmv.fastcredit.cards.Card;
import com.sashashtmv.fastcredit.cards.CardLab;
import com.sashashtmv.fastcredit.credits.Bank;
import com.sashashtmv.fastcredit.credits.BankLab;
import com.sashashtmv.fastcredit.loans.Loaner;
import com.sashashtmv.fastcredit.loans.LoanerLab;
import com.sashashtmv.fastcredit.other.Other;
import com.sashashtmv.fastcredit.other.OtherLab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CountriesFragment extends Fragment {


    public CountriesFragment() {

    }

    public static CountriesFragment newInstance() {
        CountriesFragment fragment = new CountriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }
    AsyncTask parser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] mas = getResources().getStringArray(R.array.countries);
        String[] res = new String[mas.length];
        List<String> countriesBuf = Arrays.asList("", "argentina.json", "armenia.json", "australia.json", "bulgaria.json", "canada.json", "colombia.json",
                "croatia.json", "czechRepublic.json", "denmark.json", "dominicanRepublic.json", "estonia.json", "finland.json", "georgia.json", "germany.json", "hungary.json",
                "indonesia.json", "kazakhstan.json", "latvia.json", "lithuania.json", "mexico.json", "moldova.json", "norway.json", "panama.json", "poland.json", "romania.json",
                "russia.json", "sweden.json", "ukraine.json", "unitedKingdom.json", "unitedStates.json");
        List<String> buff = new ArrayList<>();
        res[0] = mas[0];
        int j = 1;
        buff.add("");
        if (Locale.getDefault().getCountry() == "RU") {
            res[1] = "Россия";
            buff.add("russia.json");
            for (int i = 2; i < mas.length; i++) {
                if (!mas[j].equals("Россия")) {
                    res[i] = mas[j];
                    buff.add(countriesBuf.get(j));
                    j++;
                } else {
                    i--;
                    j++;
                }
            }
        }
        View v = inflater.inflate(R.layout.fragment_countries, container, false);

        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        if(res[1] == null){
            res = mas;
            buff = countriesBuf;
        }
        final List<String> countries = buff;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item, res);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) v.findViewById(R.id.spinner_countries);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                ((TextView) parent.getChildAt(0)).setTextSize(24);
                if (position > 0) {
                    String country = countries.get(position);

                    parser = new ParseTask(country).execute();
                    Intent intent = ServiceActivity.newIntent(getActivity());//запуск активности из фрагмента
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        setRetainInstance(true);

        return v;
    }

    public List<String> reversCountry(List<String> list, String reverse) {
        list.remove(reverse);
        list.add(1, reverse);
        return list;
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {


        HttpURLConnection uC;
        BufferedReader br;
        String resultJoin;
        String nameCountry;

        InputStream inpStr;
        StringBuffer sb;

        URL url;

        public ParseTask(String nameCountry) {
            this.nameCountry = nameCountry;
        }

        @Override
        protected String doInBackground(Void... params) {

            try {

                url = new URL("https://drawall.ru/finance1/jsons/" + nameCountry);
                uC = (HttpURLConnection) url.openConnection();
                uC.setRequestMethod("GET");
                uC.connect();

                inpStr = uC.getInputStream();
                sb = new StringBuffer();

                br = new BufferedReader(new InputStreamReader(inpStr));

                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                resultJoin = sb.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
                //parser.cancel(true);
            return resultJoin;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);

            JSONObject dJO;
            JSONArray banks;
            JSONArray loans;
            JSONArray cards;
            JSONArray others;

            List<Bank> bankList = new ArrayList<>();
            List<Loaner> loanList = new ArrayList<>();
            List<Card> cardList = new ArrayList<>();
            List<Other> otherList = new ArrayList<>();


            try {
                dJO = new JSONObject(strJson);

                banks = dJO.getJSONArray("banks");
                if (banks!= null) {

                    for (int i = 0; i < banks.length(); i++) {
                        JSONObject bank = banks.getJSONObject(i);
                        Bank bankItem = new Bank();
                        bankItem.setAdressBank(Uri.parse(bank.getString("url")));
                        bankItem.setAdressPicture(Uri.parse(bank.getString("iconUrl")));
                        bankItem.setDescription(bank.getString("description"));
                        bankItem.setTitle(bank.getString("title"));

                        bankList.add(bankItem);
                    }
                new BankLab(bankList);
                }

                loans = dJO.getJSONArray("loans");
                if (loans!= null) {
                    for (int i = 0; i < loans.length(); i++) {
                        JSONObject loan = loans.getJSONObject(i);
                        Loaner loanItem = new Loaner();
                        loanItem.setAdressLoaner(Uri.parse(loan.getString("url")));
                        loanItem.setAdressPicture(Uri.parse(loan.getString("iconUrl")));
                        loanItem.setDescription(loan.getString("description"));
                        loanItem.setTitle(loan.getString("title"));

                        loanList.add(loanItem);
                    }
                new LoanerLab(loanList);
                }

                cards = dJO.getJSONArray("cards");
                if (cards!= null) {
                    for (int i = 0; i < cards.length(); i++) {
                        JSONObject card = cards.getJSONObject(i);
                        Card cardItem = new Card();
                        cardItem.setAdressBank(Uri.parse(card.getString("url")));
                        cardItem.setAdressPicture(Uri.parse(card.getString("iconUrl")));
                        cardItem.setDescription(card.getString("description"));
                        cardItem.setTitle(card.getString("title"));

                        cardList.add(cardItem);
                    }
                new CardLab(cardList);
                }

                others = dJO.getJSONArray("other");
                if (others != null) {
                    for (int i = 0; i < others.length(); i++) {

                        JSONObject other = others.getJSONObject(i);
                        Other otherItem = new Other();
                        otherItem.setAdressOther(Uri.parse(other.getString("url")));
                        otherItem.setAdressPicture(Uri.parse(other.getString("iconUrl")));
                        otherItem.setDescription(other.getString("description"));
                        otherItem.setTitle(other.getString("title"));

                        otherList.add(otherItem);
                    }
                new OtherLab(otherList);
                }
                parser.cancel(true);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception e){
            }
        }

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
