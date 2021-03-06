package com.softvision.botanica.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.softvision.botanica.R;
import com.softvision.botanica.bl.BusinessLogic;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.ui.utils.UiUtils;
import com.softvision.botanica.ui.utils.ValidationUtils;
import com.softvision.botanica.utils.StringUtils;
import com.softvision.botanica.utils.injection.AttachClick;
import com.softvision.botanica.utils.injection.InjectLayout;
import com.softvision.botanica.utils.injection.InjectView;
import com.softvision.botanica.utils.injection.Injector;

import java.util.regex.Pattern;

@InjectLayout(R.layout.activity_login)
public class LoginActivity extends BotanicaActivity {

    @InjectView(R.id.email)
    private EditText emailEditView;

    @InjectView(R.id.login_button)
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Injector.setupActivity(this);

        emailEditView.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event != null) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN || event.getAction() == KeyEvent.KEYCODE_ENTER) {
                        assertLogin();
                        return false;
                    }
                } else if (actionId == EditorInfo.IME_ACTION_DONE) {
                    assertLogin();
                    return false;
                }
                return true;
            }
        });

        if(BusinessLogic.getFacade().isLoggedIn()) {
            advance();
        }
    }

    // used by injection mechanims
    @SuppressWarnings("UnusedDeclaration")
    @AttachClick(R.id.login_button)
    public void handleOnClick() {
        assertLogin();
    }

    private void assertLogin() {
        if (ValidationUtils.isValidEmailAdress(emailEditView.getText().toString())) {
            BusinessLogic.getFacade().setUserEmail(emailEditView.getText().toString());
            advance();
        } else {
            UiUtils.showAcknowledgeDialog(this, getResources().getString(R.string.invalid_email_title), getResources().getString(R.string.invalid_email_warning));
        }
    }

    private void advance() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

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
