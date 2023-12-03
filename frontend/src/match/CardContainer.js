import React from "react"
import Conglomerate from "./Conglomerate";
import CardElement from "./CardElement";
function CardContainer({cards}) {
    console.log(cards);
    return (
        <div className={"cardContainer"}>
            {cards.map((x, i) =>
                <CardElement a = {i}>
                    {x}
                </CardElement>
            )}
        </div>
    );
}

export default CardContainer;
