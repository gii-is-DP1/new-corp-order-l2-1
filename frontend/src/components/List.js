import * as Colors from "../util/Colors";

export default function List({children, style}) {

    const listStyle = {
        backgroundColor: Colors.gray,
        width: "100%",
        padding: "5px",
        borderRadius: "8px",
        overflowY: "auto",
        overflowX: "auto",
        display: "flex",
        flexDirection: "column",
        gap: "5px"
    }

    return(
        <div style={{...listStyle,...style}}>
            {children}
        </div>
    );

}
