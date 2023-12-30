import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from 'jwt-decode';
import {black, white} from "./util/Colors";
import {Text} from "./components/Text";

const AppNavbar = () => {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState('');
    const jwt = tokenService.getLocalAccessToken();

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
        }
    }, [jwt]);

    let navLinks = <></>;
    let userLogout = <></>;

    if (!jwt) {
        navLinks = createNavLinks([
            {link: '/home', text: 'HOME'},
            {link: '/register', text: 'REGISTER'},
            {link: '/login', text: 'LOGIN'},
        ]);
    } else {
        roles.forEach((role) => {
            if (role === 'ADMIN') {
                navLinks = createNavLinks([
                    {link: '/matches', text: 'MATCHES'},
                    {link: '/moderation', text: 'MODERATION'},
                    {link: '/achievements', text: 'ACHIEVEMENTS'},
                    {link: '/stats', text: 'STATS'},
                    {link: '/boh', text: 'LEAVE ADMIN PANEL'},
                ]);
            }
            if (role === 'PLAYER') {
                navLinks = createNavLinks([
                    {link: '/stats', text: 'STATS'},
                    {link: '/friends', text: 'FRIENDS'},
                    {link: '/boh', text: 'PLAY NOW'},
                ]);
            }
        });

        userLogout = (
            <>
                <div style={{color: 'white'}} className="justify-content-end">
                    {username}
                </div>
                <div className="d-flex">
                    <Link style={{color: 'white'}} id="logout" to="/logout">
                        Logout
                    </Link>
                </div>
            </>
        );
    }

    const navBarStyle = {
        height: '75px',
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: '0 20px',
        background: black,
        color: white,
    };

    return (
        <header style={navBarStyle}>
            <Link to="/">
                <img
                    src="/Images/New-corp-order-logo.png"
                    alt="logo"
                    style={{maxHeight: "60px", cursor: 'pointer'}}
                />
            </Link>
            <div style={{display: 'flex'}}>
                <div style={{display: "flex", flexDirection: "row", gap: '10px'}}>{navLinks}</div>
                <div>{userLogout}</div>
            </div>
        </header>
    );
};

function createNavLinks(navItem) {
    const items = navItem.map(item => (
        <Link to={item.link} style={{textDecoration: "none", color: white}}>
            <Text>{item.text}</Text>
        </Link>
    ));

    return <>{items}</>;
}

export default AppNavbar;
