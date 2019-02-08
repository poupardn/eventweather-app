package com.superapis.eventweather.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.superapis.eventweather.EventWeatherApplication;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EventWeatherApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventWeatherControllerTests {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Value("${eventbrite.oauthkey}")
    private String eventbriteOauth;

    @Value("${darksky.key}")
    private String darkSkyKey;

    @Before
    public void init() {

    }

    public String getUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    /**
     * This test just covers simple functionality. Because APIs are in flux, there's
     * no guarantees of data, so we ensure some data returns.
     */
    @Test
    public void testGetEventWeather() {
        try {
            HttpEntity<String> entity = new HttpEntity<String>(headers);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(getUrl("/eventweather"))
                    .queryParam("latitude", "42.251204").queryParam("longitude", "-83.210013")
                    .queryParam("within", "10mi");
            ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                    String.class);
            JSONArray events = new JSONArray(response.getBody());
            assertNotNull(events);
            if (events.length() > 0) {
                assertNotNull(events.getJSONObject(0).getString("eventName"));
            }
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }

    }

    /**
     * Tests only finding the venue info for an event. We hope that this venue_id is
     * never deleted.
     */
    @Test
    public void testGetVenue() {
        JSONObject venue = EventWeatherController.getVenueLocation("27763939", eventbriteOauth);
        try {
            assertNotNull(venue.getString("latitude"));
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
    }

    /** Tests just the gathering of events for an area */
    @Test
    public void testGetEventbriteEvents() {
        JSONArray events = EventWeatherController.getEventsFromEventbrite("42.251225", "-83.209814", "10mi",
                eventbriteOauth);
        assertTrue(events.length() > 0);
    }

    /** Tests getting the weather for a given location/time. */
    @Test
    public void testGetWeather() {
        JSONObject weather = EventWeatherController.getWeatherForEvent("42.334056", "-83.050436", "2019-02-05T18:30:00",
                darkSkyKey);
        try {
            assertNotNull(weather.getString("temperature"));
        } catch (Exception e) {
            System.out.println(e);
            fail();
        }
    }
}
