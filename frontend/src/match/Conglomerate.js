import {conglomerate} from "./CompanyMatrix";

export default function Conglomerate(p){
    let src;
    switch (p.state.type) {
        case conglomerate.OMNICORP:
            src = "/Images/Conglomerates/omnicorp.png";
            break;
        case conglomerate.TOTAL_ENTERTAINMENT:
            src = "/Images/Conglomerates/totalentertainment.png";
            break;
        case conglomerate.GENERIC_INC:
            src = "/Images/Conglomerates/genericinc.png";
            break;
        case conglomerate.MEGAMEDIA:
            src = "/Images/Conglomerates/megamedia.png" ;
            break;
    }
    return(
        <>
            <img src={src} alt={"Image of Conglomerate of type" + p.state.type}/>
        </>
    );
}
