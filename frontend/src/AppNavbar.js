import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from 'jwt-decode';
import {black, white} from "./util/Colors";
import {Text} from "./components/Text";
import Button, {ButtonType} from "./components/Button";

const AppNavbar = () => {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState('');
    const jwt = tokenService.getLocalAccessToken();
    const navigate = useNavigate()

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
            {link: '/register', text: 'Register'},
            {link: '/login', text: 'Login'},
        ]);
    } else {
        roles.forEach((role) => {
            if (role === 'ADMIN') {
                navLinks = createNavLinks([
                    {link: '/matches', text: 'Matches'},
                    {link: '/moderation', text: 'Moderation'},
                    {link: '/achievements', text: 'Achievements'},
                    {link: '/stats', text: 'Stats'},
                    {link: '/boh', text: 'Leave Admin Panel'},
                ]);
            }
            if (role === 'USER') {
                navLinks = createNavLinks([
                    {link: `/user/${username}/friends`, text: 'Friends'},
                    {link: `/user/${username}/stats`, text: 'Stats'},
                    {link: `/user/${username}`, text: 'Profile'},
                    {link: '', text: 'Play now', isButton: true},
                ]);
            }
        });
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
