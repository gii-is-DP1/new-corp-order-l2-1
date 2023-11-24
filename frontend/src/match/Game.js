const CompanyType = {
    BROADCAST_NETWORK: 0,
    PRINT_MEDIA: 1,
    GUERRILLA_MARKETING: 2,
    AMBIENT_ADVERTISING: 3,
    ONLINE_MARKETING: 4,
    SOCIAL_MEDIA: 5
}

const CompanyTile = {
    SLIMGROTZ_INC: {type: CompanyType.BROADCAST_NETWORK, src: "/Images/Companies/BroadcastNetwork/slimgrotz-inc.png"},
    FLICKERING_LIGHTS: {
        type: CompanyType.BROADCAST_NETWORK,
        src: "/Images/Companies/BroadcastNetwork/flickering-lights.png"
    },
    GLOBAL_CORP: {type: CompanyType.BROADCAST_NETWORK, src: "/Images/Companies/BroadcastNetwork/global-corp.png"},
    PAGE_ONE_CORP: {type: CompanyType.PRINT_MEDIA, src: "/Images/Companies/PrintMedia/page-one-corp.png"},
    READALOT: {type: CompanyType.PRINT_MEDIA, src: "/Images/Companies/PrintMedia/readalot.png"},
    GENERTIC_SUB_INC: {type: CompanyType.PRINT_MEDIA, src: "/Images/Companies/PrintMedia/generic-sub-inc.png"},
    SUMLIMINAL_SUBSIDIARY: {
        type: CompanyType.GUERRILLA_MARKETING,
        src: "/Images/Companies/GuerrillaMarketing/subliminal-subsidiary.png"
    },
    VISUAL_TERROR_INC: {
        type: CompanyType.GUERRILLA_MARKETING,
        src: "/Images/Companies/GuerrillaMarketing/visual-terror-inc.png"
    },
    WORDOFMOUTH: {type: CompanyType.GUERRILLA_MARKETING, src: "/Images/Companies/GuerrillaMarketing/wordofmouth.png"},
    WALLPAPER: {type: CompanyType.AMBIENT_ADVERTISING, src: "/Images/Companies/AmbientAdvertising/wallpaper.png"},
    HOLOGRAFX: {type: CompanyType.AMBIENT_ADVERTISING, src: "/Images/Companies/AmbientAdvertising/holografx.png"},
    ALL_AROUND_YOU: {
        type: CompanyType.AMBIENT_ADVERTISING,
        src: "/Images/Companies/AmbientAdvertising/all-around-you.png"
    },
    PIX_CHIX: {type: CompanyType.ONLINE_MARKETING, src: "/Images/Companies/OnlineMarketing/pix_chix.png"},
    PRETTY_OBSCURE_INC: {
        type: CompanyType.ONLINE_MARKETING,
        src: "/Images/Companies/OnlineMarketing/pretty-obscure-inc.png"
    },
    ANONYMOUS_CROWD: {type: CompanyType.SOCIAL_MEDIA, src: "/Images/Companies/SocialMedia/anonymous-crowd.png"},
    LEADERBOARDER: {type: CompanyType.SOCIAL_MEDIA, src: "/Images/Companies/SocialMedia/leaderboarder.png"},
    XCURBR: {type: CompanyType.SOCIAL_MEDIA, src: "/Images/Companies/SocialMedia/xcurbr.png"},
}

function CompanyMatrix({companyTiles}) {
    return <div style={{display: "flex", flexWrap: "wrap"}}>
        {companyTiles.map((company) => <img height={"200px"} src={company.src}/>)}
    </div>;
}

export function Game() {
    return <>
        <p>GAME IN PROGRESS</p>
        <CompanyMatrix companyTiles={Object.values(CompanyTile)}/>
    </>;
}
