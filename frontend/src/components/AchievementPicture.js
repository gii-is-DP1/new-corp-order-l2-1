import EmojiEventsIcon from '@mui/icons-material/EmojiEvents';
import {yellow, grayDarker} from "../util/Colors";

export default function AchievementPicture({url, style, earned}) {
    const defaultStyle = {
        width: "100%",
        height: "100%",
        borderRadius: "50%",
        overflow: "hidden"
    }

    const backgroundStyle = {
        backgroundColor: "#F8F8F8",
        width: "100%",
        height: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    }

    const imgStyle = {
        filter: earned ? 'none' : 'grayscale(100%)'
    }

    return <div style={{...defaultStyle, ...style}}>
        {
            url == null
                ? <div style={backgroundStyle}>
                    <EmojiEventsIcon style={{width: "70%", height: "70%", color: earned ? yellow : grayDarker}}/>
                </div>

                : <div style={backgroundStyle}>
                    <img style={imgStyle} width="64px" height="64px" alt="profile image" src={url}/>
                </div>
        }
    </div>
}
