import {RotatableConglomerates} from "../../components/collections/RotatableConglomerates";
import {ConsultantMultiset} from "../../components/multisets/ConsultantMultiset";
import {ViewerContainer} from "./Viewer";
import React from "react";

export function OpponentConglomerateViewer({opponent}) {//TODO: remove in-line css
    const hqConglomerates = new RotatableConglomerates(opponent.hq.nonRotatedConglomerates, opponent.hq.rotatedConglomerates);

    return <ViewerContainer title={opponent.username + "'s Conglomerates"}
                            containerStyle={{display: "flex", flexWrap: "wrap"}}
                            buttonContent={<p>View {opponent.username}'s </p>}
                            items={[...hqConglomerates.components]}/>
}
