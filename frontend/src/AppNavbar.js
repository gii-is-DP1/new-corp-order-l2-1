import React, {useEffect, useState} from 'react';
import {Collapse, Nav, Navbar, NavbarBrand, NavbarText, NavbarToggler, NavItem, NavLink} from 'reactstrap';
import {Link} from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from "jwt-decode";
import * as Colors from "./util/Colors";

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
                createNavLinks([
                    {link: "/matches", text: "MATCHES"},
                    {link: "/moderation", text: "MODERATION"},
                    {link: "/achievements", text: "ACHIEVEMENTS"},
                    {link: "/stats", text: "STATS"},
                    {link: "/boh", text: "LEAVE ADMIN PANEL"}
                ])
            )
        }
        if (role === "PLAYER") {
            ownerLinks = (
                createNavLinks([
                    {link: "/stats", text: "STATS"},
                    {link: "/friends", text: "FRIENDS"},
                    {link: "/boh", text: "PLAY NOW"}
                ])
            )
        }
    })

    if (!jwt) {
        publicLinks = (
            createNavLinks([
                {link: "/home", text: "HOME"},
                {link: "/register", text: "REGISTER"},
                {link: "/login", text: "LOGIN"}
            ])
        )
    } else {
        userLinks = (
            <>
            </>
        )
        userLogout = (
            <>
                <NavbarText style={{color: "white"}} className="justify-content-end">{username}</NavbarText>
                <NavItem className="d-flex">
                    <NavLink style={{color: "white"}} id="logout" tag={Link} to="/logout">Logout</NavLink>
                </NavItem>
            </>
        )

    }

    const navBarStyle = {
        height: "75px"
    }

    return (
        <div style={{margin: "0"}}>
            <Navbar expand={true} className="bg-navbar" style={navBarStyle}>
                <NavbarBrand href="/" style={navBarStyle}>
                    <img src="/Images/New-corp-order-logo.png" alt="logo" style={{maxHeight: "100%"}}/>
                </NavbarBrand>
                <NavbarToggler onClick={toggleNavbar} className="ms-2"/>
                <Collapse style={{justifyContent: "flex-end"}} isOpen={!collapsed} navbar>
                    <Nav navbar>
                        {userLinks}
                        {adminLinks}
                        {ownerLinks}
                    </Nav>
                    <Nav navbar>
                        {publicLinks}
                        {userLogout}
                    </Nav>
                </Collapse>
            </Navbar>
        </div>
    );
}

function createNavLinks(navItem) {

    const items = navItem.map(item => (
        <NavItem>
            <NavLink style={{color: Colors.white}} tag={Link} to={item.link}>
                <h2 style={{fontSize: "14px"}}>{item.text}</h2>
            </NavLink>
        </NavItem>
    ));

    return <>
        {items}
    </>;
}

export default AppNavbar;
