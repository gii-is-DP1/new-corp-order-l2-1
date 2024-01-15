export class FrontendState{
    component = "Unimplemented State Component";
    nextState;
    constructor(gameState, frontendState) {
        this.nextState = this.getNextState(gameState, frontendState) ?? null
    }
    getNextState(gameState, frontendState){
       throw "Unimplemented exception"
    }
}
