import PersonIcon from '@mui/icons-material/Person';
import {black, grayDarker} from "../util/Colors";

export default function ProfilePicture({url, isTransparent, style}) {
    const defaultStyle = {
        width: "100%",
        height: "100%",
        borderRadius: "50%",
        overflow: "hidden"
    }

    const backgroundStyle = {
        backgroundColor: grayDarker,
        width: "100%",
        height: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        opacity: isTransparent ? 0.2 : 1,
    }

    return <div style={{...defaultStyle, ...style}}>
        {
            url == null
                ? <div style={backgroundStyle}>
                    <PersonIcon style={{width: "70%", height: "70%"}}/>
                </div>
                : <img width="100%" height="100%" alt="profile image" src={url}/>
        }
    </div>
}
