import React from "react"
import Conglomerate from "./Conglomerate"

class Hand extends React.Component{
    render(){

        return(
            <>
                <p>ciao</p>
                 <Conglomerate state = {{type : "TOTAL_ENTERTAINMENT"}} />
                  <Conglomerate state = {{type : "GENERIC_INC"}} />
                   <Conglomerate state = {{type : "OMNIMEDIA"}} />
                    <Conglomerate state = {{type : "OMNICORP"}} />
            </>
        );
    }
}
export default Hand;
