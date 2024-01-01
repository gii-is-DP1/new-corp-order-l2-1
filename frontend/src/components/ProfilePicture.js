export default function ProfilePicture({url, isTransparent, style}) {
    const defaultStyle = {
        width: "100%",
        height: "100%",
        borderRadius: "50%",
        overflow: "hidden",
    }

    const backgroundStyle = {
        backgroundColor: "#F8F8F8",
        width: "100%",
        height: "100%",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        opacity: isTransparent?0.2:1,
    }
    return <div style={{...defaultStyle, ...style}}>
        {
            url == null
                ? <div style={backgroundStyle}>
                    <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" fill="#2C2C2C" class="bi bi-person-fill" viewBox="0 0 16 16">
                        <path d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                    </svg>
                </div>
                : <img width="64px" height="64px" alt="profile image" src={url}/>
        }
    </div>
}
