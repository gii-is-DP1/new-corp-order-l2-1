import {CompanyMatrix} from "./CompanyMatrix";

const CompanyType = {
    BROADCAST_NETWORK: "broadcast-network",
    PRINT_MEDIA: "print-media",
    GUERRILLA_MARKETING:"guerrilla-marketing",
    AMBIENT_ADVERTISING: "ambient-advertising",
    ONLINE_MARKETING: "online-marketing",
    SOCIAL_MEDIA: "social-media"
}

const Company = {
    SLIMGROTZ_INC: {name:"slimgrotz-inc", type: CompanyType.BROADCAST_NETWORK},
    FLICKERING_LIGHTS:  {name:"flickering-lights", type: CompanyType.BROADCAST_NETWORK},
    GLOBAL_CORP:  {name:"global-corp", type:CompanyType.BROADCAST_NETWORK},
    PAGE_ONE_CORP:  {name:"page-one-corp", type:CompanyType.PRINT_MEDIA},
    READALOT:  {name:"readalot", type:CompanyType.PRINT_MEDIA},
    GENERIC_SUB_INC:  {name:"generic-sub-inc", type:CompanyType.PRINT_MEDIA},
    SUBLIMINAL_SUBSIDIARY:  {name:"subliminal-subsidiary", type:CompanyType.GUERRILLA_MARKETING},
    VISUAL_TERROR_INC:  {name:"visual-terror-inc", type:CompanyType.GUERRILLA_MARKETING},
    WORDOFMOUTH:  {name:"wordofmouth", type:CompanyType.GUERRILLA_MARKETING},
    WALLPAPER:  {name:"wallpaper", type:CompanyType.AMBIENT_ADVERTISING},
    HOLOGRAFX:  {name:"holografx", type:CompanyType.AMBIENT_ADVERTISING},
    ALL_AROUND_YOU:  {name:"all-around-you", type:CompanyType.AMBIENT_ADVERTISING},
    PIX_CHIX:  {name:"pix-chix", type:CompanyType.ONLINE_MARKETING},
    PRETTY_OBSCURE_INC:  {name:"pretty-obscure-inc", type:CompanyType.ONLINE_MARKETING},
    ANONYMOUS_CROWD:  {name:"anonymous-crowd", type:CompanyType.SOCIAL_MEDIA},
    LEADERBOARDER:  {name:"leaderboarder", type:CompanyType.SOCIAL_MEDIA},
    XCURBR:  {name:"xcurbr", type:CompanyType.SOCIAL_MEDIA},
}

export function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
}

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
    ]

    return <>
        <p>GAME IN PROGRESS</p>
        <CompanyMatrix companyTiles={companyTiles}/>
    </>;
}
