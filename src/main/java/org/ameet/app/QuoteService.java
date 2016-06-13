package org.ameet.app;

import org.ameet.app.model.QuoteResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * Created by achaub001c on 6/13/2016.
 */
public class QuoteService {

    @Autowired
    private RestTemplate restTemplate;


    public String getQuote(String url) {
        return restTemplate.getForObject(url, QuoteResource.class).getValue().getQuote();
    }
}
