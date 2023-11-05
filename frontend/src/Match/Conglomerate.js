export const conglomerateType = {OMNICORP: "OMNICORP",TOTAL_ENTERTAINMENT: "TOTAL_ENTERTAINMENT", GENERIC_INC: "GENERIC_INC", MEGAMEDIA: "MEGAMEDIA" };

export default function Conglomerate(p){
    let src;
    switch (p.state.type) {
        case conglomerateType.OMNICORP:
            src = "/Images/Conglomerates/Omnicorp.png";
            break;
        case conglomerateType.TOTAL_ENTERTAINMENT:
            src = "/Images/Conglomerates/TotalEntertainment.png";
            break;
        case conglomerateType.GENERIC_INC:
            src = "/Images/Conglomerates/GenericInc.png";
            break;
        case conglomerateType.MEGAMEDIA:
            src = "/Images/Conglomerates/MegaMedia.png" ;
            break;
    }
    return(
        <>
            <img src={src} alt={"Image of Conglomerate of type" + p.state.type}/>
        </>
    );
}
