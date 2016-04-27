package com.horses.launchpad.lima16;

import android.content.Intent;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.OnClick;
import io.paperdb.Paper;

public class LoginActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 2342;

    private GoogleApiClient apiClient;

    @Override
    protected int getView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        onSummit();
    }

    @OnClick(R.id.image)
    protected void onSummit(){

        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {

        PersonEntity entity = new PersonEntity();
        entity.setId(result.getSignInAccount().getId());
        entity.setName(result.getSignInAccount().getDisplayName());
        entity.setEmail(result.getSignInAccount().getEmail());
        entity.setPhoto(result.getSignInAccount().getPhotoUrl() == null ? "" : result.getSignInAccount().getPhotoUrl().toString());

        Paper.book().write("person", entity);
        Paper.book().write("session", true);

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}

