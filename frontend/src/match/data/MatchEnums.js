import {frontendState} from "../game/gameLogic";

export const PLOT = "plot";
export const INFILTRATE = "infiltrate";
export const TAKEOVER = "takeover";

export const conglomerate = { //TODO make color a gradient
    OMNICORP: {name: "Omnicorp", color: "#c6c2a9", src: "/Images/Conglomerates/omnicorp.png"},
    TOTAL_ENTERTAINMENT: {
        name: "Total Entertainment",
        color: "#258b9f",
        src: "/Images/Conglomerates/totalentertainment.png"
    },
    GENERIC_INC: {name: "Generic Inc", color: "#3e2f21", src: "/Images/Conglomerates/genericinc.png"},
    MEGAMEDIA: {name: "Megamedia", color: "#f74c1e", src: "/Images/Conglomerates/megamedia.png"},
}

export function getConglomerateName(value)
{
    return Object.keys(conglomerate).find(key => conglomerate[key] === value);
}

export const propics = [...Array(21).keys()].map(n =>   "/Images/propics/("+(n+1)+").jpg");

export const CompanyType = {
    BROADCAST_NETWORK: "broadcast-network",
    PRINT_MEDIA: "print-media",
    GUERRILLA_MARKETING: "guerrilla-marketing",
    AMBIENT_ADVERTISING: "ambient-advertising",
    ONLINE_MARKETING: "online-marketing",
    SOCIAL_MEDIA: "social-media"
}

export const consultant = {
    MEDIA_ADVISOR: "/images/consultants/media-advisor.png",
    DEALMAKER: "/images/consultants/dealmaker.png",
    CORPORATE_LAWYER: "/images/consultants/corporate-lawyer.png",
    MILITARY_CONTRACTOR: "/images/consultants/military-contractor.png",
};

export const secretObjective = {
    BROADCAST_NETWORK: "/Images/secret-objectives/broadcast-network.png",
    PRINT_MEDIA: "/Images/secret-objectives/print-media.png",
    GUERRILLA_MARKETING: "/Images/secret-objectives/guerrilla-marketing.png",
    AMBIENT_ADVERTISING: "/Images/secret-objectives/ambient-advertising.png",
    ONLINE_MARKETING: "/Images/secret-objectives/online-marketing.png",
    SOCIAL_MEDIA: "/Images/secret-objectives/social-media.png",
}

export const Company = {
    SLIMGROTZ_INC: {name: "slimgrotz-inc", type: CompanyType.BROADCAST_NETWORK},
    FLICKERING_LIGHTS: {name: "flickering-lights", type: CompanyType.BROADCAST_NETWORK},
    GLOBAL_CORP: {name: "global-corp", type: CompanyType.BROADCAST_NETWORK},

    PAGE_ONE_CORP: {name: "page-one-corp", type: CompanyType.PRINT_MEDIA},
    READALOT: {name: "readalot", type: CompanyType.PRINT_MEDIA},
    GENERIC_SUB_INC: {name: "generic-sub-inc", type: CompanyType.PRINT_MEDIA},

    SUBLIMINAL_SUBSIDIARY: {name: "subliminal-subsidiary", type: CompanyType.GUERRILLA_MARKETING},
    VISUAL_TERROR_INC: {name: "visual-terror-inc", type: CompanyType.GUERRILLA_MARKETING},
    WORDOFMOUTH: {name: "wordofmouth", type: CompanyType.GUERRILLA_MARKETING},

    WALLPAPER: {name: "wallpaper", type: CompanyType.AMBIENT_ADVERTISING},
    HOLOGRAFX: {name: "holografx", type: CompanyType.AMBIENT_ADVERTISING},
    ALL_AROUND_YOU: {name: "all-around-you", type: CompanyType.AMBIENT_ADVERTISING},

    PIX_CHIX: {name: "pix-chix", type: CompanyType.ONLINE_MARKETING},
    PRETTY_OBSCURE_INC: {name: "pretty-obscure-inc", type: CompanyType.ONLINE_MARKETING},
    CLICKBAITER: {name:"clickbaiter", type:CompanyType.ONLINE_MARKETING},

    ANONYMOUS_CROWD: {name: "anonymous-crowd", type: CompanyType.SOCIAL_MEDIA},
    LEADERBOARDER: {name: "leaderboarder", type: CompanyType.SOCIAL_MEDIA},
    XCURBR: {name: "xcurbr", type: CompanyType.SOCIAL_MEDIA},
}


