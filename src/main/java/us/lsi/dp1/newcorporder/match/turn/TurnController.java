package us.lsi.dp1.newcorporder.match.turn;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.bind.FromPathVariable;
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
    public void selectTurn(@RequestParam("action") Action action, @FromPathVariable Match match) {
        match.getTurnSystem().selectAction(action);
    }

    @PostMapping("/plot")
    @VerifyCurrentTurn
    public PlotResponse plot(@RequestBody @Valid PlotRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onPlotRequest(request);
    }

    @DeleteMapping("/shares")
    @VerifyCurrentTurn
    public DiscardShareResponse discardShare(@RequestBody @Valid DiscardShareRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onDiscardShareRequest(request);
    }

    @PostMapping("/consultants/use")
    @VerifyCurrentTurn
    public UseConsultantResponse useConsultant(@RequestBody @Valid UseConsultantRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onUseConsultantRequest(request);
    }

    @PostMapping("/infiltrate")
    @VerifyCurrentTurn
    public InfiltrateResponse infiltrate(@RequestBody @Valid InfiltrateRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onInfiltrateRequest(request);
    }

    @PostMapping("/consultants/take")
    @VerifyCurrentTurn
    public void takeConsultant(@RequestBody @Valid TakeConsultantRequest request, @FromPathVariable Match match) {
        match.getTurnSystem().getCurrentTurn().onTakeConsultantRequest(request);
    }

    @PostMapping("/take-over")
    @VerifyCurrentTurn
    public TakeOverResponse takeOver(@RequestBody @Valid TakeOverRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onTakeOverRequest(request);
    }

    @PostMapping("/company-abilities")
    @VerifyCurrentTurn
    public UseCompanyAbilityResponse useCompanyAbility(@RequestBody @Valid UseCompanyAbilityRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onUseCompanyAbilityRequest(request);
    }
}
