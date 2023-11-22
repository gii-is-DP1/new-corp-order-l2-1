import React, { useState, useEffect } from 'react';
import { Navbar, NavbarBrand, NavLink, NavItem, Nav, NavbarText, NavbarToggler, Collapse } from 'reactstrap';
import { Link } from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from "jwt-decode";

function AppNavbar() {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState("");
    const jwt = tokenService.getLocalAccessToken();
    const [collapsed, setCollapsed] = useState(true);

    const toggleNavbar = () => setCollapsed(!collapsed);

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt])

    let adminLinks = <></>;
    let ownerLinks = <></>;
    let userLinks = <></>;
    let userLogout = <></>;
    let publicLinks = <></>;


    roles.forEach((role) => {
        if (role === "ADMIN") {
            adminLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/matches">MATCHES</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/moderation">MODERATION</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/achievements">ACHIEVEMENTS</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/stats">STATS</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/boh">LEAVE ADMIN PANEL</NavLink>
                    </NavItem>
                </>
            )
        }
        if (role === "PLAYER") {
            ownerLinks = (
                <>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/stats">STATS</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/friends">FRIENDS</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink style={{ color: "white" }} tag={Link} to="/boh">PLAY NOW</NavLink>
                    </NavItem>

                </>
            )
        }
    })

    if (!jwt) {
        publicLinks = (
            <>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="home" tag={Link} to="/home">HOME</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="register" tag={Link} to="/register">REGISTER</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink style={{ color: "white" }} id="login" tag={Link} to="/login">LOGIN</NavLink>
                </NavItem>
            </>
        )
    } else {
        userLinks = (
            <>
                <NavItem>
                    <NavLink style={{ color: "white" }} tag={Link} to="/tmp">TMP</NavLink>
                </NavItem>
            </>
        )
        userLogout = (
            <>
                <NavbarText style={{ color: "white" }} className="justify-content-end">{username}</NavbarText>
                <NavItem className="d-flex">
                    <NavLink style={{ color: "white" }} id="logout" tag={Link} to="/logout">Logout</NavLink>
                </NavItem>
            </>
        )

    }

    return (
        <div>
            <Navbar expand="md" dark color="dark">
                <NavbarBrand href="/">
                    <img alt="logo" src="/Images/New-corp-order-logo.jpg" style={{ height: 40, width: 40 }} />
                </NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="ms-2" />
                <Collapse isOpen={!collapsed} navbar>
                    <Nav className="me-auto mb-2 mb-lg-0" navbar>
                        {userLinks}
                        {adminLinks}
                        {ownerLinks}
                    </Nav>
                    <Nav className="ms-auto mb-2 mb-lg-0" navbar>
                        {publicLinks}
                        {userLogout}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

export default AppNavbar;
