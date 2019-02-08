Background: This is a Spring Boot app powered by Eventbrite and DarkSky APIs. Built with Maven. I deployed this to Elastic Beanstalk.

To run this project locally, I assume you have maven installed and at least JDK8+.

Steps to build:
1) You will need to get api keys from Eventbrite and DarkSky. For Eventbrite, you should use the Personal OAUTH token instead of public.
2) There are keys in src/main/resources/application.properties for each of the API keys. Once set, they should auto wire.
3) Now you should be able to run "mvn spring-boot:run" from the root of the back-end to start the app.
