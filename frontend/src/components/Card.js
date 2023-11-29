export default function Card({style, title, subtitle, icon, children}) {

    const cardStyle = {
        minWidth: "200px",
        minHeight: "200px",
        width: "fit-content",
        borderRadius: "25px",
        boxShadow: "0 0 25px #592c2c2c",
    }

    const headerStyle = {
        display: "flex",
        flexDirection: "row",
        backgroundColor: "#2c2c2c",
        margin: "0px",
        borderTopLeftRadius: "25px",
        borderTopRightRadius: "25px",
        color: "white"
    }

    const headerTextStyle = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        padding: "10px 0 10px",
        flex: 1
    }

    const iconStyle = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        margin: "auto",
        padding: "0 25px"
    }

    return (
        <div style={{...cardStyle, ...style}}>
            <header style={headerStyle}>
                <div style={headerTextStyle}>
                    <h1 style={{fontSize: "32px"}}>
                        {title}
                    </h1>
                    <h2 style={{fontSize: "24px"}}>
                        {subtitle}
                    </h2>
                </div>
                <img src={icon} alt={"icon"} style={iconStyle}/>
            </header>
            {children}
        </div>
    )
}
