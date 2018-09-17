package com.dextroxd.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int noc = 1;

    public void submitOrder(View view) {
        EditText na = (EditText) findViewById(R.id.edit_text);
        String nam = String.valueOf(na.getText());
        CheckBox cb = (CheckBox) findViewById(R.id.check_box);
        CheckBox cb1 = (CheckBox) findViewById(R.id.check_box1);
        String p = createOrderSummary(calculatePrice(cb.isChecked(), cb1.isChecked()), cb.isChecked(), cb1.isChecked(), nam);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java for " + nam);
        intent.putExtra(Intent.EXTRA_TEXT, p);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean x, boolean y) {
        int pr = 0;
        int qr = 0;
        if (x) {
            pr = 1;
        }
        if (y) {
            qr = 2;
        }
        return noc * (5 + pr + qr);

    }

    private String createOrderSummary(int x, boolean y, boolean z, String n) {
        String name = "Name : " + n;
        String wc = "Add whipped cream?" + y;
        String ch = "Add chocolate?" + z;
        String Quant = "Quantity : " + noc;
        String priceMessage = "Total : $" + x;
        String p = name + "\n" + wc + "\n" + ch + "\n" + Quant + "\n" + priceMessage;
        return p;
    }

    public void increase(View view) {
        if (noc < 100) {
            noc++;
        } else {
            noc = 100;
            Toast.makeText(getApplicationContext(), "You cannot have more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }

        display(noc);
    }

    public void decrease(View view) {
        if (noc > 1) {
            noc--;
        } else {
            noc = 1;
            Toast.makeText(getApplicationContext(), "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        display(noc);
    }

    private void display(int n) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + n);
    }


}