function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
}

export function CompanyTile({company}) {
    const quantity = company.agents;
    const color = company.type.color;
    const alt = company.company + " of type " + company.type.name;

    return (
        <div
            style={{
                margin: "1%",
                position: "relative",
                maxWidth: "100%",
                flexShrink: 1
            }}
        >
            <img style={{
                maxWidth: "100%",
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
