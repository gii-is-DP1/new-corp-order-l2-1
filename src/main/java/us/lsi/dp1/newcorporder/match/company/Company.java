package us.lsi.dp1.newcorporder.match.company;

import lombok.Getter;

@Getter
@SuppressWarnings("SpellCheckingInspection")
public enum Company {

    SLIMGROTZ_INC(CompanyType.BROADCAST_NETWORK),
    FLICKERING_LIGHTS(CompanyType.BROADCAST_NETWORK),
    GLOBAL_CORP(CompanyType.BROADCAST_NETWORK),

    PAGE_ONE_CORP(CompanyType.PRINT_MEDIA),
    READALOT(CompanyType.PRINT_MEDIA),
    GENERIC_SUB_INC(CompanyType.PRINT_MEDIA),

    SUBLIMINAL_SUBSIDIARY(CompanyType.GUERRILLA_MARKETING),
    VISUAL_TERROR_INC(CompanyType.GUERRILLA_MARKETING),
    WORDOFMOUTH(CompanyType.GUERRILLA_MARKETING),

    WALLPAPER(CompanyType.AMBIENT_ADVERTISING),
    HOLOGRAFX(CompanyType.AMBIENT_ADVERTISING),
    ALL_AROUND_YOU(CompanyType.AMBIENT_ADVERTISING),

    PIX_CHIX(CompanyType.ONLINE_MARKETING),
    PRETTY_OBSCURE_INC(CompanyType.ONLINE_MARKETING),
    CLICKBAITER(CompanyType.ONLINE_MARKETING),

    ANONYMOUS_CROWD(CompanyType.SOCIAL_MEDIA),
    LEADERBOARDER(CompanyType.SOCIAL_MEDIA),
    XCURBR(CompanyType.SOCIAL_MEDIA);

    private final CompanyType type;

    Company(CompanyType type) {
        this.type = type;
    }
}
