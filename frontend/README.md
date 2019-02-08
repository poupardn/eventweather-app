This is a React Javascript application with AWS Amplify components added in to simplify authentication (uses AWS Cognito) and hosting. 

To run this, you will need to install the amplify-cli, visit [the Amplify docs](https://aws-amplify.github.io/docs/). This requires an AWS account, but can be run on the free tier. Also, the amplify-cli must be run on bash (Linux/MacOS/WSL).

1) From the root of the app, run "npm install". This will take awhile.
2) Run ```amplify init``` 
   - You can choose your own project/application name. 
   - You can also select your default editor. 
   - When prompted, choose ```Javascript``` and ```React```   
   - Accept default values for the path and build commands.
   - For AWS profile, select ```Yes``` and choose the ```default``` profile
3) Run ```amplify add auth```. Select Yes when asked if you’d like to use the default authentication and security configuration.
4) Run ```amplify push```. This will push your changes to the cloud handle the AWS Cognito setup and configuration.
5) Now auth is complete. You should be able to run the app locally with ```npm start```

NOTE: There is a proxy set up in the package.json, this is to bypass the Single-Origin Policy in most browsers. The port defaults to 8080, so if you changed your Spring Boot backend port, it will need to be changed here.
