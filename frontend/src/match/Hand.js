import React from "react"
import Conglomerate, {conglomerateType} from "./Conglomerate"
import CardContainer from "./CardContainer";

class Hand extends React.Component{
    conglomerates = {
        [conglomerateType.TOTAL_ENTERTAINMENT]: 1,
        [conglomerateType.GENERIC_INC]: 2,
        [conglomerateType.OMNICORP]: 3,
        [conglomerateType.MEGA_MEDIA]: 4
    };


    render(){

        return(
            <div className="handcontainer">
                <CardContainer
                    cards = {[
                        ...this.showConglomerates(conglomerateType.OMNICORP),
                        ...this.showConglomerates(conglomerateType.TOTAL_ENTERTAINMENT),
                        ...this.showConglomerates(conglomerateType.GENERIC_INC),
                        ...this.showConglomerates(conglomerateType.MEGA_MEDIA)
                    ]}
                />
            </div>
        );
    };

    showConglomerates(type){
        return [...Array(this.conglomerates[type])].map(()=>(<Conglomerate state = {{type : type}}/>))
    }
}


export default Hand;
