
import * as Colors from "../util/Colors";
export default function ListLine({children, icon, buttons, profileImages}) {


    const containerStyle = {
        backgroundColor: Colors.white,
        color: Colors.black,
        display: "flex",
        flexDirection: "row",
        borderRadius: "8px",
        height: "50px",
    }

    const leftDivStyle = {
        display: "flex",
        flexDirection: "row",
        justifyContent: "flex-start",
        alignItems: "center",
        padding:"5px",
        flex: "0.5"
    }

    const rightDivStyle = {
        display: "flex",
        flexDirection: "row",
        justifyContent: "flex-end",
        alignItems: "center",
        padding:"5px",
        margin:"5px",
        flex: "0.5"
    }

    const fontStyle = {
        alignItems: "center",
        margin:"0px"
    }

    const profileImageStyle = {
        borderRadius: "50%",
        width: "30px",
        height: "30px",
        margin: "0px",
    }

    const iconStyle = {
        display: "flex",
        borderRadius: "50%",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        height: "30px",
        width: "30px",
    }

    const listStyle = {
        display: "flex",
        listStyleType: "none",
    }

    let listIcons = profileImages.map(image =>
        <li>
            <img src={image} alt={"profileImage"} style={profileImageStyle} />
        </li>)

    return <div style={containerStyle}>
        <div style={leftDivStyle}>
            {icon && <img src={icon} alt={"icon"} style={iconStyle}/>}
            {children}

        </div>
        <div style={rightDivStyle}>
            {profileImages && <ul style={listStyle}>{listIcons}</ul>}
            {buttons}
        </div>
    </div>

}
