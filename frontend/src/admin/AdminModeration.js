import AppNavbar from "../AppNavbar";
import {black, white} from "../util/Colors";
import {Title} from "../components/Title";
import List from "../components/List";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import {Subtitle} from "../components/Subtitle";
import TextInput from "../components/TextInput";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useEffect, useState} from "react";
import ProfilePicture from "../components/ProfilePicture";
import {useNavigate} from "react-router-dom";
import {Pressable} from "../components/Pressable";
import {Text} from "../components/Text";
import {propics} from "../match/data/MatchEnums";

export function AdminModeration() {
    const [usersData, setUsersData] = useState([])
    const [filter, setFilter] = useState("")
    const [page, setPage] = useState(0)
    const navigate = useNavigate()

    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }

    const buttonStyle = {
        padding: "10px",
    }

    useEffect(() => {
        fetchUsersData()
    }, [filter, page]);

    const fetchUsersData = async (clear) => {
        try {
            const fetch = await fetchAuthenticated(`/api/v1/users?filter=${filter}&page=${page}`, "GET")
                .then(async response => await response.json())
            setUsersData(clear ? fetch : [...usersData, ...fetch])
        } catch (error) {
            navigate('')
        }
    };

    let usersItem = usersData?.map(user => {
        return <ListLine sideContent={(
            <div style={{display: "flex", flexDirection: "row", gap: "5px"}}>
                <Button onClick={() => fetchAuthenticated(`/api/v1/users/${user.username}`, "DELETE")
                    .then(() => fetchUsersData(true))}
                        style={buttonStyle}
                        buttonType={ButtonType.danger}>
                    Delete
                </Button>
                <Button style={buttonStyle} buttonType={ButtonType.secondaryLight}
                        onClick={() => navigate(`/user/${user.username}`)}>
                    Profile
                </Button>
            </div>
        )}>
            <ProfilePicture url={propics[user.picture]} style={{width: "40px", height: "40px"}}/>
            <Subtitle>{user.username}</Subtitle>
        </ListLine>
    })

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                    <Title style={{fontSize: "60px", color: white}}>
                        Moderation
                    </Title>
                    <Subtitle style={{marginBottom: "15px", fontSize: "15px", color: white}}>
                        Select player to view actions
                    </Subtitle>
                    <div style={{display: 'flex', flexDirection: "row", gap: "10px"}}>
                        <TextInput onClick={content => {
                            setFilter(content)
                            setUsersData([])
                        }}
                                   style={{width: "600px", fontSize: "20px", textTransform: "uppercase"}}
                                   placeholder="Filter..."/>
                        {filter != "" &&
                            <Button onClick={() => {
                                setFilter("")
                                setUsersData([])
                            }}
                                    buttonType={ButtonType.danger}
                                    style={{fontSize: "20px", textTransform: "uppercase"}}>
                                Delete filter
                            </Button>}
                    </div>
                </div>
                <div style={{marginTop: "15px"}}>
                    <List style={{maxHeight: "525px", width: "800px", backgroundColor: black, overflow: "auto"}}>
                        {usersItem}
                    </List>
                </div>
                <Pressable onClick={() => setPage(page + 1)}>
                    <Text style={{color: white}}>View more</Text>
                </Pressable>
            </section>
        </div>
    )
}
