package us.lsi.dp1.newcorporder.match.turn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.bind.FromPathVariable;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.VerifyCurrentTurn;
import us.lsi.dp1.newcorporder.match.payload.request.*;
import us.lsi.dp1.newcorporder.match.payload.request.infiltrate.BasicInfiltrate;
import us.lsi.dp1.newcorporder.match.payload.response.*;


@RestController
@RequestMapping("/api/v1/matches/{match}/turn")
@SecurityRequirement(name = "bearerAuth")
@ApiResponse(
    responseCode = "403",
    description = "The given action is illegal for the current state of the match"
)
@Tag(name = "Turn", description = "The Turn API")
public class TurnController {

    @Operation(
        summary = "Select a turn",
        description = "Select a turn",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The created achievement"
    )
    @PostMapping
    @VerifyCurrentTurn
    public void selectTurn(@RequestParam("action") Action action, @FromPathVariable Match match) {
        match.getTurnSystem().selectAction(action);
    }

    @Operation(
        summary = "Select plot action",
        description = "Select plot action",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Plot action done"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid plot request"
    )
    @PostMapping("/plot")
    @VerifyCurrentTurn
    public PlotResponse plot(@RequestBody @Valid PlotRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onPlotRequest(request);
    }

    @Operation(
        summary = "Discard a share",
        description = "Discard a share",
        tags = "delete"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Share discarded"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid discard share request"
    )
    @DeleteMapping("/shares")
    @VerifyCurrentTurn
    public DiscardShareResponse discardShare(@RequestBody @Valid DiscardShareRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onDiscardShareRequest(request);
    }

    @Operation(
        summary = "Use a consultant",
        description = "Use a consultant",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Consultant used"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid use consultant request"
    )
    @PostMapping("/consultants/use")
    @VerifyCurrentTurn
    public UseConsultantResponse useConsultant(@RequestBody @Valid UseConsultantRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onUseConsultantRequest(request);
    }

    @Operation(
        summary = "Select infiltrate action",
        description = "Select infiltrate action",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Infiltrate action done"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid infiltrate request"
    )
    @PostMapping("/infiltrate")
    @VerifyCurrentTurn
    public InfiltrateResponse infiltrate(@RequestBody @Valid InfiltrateRequest request, @FromPathVariable Match match) {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
        System.out.println(((BasicInfiltrate) request.getInfiltrate()).getNumberOfShares());
        System.out.println(((BasicInfiltrate) request.getInfiltrate()).getTile().getX());
        System.out.println(((BasicInfiltrate) request.getInfiltrate()).getTile().getX());
        System.out.println(((BasicInfiltrate) request.getInfiltrate()).getConglomerateType().name());
        return match.getTurnSystem().getCurrentTurn().onInfiltrateRequest(request);
    }

    @Operation(
        summary = "Take a consultant",
        description = "Take a consultant",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Consultant taken"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid take consultant request"
    )
    @PostMapping("/consultants/take")
    @VerifyCurrentTurn
    public void takeConsultant(@RequestBody @Valid TakeConsultantRequest request, @FromPathVariable Match match) {
        match.getTurnSystem().getCurrentTurn().onTakeConsultantRequest(request);
    }

    @Operation(
        summary = "Select take over action",
        description = "Select take over action",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Take over action done"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid take over request"
    )
    @PostMapping("/take-over")
    @VerifyCurrentTurn
    public TakeOverResponse takeOver(@RequestBody @Valid TakeOverRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onTakeOverRequest(request);
    }

    @Operation(
        summary = "Use a company ability",
        description = "Use a company ability",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Company ability used"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Not valid use company ability request"
    )
    @PostMapping("/company-abilities")
    @VerifyCurrentTurn
    public UseCompanyAbilityResponse useCompanyAbility(@RequestBody @Valid UseCompanyAbilityRequest request, @FromPathVariable Match match) {
        return match.getTurnSystem().getCurrentTurn().onUseCompanyAbilityRequest(request);
    }
}
