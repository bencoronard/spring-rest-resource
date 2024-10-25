package th.co.moneydd.adminportal.gateway_scheduler.gateway.dtos.responses.inbound;

import com.fasterxml.jackson.annotation.JsonProperty;

import th.co.moneydd.adminportal.gateway_scheduler.gateway.dtos.KongConfig;

public record KongGatewayResponseInbound(
    @JsonProperty("config") KongConfig config,
    @JsonProperty("enabled") Boolean enabled) {
}
