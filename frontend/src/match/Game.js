import {CompanyMatrix} from "./CompanyMatrix";
import {Company, CompanyType, conglomerate, consultant, secretObjective} from "./MatchEnums";
import Conglomerate from "./Conglomerate";
import React, {useState} from "react";
import Selector from "./Selector/Selector";
import {onlySelectOfSameColor, selectQuantity} from "./Selector/ChangeSelectableItemsFunctions";
import {selectAtLeastOne} from "./Selector/OnConfirmFunctions";
import {ViewerContainer} from "./Viewer";
import CompanyTileSelector from "./CompanyTileSelector";
import Button from "../components/Button";

export function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
}

export function Game() {
    const selection = [
        <Conglomerate conglomerate={conglomerate.OMNICORP}/>,
        <Conglomerate conglomerate={conglomerate.OMNICORP}/>,
        <Conglomerate conglomerate={conglomerate.GENERIC_INC}/>,
        <Conglomerate conglomerate={conglomerate.TOTAL_ENTERTAINMENT}/>,
        <Conglomerate conglomerate={conglomerate.MEGAMEDIA}/>,
        <Conglomerate conglomerate={conglomerate.MEGAMEDIA}/>,
    ];
    const conglomerateContainerStyle = {
        display: "flex",
    }

    const currentState = "Infiltrate";
    let selector;
    switch (currentState) {
        case "Infiltrate":
            selector = <Selector
                title={"Infiltrate"}
                subtitle={"Select n cards of the same color"}
                selection={selection}
                selectableElements={[...Array(selection.length).keys()]}
                canConfirm={selectAtLeastOne}
                changeSelectableItems={onlySelectOfSameColor}
                onConfirm={(selectedElements) => {
                    alert(selectedElements)
                }}
                containerStyle={conglomerateContainerStyle}
                itemStyle={{}}
            />
    }

    const hand = [...[...Array(gameState.player.hand.GENERIC_INC)].map(() => <Conglomerate
        conglomerate={conglomerate.GENERIC_INC}/>),
        ...[...Array(gameState.player.hand.MEGAMEDIA)].map(() => <Conglomerate conglomerate={conglomerate.MEGAMEDIA}/>),
        ...[...Array(gameState.player.hand.OMNICORP)].map(() => <Conglomerate conglomerate={conglomerate.OMNICORP}/>),
        ...[...Array(gameState.player.hand.TOTAL_ENTERTAINMENT)].map(() => <Conglomerate
            conglomerate={conglomerate.TOTAL_ENTERTAINMENT}/>)
    ];

    const hqConsultants = [
        ...[...Array(gameState.player.hq.consultants.DEALMAKER)].map(() => <img height={"200px"}
                                                                                src={consultant.DEALMAKER}/>),
        ...[...Array(gameState.player.hq.consultants.CORPORATE_LAWYER)].map(() => <img height={"200px"}
                                                                                       src={consultant.CORPORATE_LAWYER}/>),
        ...[...Array(gameState.player.hq.consultants.MEDIA_ADVISOR)].map(() => <img height={"200px"}
                                                                                    src={consultant.MEDIA_ADVISOR}/>),
        ...[...Array(gameState.player.hq.consultants.MILITARY_CONTRACTOR)].map(() => <img height={"200px"}
                                                                                          src={consultant.MILITARY_CONTRACTOR}/>),
    ];
    const hqConglomerates = gameState.player.hq.conglomerates.map((item) => <Conglomerate conglomerate={item.type}
                                                                                          isRotated={item.isRotated}/>);
    const secretObjectives = gameState.player.hq.secretObjectives.map((item) => <img height={"200px"} src={item}
                                                                                     alt={item}/>)
    const openDisplay = gameState.generalSupply.openDisplay.map((item) => <Conglomerate conglomerate={item}/>)

    const deck = <img height={"200px"} src={"/images/card-back.jpg"}/>

    const generalSupplyConsultants = [
        ...[...Array(gameState.generalSupply.consultants.DEALMAKER)].map(() => <img height={"200px"}
                                                                                    src={consultant.DEALMAKER}/>),
        ...[...Array(gameState.generalSupply.consultants.CORPORATE_LAWYER)].map(() => <img height={"200px"}
                                                                                           src={consultant.CORPORATE_LAWYER}/>),
        ...[...Array(gameState.generalSupply.consultants.MEDIA_ADVISOR)].map(() => <img height={"200px"}
                                                                                        src={consultant.MEDIA_ADVISOR}/>),
        ...[...Array(gameState.generalSupply.consultants.MILITARY_CONTRACTOR)].map(() => <img height={"200px"}
                                                                                              src={consultant.MILITARY_CONTRACTOR}/>),
    ];
    const [selectedCompany, setSelectedCompany] = useState(1);

    const PLOT = "plot";
    const INFILTRATE = "infiltrate";
    const TAKEOVER = "takeover";

    const [state, setState] = useState({
        action: null,
        plot: {
            firstConglomerate: null,
            secondConglomerate: null,
        },
        infiltrate: {
            conglomerates: null,
            companyTile: null,
            consultant: null,
            mediaAdvisor: {
                conglomerate: null,
            },
            corporateLawyer: {
                conglomerates: null,
                company: null,
            },
            takeConsultant: null
        },
        takeover: {
            consultant: null,
            conglomerates: null,
            companyTiles: null,
            ability: {
                choice: null,
                broadcastNetwork: {
                    companies: null,
                },
                guerrillaMarketing: {
                    conglomerates: null,
                },
                printMedia: {
                    yourConglomerate: null,
                    otherHq: null,
                    otherConglomerate: null,
                },
                ambientAdvertising: {
                    conglomerates: null,
                },
                socialMedia: {
                    hq: null,
                    conglomerate: null,
                },
                onlineMarketing: {
                    companies: null,
                }
            },
            dealmaker: {
                conglomerates: null,
            }
        }
    })
    const setMoveState = (action) => {
        state.action = action;
        setState({...state});
    }
    const openDisplayAndDeck = [...openDisplay, deck];

    const frontendState = {
        plot: {
            DRAW_FIRST_CONGLOMERATE: 0,
            DRAW_SECOND_CONGLOMERATE: 1,
        },
        infiltrate: {
            PICK_CONGLOMERATES: 2,
            PICK_CONSULTANT: 3,
            MEDIA_ADVISOR_PICK_CONGLOMERATE: 4,
            CORPORATE_LAWYER_PICK_CONGLOMERATES: 5,
            CORPORATE_LAWYER_PICK_COMPANY: 6,
            PICK_COMPANY: 7,
            TAKE_CONSULTANT: 8,
        },
        takeover: {
            PICK_CONSULTANT: 9,
            PICK_CONGLOMERATES: 10,
            PICK_TWO_COMPANY_TILES: 11,
            ACTIVATE_ABILITY: 12,
            DEALMAKER_DRAW_TWO_CARDS_FROM_DECK: 12,
        },
        BROADCAST_NETWORK_PICK_COMPANIES: 13,
        GUERRILLA_MARKETING_PICK_CONGLOMERATES: 14,
        PRINT_MEDIA_PICK_YOUR_CONGLOMERATE: 15,
        PRINT_MEDIA_PICK_OTHER_CONGLOMERATE: 16,
        AMBIENT_ADVERTISING_PICK_CONGLOMERATES: 17,
        SOCIAL_MEDIA_PICK_CONGLOMERATE: 18,
        ONLINE_MARKETING_PICK_COMPANIES: 19,
        DISCARD: 20,
        DONE: 21,
    };

    const [canActivateCompanyAbility, setCanActivateCompanyAbility] = useState(false);

    const currentAction = () => {
        if (state.action === PLOT) {
            if (state.plot.firstConglomerate === null)
                return frontendState.plot.DRAW_FIRST_CONGLOMERATE;
            if (state.plot.secondConglomerate === null)
                return frontendState.plot.DRAW_SECOND_CONGLOMERATE;
        } else if (state.action === INFILTRATE) {
            if (state.infiltrate.conglomerates === null)
                return frontendState.infiltrate.PICK_CONGLOMERATES;
            if (state.infiltrate.companyTile === null)
                return frontendState.infiltrate.PICK_COMPANY;
            if (state.infiltrate.consultant === null)
                return frontendState.infiltrate.PICK_CONSULTANT
            if (state.infiltrate.consultant === consultant.MEDIA_ADVISOR)
                return frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE;
            if (state.infiltrate.consultant === consultant.CORPORATE_LAWYER) {
                if (state.infiltrate.corporateLawyer.conglomerates === null)
                    return frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES;
                if (state.infiltrate.corporateLawyer.company === null)
                    return frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY;
            }
        } else if (state.action === TAKEOVER) {
            if (state.takeover.consultant === null)
                return frontendState.takeover.PICK_CONSULTANT;
            if (state.takeover.conglomerates === null)
                return frontendState.takeover.PICK_CONGLOMERATES;
            if (state.takeover.companyTiles === null)
                return frontendState.takeover.PICK_TWO_COMPANY_TILES;
            if (state.takeover.consultant === consultant.DEALMAKER)
                return frontendState.takeover.DEALMAKER_DRAW_TWO_CARDS_FROM_DECK;
            if (state.takeover.ability.choice === null && canActivateCompanyAbility)
                return frontendState.takeover.ACTIVATE_ABILITY;
            if (state.takeover.ability.choice === CompanyType.BROADCAST_NETWORK && state.takeover.ability.broadcastNetwork.companies === null)
                return frontendState.BROADCAST_NETWORK_PICK_COMPANIES;
            if (state.takeover.ability.choice === CompanyType.AMBIENT_ADVERTISING && state.takeover.ability.ambientAdvertising.conglomerates === null)
                return frontendState.AMBIENT_ADVERTISING_PICK_CONGLOMERATES;
            if (state.takeover.ability.choice === CompanyType.SOCIAL_MEDIA && state.takeover.ability.socialMedia.conglomerate === null)
                return frontendState.SOCIAL_MEDIA_PICK_CONGLOMERATE;
            if (state.takeover.ability.choice === CompanyType.PRINT_MEDIA && state.takeover.ability.printMedia.yourConglomerate === null)
                return frontendState.PRINT_MEDIA_PICK_YOUR_CONGLOMERATE;
            if (state.takeover.ability.choice === CompanyType.PRINT_MEDIA && state.takeover.ability.printMedia.otherConglomerate === null)
                return frontendState.PRINT_MEDIA_PICK_OTHER_CONGLOMERATE;
            if (state.takeover.ability.choice === CompanyType.GUERRILLA_MARKETING && state.takeover.ability.guerrillaMarketing.conglomerates === null)
                return frontendState.GUERRILLA_MARKETING_PICK_CONGLOMERATES;
            if (state.takeover.ability.choice === CompanyType.ONLINE_MARKETING && state.takeover.ability.onlineMarketing.companies === null)
                return frontendState.ONLINE_MARKETING_PICK_COMPANIES;
        }
        if (gameState.player.hand.OMNICORP +
            gameState.player.hand.MEGAMEDIA +
            gameState.player.hand.TOTAL_ENTERTAINMENT +
            gameState.player.hand.GENERIC_INC
            > 6
        ) //TODO hand count
            return frontendState.DISCARD;
        return frontendState.DONE;
    };

    return <div style={{display: "flex"}}>
        <p>Current action: {state.action}</p>
        {state.action == null
            ? <div>
                <h1>Pick an action</h1>
                <Button onClick={() => setMoveState(PLOT)}>PLOT</Button>
                <Button onClick={() => setMoveState(INFILTRATE)}>INFILTRATE</Button>
                <Button onClick={() => setMoveState(TAKEOVER)}>TAKEOVER</Button>
            </div>
            : <></>
        }
        {state.action === PLOT && state.plot.firstConglomerate === null
            ? <div>
                <Selector title={"Select a card"} selection={openDisplayAndDeck}
                          canConfirm={selectAtLeastOne}
                          changeSelectableItems={selectQuantity(1)}
                          onConfirm={(selected) => {
                              state.plot.firstConglomerate = openDisplayAndDeck[selected[0]];
                              //gameState.generalSupply.openDisplay //TODO: remove card from general supply
                              setState({...state});
                          }}
                          containerStyle={conglomerateContainerStyle}
                />
            </div>
            : <></>
        }
        {state.action === PLOT && state.plot.firstConglomerate !== null && state.plot.secondConglomerate === null //TODO: remover copy-pega
            ? <div>
                <Selector title={"Select another card"} selection={openDisplayAndDeck}
                          canConfirm={selectAtLeastOne}
                          changeSelectableItems={selectQuantity(1)}
                          onConfirm={(selected) => { //TODO: remove card from general supply
                              state.plot.secondConglomerate = openDisplayAndDeck[selected[0]];
                              setState({...state});
                          }}
                          containerStyle={conglomerateContainerStyle}
                />
            </div>
            : <></>
        }


        <ViewerContainer title="Your Hand" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View Hand</p>}
                         items={hand}/>
        <ViewerContainer title="Your HQ" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View Hq</p>}
                         items={[...hqConglomerates, ...hqConsultants, ...secretObjectives]}/>

        <ViewerContainer title="General Supply" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View General Supply</p>}
                         items={[...openDisplay, deck, ...generalSupplyConsultants]}/>

        {gameState.opponents.map((opponent) => {
            const opponentHqConglomerates = opponent.hq.conglomerates.map((item) => <Conglomerate
                conglomerate={item.type} isRotated={item.isRotated}/>);
            const opponentHqConsultants = [
                ...[...Array(opponent.hq.consultants.DEALMAKER)].map(() => <img height={"200px"}
                                                                                src={consultant.DEALMAKER}/>),
                ...[...Array(opponent.hq.consultants.CORPORATE_LAWYER)].map(() => <img height={"200px"}
                                                                                       src={consultant.CORPORATE_LAWYER}/>),
                ...[...Array(opponent.hq.consultants.MEDIA_ADVISOR)].map(() => <img height={"200px"}
                                                                                    src={consultant.MEDIA_ADVISOR}/>),
                ...[...Array(opponent.hq.consultants.MILITARY_CONTRACTOR)].map(() => <img height={"200px"}
                                                                                          src={consultant.MILITARY_CONTRACTOR}/>),
            ];

            return <>
                <ViewerContainer title={opponent.username + "'s HQ"}
                                 containerStyle={{display: "flex", flexWrap: "wrap"}}
                                 buttonContent={<p>View {opponent.username}'s HQ</p>}
                                 items={[...opponentHqConglomerates, ...opponentHqConsultants]}/>

            </>
        })}
    </div>;

    /*
    *
        <div>
            <CompanyMatrix companyTiles={gameState.companies}/>
            {selectedCompany === -1
                ? <CompanyTileSelector companies={gameState.companies}
                                       selectableCompanies={[...Array(16).keys()]}
                                       title="Select a company tile"
                                       onConfirmSelection={(i) => setSelectedCompany(i)}
                                       needsDoubleSelect={true}
                />
                : <p> You selected {gameState.companies[selectedCompany].company.name}</p>
            }
        </div>
    * */
}

