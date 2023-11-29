export default function ProfilePicture({url, isTransparent}) {
    const style = {
        width: "64px",
        height: "64px",
        borderRadius: "50%",
        overflow: "hidden",
    }

    const backgroundStyle = {
        backgroundColor: "#F8F8F8",
        width: "64px",
        height: "64px",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        opacity: isTransparent?0.2:1,
    }
    return <div style={style}>
        {
            url == null
                ? <div style={backgroundStyle}>
                    <img src="/svg/user-icon.svg" alt="user" />
                </div>
                : <img width="64px" height="64px" alt="profile image" src={url}/>
        }
    </div>
}
