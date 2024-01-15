import React, {useEffect, useState} from 'react';
import {Link, useNavigate} from 'react-router-dom';
import tokenService from './services/token.service';
import jwt_decode from 'jwt-decode';
import {black, white} from "./util/Colors";
import {Text} from "./components/Text";
import Button, {ButtonType} from "./components/Button";
import ProfilePicture from "./components/ProfilePicture";
import {Pressable} from "./components/Pressable";
import fetchAuthenticated from "./util/fetchAuthenticated";
import {propics} from "./match/data/MatchEnums";

const AppNavbar = () => {
    const [roles, setRoles] = useState([]);
    const [username, setUsername] = useState('');
    const jwt = tokenService.getLocalAccessToken();
    const navigate = useNavigate()
    const [userPicture, setUserPicture] = useState(null)

    const fetchUserPicture = async () => {
        try {
            setUserPicture(await fetchAuthenticated(`/api/v1/users/${tokenService.getUser().username}/picture`, "GET")
                .then(response => response.json())
                .then(response => response.picture));
        } catch (error) {
            console.log(error.message)
        }
    }

    useEffect(() => {
        if (jwt) {
            setRoles(jwt_decode(jwt).authorities);
            setUsername(jwt_decode(jwt).sub);
            fetchUserPicture()
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
                    {link: '/rankings/games_won', text: 'Rankings'},
                    {link: '/metrics', text: 'Metrics'},
                    {link: '/admin/matches', text: 'Matches'},
                    {link: '/admin/moderation', text: 'Moderation'},
                    {link: '/admin/achievements', text: 'Achievements'},
                    {link: `/user/${username}`, isProfilePicture: true},
                ]);
            }
            if (role === 'USER') {
                navLinks = createNavLinks([
                    {link: '/rankings/games_won', text: 'Rankings'},
                    {link: '/metrics', text: 'Metrics'},
                    {link: `/user/${username}/friends`, text: 'Friends'},
                    {link: `/user/${username}`, isProfilePicture: true},
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

    function createNavLinks(navItem) {
        const items = navItem.map(item => (
            <div style={{display: "flex", flexDirection: "row", alignItems: "center"}}>
                {!item.isButton &&
                    <Link to={item.link} style={{textDecoration: "none", color: white}}>
                        <Text>{item.text}</Text>
                    </Link>}

                {item.isButton &&
                    <Button onClick={() => navigate(`/${item.link}`)}
                            buttonType={ButtonType.primary}>{item.text}</Button>}

                {item.isProfilePicture &&
                    <Pressable onClick={() => navigate(`${item.link}`)}>
                        <ProfilePicture url={propics[userPicture]}
                                        style={{width: "45px", height: "45px"}}
                                        alt={"user picture"}/>
                    </Pressable>}
            </div>
        ));
        return <>{items}</>;
    }

    return (
        <header style={navBarStyle}>
            <Link to="/">
                <img
                    src="/images/New-corp-order-logo.png"
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

export default AppNavbar;
