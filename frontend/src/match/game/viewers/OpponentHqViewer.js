import {RotatableConglomerates} from "../../components/collections/RotatableConglomerates";
import {ConsultantMultiset} from "../../components/multisets/ConsultantMultiset";
import {ViewerContainer} from "./Viewer";
import React from "react";
import {ConglomerateMultiset} from "../../components/multisets/ConglomerateMultiset";

export function OpponentHqViewer({opponent}) {//TODO: remove in-line css
    const opponentNonrotatedHqConglomerates = new ConglomerateMultiset(opponent.hq.nonRotatedConglomerates);
    const opponentRotatedHqConglomerates = new ConglomerateMultiset(opponent.hq.rotatedConglomerates);
    const hqConglomerates = new RotatableConglomerates(opponentNonrotatedHqConglomerates.values,opponentRotatedHqConglomerates.values);
    const hqConsultants = new ConsultantMultiset(opponent.hq.consultants);

    return <ViewerContainer title={opponent.username + "'s HQ"}
                            containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>View {opponent.username}'s HQ</p>}
                            items={[...hqConglomerates.components, ...hqConsultants.components]}/>
}
