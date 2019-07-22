package com.sashashtmv.waterorder.fragments;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;
import com.sashashtmv.waterorder.PreferenceHelper;
import com.sashashtmv.waterorder.R;
import com.sashashtmv.waterorder.model.Customer;
import com.sashashtmv.waterorder.model.Order;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;


public class InformationFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView mDateDelivery;
    private static TextView mAddressDelivery;
    private TextView mTimeDelivery;
    private Button mSelectAddress;
    private Button mSelectDateRight;
    private Button mSelectDateLeft;
    private Button mSelectTimeRight;
    private Button mSelectTimeLeft;
    private Button mOrder;
    private ImageView mBrend;
    Toolbar toolbar;

    DialogDelivery mDialogDelivery;
    private Customer mCustomer;

    private final long timeDate = 86400000;
    private long currentTime;
    private int count = 0;
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
    private SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    private String[] mas = new String[]{"С 08:00 до 12:00", "С 12:00 до 15:00", "С 15:00 до 18:00"};
    private Calendar mCalendar;
    private int i = 0;
    private String mDate;
    private String mTime;
    private String adress;

    FragmentManager mFragmentManager;
    private PreferenceHelper mPreferenceHelper;
    private EndFragment endFragment;
    private Order mWaterOrder;


    public InformationFragment() {
        // Required empty public constructor
    }

    public static InformationFragment newInstance() {
        InformationFragment fragment = new InformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, fragment.mDate);
        args.putString(ARG_PARAM2, fragment.mTime);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDate = getArguments().getString(ARG_PARAM1);
            mTime = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        setHasOptionsMenu(true);

        toolbar = view.findViewById(R.id.toolbar_fragment_information);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);


        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
        activity.getSupportActionBar().setTitle("");

        PreferenceHelper.getInstance().init(getActivity());
        mPreferenceHelper = PreferenceHelper.getInstance();
        mFragmentManager = activity.getFragmentManager();
        mDialogDelivery = new DialogDelivery();
        mBrend = view.findViewById(R.id.iv_brand);

        mDateDelivery = view.findViewById(R.id.tx_date_delivery);
        mAddressDelivery = view.findViewById(R.id.tx_address_delivery);
        mTimeDelivery = view.findViewById(R.id.tx_time_delivery);

        mSelectAddress = view.findViewById(R.id.bt_select_address_right);
        mSelectDateRight = view.findViewById(R.id.bt_right_date);
        mSelectDateLeft = view.findViewById(R.id.bt_left_date);
        mSelectTimeRight = view.findViewById(R.id.bt_right_time);
        mSelectTimeLeft = view.findViewById(R.id.bt_left_time);

        mCalendar = Calendar.getInstance();
        currentTime = mCalendar.getTimeInMillis();
        mDate = dayFormat.format(currentTime) + " " + monthFormat.format(currentTime);
        mDateDelivery.setText(mDate);
        mDateDelivery.setTextColor(getResources().getColor(R.color.Black));
        mDateDelivery.setTextSize(24);
        mTimeDelivery.setTextColor(getResources().getColor(R.color.Black));
        mTimeDelivery.setTextSize(20);

        mOrder = view.findViewById(R.id.bt_order);

        mBrend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
                startActivity(intent);
            }
        });

        mSelectDateRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                currentTime = currentTime + timeDate;
                i = 0;
                mTime = mas[i];
                mDate = dayFormat.format(currentTime) + " " + monthFormat.format(currentTime);
                mTimeDelivery.setText(mTime);
                mDateDelivery.setText(mDate);
            }
        });
        mSelectDateLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 0) {
                    count--;
                    currentTime = currentTime - timeDate;
                }
                if (count == 0) {
                    if (mCalendar.before(new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) - 5, mCalendar.get(Calendar.DAY_OF_YEAR), 12, 0))) {
                        i = 1;
                    } else if (mCalendar.before(new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) - 5, mCalendar.get(Calendar.DAY_OF_YEAR), 15, 0))) {
                        i = 2;
                    } else {
                        i = 0;
                    }
                    mTime = mas[i];
                    mTimeDelivery.setText(mTime);
                }
                mDate = dayFormat.format(currentTime) + " " + monthFormat.format(currentTime);
                mDateDelivery.setText(mDate);
            }
        });


        if (mCalendar.before(new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) - 5, mCalendar.get(Calendar.DAY_OF_YEAR), 12, 0))) {
            i = 1;
            Log.i(TAG, "mCalendar: " + mCalendar.getTime() + ", FiltreTime " + new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH)-5, mCalendar.get(Calendar.DAY_OF_YEAR), 11, 0).getTime());
        } else if (mCalendar.before(new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) - 5, mCalendar.get(Calendar.DAY_OF_YEAR), 15, 0))) {
            i = 2;
        } else {
            i = 0;
            mCalendar.add(Calendar.DAY_OF_YEAR, 1);
            currentTime = mCalendar.getTimeInMillis();
            mDate = dayFormat.format(currentTime) + " " + monthFormat.format(currentTime);
            mDateDelivery.setText(mDate);
        }
        mTime = mas[i];
        mTimeDelivery.setText(mTime);

        mSelectTimeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "i: " + i);
                    if (i != 2) {
                        i++;
                        mTime = mas[i];
                        mTimeDelivery.setText(mTime);
                    }
            }
        });

        mSelectTimeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        Toast.makeText(getContext(),new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH)-3, mCalendar.get(Calendar.DAY_OF_YEAR), 17, 0).getTime().toString()
