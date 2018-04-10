/*
public void onPostExecute(Boolean success, String error) {
        mAuthTask = null;

        if (success.booleanValue()) {
            Intent myIntent = new Intent(LoginScreen.this,
                    HomePage.class);
            // Can't hit back button and come here
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(myIntent);
            finish();
        } else {
            mEmailView.setError(error);
            mEmailView.requestFocus();
            showProgress(false);
        }
    }
*/
