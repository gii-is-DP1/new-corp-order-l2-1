export const conglomerateType = {
    OMNICORP: "OMNICORP",
    TOTAL_ENTERTAINMENT: "TOTAL_ENTERTAINMENT",
    GENERIC_INC: "GENERIC_INC",
    MEGA_MEDIA: "MEGAMEDIA"
};

export default function Conglomerate(p){
    let src;
    switch (p.state.type) {
        case conglomerateType.OMNICORP:
            src = "/Images/Conglomerates/omnicorp.png";
            break;
        case conglomerateType.TOTAL_ENTERTAINMENT:
            src = "/Images/Conglomerates/totalentertainment.png";
            break;
        case conglomerateType.GENERIC_INC:
            src = "/Images/Conglomerates/genericinc.png";
            break;
        case conglomerateType.MEGA_MEDIA:
            src = "/Images/Conglomerates/megamedia.png" ;
            break;
    }
    return(
        <>
            <img src={src} alt={"Image of Conglomerate of type" + p.state.type}/>
        </>
    );
}
