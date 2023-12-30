import AppNavbar from "../AppNavbar";
import {black, white} from "../util/Colors";
import {Title} from "../components/Title";
import List from "../components/List";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import {Subtitle} from "../components/Subtitle";
import TextInput from "../components/TextInput";

export function AdminAchievements() {

    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }

    const textInputStyle = {
        width: "500px",
        fontSize:"20px",
        textTransform: "uppercase",
        padding: "15px"
    }


    let matchItems = []
    for (let i = 1; i < 20; i++) {
        matchItems.push(
            <ListLine sideContent={(
                <div style = {{display:"flex", flexDirection:"row", gap: "5px"}}>
                    <Button buttonType={ButtonType.secondaryLight}>
                        edit
                    </Button>
                    <Button  buttonType={ButtonType.danger}>
                        delete
                    </Button>
                </div>
            )}>
                <img src="https://c0.klipartz.com/pngpicture/938/540/gratis-png-emoticon-smiley-signo-de-interrogacion-smiley.png"
                     alt="logo"
                     style={{width: "40px", height: "40px", borderRadius: "50%"}}/>
                <Subtitle> Achievement #{i} </Subtitle>
            </ListLine>
        )
    }

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <div style={{gap:"15px", display:"flex", flexDirection:"column", alignItems: "center"}}>
                    <Title style={{fontSize: "60px", color: white}}>
                        achievements
                    </Title>
                    <Subtitle style={{fontSize: "20px", color: white}}>
                        Create, modify or delete achievements
                    </Subtitle>
                    <div style={{display:"flex", flexDirection:"row", gap:"20px"}}>
                        <TextInput style={textInputStyle} placeholder="Filter..."/>
                        <Button style={{width:"200px"}} buttonType={ButtonType.success}>
                            Create
                        </Button>
                    </div>
                </div>
                <div style={{marginTop:"22px"}}>
                    <List style={{maxHeight: "650px", width: "800px", backgroundColor: black, overflow: "auto"}}>
                        {matchItems}
                    </List>
                </div>
            </section>
        </div>
    )
}
