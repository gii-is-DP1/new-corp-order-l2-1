import {ConsultantMultiset} from "../components/multisets/ConsultantMultiset";
import {getCurrentFrontendView} from "../game/gameLogic";
import {ConglomerateMultiset} from "../components/multisets/ConglomerateMultiset";
import {SecretObjectives} from "../components/collections/SecretObjectives";
import {Conglomerates} from "../components/collections/Conglomerates";
import {CompanyTiles} from "../components/collections/CompanyTiles";
import {RotatableConglomerates} from "../components/collections/RotatableConglomerates";
import {Opponents} from "../components/collections/Opponents";

export class State {
    constructor(state, setGameState) {
        this.state = state;
        this.hqConsultants = new ConsultantMultiset(state.game.player.hq.consultants);
        this.hqConglomerates = new RotatableConglomerates(state.game.player.hq.nonRotatedConglomerates, state.game.player.hq.rotatedConglomerates);
        this.generalSupplyConsultants = new ConsultantMultiset(state.game.generalSupply.consultants);
        this.secretObjectives = new SecretObjectives(state.game.player.hq.secretObjectives);
        this.openDisplay = new Conglomerates(state.game.generalSupply.openDisplay);
        this.hand = new ConglomerateMultiset(state.game.player.hand);
        this.playerConsultants = new ConsultantMultiset(state.game.player.hq.consultants);
        this.companyTiles = new CompanyTiles(state.game.companies);
        this.frontendView = getCurrentFrontendView(state);
        this.opponents = new Opponents(state.game.opponents);
        this.update = () => {setGameState({...state})}
    }
}

