## Endpointai ir pavyzdys Postmanui

### Registracija
POST
```
localhost:8080/api/v1/auth/register
```
_**JSON Užklausa:**_ pavyzdis
```
{
    "firstname": "Vardenis",
    "lastname": "Pavardenis",
    "username": "useris",
    "email": "useris@bob.com",
    "password": "labalaba"
}
```
### Login

POST
```
localhost:8080/api/v1/auth/login
```
_**JSON Užklausa:**_ pavyzdis
```
{
    "username": "useris",
    "password": "labalaba"
}
```
