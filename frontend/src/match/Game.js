import {CompanyMatrix} from "./CompanyMatrix";
import {Company, CompanyType, conglomerate, consultant, secretObjective} from "./MatchEnums";
import CardSelectView from "./CardSelectView";
import Conglomerate, {conglomerateType} from "./Conglomerate";
import React from "react";
import Selector from "./Selector/Selector";
import {onlySelectOfSameColor} from "./Selector/ChangeSelectableItemsFunctions";
import {selectAtLeastOne} from "./Selector/OnConfirmFunctions";
import Viewer, {ViewerContainer} from "./Viewer";

export function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
}

/*
export function getCompany(company){
    return [...Array(this.conglomerates[conglomerate.OMNICORP])].map(()=>(<C state = {{type : type}}/>))
}*/

export function Game() {
    const companyTiles = [
        Company.GLOBAL_CORP,
        Company.WORDOFMOUTH,
        Company.SLIMGROTZ_INC,
        Company.VISUAL_TERROR_INC,
        Company.SUBLIMINAL_SUBSIDIARY,
        Company.GENERIC_SUB_INC,
        Company.ALL_AROUND_YOU,
        Company.HOLOGRAFX,
        Company.ANONYMOUS_CROWD,
        Company.PAGE_ONE_CORP,
        Company.READALOT,
        Company.LEADERBOARDER,
        Company.FLICKERING_LIGHTS,
        Company.WALLPAPER,
        Company.XCURBR,
        Company.PIX_CHIX,
    ];

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
    const openDisplay = gameState.generalSupply.openDisplay.map((item) => <Conglomerate conglomerate={item}/> )

    const deck = <img height={"200px"} src={"/images/card-back.jpg"} />

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

    return <>
        <p>GAME IN PROGRESS</p>

        <ViewerContainer title="Your Hand" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View Hand</p>}
                         items={hand}/>
        <ViewerContainer title="Your HQ" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View Hq</p>}
                         items={[...hqConglomerates, ...hqConsultants, ...secretObjectives]}/>

        <ViewerContainer title="General Supply" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View General Supply</p>}
                         items={[...openDisplay,deck,...generalSupplyConsultants]}/>

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
                                 items={[...opponentHqConglomerates,...opponentHqConsultants]}/>

            </>
        })}
    </>;
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
    companies: [{company: Company.READALOT, agents: 1, type: conglomerate.OMNICORP}],
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
