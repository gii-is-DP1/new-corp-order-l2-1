import {RotatableConglomerates} from "../../components/collections/RotatableConglomerates";
import {ConsultantMultiset} from "../../components/multisets/ConsultantMultiset";
import {ViewerContainer} from "./Viewer";
import React from "react";

export function OpponentHqViewer({opponent}) {
    const hqConglomerates = new RotatableConglomerates(opponent.hq.nonRotatedConglomerates, opponent.hq.rotatedConglomerates);
    const hqConsultants = new ConsultantMultiset(opponent.hq.consultants);

    return <ViewerContainer title={opponent.username + "'s HQ"}
                            containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>View {opponent.username}'s HQ</p>}
                            items={[...hqConglomerates.components, ...hqConsultants.components]}/>
}
