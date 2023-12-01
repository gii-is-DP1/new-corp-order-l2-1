export default function Conglomerate({conglomerate, isRotated = false}){
    return(
        <>
            <img style={{transform: isRotated?"rotate(90deg)":"", maxHeight:"200px"}}  src={conglomerate.src} alt={"Image of Conglomerate of type " + conglomerate.name}/>
        </>
    );
}
