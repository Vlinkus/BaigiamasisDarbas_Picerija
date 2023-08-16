# Pizzeria - REST Server
<i>Note that the project where you are reading this is only the back-end part!
you will also need the front-end part. Link is below this note ↓</i>
<a href="https://github.com/Vlinkus/BaigiamasisDarbas_PicerijaFront">Front-end</a>

[***Readme Lietuviškai***](README.md)

# Table of Contents

- [**Introduction**](#Introduction)
  - [Creators](#creators)
  - [Stack](#stack)
- [**Launching server**](#launching-server)
  - [Getting the repository](#getting-the-repository)
- [**Running the server**](#running-the-server)
  - [API commands](#api-commands)
    - [Swagger 3 - OpenAPI 3](#swagger-3---openapi-3)
    - [Authentication and authorization](#authentication-and-authorization)
      - [Registration](#registration)
      - [Login authentication](#login---authentication)
      - [Logout](#logout)
      - [Refresh token](#refresh-token)


# Introduction

<p>This final project represents a pizza restaurant's REST server or the backend. 
For the React frontend, you can click <a href="https://github.com/Vlinkus/BaigiamasisDarbas_PicerijaFront">this link.</a></p>

## Creators

This project was done by 3 contributors(one had two accounts😂):

<a href="https://github.com/Vlinkus/BaigiamasisDarbas_Picerija/graphs/contributors">
    <img src="https://contrib.rocks/image?repo=Vlinkus/BaigiamasisDarbas_Picerija" width="40%"/>
</a>

This server is secured from XSS (Cross Site Scripting) attacks and implements CORS (Cross-origin resource sharing)
for secure communication between Front and Back.
## Stack

The Stack used in the REST server:
- **Java 17**
- **Spring Framework Stack:**
  - Spring Boot 3.1.2
  - Spring Boot Data JPA
  - Spring Boot Web
  - Spring Boot Security
  - Spring Boot Validation
  - Spring Boot Test
- **Database:**
  - MySQL
- **API Documentation Stack:**
  - Swagger UI 3
  - OpenAPI 3
- **Other Libraries:**
  - Lombok *(for generating boilerplate code)*
  - Mockito *(for testing)*
  - Spring Security Test *(for security-related testing)*

# Launching server

Before you go on, make sure you have at least ***JDK 17*** installed on your system

## getting the repository

To get this repository, just got to the local folder where you would like to store it and run the git clone command:

```shell
git clone https://github.com/Vlinkus/BaigiamasisDarbas_Picerija.git
```

There are also many more methods to acquire this project. 
In the project's GitHub repository, press the "code" button for additional options.

After a quick installation, you should be good to go with the code editor of your choice (*Eclipse*, *Intellij IDEA*...)

# Running the server

## API commands

Project's default port is set to 8080. 
All the links in these api related subsections will be using previously mentioned port.

### Swagger 3 - OpenAPI 3

This project is backed by OpenAPI 3 documentation. 
If you are interested to analyse the endpoints in detail and know all the possible responses, headers and cookies, 
feel free to go to the swagger-ui default page: http://localhost:8080/swagger-ui/index.html

### authentication and authorization

To start, you will need to create an account. 
If you are running just the REST server, the best choice for you will be running Postman or any other alternative.

#### registration
Registering an account is pretty straight forward process:
- Set *HTTP* request to ``POST``
- Set the address to ``localhost:8080/api/v1/auth/register``
- Send the *JSON* body like on the example below

```json
{
    "firstname": "Name",
    "lastname": "Surename",
    "username": "someUsername",
    "email": "ran@dom.cam",
    "password": "password",
    "role": "USER"
}
```
Important thing to mention, is that "*role*" field is not mandatory and may not be mentioned by the sender, 
in which case the default role for registering user will be "USER".

All the possible role variations: `USER`, `MANAGER`, `ADMIN`

#### login - authentication
Logging in requires only two fields.
- Set *HTTP* request to ``POST``
- Set the address to ``localhost:8080/api/v1/auth/login``

```json
{
    "username": "someUsername",
    "password": "password"
}
```
After a successful authentication, you will receive a ***JWT access token***, 
user's ***role*** and a ***HttpOnly refresh token cookie***. 


#### logout
Logging out is a simple process:

- Have a valid ``HttpOnly JWT refresh token``.
- Have a valid ``JWT access token`` - optional.
- Set *HTTP* request to ``GET``
- Set the address to ``localhost:8080/api/v1/auth/logout``

The logout API allows users to terminate their session and invalidate the access token if present. 
This prevents the both tokens from being used for any further authorization requests.

#### refresh token
Refreshing the access token is a part of the token-based authentication flow. 
JWT access token has a short lifespan, thus require to be refreshed with the help of ***refresh token***:

- Have a valid ``HttpOnly JWT refresh token``.
- Set *HTTP* request to ``GET``
- Set the address to ``localhost:8080/api/v1/auth/refresh-token``

The refresh-token API allows users to obtain a new access token using a valid refresh token. 
This helps to extend the session duration without requiring the user to re-enter their credentials.
