package com.example.android.justjava;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    int quantity=0;
    boolean checked1, checked2;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);         //creates the activity
        setContentView(R.layout.activity_main);  //tells that content view of the activity should be set to the layout as specified in activity main file
    }

    public void submitOrder(View view) {
        EditText namefield = (EditText) findViewById(R.id.name_field);
        CheckBox c1= (CheckBox) findViewById(R.id.checkbox1);
        CheckBox c2= (CheckBox) findViewById(R.id.checkbox2);
        checked1 = c1.isChecked();
        checked2 = c2.isChecked();
        name= namefield.getText().toString();
        int price= calculatePrice(); //for Method 1
        String priceMessage= createOrderSummary(name, price, checked1, checked2);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for " +name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //calculatePrice(quantity,5);  for Method 2
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);   // R.id.quantity_text_view refers to view Id
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given price on the screen.
     */

   // Method1
    private int calculatePrice(){
        int base_price =quantity*5, whipped_cream = quantity*1, chocolate = quantity*2;
        if (checked1==false && checked2==false){
            return base_price;
        }
        else if (checked1==false && checked2==true){
            return  base_price+chocolate;
        }
        else if (checked1==true && checked2==false){
            return  base_price+whipped_cream;
        }
        else
            return base_price+whipped_cream+chocolate;
     }

    /**
     * Create summary of the order
     * @param price
     * @return text summary
     */

     private String createOrderSummary(String name, int price, boolean checked1, boolean checked2){
         String priceMessage = "Name: " +name+ "\nAdd whipped cream? " +checked1+ "\nAdd chocolate? " +checked2+ "\nQuantity: " +quantity+ "\nTotal: $" +price+ "\nThank You!";
         return priceMessage;
     }

    /**Method2
    private void calculatePrice(int quantity, int price){
        price=quantity*price;
    }
     */

    public void increment(View view) {
        if (quantity>100){
            Toast toast = Toast.makeText(this,"You cannot order more than 100 coffees",Toast.LENGTH_SHORT);
            toast.show();
        }
            quantity=quantity+1;
            displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity<=0) {
            Toast toast = Toast.makeText(this,"You cannot have less than 1 coffee",Toast.LENGTH_SHORT);
            toast.show();
        }
            quantity = quantity - 1;
            displayQuantity(quantity);
    }
}
