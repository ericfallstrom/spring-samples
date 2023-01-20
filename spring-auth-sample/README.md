# spring-auth-sample
Sample application of JWT authentication using Spring-Security using either username/password or oauth2.

## Existing users
Application is bootstrapped with these two existing users:
* admin/password - `ROLE_ADMIN`
* user/password - `ROLE_USER`

## Register new user
New users will have role `ROLE_USER`
#### Request:
```http
POST http://localhost:9191/api/auth/register

{
    "username": "user2",
    "password": "password"
}
```

## Login with credentials
#### Request:
```http
POST http://localhost:9191/api/auth/login

{
  "username": "user2",
  "password": "password"
}
```
#### Response:
```http
HTTP/1.1 200
Set-Cookie: Refresh-Token=<refresh-token>

{
  "accessToken": "<access-token>"
}
```

## Login or register user with OAuth2
Missing users will be auto-created with their email as their username and with role `ROLE_USER`

### Required properties
To login with Google or Facebook the following variables needs to be set as system variables, or you can update application.yml. 
* `GOOGLE_CLIENT_ID`
* `GOOGLE_CLIENT_SECRET`
* `FACEBOOK_CLIENT_ID`
* `FACEBOOK_CLIENT_ID`

#### Request:
```
GET http://localhost:9191/oauth2/authorization/google`
GET http://localhost:9191/oauth2/authorization/facebook
```

#### Response:
```http
HTTP/1.1 200
Set-Cookie: Refresh-Token=<refresh-token>

{
  "accessToken": "<access-token>"
}
```
## Get new refresh-token 
#### Request:
```http
POST http://localhost:9191/api/auth/refresh-token
Cookie: Refresh-Token=<refresh-token>
```
#### Response:
```http
HTTP/1.1 200
Set-Cookie: Refresh-Token=<refresh-token>

{
  "accessToken": "<access-token>"
}
```
## Test authorization
### Anon access
#### Request:
```http
GET http://localhost:9191/api/public/test
```
### ROLE_USER access
#### Request:
```http
GET http://localhost:9191/api/private/test
Authorization: Bearer {{access_token}}
```
### ROLE_ADMIN access
#### Request:
```http
GET http://localhost:9191/api/admin/test
Authorization: Bearer {{access_token}}
```
