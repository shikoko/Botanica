package com.softvision.botanica.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.softvision.botanica.R;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.utils.injection.AttachClick;
import com.softvision.botanica.utils.injection.InjectLayout;
import com.softvision.botanica.utils.injection.InjectView;
import com.softvision.botanica.utils.injection.Injector;

@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BotanicaActivity {

    @InjectView(R.id.email)
    private EditText emailEditView;

    @InjectView(R.id.login_button)
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.setupActivity(this);
    }

    // used by injection mechanims
    @SuppressWarnings("UnusedDeclaration")
    @AttachClick(R.id.login_button)
    public void handleOnClick() {
//        if (StringUtils.hasContent(emailEditView.getText().toString())) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
//        } else {
//            Toast.makeText(this, "Enter an email first", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
