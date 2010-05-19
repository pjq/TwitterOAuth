package net.impjq.twitteroauth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.exception.OAuthNotAuthorizedException;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class TwitterOAuth extends Activity {
	/** Called when the activity is first created. */
	public static String CONSUMER_KEY = "fG64E6JJc4VeOIZTRK7w";
	public static String CONSUMER_SECRET = "mb0SrBko7SO8B4KrCOVnEXHsYxC17VyZY1vg983H68s";
	public static String CALLBACK_URL = "yourapp://twitt";
	OAuthProvider provider;
	OAuthConsumer consumer;
	String OAUTH_VERIFIER = "access_token";
	public static final String tag="TwitterOAuth";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Log.i(tag, "onCreate");
		//setProxy();
		consumer = new DefaultOAuthConsumer(CONSUMER_KEY, CONSUMER_SECRET);

		provider = new DefaultOAuthProvider(
				"http://twitter.com/oauth/request_token",
				"http://twitter.com/oauth/access_token",
				"http://twitter.com/oauth/authorize");

		String authUrl;
		try {
			Log.i(tag, "retrieveRequestToken");
			authUrl = provider.retrieveRequestToken(consumer, CALLBACK_URL);
			Log.i(tag, "authUrl="+authUrl);
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		} catch (OAuthMessageSignerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthNotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthExpectationFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthCommunicationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void setProxy() {
		Log.i("MainActivity", "setProxy");
		System.getProperties().setProperty("http.proxyHost", "10.85.40.153");
		System.getProperties().setProperty("http.proxyPort", "8000");
	}

	@Override
	public void onResume() {
		super.onResume();

		Uri uri = this.getIntent().getData();
		Log.i("onResume", "uri="+uri);
		if (uri != null && uri.toString().startsWith(CALLBACK_URL)) {
			String verifier = uri.getQueryParameter(OAUTH_VERIFIER);
			// this will populate token and token_secret in consumer
			try {
				provider.retrieveAccessToken(consumer, verifier);
			} catch (OAuthMessageSignerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthNotAuthorizedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthExpectationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OAuthCommunicationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}