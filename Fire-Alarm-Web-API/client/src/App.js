import React from "react";
import "./App.css";
import { Component } from "react";
import axios from "axios";
import Header from "./components/Header";
import FireAlarm from "./components/FireAlarm";
import { Container, Row, Col } from "reactstrap";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      sensors: [],
    };
  }

  componentDidMount() {
    this.interval = setInterval(() => {
      axios.get("http://localhost:3000").then((res) => {
        const sensors = res.data;
        this.setState({ sensors });
        {
          console.log(this.state.sensors);
        }
      });
    }, 5000);
  }

  render() {
    return (
      <div className="App">
        <Header></Header>
        <Container>
          <Row>
            {" "}
            {this.state.sensors.map((sensor) => {
              return (
                <FireAlarm
                  key={sensor.sensorId}
                  id={sensor.sensorNo}
                  active={sensor.active}
                  floor={sensor.floorNo}
                  room={sensor.roomNo}
                  smokelevel={sensor.smokeLevel}
                  co2level={sensor.co2Level}
                ></FireAlarm>
              );
            })}
          </Row>
        </Container>
      </div>
    );
  }
}

export default App;
