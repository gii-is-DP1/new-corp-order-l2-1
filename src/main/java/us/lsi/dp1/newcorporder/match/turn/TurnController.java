package us.lsi.dp1.newcorporder.match.turn;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.VerifyCurrentTurn;
import us.lsi.dp1.newcorporder.match.payload.request.*;
import us.lsi.dp1.newcorporder.match.payload.response.*;

@RestController
@RequestMapping("/api/v1/matches/{match}/turn")
@SecurityRequirement(name = "bearerAuth")
public class TurnController {

    @PostMapping
    @VerifyCurrentTurn
    public void selectTurn(@PathVariable Match match, @RequestParam("action") Action action) {
        match.getTurnSystem().selectAction(Action.PLOT);
    }

    @PostMapping("/plot")
    @VerifyCurrentTurn
    public PlotResponse plot(@PathVariable Match match, @RequestBody @Valid PlotRequest request) {
        return match.getTurnSystem().getCurrentTurn().onPlotRequest(request);
    }

    @DeleteMapping("/shares")
    @VerifyCurrentTurn
    public DiscardShareResponse discardShare(@PathVariable Match match, @RequestBody @Valid DiscardShareRequest request) {
        return match.getTurnSystem().getCurrentTurn().onDiscardShareRequest(request);
    }

    @PostMapping("/consultants/use")
    @VerifyCurrentTurn
    public UseConsultantResponse useConsultant(@PathVariable Match match, @RequestBody @Valid UseConsultantRequest request) {
        return match.getTurnSystem().getCurrentTurn().onUseConsultantRequest(request);
    }

    @PostMapping("/infiltrate")
    @VerifyCurrentTurn
    public InfiltrateResponse infiltrate(@PathVariable Match match, @RequestBody @Valid InfiltrateRequest request) {
        return match.getTurnSystem().getCurrentTurn().onInfiltrateRequest(request);
    }

    @PostMapping("/consultants/take")
    @VerifyCurrentTurn
    public void takeConsultant(@PathVariable Match match, @RequestBody @Valid TakeConsultantRequest request) {
        match.getTurnSystem().getCurrentTurn().onTakeConsultantRequest(request);
    }

    @PostMapping("/take-over")
    @VerifyCurrentTurn
    public TakeOverResponse takeOver(@PathVariable Match match, @RequestBody @Valid TakeOverRequest request) {
        return match.getTurnSystem().getCurrentTurn().onTakeOverRequest(request);
    }

    @PostMapping("/company-abilities")
    @VerifyCurrentTurn
    public UseCompanyAbilityResponse useCompanyAbility(@PathVariable Match match, @RequestBody @Valid UseCompanyAbilityRequest request) {
        return match.getTurnSystem().getCurrentTurn().onUseCompanyAbilityRequest(request);
    }
}
