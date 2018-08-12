package com.auxilio.auxilio;

import android.os.AsyncTask;
import android.util.Base64;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TwilioAPI extends AsyncTask<String, Integer, Long>{
    public static final String ACCOUNT_SID = "ACdf606dffe042a5ab003d42bad07d3784";
    public static final String AUTH_TOKEN = "992b032ae2243a59c7ddf441ea135601";
    public static final String TWILIO_NUMBER = "+18312288938";
    protected Long doInBackground(String... digits) {

        String[] numbers = {"+15106480370", "+18188079879"};
        for(String number : numbers){
            sendSMS(number);
        }
        return new Long(1);
    }

    public void sendSMS(String number){
        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(
                "https://api.twilio.com/2010-04-01/Accounts/"+ACCOUNT_SID+"/SMS/Messages");
        String base64EncodedCredentials = "Basic "
                + Base64.encodeToString(
                (ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(),
                Base64.NO_WRAP);

        httppost.setHeader("Authorization",
                base64EncodedCredentials);
        try {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("From",
                    TWILIO_NUMBER));
            nameValuePairs.add(new BasicNameValuePair("To",
                    number));
            nameValuePairs.add(new BasicNameValuePair("Body",
                    "Welcome to Twilio"));

            httppost.setEntity(new UrlEncodedFormEntity(
                    nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            System.out.println("Entity post is: "
                    + EntityUtils.toString(entity));


        } catch (ClientProtocolException e) {

        } catch (IOException e) {

        }
    }
}
