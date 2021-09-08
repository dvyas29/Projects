import React from "react";
import { Navbar, Nav, Form, FormControl, Button } from "react-bootstrap";

class NavBar extends React.Component {
    constructor(props) {
        super(props);
        this.logOut = this.logOut.bind(this);
    }

    logOut() {
        localStorage.removeItem("name")
        localStorage.removeItem("type")
        localStorage.removeItem("userData")
    }
    render() {
        return (
            <Navbar bg="dark" variant="dark">
                <Nav className="mr-auto">
                    <Nav.Link href="/homepage" active>
                        Halifax Foodie
                   </Nav.Link>
                    <Nav.Link href="/chat">Chat</Nav.Link>
                </Nav>
                <Nav className="ms-auto">
                    <Nav.Link href="/" onClick={this.logOut} >SignOut</Nav.Link>
                </Nav>
            </Navbar>
        );
    }
}

export default NavBar;
