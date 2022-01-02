<h1 align="center">
  RestfullAPI for POLSL Web Application
  <br>
  <img src="https://raw.githubusercontent.com/Milosz08/ReactJS_Web_Application_POLSL/master/img/main-logo.png" width="200">
  <br>
</h1>
<p align="center" style="font-size: 1.2rem;">
RestAPI created for ReactJS application using Java and Spring Boot framework. To create this programming interface I used JPA and Hibernate to provide a connection between object-oriented programming and storing data as tables in mySQL database.
</p>

> See this website at [informatyka-pol-elektr.pl](https://informatyka-pol-elektr.pl/) <br>
> See Front-end for this website written in ReactJS in my repo: [ReactJS_Web_Application_POLSL](https://github.com/Milosz08/ReactJS_Web_Application_POLSL)

<hr/>

## About the Project
For the back-end of the application written in ReactJS, I decided to use Java Spring Boot because it integrates well with the React environment and is stable and more safe to other solutions (e.g. PHP or NodeJS). I decided to store the data in the application in the popular relational mySQL database due to the lack of redundancy and consistency of the inserting data. In this project JPA and Hibernate technologies are responsible for integration of object model (Java) with table model (mySQL).

## Security (JWT)
This API is fully protected by JWT technology. Without authorization, only GET methods and selected endpoints with POST method are available. To handle the rest, a Bearer token is required (this is automatically generated upon successful customer login and is deleted immediately upon logout).

## Clone and Installation
If you want to clone and work with this repository, use the built-in interface in your IDE (for example Intelij Idea or Eclipse EE) or use the clone project algorithm with git bash:<br>
1. Open Git Bash.
2. Change the current working directory to the location where you want the cloned directory.
3. Type `git clone` and then paste the URL you copied earlier.
  
```
$ git clone https://github.com/Milosz08/SpringBoot_RestfullApi_POLSL
```

## License
This application is on Apache 2.0 License [terms of use](https://www.apache.org/licenses/LICENSE-2.0).
