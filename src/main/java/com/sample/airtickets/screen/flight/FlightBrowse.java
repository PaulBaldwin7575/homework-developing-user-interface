package com.sample.airtickets.screen.flight;

import com.sample.airtickets.app.TicketService;
import com.sample.airtickets.entity.Airport;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.component.Button;
import io.jmix.ui.component.DateField;
import io.jmix.ui.component.EntityComboBox;
import io.jmix.ui.component.HasValue;
import io.jmix.ui.model.CollectionLoader;
import io.jmix.ui.screen.*;
import com.sample.airtickets.entity.Flight;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

@UiController("Flight.browse")
@UiDescriptor("flight-browse.xml")
@LookupComponent("flightsTable")
public class FlightBrowse extends StandardLookup<Flight> {
    @Autowired
    private CollectionLoader<Flight> flightsDl;
    @Autowired
    private EntityComboBox<Airport> airportField;
    @Autowired
    private DateField<Date> takeFrom;
    @Autowired
    private DateField<LocalDate> takeTo;


    private String query = "select f from Flight f where";
    @Subscribe("airportField")
    public void onAirportFieldValueChange(HasValue.ValueChangeEvent<Airport> event) {
        if (airportField != null) {
            Airport airport = airportField.getValue();
            query += " f.fromAirport = :airport or f.toAirport = :airport and";
            flightsDl.setParameter("airport", airport);
        } else {
            flightsDl.setQuery(query);
        }
        query += " 1 = 1";
        flightsDl.setQuery(query);
        flightsDl.load();
        getScreenData().loadAll();
    }



    @Subscribe("takeFrom")
    public void onTakeFromValueChange(HasValue.ValueChangeEvent<LocalDate> event) {
        if (takeFrom != null) {
            ZonedDateTime date = Objects.requireNonNull(takeFrom.getValue()).toInstant().atZone(ZoneId.systemDefault());
            query += "  f.takeOffDate >= :dateStart and";
            flightsDl.setParameter("dateStart", date.toOffsetDateTime());
        }
        query += " 1 = 1";
        flightsDl.setQuery(query);
        flightsDl.load();
        getScreenData().loadAll();

    }

    @Subscribe("clearFilterBtn")
    public void onClearFilterBtnClick(Button.ClickEvent event) {
        airportField.clear();
        flightsDl.setQuery("select f from Flight f");
        flightsDl.removeParameter("airport");
        flightsDl.load();
        getScreenData().loadAll();
    }
}