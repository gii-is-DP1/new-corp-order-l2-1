import React, {useEffect, useState} from 'react';
import List from "../components/List";
import ListLine from "../components/ListLine";
import {Text} from "../components/Text"
import fetchAuthenticated from "../util/fetchAuthenticated";
import Button, {ButtonType} from "../components/Button";
import {useNavigate} from "react-router-dom";
import {dangerBackground} from "../util/Colors";

export function Notifications() {
    const [notifications, setNotifications] = useState(null)
    const [error, setError] = useState(null)
    const navigate = useNavigate()

    const fetchNotifications = async () => {
        try {
            setNotifications(await fetchAuthenticated(`/api/v1/notifications`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            console.log(error.message)
        }
    }

    useEffect(() => {
        fetchNotifications()
        setInterval(() => fetchNotifications(), 5000)
    }, []);

    const notificationStyle = {
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        position: 'fixed',
        top: '75px',
        zIndex: '1',
        background: 'transparent'
    };

    const lineStyle = {
        display: "flex",
        flexDirection: "row",
        minWidth: "650px",
        backgroundColor: "rgba(255, 255, 255, 0.4)"
    }

    async function joinGameRequest(value) {
        try {
            await fetchAuthenticated(`/api/v1/matches/${value}/join`, "POST")
                .then(() => navigate(`match/${value}`))
        } catch (error) {
            setError(error.message)
        }
    }

    async function deleteNotification(notification) {
        await fetchAuthenticated(`/api/v1/notifications/${notification.id}`, "DELETE")
    }

    const notificationsItems = notifications?.map(notification =>
        <>
            <ListLine style={lineStyle} sideContent={
                <>
                    <Button onClick={async () => {
                        await deleteNotification(notification)
                        await joinGameRequest(notification.matchCode)
                    }}
                            buttonType={ButtonType.success}>
                        Play
                    </Button>
                    <Button
                        onClick={async () => {
                            await deleteNotification(notification)
                            await fetchNotifications()
                        }}
                        buttonType={ButtonType.danger}>
                        Deny
                    </Button>
                </>
            }>
                <Text>{notification.sender.username} invited you to match #{notification.matchCode} </Text>
            </ListLine>
            {error &&
                <Text style={{color: dangerBackground}}>{error}</Text>}
        </>
    )

    return (
        <>
            <List style={notificationStyle}>
                {notificationsItems}
            </List>
        </>
    );
}
