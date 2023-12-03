import {white} from "../util/Colors";
import {Title} from "./Title";
import {Subtitle} from "./Subtitle";

export default function Card({style, title, subtitle, icon, children}) {

    const cardStyle = {
        backgroundColor: white,
        minWidth: "350px",
        minHeight: "150px",
        width: "fit-content",
        borderRadius: "25px",
        boxShadow: "0 0 25px #592c2c2c",
        display: "flex",
        flexDirection: "column"
    }

    const headerStyle = {
        flex: "0 1 auto",
        display: "flex",
        flexDirection: "row",
        backgroundColor: "#2c2c2c",
        borderTopLeftRadius: "25px",
        borderTopRightRadius: "25px",
        color: white
    }

    const headerContentStyle = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        textAlign: "center",
        padding: "15px 25px 15px",
        gap: "5px",
        flex: 1
    }

    const headerIconStyle = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        margin: "auto",
        padding: "0 25px"
    }

    const contentStyle = {
        flex: 1,
        height: "100%"
    }

    return (
        <div style={{...cardStyle, ...style}}>
            <header style={headerStyle}>
                <div style={headerContentStyle}>
                    <Title>{title}</Title>
                    <Subtitle>{subtitle}</Subtitle>
                </div>
                <div style={headerIconStyle}>
                    {icon}
                </div>
            </header>
            <div style={contentStyle}>
                {children}
            </div>
        </div>
    )
}
