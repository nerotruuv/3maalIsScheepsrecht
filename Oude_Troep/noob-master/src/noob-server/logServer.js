const express = require('express');
const app = express();
const dbcreds = require('../../../dbcreds.json');
const mysql = require('mysql');

app.use(express.static('public'));

// Create a pool, so we can handle multiple simultaneous connections.
const pool = mysql.createPool({
    connectionLimit: 10, // Probably set this to however many countries we have.
    host     : dbcreds.host,
    user     : dbcreds.uname,
    password : dbcreds.password,
    database : dbcreds.db,
    debug    : false
});

app.get('/', function (req, res) {
    let query = 'SELECT * FROM messages ORDER BY time DESC LIMIT 50'
    pool.query(query, (err, rows) => {
        // In the event of an error at this tage of a request, log to console and set the status-code to 500
        // (internal server error).
        if (err) {
            console.error(err);
            res.status(500)
            res.send();
        } else {
            res.json(rows);
        }
        req.destroy();
    });
});

app.listen(8080);
