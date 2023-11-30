import * as Colors from "../util/Colors";

export default function List({children, style}) {

    const listStyle = {
        overflowY: "scroll",
        borderRadius: "8px",
        backgroundColor: Colors.grayDarker
    }

    return(
        <div style={{...listStyle,...style}}>
            {children}
        </div>
    );

}
