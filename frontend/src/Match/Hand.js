import React from "react"
import Conglomerate, {conglomerateType} from "./Conglomerate"

class Hand extends React.Component{
    conglomerates = {[conglomerateType.TOTAL_ENTERTAINMENT]: 1, [conglomerateType.GENERIC_INC]: 2, [conglomerateType.OMNICORP]: 3, [conglomerateType.MEGAMEDIA]: 4};


    render(){

        return(
            <div className="handcontainer">
                {this.showConglomerates(conglomerateType.OMNICORP)}
                {this.showConglomerates(conglomerateType.TOTAL_ENTERTAINMENT)}
                {this.showConglomerates(conglomerateType.GENERIC_INC)}
                {this.showConglomerates(conglomerateType.MEGAMEDIA)}
            </div>
        );
    };

    showConglomerates(type){
        return [...Array(this.conglomerates[type])].map(()=>(<Conglomerate state = {{type : type}}/>))
    }
}


export default Hand;
