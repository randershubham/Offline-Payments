package com.bits.offline.payments.request.outbound.common;

public class CardAcceptor {

    private Address address;
    private String idCode;
    private String name;
    private String terminalId;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public static CardAcceptor createCardAcceptor() {
        CardAcceptor cardAcceptor = new CardAcceptor();
        cardAcceptor.setIdCode("ABCD1234ABCD123");
        cardAcceptor.setName("Visa Inc. USA-Foster City");
        cardAcceptor.setTerminalId("ABCD1234");
        cardAcceptor.setAddress(Address.createAddress());
        return cardAcceptor;
    }
}
