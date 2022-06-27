const express = require('express');
const fs = require('fs');
const https = require('https');
const mysql = require('mysql');
const validator = require('validator');
const messages = require('./messages.json');
const filepaths = require('../../../filepaths.json');
const dbcreds = require('../../../dbcreds.json');

const countrySchema = require(filepaths.landSchema);
const allowedApiCalls = [ 'balance', 'withdraw' ];
const r = messages.noob;
const wysd = messages.wysd;

const app = express();
app.use(express.json());

// HTTPS server options
// Note that rejectUnauth is false in order to politely respond to invalid certs
const opts = {
    key: fs.readFileSync(filepaths.noobServerKey),
    cert: fs.readFileSync(filepaths.noobServerChain),
    requestCert: true,
    rejectUnauthorized: false,
    ca: [fs.readFileSync(filepaths.noobRoot),
         fs.readFileSync(filepaths.countryCA)]
};

// Connection to the log database
const con = mysql.createConnection({
    host: dbcreds.host,
    user: dbcreds.uname,
    password: dbcreds.password,
    database: dbcreds.db
});

con.connect(function(e) {
    if (e) throw e;
});

writeLogs("NOOB startup complete.");

/**
* function writeLogs(message)
* Function for cleaner logging to stdout and mysql database
*
* message:	Log/error message string
* ---
* TODO: add extra (internal) logfile with more info (like IP addresses)
*/
function writeLogs(message) {
    const sanitized_message = validator.escape(message);
    const time = new Date().toLocaleString('nl-NL', {hour12: false});
//    console.log(message);		// For debugging purposes
    var sql = "INSERT INTO messages (message) VALUES ('" + sanitized_message + "');";
    con.query(sql, function (e, result) {
        if (e) {
            fs.appendFile(filepaths.publicLog, '[' + time + '] ' + message + '\n', (e) => {
	        if (e) { console.log(e); }
	    });
	    con.end();
	    return console.log('error:' + e.message);
        }
    });
}

/**
* async function sendRequest(dstIP, sendObj, apiMethod, _callback)
* Initiates HTTPS request to target country after receiving a request from source country.
* Async function with max timeout of 3 seconds. Assumes that everyone uses port 8443.
*
* dstIP:	IP address of the target country.
* sendObj:	The request object that is to be passed on to the target country.
* apiMethod:	The API method to call on the target server. Equals the method called by source country.
* httpMethod: 	Which HTTP method is to be used. POST or GET probably.
* _callback:	Callback function to be executed when the response from the target country is received.
*/
async function sendRequest(dstIP, sendObj, apiMethod, httpMethod, _callback) {
    const apiMethodStr = '/'.concat(apiMethod);
    const https_options = {
	host:	 	dstIP,
	port: 		8443,
	path: 		apiMethodStr,
	method:		httpMethod,
        headers:        { 'Content-Type': 'application/json' },
        cert: 		opts.cert,
	key:            opts.key,
        rejectUnauthorized: false,
	timeout: 	3000
    };

    try {
        const req = await https.request(https_options, (res) => {
	    res.setEncoding('utf8');
	    res.on('data', (obj) => {
		try {
		    const responseObj = JSON.parse(obj);
		    const resFromBank = JSON.parse(responseObj).head.fromBank;
		    const resToBank = sendObj.head.fromBank;
		    writeLogs("Response from [" + resFromBank + "]. Forwarding to [" + resToBank + "]");
		    _callback(true, res.statusCode, responseObj);
		}
		catch(e) {
		    writeLogs(r.jsonParseError.message + e.message);
                    _callback(false, r.jsonParseError.code, r.jsonParseError.message + wysd.seeLogs);
		}
	    });
	});
	req.on('socket', function (socket) {
    	    socket.setTimeout(https_options.timeout);
    	    socket.on('timeout', function() {
        	writeLogs(r.timeoutError.message);
		req.destroy();
		_callback(false, r.timeoutError.code, r.timeoutError.message);
    	    });
	});
	req.on('error', (e) => {
	    writeLogs(r.requestCompileError.message + e.message);
		req.destroy();
	    _callback(false, r.requestCompileError.code, r.requestCompileError.message + wysd.seeLogs);
	});
	req.write(JSON.stringify(sendObj));
	req.end();
    } catch(e) {
	writeLogs(r.sendRequestError.message + e.message);
	_callback(false, r.sendRequestTLDR.code, r.sendRequestTLDR.message + wysd.blame);
    }
}

// Test method (no certificate required)
app.get('/test', (req, res) => {
    res.status(r.noobTest.code).send(r.noobTest.message);
})

// TODO:
//  /register: Automatic registration on first API call with valid certificate

//register endpoint
//app.get('/register', (req, res) => {
//
//})

//Handles all allowed API calls
//POST only. Use of app.all() would have been less safe imo.
app.post('/api/:requestType', async (req, res) => {
    const methodCalled = req.params.requestType
    const fromBank = req.body.head.fromBank;
    const toBank = req.body.head.toBank;
    const toCtry = req.body.head.toCtry;

    if (!allowedApiCalls.includes(methodCalled.toLowerCase())) {
	writeLogs("[NOOB]: " + methodCalled + " is not an allowed API call." + wysd.rtfm)
	res.status(501).send("[NOOB]: " + methodCalled + " is not an allowed API call." + wysd.rtfm);
	return;
    }

    writeLogs("Incoming " + methodCalled + " request");

    if (req.client.authorized) {
        if (!req.is("application/json")){
	    writeLogs(r.expectedJSONError.message + wysd.sanityCheck)
            res.status(r.expectedJSONError.code).send(r.expectedJSONError.message + wysd.sanityCheck);
            return;
        }
        writeLogs("Request from [" + fromBank + "] going to [" + toBank + "]");
        const registeredCountries = JSON.parse(JSON.stringify(countrySchema));
	const dstIP = registeredCountries[toCtry];
        if(typeof dstIP === undefined){
            writeLogs("[NOOB]: Bad request: Country [" + toCtry + "] does not exist.");
	    res.status(400).send("[NOOB]: Bad request: Country [" + toCtry + "] does not exist.");
        } else {
	    try {
	        const obj = await sendRequest(dstIP, req.body, methodCalled, 'POST', function(success, code, result) {
		    const response = success ? JSON.parse(result) : result;
		    res.status(code).send(response);
	 	});
    	    } catch(e) {
		writeLogs(r.awaitError.message + e.message);
		res.status(r.somethingHappened.code).send(r.somethingHappened.message + wysd.seeLogs);
	    }
        }
    } else {
	writeLogs(r.invalidCertIssuer.message + req.socket.getPeerCertificate().issuer);
        res.status(r.unauthorized.code).send(r.unauthorized.message + wysd.seeLogs);
    }
});

https.createServer(opts, app).listen(8443, '0.0.0.0');
