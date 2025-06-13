package com.matchinvest.soap.endpoint;

import com.matchinvest.soap.certification.*;
import org.springframework.ws.server.endpoint.annotation.*;

@Endpoint
public class CertificationEndpoint {
  private static final String NAMESPACE = "http://matchinvest.com/soap/certification";

  @PayloadRoot(namespace = NAMESPACE, localPart = "GetCertificationStatusRequest")
  @ResponsePayload
  public GetCertificationStatusResponse getStatus(@RequestPayload GetCertificationStatusRequest req) {
    GetCertificationStatusResponse resp = new GetCertificationStatusResponse();
    resp.setStatus(req.getAdvisorId() % 2 == 0 ? "Certified" : "Not Certified");
    return resp;
  }
}
