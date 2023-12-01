import {CompanyTile} from "./CompanyTile";

export function CompanyMatrix({companyTiles}) {
    const style = {display: "flex", flexWrap: "wrap", width: "50%"}
    return <div style={style}>
        {companyTiles.map((company) => <CompanyTile company={company}/>)}
    </div>;
}

