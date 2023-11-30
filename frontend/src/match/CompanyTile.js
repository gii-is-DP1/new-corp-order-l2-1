import {getCompanyImageSrc} from "./Game";

export function CompanyTile({company, divStyle, onClick}) {
    const quantity = company.agents;
    const color = company.type.color;
    const alt = company.company + " of type " + company.type.name;

    return (
        <div
            style={{
                flexBasis: "22.5%",
                flexShrink: 1,
                width: "25%",
                margin: "1%",
                position: "relative",
                top: 0,
                left: 0,
                ...divStyle
            }}
            onClick={onClick}
        >
            <img style={{
                maxWidth: "100%",
                margin: "auto"
            }} src={getCompanyImageSrc(company.company)} alt={alt}/>
            <AgentsIndicator color={color} quantity={quantity}/>
        </div>
    );
}

function AgentsIndicator({color, quantity}) {
    return <div style={{
        position: "absolute",
        bottom: "5px",
        right: "5px",
        textAlign: "center",
        transform: "rotate(0deg)", backgroundColor: color, width: "30%",
        height: "30%",
        borderColor: "#f2f2f0",
        borderWidth: "0",
        borderRadius: "3px",
        border: "solid"
    }}>
                  <span style={{
                      width: "100%",
                      height: "100%",
                      color: "white",
                      textShadow: " -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000, 1px 1px 0 #000",
                  }}>
                      {quantity}
                  </span>
    </div>
}
