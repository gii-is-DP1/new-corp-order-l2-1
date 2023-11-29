import {getCompanyImageSrc} from "./Game";
import {useState} from "react";

export function CompanyMatrix({companyTiles}) {
    const style = {display: "flex", flexWrap: "wrap", width: "50%"}
    const sampleCompany = {agents: 1, type: conglomerate.GENERIC_INC};
    const [companies, setCompanies] = useState([
        {agents: 1, type: conglomerate.OMNICORP},
        {agents: 2, type: conglomerate.TOTAL_ENTERTAINMENT},
        {agents: 4, type: conglomerate.MEGAMEDIA},
        {agents: 3, type: conglomerate.GENERIC_INC},
        {agents: 1, type: conglomerate.TOTAL_ENTERTAINMENT},
        {agents: 2, type: conglomerate.MEGAMEDIA},
        {agents: 4, type: conglomerate.TOTAL_ENTERTAINMENT},
        {agents: 2, type: conglomerate.GENERIC_INC},
        {agents: 3, type: conglomerate.MEGAMEDIA},
        {agents: 1, type: conglomerate.TOTAL_ENTERTAINMENT},
        {agents: 4, type: conglomerate.GENERIC_INC},
        {agents: 3, type: conglomerate.OMNICORP},
        {agents: 2, type: conglomerate.MEGAMEDIA},
        {agents: 2, type: conglomerate.GENERIC_INC},
        {agents: 4, type: conglomerate.TOTAL_ENTERTAINMENT},
        {agents: 1, type: conglomerate.OMNICORP},
    ]);

    return <div style={style}>
        {companyTiles.map((company, i) => <CompanyTile company={company} state={companies[i]}/>)}
    </div>;
}

const conglomerate = { //TODO make color a gradient
    OMNICORP: {name: "Omnicorp", color: "#c6c2a9"},
    TOTAL_ENTERTAINMENT: {name: "Total Entertainment", color: "#258b9f"},
    GENERIC_INC: {name: "Generic Inc", color: "#3e2f21"},
    MEGAMEDIA: {name: "Megamedia", color: "#f74c1e"},
}

export function CompanyTile({company, state}) {
    const agents = state.agents;
    const agentsType = state.type;
    const color = agentsType.color;

    const style = {
        flexBasis: "22.5%",
        flexShrink: 1,
        width: "25%",
        margin: "1%",
    }
    const companyStyle = {
        maxWidth: "100%",
    }
    const alt = company.name + " of type " + company.type;

    return (
        <div style={{
            ...style,
            position: "relative",
            top: 0,
            left: 0,
        }}>
            <img style={{
                ...companyStyle, position: "relative",
                top: 0,
                left: 0
            }} src={getCompanyImageSrc(company)} alt={alt}/>
            <div style={{
                position: "absolute",
                bottom: "5px",
                right: "5px",
                textAlign: "center",
                transform: "rotate(0deg)", backgroundColor: color, width: "30%",
                height: "30%",
                borderColor: "#f2f2f0",
                borderWidth:"0",
                borderRadius:"3px",
                border: "solid"
            }}>
                  <span style={{
                      width: "100%",
                      height: "100%",
                      color:"white",
                      textShadow:" -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000",
                  }}>{agents}
                  </span>
            </div>
        </div>
    );
}
