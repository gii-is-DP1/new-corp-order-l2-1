export default function Conglomerate({conglomerate}){
    return(
        <>
            <img src={conglomerate.src} alt={"Image of Conglomerate of type" + conglomerate.name}/>
        </>
    );
}
