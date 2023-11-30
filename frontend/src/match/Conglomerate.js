export default function Conglomerate({conglomerate}){
    return(
        <>
            <img height={"100px"} src={conglomerate.src} alt={"Image of Conglomerate of type" + conglomerate.name}/>
        </>
    );
}
