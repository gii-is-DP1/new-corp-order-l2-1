import {useState} from "react";
import Button, {buttonContexts, buttonStyles} from "../components/Button";
import {CompanyTile} from "./CompanyTile";

export default function CompanyTileSelector({
                                                title,
                                                companies,
                                                selectableCompanies,
                                                onConfirmSelection,
                                                needsDoubleSelect = false
                                            }) {
    const [selected, setSelected] = useState(-1);
    const [secondSelected, setSecondSelected] = useState(-1);
    const canConfirm = selected !== -1 && (!needsDoubleSelect || secondSelected !== -1);
    return <div style={{position: "absolute", width: "100%", height: "100%", background: "white", left: "0", top: "0"}}>
        <h2>{title}</h2>

        <div style={{display: "flex", flexWrap: "wrap", width: "65%"}}>
            {companies.map((company, i) =>
                <CompanyTile onClick={selectableCompanies.includes(i) ? () =>
                {
                    if(selected === i) setSelected(-1);
                    else if(secondSelected === i) setSecondSelected(-1);
                    else if(selected === -1) setSelected(i);
                    else if(needsDoubleSelect && secondSelected === -1) setSecondSelected(i);
                } : () => {}
                }
                     divStyle={{
                         cursor: "pointer",
                         borderColor: "black",
                         borderStyle: "solid",
                         borderWidth: (i === selected || i === secondSelected ? 8 : 0),
                         opacity: selectableCompanies.includes(i) ? 1 : 0.3,
                     }} company={company}/>
            )}
        </div>


        <Button onClick={() => onConfirmSelection(selected, secondSelected)}
                isDisabled={!canConfirm}
                buttonStyle={buttonStyles.primary}
                buttonContext={buttonContexts.light}>
            Confirm
        </Button>
    </div>
}
