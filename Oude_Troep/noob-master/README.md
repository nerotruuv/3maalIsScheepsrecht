# NOOB

# Summary
NOOB IP: `145.24.222.82` (2021/2022)

NOOB is a central banking system that allows registered country banks to communicate with each other via SSL, without the need for individual country banks to keep track of a routing table.
For more info on request formats, registering a country server, connecting with the API, and more: check the wiki.

* To test your connection with NOOB: `HTTP GET 145.24.222.82:8443/test` (No auth required). 
* Authorized clients can reach the API at `HTTPS POST 145.24.222.82:8443/api/{ balance || withdraw }`.
* For (your) debugging purposes, NOOB keeps a public log at `HTTP GET 145.24.222.82:8080`.
The log page returns the last 50 log entries as JSON data, which can be viewed in the browser or parsed by a client.

# Testing your country server
Registered countries can send /balance and /withdraw requests to countrycode T1 via NOOB, using any bankcode. T1 is a test country that sends back hardcoded JSON responses, so some of the fields in the response may not match what you sent in your request.
T1 also has a test method: `HTTP GET 145.24.222.111:8443/test` (No auth required)

# Commands
* Install  - `npm install`
* Run NOOB - `npm run noob`npm

* Run NOOB server only (without publig logging server) - `npm run server`
* Run public log server only - `npm run logs`



