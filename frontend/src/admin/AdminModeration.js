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

export function AdminModeration() {
    const [usersData, setUsersData] = useState(null)
    const [filter, setFilter] = useState("")
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
    }, [filter]);

    const fetchUsersData = async () => {
        try {
            setUsersData(await fetchAuthenticated(`/api/v1/users?username=${filter}`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            navigate('')
        }
    };

    let usersItem = usersData?.map(user => {
       return  <ListLine sideContent={(
            <div style = {{display:"flex", flexDirection:"row", gap: "5px"}}>
                <Button onClick={() => fetchAuthenticated(`/api/v1/users/${user.username}`, "DELETE")
                                    .then(() => fetchUsersData())}
                        style={buttonStyle}
                        buttonType={ButtonType.danger} >
                    Ban
                </Button>
                <Button style={buttonStyle} buttonType={ButtonType.secondaryLight} onClick={() => navigate(`/user/${user.username}`) }>
                    Profile
                </Button>
            </div>
        )}>
           <ProfilePicture url={user.picture} style={{width: "40px", height: "40px"}}/>
            <Subtitle>{user.username}</Subtitle>
        </ListLine>
    })

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <div style={{gap:"15px", display:"flex", flexDirection:"column", alignItems: "center"}}>
                    <Title style={{fontSize: "60px", color: white}}>
                        Moderation
                    </Title>
                    <Subtitle style={{fontSize: "20px", color: white}}>
                        Select player to view actions
                    </Subtitle>
                    <div style={{display:'flex', flexDirection:"row", gap:'20px'}}>
                        <TextInput onClick={setFilter}
                                   style={{width: "600px", fontSize: "20px", textTransform: "uppercase"}}
                                   placeholder="Filter..."/>
                        {filter != "" && <Button onClick={() => setFilter("")} buttonType={ButtonType.danger} style={{fontSize: "20px", textTransform: "uppercase"}}>Delete filter</Button>}
                    </div>
                </div>
                <div style={{marginTop:"22px"}}>
                    <List style={{maxHeight: "650px", width: "800px", backgroundColor: black, overflow: "auto"}}>
                        {usersItem}
                    </List>
                </div>
            </section>
        </div>
    )
}
