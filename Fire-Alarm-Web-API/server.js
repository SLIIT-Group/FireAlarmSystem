const http = require("http");
const express = require("express");
const mysql = require("mysql");
const bodyParser = require("body-parser");
const cors = require("cors");


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
  console.log("You are now connected with mysql database...");
});

app.use(bodyParser.json()); // to support JSON-encoded bodies
app.use(
  bodyParser.urlencoded({
    // to support URL-encoded bodies
    extended: true,
  })
);
//Start server
var server = app.listen(3000, "localhost", function () {
  var host = server.address().address;
  var port = server.address().port;

  console.log("Example app listening at http://%s:%s", host, port);
});

//GET route --> http://localhost:3000
app.get("/", function (req, res) {
  //console.log("GET request received...");

  connection.query("select * from sensor_list", function (
    error,
    results,
    fields
  ) {
    if (error) throw error;    
    res.send(JSON.stringify(results));
  });
});

//POST
app.post("/add", (req, res) => {
  const { sensorNo, active, roomNo, floorNo, smokeLevel, co2Level } = req.body;

  res.send({
    sensorNo,
    active,
    roomNo,
    floorNo,
    smokeLevel,
    co2Level,
  });
  connection.query(
    `INSERT INTO sensor_list (sensorNo, active, floorNo, roomNo, smokeLevel, co2Level) VALUES ('${sensorNo}', '${active}', '${roomNo}', '${floorNo}', '${smokeLevel}', '${co2Level}') `,
    function (err, result) {
      if (err) throw err;
    }
  );
});

//PUT
app.put("/update", (req, res) => {
  const { sensorNo, active, roomNo, floorNo, smokeLevel, co2Level } = req.body;

  connection.query(
    `UPDATE sensor_list SET active = '${active}', floorNo = '${floorNo}', roomNo = '${roomNo}', smokeLevel = '${smokeLevel}', co2Level = '${co2Level}' WHERE sensorNo = '${sensorNo}' `,
    function (err, result) {
      if (err) throw err;

      return res.send("Received a PUT HTTP request");
    }
  );
});

//DELETE
app.delete("/delete", (req, res) => {
  const { sensorNo } = req.body;

  connection.query(
      `DELETE FROM sensor_list WHERE sensorNo = '${sensorNo}' `,
      function (err, result) {
        if (err) throw err;

        return res.send("Received a DELETE HTTP request");
      }
  );
});

//this will be used for timely update sensors co2 and smoke levels
app.put("/updatesensor", (req, res) => {
  const { sensorNo, smokeLevel, co2Level } = req.body;

  connection.query(
      `UPDATE sensor_list SET  smokeLevel = '${smokeLevel}', co2Level = '${co2Level}' WHERE sensorNo = '${sensorNo}' `,
      function (err, result) {
        if (err) throw err;

        return res.send("Received a PUT HTTP request");
        console.log("Received a PUT HTTP request");
      }
  );
});
