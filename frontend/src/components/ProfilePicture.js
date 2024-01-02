import PersonIcon from '@mui/icons-material/Person';

export default function ProfilePicture({url, isTransparent, style}) {
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
        opacity: isTransparent ? 0.2 : 1,
    }

    return <div style={{...defaultStyle, ...style}}>
        {
            url == null
                ? <div style={backgroundStyle}>
                    <PersonIcon style={{width: "70%", height: "70%"}}/>
                </div>
                : <img width="64px" height="64px" alt="profile image" src={url}/>
        }
    </div>
}
