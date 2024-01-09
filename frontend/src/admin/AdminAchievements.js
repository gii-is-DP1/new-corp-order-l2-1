import AppNavbar from "../AppNavbar";
import {black, grayDarker, white} from "../util/Colors";
import {Title} from "../components/Title";
import List from "../components/List";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import {Subtitle} from "../components/Subtitle";
import TextInput from "../components/TextInput";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import AchievementPicture from "../components/AchievementPicture";

export function AdminAchievements() {
    const [achievementsData, setAchievementsData] = useState(null)
    const [filter, setFilter] = useState("")
    const navigate = useNavigate()

    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        overflow: "auto"
    }

    const textInputStyle = {
        width: "500px",
        fontSize: "20px",
        textTransform: "uppercase",
    }

    const fetchAchievementsData = async () => {
        try {
            setAchievementsData(await fetchAuthenticated(`/api/v1/achievements?name=${filter}`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            navigate('')
        }
    };

    useEffect(() => {
        fetchAchievementsData()
    }, [filter]);

    let achievementItems = achievementsData?.map(achievement => {
        return  <ListLine sideContent={(
            <div style = {{display:"flex", flexDirection:"row", gap: "5px"}}>
                <Button buttonType={ButtonType.secondaryLight} onClick={() => navigate(`/admin/achievements/${achievement.id}`)}>
                    edit
                </Button>
                <Button onClick={() => fetchAuthenticated(`/api/v1/achievements/${achievement.id}`, "DELETE")
                    .then(() => fetchAchievementsData())}
                    buttonType={ButtonType.danger}>
                    delete
                </Button>
            </div>
        )}>
            <AchievementPicture url={achievement.imageUrl} style={{width: "40px", height: "40px"}} earned={true}/>
            <Subtitle> {achievement.name} </Subtitle>
            <Subtitle style={{fontSize: '12px', color: grayDarker}}> {achievement.description} </Subtitle>
        </ListLine>
    })

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                    <Title style={{fontSize: "60px", color: white}}>
                        achievements
                    </Title>
                    <Subtitle style={{fontSize: "20px", color: white}}>
                        Create, modify or delete achievements
                    </Subtitle>
                    <div style={{display:"flex", flexDirection:"row", gap:"20px"}}>
                        <TextInput onClick={setFilter}
                                   style={{width: "600px", fontSize:"20px",  textTransform: "uppercase"}}
                                   placeholder="Filter..."/>
                        {filter != "" && <Button onClick={() => setFilter("")} buttonType={ButtonType.danger} style={{fontSize: "20px", textTransform: "uppercase"}}>Delete filter</Button>}
                        <Button style={{width:"200px"}} buttonType={ButtonType.success} onClick={() => navigate(`/admin/achievements/create`)}>
                            Create
                        </Button>
                    </div>
                </div>
                <div style={{marginTop:"22px"}}>
                    <List style={{maxHeight: "525px", width: "800px", backgroundColor: black, overflow: "auto"}}>
                        {achievementItems}
                    </List>
                </div>
            </section>
        </div>
    )
}