//                + "     " + mCalendar.getTime().toString() + "   " + i, Toast.LENGTH_LONG).show();
                if ((currentTime - 1000) > mCalendar.getTimeInMillis()) {
                    if (i > 0) {
                        i--;
                    }
                } else if (mCalendar.before(new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) - 5, mCalendar.get(Calendar.DAY_OF_YEAR), 12, 0))) {
                    if (i > 1) {
                        i--;
                    }
                } else if (mCalendar.before(new GregorianCalendar(mCalendar.get(Calendar.YEAR), mCalendar.get(Calendar.MONTH) - 5, mCalendar.get(Calendar.DAY_OF_YEAR), 15, 0))) {
                    if (i > 2) {
                        i--;
                    }
                } else {
                    if (i > 0) {
                        i--;
                    }
                }
                mTime = mas[i];
                mTimeDelivery.setText(mTime);
            }
        });

        mSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogDelivery.show(getFragmentManager(), "dialog");
            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWaterOrder != null && mCustomer != null) {
                    String message = "«Аquacity»: " + mWaterOrder.getCountWater1() + " бут., " + "«Источник здоровья»: " + mWaterOrder.getCountWater2() + " бут., " + "«Н2ВiO»: " + mWaterOrder.getCountWater3() + " бут., " +
                            "Тара: " + mWaterOrder.getCountTara() + ", Помпа: " + mWaterOrder.getCountPompa() + ", Общая сумма: " + mWaterOrder.getSum() + " грн." + " Имя заказчика - " + mCustomer.getName() +
                            " Адрес заказчика - улица " + mCustomer.getNameStreet() + ", дом " + mCustomer.getNumberHouse() + ", подъезд/квартира - " + mCustomer.getFlatNumber() + ". " + "Номер телефона - " +
                            mCustomer.getTelephoneNumber() + "Название компании - " + mCustomer.getNameCompany() + ", Дата доставки - " + mDateDelivery.getText() + ", Время доставки - " + mTimeDelivery.getText();
                    //Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                    Intent i = new Intent(Intent.ACTION_SEND);
//                    i.setType("message/rfc822");
//                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{"sashashtmv@gmail.com"});
//                    i.putExtra(Intent.EXTRA_SUBJECT, "Order");
//                    i.putExtra(Intent.EXTRA_TEXT, message);
//                    try {
//                        startActivity(Intent.createChooser(i, "Send mail..."));
//                    } catch (android.content.ActivityNotFoundException ex) {
//                        //Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
//                    }
                    ParseObject orders = new ParseObject("Orders");

                    Calendar cal = Calendar.getInstance(); // creates calendar
                    cal.setTime(new Date()); // sets calendar time/date
                    cal.add(Calendar.HOUR_OF_DAY, 3); // adds one hour
// Store an object
                    orders.put("TimeOrder", cal.getTime());
                    orders.put("NameCustomer", mCustomer.getName());
                    orders.put("TelephoneNumber", mCustomer.getTelephoneNumber());
                    orders.put("AddressCustomer", "ул." + mCustomer.getNameStreet() + ", дом " + mCustomer.getNumberHouse() + ", подъезд/кв - " + mCustomer.getFlatNumber());
                    orders.put("NameCompany", mCustomer.getNameCompany());
                    orders.put("Water1", mWaterOrder.getCountWater1());
                    orders.put("ResourceHealth", mWaterOrder.getCountWater2());
                    orders.put("Water3", mWaterOrder.getCountWater3());
                    orders.put("Tara", mWaterOrder.getCountTara());
                    orders.put("Pompa", mWaterOrder.getCountPompa());
                    orders.put("Sum", mWaterOrder.getSum());
                    orders.put("TimeAndDateDelivery", mTimeDelivery.getText() + ", " + mDateDelivery.getText());
// Saving object
                    orders.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Success
                            } else {
                                // Error
                            }
                        }
                    });
//отправка письма через сервер
                    Map<String, String> params = new HashMap<>();

                    String emailAddress = "sima.binar@gmail.com";
                    String emailSubject = "Order water";
                    String emailBody = message;
// Create the fields "emailAddress", "emailSubject" and "emailBody"
// As Strings and use this piece of code to add it to the request
                    params.put("toEmail", emailAddress);
                    params.put("subject", emailSubject);
                    params.put("body", emailBody);

                    ParseCloud.callFunctionInBackground("sendGridEmail", params, new FunctionCallback<Object>() {
                        @Override
                        public void done(Object response, ParseException exc) {
                            if (exc == null) {
//                                Toast.makeText(getActivity(), mAddressDelivery.getText(), Toast.LENGTH_LONG).show();
                            } else {
//                                Toast.makeText(getActivity(), "Don't send", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                endFragment = EndFragment.newInstance();
                mFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, endFragment, "endFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        Intent intent;
        if (id == R.id.icon_call) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://aquacity.zp.ua"));
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateUI(Customer customer) {
        mCustomer = customer;

        if (mCustomer.getFlatNumber() != null) {
            adress = "ул." + mCustomer.getNameStreet() + ", дом " + mCustomer.getNumberHouse() + ", подъезд/кв." + mCustomer.getFlatNumber();
        } else adress = "ул." + mCustomer.getNameStreet() + ", дом " + mCustomer.getNumberHouse();

    }

    @Override
    public void onResume() {
        super.onResume();
        mAddressDelivery.setText(adress);
        if (adress != null && !adress.isEmpty()) {
            //Toast.makeText(getActivity(), mAddressDelivery.getText(), Toast.LENGTH_LONG).show();
            mOrder.setEnabled(true);
        } else
            mOrder.setEnabled(false);
        //Toast.makeText(getActivity(), adress, Toast.LENGTH_LONG).show();
        mAddressDelivery.setTextColor(getResources().getColor(R.color.Black));
        mAddressDelivery.setTextSize(16);
        mTimeDelivery.setText(mTime);
        mDateDelivery.setText(mDate);

    }

    public void updateOrder(Order order) {
        mWaterOrder = order;
        //Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
    }
}
