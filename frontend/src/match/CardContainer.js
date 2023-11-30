import React from "react"
import Conglomerate from "./Conglomerate";
import CardElement from "./CardElement";
import {conglomerate} from "./MatchEnums";
function CardContainer({cards}) {
    return (<>
            <CardElement conglomerate={conglomerate.OMNICORP} num={cards.OMNICORP}/>
            <CardElement conglomerate={conglomerate.GENERIC_INC} num={cards.GENERIC_INC}/>
            <CardElement conglomerate={conglomerate.TOTAL_ENTERTAINMENT} num={cards.TOTAL_ENTERTAINMENT}/>
            <CardElement conglomerate={conglomerate.MEGAMEDIA} num={cards.MEGAMEDIA}/>
        </>
    );
}

export default CardContainer;
