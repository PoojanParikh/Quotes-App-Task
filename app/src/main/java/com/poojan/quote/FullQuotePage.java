package com.poojan.quote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by VNurtureTechnologies on 17/02/17.
 */

public class FullQuotePage extends AppCompatActivity {

    ImageView shareImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_quote_page);

        TextView quoteText = (TextView)findViewById(R.id.tv_quote);
        Intent intent = getIntent();
        String quote = intent.getStringExtra("Quotes");

        quoteText.setText(quote);

        /*shareImage = (ImageView) findViewById(R.id.share_image);

        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();

                shareIntent.setType("text/plain");
                Intent intent = getIntent();
                final String quotes = intent.getStringExtra("Quotes");
                shareIntent.putExtra(Intent.EXTRA_TEXT, quotes);
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.action_bar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.share_icon: {
                /*Share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {*/
                        Intent shareIntent = new Intent();

                        shareIntent.setType("text/plain");
                        Intent intent = getIntent();
                        final String quotes = intent.getStringExtra("Quotes");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, quotes);
                        startActivity(Intent.createChooser(shareIntent, "Share using"));
                   /* }
                });*/
                return true;
            }

    }

        return super.onOptionsItemSelected(item);
}

}
