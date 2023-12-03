import {getCompanyImageSrc} from "./Game";
import {useState} from "react";

export function CompanyMatrix({companyTiles}) {
    const style = {display: "flex", flexWrap: "wrap", width: "50%"}

    return <div style={style}>
        {companyTiles.map((company) => <CompanyTile company={company}/>)}
    </div>;
}

function addAgent(company, type){

}

const conglomerate = {
    OMNICORP: 0,
    TOTAL_ENTERTAINMENT: 1,
    GENERIC_INC: 2,
    MEGAMEDIA: 3
}

export function CompanyTile({company}) {
    const [agents, setAgents] = useState(0);
    const [type, setType] = useState(null);

    const style = {
        flexBasis: "22.5%",
        flexShrink: 1,
        width: "25%",
        margin: "1%",
    }
    const companyStyle = {
        maxWidth:"100%",
    }
    return (
        <div style={style}>
            <img style = {companyStyle} src={getCompanyImageSrc(company)} alt={company.name + " of type " + company.type} />
        </div>
    );
}
