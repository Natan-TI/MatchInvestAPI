package com.matchinvest.soap.certification;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "GetCertificationStatusRequest", namespace = "http://matchinvest.com/soap/certification")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCertificationStatusRequest {
    @XmlElement(name = "advisorId", namespace = "http://matchinvest.com/soap/certification")
    private long advisorId;
    public long getAdvisorId() { return advisorId; }
    public void setAdvisorId(long advisorId) { this.advisorId = advisorId; }
}
