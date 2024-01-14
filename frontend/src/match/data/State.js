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
        this.nonrotatedHqConglomerates = new ConglomerateMultiset(state.game.player.hq.nonRotatedConglomerates);
        this.rotatedHqConglomerates = new ConglomerateMultiset(state.game.player.hq.rotatedConglomerates);
        this.hqConglomerates = new RotatableConglomerates(this.nonrotatedHqConglomerates.values,this.rotatedHqConglomerates.values);
        this.generalSupplyConsultants = new ConsultantMultiset(state.game.generalSupply.consultants);
        this.secretObjectives = new SecretObjectives(state.game.player.hq.secretObjectives);
        this.openDisplay = new ConglomerateMultiset(state.game.generalSupply.openDisplay);
        this.hand = new ConglomerateMultiset(state.game.player.hand);
        this.playerConsultants = new ConsultantMultiset(state.game.player.hq.consultants);
        this.companyTiles = new CompanyTiles(state.game.companies);
        this.frontendView = getCurrentFrontendView(state);
        this.opponents = new Opponents(state.game.opponents);
        this.update = () => {setGameState({...state})}

        console.log(this);
    }
}

