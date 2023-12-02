import * as Colors from "../util/Colors";

export default function List({children, style}) {

    const listStyle = {
        backgroundColor: Colors.gray,
        width: "100%",
        padding: "5px",
        borderRadius: "8px",
        overflow: "scroll",
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
