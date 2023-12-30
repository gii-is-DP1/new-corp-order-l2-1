import AppNavbar from "../AppNavbar";
import {black, white} from "../util/Colors";
import {Title} from "../components/Title";
import List from "../components/List";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import {Subtitle} from "../components/Subtitle";
import TextInput from "../components/TextInput";

export function AdminModeration() {

    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }

    const buttonStyle = {
        padding: "10px",
    }

    let matchItems = []
    for (let i = 1; i < 20; i++) {
        matchItems.push(
            <ListLine sideContent={(
                <div style = {{display:"flex", flexDirection:"row", gap: "5px"}}>
                    <Button style={buttonStyle} buttonType={ButtonType.danger}>
                        Ban
                    </Button>
                    <Button style={buttonStyle} buttonType={ButtonType.secondaryLight}>
                        Profile
                    </Button>
                </div>
            )}>
                <img src="https://w7.pngwing.com/pngs/682/203/png-transparent-account-user-person-profile-avatar-basic-interface-icon.png"
                     alt="logo"
                     style={{width: "40px", height: "40px", borderRadius: "50%"}}/>
                <Subtitle>· Jugador #{i} |</Subtitle>
            </ListLine>
        )
    }

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <Title style={{fontSize: "60px", color: white}}>
                    Moderation
                </Title>
                <Subtitle style={{fontSize: "20px", color: white}}>
                    Select player to view actions
                </Subtitle>
                <TextInput style={{width: "600px", fontSize:"20px",  textTransform: "uppercase", padding: "15px"}} placeholder="Filter..."/>
                <div style={{marginTop:"22px"}}>
                    <List style={{maxHeight: "650px", width: "800px", backgroundColor: black, overflow: "auto"}}>
                        {matchItems}
                    </List>
                </div>
            </section>
        </div>
    )
}
