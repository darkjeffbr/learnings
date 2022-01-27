package com.darkjeff.i18n.movies;

import io.quarkus.runtime.StartupEvent;
import javax.enterprise.event.Observes;
import javax.inject.Singleton;

@Singleton
public class TimezoneSettings {

    public void setTimezone(@Observes StartupEvent startupEvent) {
        System.setProperty("user.timezone", "UTC");
    }
}