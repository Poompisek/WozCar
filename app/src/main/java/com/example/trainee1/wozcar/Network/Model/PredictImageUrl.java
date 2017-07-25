package com.example.trainee1.wozcar.Network.Model;

import android.net.Uri;

import com.example.trainee1.wozcar.Activity.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;

import timber.log.Timber;

/**
 * Created by Trainee1 on 7/20/2017.
 */

public class PredictImageUrl {

    public static void main()
    {
        HttpClient httpclient = HttpClients.createDefault();

        try
        {
            URIBuilder builder = new URIBuilder("https://southcentralus.api.cognitive.microsoft.com/customvision/v1.0/Prediction/e1ba9ac9-07bd-4585-ac70-6c0ff80e592c/image");

//            builder.setParameter("iterationId", "{string}");
//            builder.setParameter("application", "{string}");

            URI uriBuilder = builder.build();
            HttpPost request = new HttpPost(uriBuilder);
            request.setHeader("Content-Type", "application/json");
            request.setHeader("Prediction-key", "1183fe36baf145bab59e664e4642c757");


            // Request body
            StringEntity reqEntity = new StringEntity("{\n" +
                    "  \"Url\": \"https://toyota.com.my/ToyotaOfficialWebsite/media/ToyotaMedia/model/vios/vios-2016/footer-car-vios.png\"\n" +
                    "}");
            request.setEntity(reqEntity);

            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();

            Timber.d("Response in JSON", entity);

            if (entity != null)
            {
                System.out.println(EntityUtils.toString(entity));
            }
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


}
