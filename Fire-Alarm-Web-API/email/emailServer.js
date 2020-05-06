const http = require("http");
const express = require("express");
const mysql = require("mysql");
const bodyParser = require("body-parser");
const cors = require("cors");
var nodemailer = require("nodemailer");


const app = express();
app.use(cors());
const connection = mysql.createConnection({
    host: "localhost",
    user: "root",
    password: "",
    database: "sensor_db",
     port: "3308"
  });

process.env["NODE_TLS_REJECT_UNAUTHORIZED"] = 0;
connection.connect(function (err) {
  if (err) throw err;
  console.log("Connected to MySQL Database...");
});

app.use(bodyParser.json()); // to support JSON-encoded bodies
app.use(
  bodyParser.urlencoded({
    // to support URL-encoded bodies
    extended: true,
  })
);
//Start server
var server = app.listen(3005, "localhost", function () {
  var host = server.address().address;
  var port = server.address().port;

  console.log("Connected to server : port ",port);
});

setInterval(()=>{
    connection.query("select * from sensor_list", function (
        error,
        results,
        fields
      ) {
        if (error) throw error;
        var sensors = JSON.stringify(results);
        // console.log(results[0].co2Level);
        // console.log(results[0].smokeLevel);
         
        // console.log(JSON.stringify(results,["smokeLevel"]));
        // console.log(results[].["co2Level"]);
        if (results[0].co2Level > 5 && results[0].smokeLevel > 5) {
          var transporter = nodemailer.createTransport({
            service: "gmail",
            auth: {
              user: "sliitgroup19@gmail.com",
              pass: "sliit123",
            },
          });
        
          var mailOption = {
            from: "sliitgroup19@gmail.com",
            to: "sliitgroup19@gmail.com",
            subject: "Fire Alarm Alert",
            text: `Both CO2 and Smoke level is high`,
          };
        
          transporter.sendMail(mailOption, function (error, info) {
            if (error) {
              console.log(error);
            } else {
              console.log("Email sent: " + info.response);
              console.log("Both CO2 and Smoke level is high!!");
            }
          });
        } else if (results[0].co2Level > 5) {
          var transporter = nodemailer.createTransport({
            service: "gmail",
            auth: {
              user: "sliitgroup19@gmail.com",
              pass: "sliit123",
            },
          });
        
          var mailOption = {
            from: "sliitgroup19@gmail.com",
            to: "sliitgroup19@gmail.com",
            subject: "Fire Alarm Alert",
            text: `CO2 level is high`,
          };
        
          transporter.sendMail(mailOption, function (error, info) {
            if (error) {
              console.log(error);
            } else {
              console.log("Email sent: " + info.response);
              console.log("CO2 level is high!!");
            }
          });
        } else if (results[0].smokeLevel > 5) {
          var transporter = nodemailer.createTransport({
            service: "gmail",
            auth: {
              user: "sliitgroup19@gmail.com",
              pass: "sliit123",
            },
          });
        
          var mailOption = {
            from: "sliitgroup19@gmail.com",
            to: "sliitgroup19@gmail.com",
            subject: "Fire Alarm Alert",
            text: `Smoke Level level is high`,
          };
        
          transporter.sendMail(mailOption, function (error, info) {
            if (error) {
              console.log(error);
            } else {
              console.log("Email sent: " + info.response);
              console.log("Smoke level is high!!");
            }
          });
        }   else{
            console.log("Status is good!!");
        } 
        
        
      });
},10000);