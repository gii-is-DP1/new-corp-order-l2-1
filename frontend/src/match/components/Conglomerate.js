import css from "./components.module.css"

export default function Conglomerate({conglomerate, style}){
    return(
        <img className={css.conglomerate} src={conglomerate.src} alt={"Image of Conglomerate of type " + conglomerate.name}/>
    );
}
