import React from "react";
import {
  Card,
  Badge,
  CardHeader,
  CardFooter,
  CardBody,
  CardTitle,
  CardText,
  Progress,
} from "reactstrap";

export default function FireAlarm(props) {
  const { id, active, floor, room, smokeLevel, co2Level } = props;

  return (
    <div>
      <Card
        style={{ margin: "10px" }}
        body
        outline
        color={
          props.smokelevel > 5 || props.co2level > 5 ? "danger" : "success"
        }
      >
        <CardHeader className="text-center" tag="h3">
          Alarm {id}
        </CardHeader>
        <CardBody>
          <CardTitle>
            Status:{" "}
            <Badge color={active === 1 ? "success" : "danger"}>
              {active === 1 ? "Active" : "Disabled"}
            </Badge>
          </CardTitle>
          <CardText>Floor Number: {floor}</CardText>
          <CardText>Room Number: {room}</CardText>

          <Progress
            style={{ width: "70%" }}
            value={(props.smokelevel / 10) * 100}
            color={props.smokelevel > 5 ? "danger" : "success"}
          />
          <CardText>Smoke Level: {props.smokelevel}</CardText>
          <Progress
            style={{ width: "70%" }}
            value={(props.co2level / 10) * 100}
            color={props.co2level > 5 ? "danger" : "success"}
          />
          <CardText>CO2 Level: {props.co2level}</CardText>
        </CardBody>
        <CardFooter className="text-muted"></CardFooter>
      </Card>
    </div>
  );
}
