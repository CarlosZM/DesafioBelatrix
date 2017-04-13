package com.zerga.carlos.desafiobelatrix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pizarra21.myapplication.R;

public class DesafeyActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView url = null;

    private Button button = null;

    private ImageView view = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desafey);
        url=(TextView)findViewById(R.id.txtUrl);
        button=(Button) findViewById(R.id.btnImageLoad);
        view=(ImageView) findViewById(R.id.imgImageLoaded);
        this.setListener();
    }

    public void setListener()
    {
        this.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        String imageUrl=this.url.getText().toString();
        Log.d("d",imageUrl);
        if(!imageUrl.isEmpty())
        {
            ImageLoader
                    .with(this.view)
                    .load(this.url.getText().toString());
        }
        else{
            Toast.makeText(this,"Ingrese una Url",Toast.LENGTH_SHORT).show();
        }
    }
}
