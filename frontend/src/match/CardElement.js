import React from "react"
import {getCompanyImageSrc} from "./Game";
function CardElement({conglomerate, num}) {
    console.log(conglomerate + "WEEEEEEEEEEEEEEEEEEEE" + conglomerate.src);
    return [...Array(num)].map(()=>(
    <div style={{width:"10%"}}><img src={conglomerate.src} alt={"pippo"} style={{width:"100%"}}/></div>));
}

export default CardElement;
