import React from "react"
import CardContainer from "./CardContainer";
function CardSelectConglomerates({cards}) {
    const style = {display: "flex", flexWrap: "wrap", width: "100%"};
    return <div style={style}>
        <CardContainer cards={cards}/>
    </div>;
}

export default CardSelectConglomerates;
