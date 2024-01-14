import PersonIcon from '@mui/icons-material/Person';

export default function ProfilePicture({url, isTransparent, style}) {
    const defaultStyle = {
        width: "100%",
        height: "100%",
        borderRadius: "50%",
        overflow: "hidden",
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
    }

    const backgroundStyle = {
        backgroundColor: "#F8F8F8",
        opacity: isTransparent ? 0.2 : 1,
    }

    const imageAndIconStyle = {
        maxWidth: "100%",
        maxHeight: "100%",
        objectFit: "cover", // Esto asegura que la imagen se recorte para llenar el área asignada
    }

    return <div style={{...defaultStyle, ...style}}>
        {
            url == null
                ? <div style={{...backgroundStyle, ...defaultStyle}}>
                    <PersonIcon style={{width: "70%", height: "70%"}}/>
                </div>
                : <img style={imageAndIconStyle} alt="profile image" src={url}/>
        }
    </div>
}
