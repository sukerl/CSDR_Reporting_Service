//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.2 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.04.15 at 01:34:27 PM CEST 
//


package li.bankfrick.informatik.reporting.csdr.xml.entities.auth072;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SettlementInternaliserClientType1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SettlementInternaliserClientType1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Prfssnl" type="{urn:iso:std:iso:20022:tech:xsd:auth.072.001.01}InternalisationData1"/&gt;
 *         &lt;element name="Rtl" type="{urn:iso:std:iso:20022:tech:xsd:auth.072.001.01}InternalisationData1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementInternaliserClientType1", propOrder = {
    "prfssnl",
    "rtl"
})
public class SettlementInternaliserClientType1 {

    @XmlElement(name = "Prfssnl", required = true)
    protected InternalisationData1 prfssnl;
    @XmlElement(name = "Rtl", required = true)
    protected InternalisationData1 rtl;

    /**
     * Gets the value of the prfssnl property.
     * 
     * @return
     *     possible object is
     *     {@link InternalisationData1 }
     *     
     */
    public InternalisationData1 getPrfssnl() {
        return prfssnl;
    }

    /**
     * Sets the value of the prfssnl property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternalisationData1 }
     *     
     */
    public void setPrfssnl(InternalisationData1 value) {
        this.prfssnl = value;
    }

    /**
     * Gets the value of the rtl property.
     * 
     * @return
     *     possible object is
     *     {@link InternalisationData1 }
     *     
     */
    public InternalisationData1 getRtl() {
        return rtl;
    }

    /**
     * Sets the value of the rtl property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternalisationData1 }
     *     
     */
    public void setRtl(InternalisationData1 value) {
        this.rtl = value;
    }

}
