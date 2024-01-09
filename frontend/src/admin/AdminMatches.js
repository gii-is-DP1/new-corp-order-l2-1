import AppNavbar from "../AppNavbar";
import {black, white} from "../util/Colors";
import {Title} from "../components/Title";
import List from "../components/List";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import {Subtitle} from "../components/Subtitle";

export function AdminMatches() {

    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }

    let matchItems = []
    for (let i = 1; i < 20; i++) {
        matchItems.push(
            <ListLine sideContent={(
                <Button style={{}} buttonType={ButtonType.secondaryLight}>
                    Spectate
                </Button>)}>
                <Subtitle>· Match #{i} |</Subtitle>
                <Subtitle>Match State |</Subtitle>
                <Subtitle>Num players</Subtitle>
            </ListLine>
        )
    }

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <Title style={{fontSize: "60px", color: white}}>
                    Matches
                </Title>
                <Subtitle style={{fontSize: "15px", color: white}}>
                    Select a match to join (if currently playing) or view stats
                </Subtitle>
                <div>
                    <List style={{
                        marginTop: "15px",
                        maxHeight: "635px",
                        width: "800px",
                        backgroundColor: black,
                        overflow: "auto"
                    }}>
                        {matchItems}
                    </List>
                </div>
            </section>
        </div>
    )
}
