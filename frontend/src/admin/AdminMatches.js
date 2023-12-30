import AppNavbar from "../AppNavbar";
import {black, orange, white} from "../util/Colors";
import {Text} from "../components/Text";
import {Title} from "../components/Title";
import List from "../components/List";
import ListLine from "../components/ListLine";

export function AdminMatches() {

    const content = {
        flex: 1,
        display: "flex",
        flexDirection: "row",
        backgroundColor: black,
        backgroundSize: "cover",
        backgroundPosition: "center",
        justifyContent:"center",
        alignItems:"center",

    }



return (
    <div style={{display: "flex", flexDirection: "column", height: "100%"}}>
        <AppNavbar/>
        <div style = {content}>
            <div style = {{width:"60%", padding:"20px", justifyContent:"center", alignItems:"center"}}>
                <Title style={{fontSize: "60px", color:white}}>
                    Matches
                </Title>
                <Text style={{color:white, textTransform:"none", fontSize:"25px"}}>
                    Select a match to join (if currently playing) or view stats
                </Text>
                <List style={{height:"700px", backgroundColor: black}}>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                    <ListLine></ListLine>
                </List>
            </div>
        </div>
    </div>

)
}
