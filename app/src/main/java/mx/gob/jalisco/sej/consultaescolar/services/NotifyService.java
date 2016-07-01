package mx.gob.jalisco.sej.consultaescolar.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import mx.gob.jalisco.sej.consultaescolar.MainActivity;
import mx.gob.jalisco.sej.consultaescolar.utils.NetworkUtils;
import mx.gob.jalisco.sej.consultaescolar.R;
import mx.gob.jalisco.sej.consultaescolar.utils.Utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Normalizer;
import java.util.regex.Pattern;


public class NotifyService extends BroadcastReceiver {

    Context ctx;
    NetworkUtils utils;
    URL url;

    String node_title;
    String cuerpo;
    String imagen;
    String id;
    String publicado;
    int get_last_time;

    public static final String PREF_USER_LAST_NOTIFICACTION = "0";
    int last_time;

    @Override
    public void onReceive(Context context, Intent intent) {

        ctx = context;
        utils = new NetworkUtils(ctx);

        if(utils.isConnectingToInternet()){
            new DataFetcherTask().execute();
        }
    }

    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    class DataFetcherTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String serverData = null;// String object to store fetched data from server
            // Http Request Code start
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://edu.jalisco.gob.mx/consulta-escolar/services/avisos/views/services_avisos_last");
            try {
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                serverData = EntityUtils.toString(httpEntity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {

                JSONArray jsonArray = new JSONArray(serverData);

                JSONObject jsonObject = jsonArray.getJSONObject(0);
                node_title = jsonObject.getString("node_title");
                cuerpo = jsonObject.getString("cuerpo");
                id = jsonObject.getString("id");
                publicado = jsonObject.getString("publicado");
                get_last_time = Integer.parseInt(id);

                Log.i("NEW DATE",get_last_time+"");

                last_time = Integer.valueOf(Utils.readSharedSetting(ctx, PREF_USER_LAST_NOTIFICACTION, "true"));

                Log.i("DATE IN STORAGE",last_time+"");
                //Utils.saveSharedSetting(ctx, NotifyService.PREF_USER_LAST_NOTIFICACTION, "");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(last_time < get_last_time){
                new sendNotification(ctx).execute(node_title, publicado, imagen);
                Utils.saveSharedSetting(ctx, NotifyService.PREF_USER_LAST_NOTIFICACTION, String.valueOf(get_last_time));
            }
        }
    }

    private class sendNotification extends AsyncTask<String, Void, Bitmap> {
        Context ctx;
        public sendNotification(Context context) {
            super();
            this.ctx = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            InputStream in;
            node_title = params[0];
            publicado = params[1];

            try {
                url = new URL(params[2]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {

            super.onPostExecute(result);
            try {
                ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                long[] pattern = {500,500,500};
                int color = getColor(ctx, R.color.colorPrimary);
                NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(ctx)
                        .setSmallIcon(R.drawable.ic_action_icono_escuela_primary)
                        .setLargeIcon((((BitmapDrawable)ctx.getResources()
                                .getDrawable(R.drawable.gota_gris)).getBitmap()))
                        .setContentTitle(node_title)
                        .setColor(color)
                        .setContentText(cuerpo)
                        .setContentInfo("Hace: "+publicado)
                        .setTicker("")
                        .setAutoCancel(true)
                        .setSound(sound)
                        .setVibrate(pattern);

                Intent notIintent = new Intent(ctx, MainActivity.class);
                PendingIntent contIntent = PendingIntent.getActivity(ctx,0,notIintent,PendingIntent.FLAG_CANCEL_CURRENT);
                mBuilder.setContentIntent(contIntent);
                NotificationManager mNotificationManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0,mBuilder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static String cleanUrl(String input) {
        // Descomposición canónica
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Nos quedamos únicamente con los caracteres ASCII
        Pattern pattern = Pattern.compile("\\p{ASCII}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}