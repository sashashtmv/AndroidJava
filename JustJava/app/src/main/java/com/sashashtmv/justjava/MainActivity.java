package com.sashashtmv.justjava;

import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int price = 5;
    public void submitDecrement(View view){
        displayquantity(1);

    }
    public void submitIncrement(View view){
        displayquantity(-1);

    }
    int count=0;

    private void displayquantity(int number) {
        count = count+number;
        if (count < 0){
            Toast.makeText(this, getString(R.string.less_can_not_be_ordered), Toast.LENGTH_SHORT).show();//всплывающее окно
            count = 0;
        }
        if (count > 100){
            Toast.makeText(this, getString(R.string.no_more_ordering), Toast.LENGTH_SHORT).show();
            count = 100;
        }
        TextView quantityTextView = (TextView) findViewById(R.id.textView);
        quantityTextView.setText(""+  count);
    }

    public void submitOrder(View view){
        CheckBox checkBox = (CheckBox) findViewById(R.id.Whipped_Cream);
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.Chocolate);
        EditText editText = (EditText) findViewById(R.id.name_text);
        if (checkBox.isChecked())  price=6;
        if (checkBox1.isChecked()) price=6;
        if (checkBox.isChecked()&&checkBox1.isChecked())price=7;
        String priceMessage = getString(R.string.order_summary_name) + editText.getText().toString() + "\n" + getString(R.string.order_summary_whipped_cream) + checkBox.isChecked() + "\n" + getString(R.string.order_summary_chocolate)+checkBox1.isChecked()+ "\n" + getString(R.string.order_summary_quantity) + count + "\n" + getString(R.string.order_summary_price) + "$" + (count * price) + "\n" + getString(R.string.thank_you);
        composeEmail(null,getString(R.string.order_summary_email_subject), priceMessage );
        //displayMessage(priceMessage);

    }
    public void composeEmail(String[] addresses, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int price) {
//        TextView priceTextView = (TextView) findViewById(R.id.textView3);
//        priceTextView.setText(NumberFormat.getCurrencyInstance(Locale.US).format(count*price));
//    }
//    private void displayMessage(String message) {
//        TextView priceTextView = (TextView) findViewById(R.id.textView3);
//        priceTextView.setText(message);
//    }
}
