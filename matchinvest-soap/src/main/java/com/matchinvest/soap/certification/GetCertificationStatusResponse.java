package com.matchinvest.soap.certification;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "GetCertificationStatusResponse", namespace = "http://matchinvest.com/soap/certification")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetCertificationStatusResponse {
    @XmlElement(name = "status", namespace = "http://matchinvest.com/soap/certification")
    private String status;
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
