package org.ameet.app;

import org.ameet.app.model.TechQuote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by achaub001c on 6/13/2016.
 */
@Component
public class QuoteService {
    @Autowired
    private RestTemplate restTemplate;

    public TechQuote getQuote(String url) {
        TechQuote quote = restTemplate.getForObject(url, TechQuote.class);
        return quote;
    }
}
