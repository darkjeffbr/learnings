package com.darkjeff.server;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import java.util.stream.Collectors;

public class MockServer {

    public static void main(String[] args) {
        // https://www.swtestacademy.com/standalone-wiremock-stub-server-creation/
        WireMockConfiguration config = WireMockConfiguration.wireMockConfig().gzipDisabled(false).port(3456);
        WireMockServer wireMockServer = new WireMockServer(config);

        wireMockServer.stubFor(
            WireMock.post(UrlPattern.ANY)
                .withRequestBody(WireMock.containing("ServiceBookingRules"))
            .willReturn(WireMock.aResponse().withHeader("Content-Type", "text/xml; charset=utf-8").withBodyFile("ServiceBookingRules.xml"))
        );

        wireMockServer.stubFor(
            WireMock.post(UrlPattern.ANY)
                .withRequestBody(WireMock.containing("ServiceBookingRQ"))
                .willReturn(WireMock.aResponse().withHeader("Content-Type", "text/xml; charset=utf-8").withBodyFile("ServiceBooking.xml"))
        );

//        wireMockServer.addMockServiceRequestListener((request, response) -> {
//            System.out.println( request.getHeaders().all().stream().map(header -> header.key() + ":" + header.firstValue()).collect(Collectors.toList()) );
//            System.out.println(request.getBodyAsString());
//
//        });

        wireMockServer.start();
    }

}
