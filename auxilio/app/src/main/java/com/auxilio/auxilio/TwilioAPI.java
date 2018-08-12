package com.auxilio.auxilio;

import android.os.AsyncTask;
import android.util.Base64;

import com.auxilio.data.AffidivitApplication;
import com.auxilio.data.Person;

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

public class TwilioAPI extends AsyncTask<AffidivitApplication, Integer, Long>{
    private final String ACCOUNT_SID = "ACdf606dffe042a5ab003d42bad07d3784";
    private final String AUTH_TOKEN = "992b032ae2243a59c7ddf441ea135601";
    private final String TWILIO_NUMBER = "+18312288938";
    private String message = "%s, soy %s. Halgo me va a pasar, por favor cuida mis hijos";

    protected Long doInBackground(AffidivitApplication... app) {

        List<Person> relatives = app[0].getRelatives();
        String parentname = app[0].getParent().getFirstName() + " " + app[0].getParent().getLastName();

        for(Person relative : relatives){
            String number = relative.getPhoneNumber();
            String fullname = relative.getFirstName();
            sendSMS(number, String.format(message, fullname, parentname));
        }

        return new Long(1);
    }

    public void sendSMS(String number, String message){
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
                    message));

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
