import React from "react";
import { Navbar, NavbarBrand } from "reactstrap";

export default function Header() {
  return (
    <div>
      <Navbar color="dark" dark expand="md">
        <NavbarBrand href="/">Fire Alarm Dashboard</NavbarBrand>
      </Navbar>
    </div>
  );
}
