package com.superapis.eventweather.controller;

import java.util.ArrayList;
import java.util.List;

import com.superapis.eventweather.model.EventWeather;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@Configuration
@PropertySource("classpath:application.properties")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EventWeatherController {
    private static RestTemplate restTemplate = new RestTemplate();

    @Value("${eventbrite.oauthkey}")
    private String eventbriteOauth;

    @Value("${darksky.key}")
    private String darkSkyKey;

    private static String eventbriteUrl = "https://www.eventbriteapi.com/v3";
    private static String darkSkyUrl = "https://api.darksky.net/forecast/";

    /*
     * This mapping gets the top 5 events near the callee and the weather for the
     * location at the event start time. Powered by Eventbrite and Dark Sky.
     */
    @GetMapping("/eventweather")
    public @ResponseBody List<EventWeather> getEvents(@RequestParam("latitude") String lat,
            @RequestParam("longitude") String lng, @RequestParam("within") String within) {
        List<EventWeather> result = new ArrayList<EventWeather>();
        // Get the events from Eventbrite
        JSONArray events = getEventsFromEventbrite(lat, lng, within, eventbriteOauth);
        // Iterate through the top 5 events

        for (int i = 0; (i < events.length() && i < 5); i++) {
            JSONObject event = events.getJSONObject(i);
            if (event != null) {
                // Set EventWeather fields
                EventWeather current = new EventWeather();
                current.setEventName(event.getJSONObject("name").getString("text"));
                current.setUrl(event.getString("url"));
                String start = event.getJSONObject("start").getString("local");
                current.setEventStart(start);
                // Get the Venue's location from Eventbrite
                JSONObject venueLoc = getVenueLocation(event.getString("venue_id"), eventbriteOauth);
                String venueLat = venueLoc.getString("latitude");
                String venueLong = venueLoc.getString("longitude");
                String venueAddress = venueLoc.getString("localized_address_display");
                current.setAddress(venueAddress);
                // Get the weather for the venue at the event start time
                JSONObject weather = getWeatherForEvent(venueLat, venueLong, start, darkSkyKey);
                String summary = weather.getString("summary");
                Integer currentTemp = (int) Math.round(weather.getDouble("temperature"));
                Integer feelsLike = (int) Math.round(weather.getDouble("apparentTemperature"));
                Integer precChance = (int) Math.floor(weather.getDouble("precipProbability") * 100);
                current.setPrecChance(precChance.toString());
                current.setTemp(currentTemp.toString());
                current.setFeelsLike(feelsLike.toString());
                current.setSummary(summary);
                result.add(current);
            }
        }
        return result;
    }

    /*
     * Gets the venue location information, takes the token for eventbrite as a
     * parameter and the venue_id of the event.
     */
    public static JSONObject getVenueLocation(String venueId, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(eventbriteUrl + "/venues/" + venueId)
                .queryParam("token", apiKey);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                String.class);
        JSONObject venue = new JSONObject(response.getBody()).getJSONObject("address");
        return venue;
    }

    /* Get the events nearby from Eventbrite */
    public static JSONArray getEventsFromEventbrite(String lat, String lng, String within, String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(eventbriteUrl + "/events/search")
                .queryParam("location.latitude", lat).queryParam("location.longitude", lng)
                .queryParam("start_date.keyword", "today").queryParam("location.within", within)
                .queryParam("token", apiKey).queryParam("sort_by", "best");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                String.class);
        JSONObject eventbrites = new JSONObject(response.getBody());
        JSONArray events = eventbrites.getJSONArray("events");
        return events;
    }

    /* Gets the weather around a particular location. in our case, it's the venue */
    public static JSONObject getWeatherForEvent(String lat, String lng, String time, String apiKey) {
        JSONObject weather = new JSONObject();
        HttpHeaders headers = new HttpHeaders();
        String uri = darkSkyUrl + apiKey + "/" + lat.toString() + "," + lng.toString() + "," + time;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri).queryParam("exclude",
                "minutely,hourly,daily,flags");
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
                String.class);
        weather = new JSONObject(response.getBody()).getJSONObject("currently");
        return weather;
    }
}