const hqConglomerate = {
    type: conglomerate.OMNICORP,
    isRotated: true,
};

const playerConsultants = {
    MEDIA_ADVISOR: 1,
    DEALMAKER: 0,
    CORPORATE_LAWYER: 2,
    MILITARY_CONTRACTOR: 0,
};

const opponent = {
    username: "Beluga",
    conglomeratesInHand: 4,
    hq: {
        conglomerates: [
            hqConglomerate,
            hqConglomerate,
        ],
        consultants: playerConsultants
    }
}

const handConglomerates = {
    OMNICORP: 1,
    MEGAMEDIA: 2,
    TOTAL_ENTERTAINMENT: 3,
    GENERIC_INC: 0
};

const gameState = {
    companies: [
        {company: Company.WORDOFMOUTH, agents: 1, type: conglomerate.OMNICORP},
        {company: Company.SLIMGROTZ_INC, agents: 2, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.VISUAL_TERROR_INC, agents: 4, type: conglomerate.MEGAMEDIA},
        {company: Company.PIX_CHIX, agents: 3, type: conglomerate.GENERIC_INC},
        {company: Company.XCURBR, agents: 1, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.WALLPAPER, agents: 2, type: conglomerate.MEGAMEDIA},
        {company: Company.FLICKERING_LIGHTS, agents: 4, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.LEADERBOARDER, agents: 2, type: conglomerate.GENERIC_INC},
        {company: Company.READALOT, agents: 3, type: conglomerate.MEGAMEDIA},
        {company: Company.PAGE_ONE_CORP, agents: 1, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.ANONYMOUS_CROWD, agents: 4, type: conglomerate.GENERIC_INC},
        {company: Company.HOLOGRAFX, agents: 3, type: conglomerate.OMNICORP},
        {company: Company.ALL_AROUND_YOU, agents: 2, type: conglomerate.MEGAMEDIA},
        {company: Company.GENERIC_SUB_INC, agents: 2, type: conglomerate.GENERIC_INC},
        {company: Company.SUBLIMINAL_SUBSIDIARY, agents: 4, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.GLOBAL_CORP, agents: 1, type: conglomerate.OMNICORP},
    ],
    player: {
        hand: handConglomerates,
        hq: {
            secretObjectives: [
                secretObjective.AMBIENT_ADVERTISING,
                secretObjective.BROADCAST_NETWORK,
            ],
            conglomerates: [
                hqConglomerate,
                hqConglomerate,
            ],
            consultants: playerConsultants
        }
    },
    generalSupply: {
        conglomeratesLeftInDeck: 30,
        openDisplay: [
            conglomerate.GENERIC_INC,
            conglomerate.MEGAMEDIA,
            conglomerate.GENERIC_INC,
            conglomerate.TOTAL_ENTERTAINMENT,
        ],
        consultants: {
            MEDIA_ADVISOR: 2,
            DEALMAKER: 3,
            CORPORATE_LAWYER: 1,
            MILITARY_CONTRACTOR: 6,
        },
    },
    opponents: [
        opponent,
        opponent,
        opponent
    ],
}
