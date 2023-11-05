export default function Conglomerate(p){
    let src;
    switch (p.state.type) {
        case "OMNICORP":
            src = "/Images/Conglomerates/Omnicorp.png";
            break;
        case "TOTAL_ENTERTAINMENT":
            src = "/Images/Conglomerates/TotalEntertainment.png";
            break;
        case "GENERIC_INC":
            src = "/Images/Conglomerates/GenericInc.png";
            break;
        case "OMNIMEDIA":
            src = "/Images/Conglomerates/MegaMedia.png" ;
            break;
    }
    return(
        <>
            <img src={src} alt={"Image of Conglomerate of type" + p.state.type}/>
        </>
    );
}
