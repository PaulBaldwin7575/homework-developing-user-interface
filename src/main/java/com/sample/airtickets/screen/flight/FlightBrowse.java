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
//    @Autowired
//    private CollectionLoader<Flight> flightsDl;
//    @Autowired
//    private EntityComboBox<Airport> airportField;
//    @Autowired
//    private DateField<Date> takeFrom;
//    @Autowired
//    private DateField<Date> takeTo;
//
//
//    private String query = "select f from Flight f where 1 = 1";
//    private final String defaultQuery = "select f from Flight f where 1 = 1";
//    @Subscribe("airportField")
//    public void afterAirportFieldValueChange(HasValue.ValueChangeEvent<Airport> event) {
//        if (airportField != null) {
//            Airport airport = airportField.getValue();
//            query += " and f.fromAirport = :airport or f.toAirport = :airport";
//            flightsDl.setParameter("airport", airport);
//            flightsDl.setQuery(query);
//            flightsDl.load();
//            getScreenData().loadAll();
//        } else {
//            //flightsDl.setQuery(defaultQuery);
//        }
//
//    }
//
//    @Subscribe("takeFrom")
//    public void onTakeFromValueChange(HasValue.ValueChangeEvent<LocalDate> event) {
//        if (takeFrom != null) {
//            ZonedDateTime date = Objects.requireNonNull(takeFrom.getValue()).toInstant().atZone(ZoneId.systemDefault());
//            query += "and f.takeOffDate >= :dateStart";
//            flightsDl.setParameter("dateStart", date.toOffsetDateTime());
//        }
//        query += " 1 = 1";
//        flightsDl.setQuery(query);
//        flightsDl.load();
//        getScreenData().loadAll();
//
//    }
//    @Subscribe("takeTo")
//    public void onTakeToValueChange(HasValue.ValueChangeEvent<LocalDate> event) {
//        if (takeFrom != null) {
//            ZonedDateTime date = Objects.requireNonNull(takeTo.getValue()).toInstant().atZone(ZoneId.systemDefault());
//            query += "  f.takeOffDate < :dateEnd and";
//            flightsDl.setParameter("dateEnd", date.toOffsetDateTime());
//        }
//        query += " 1 = 1";
//        flightsDl.setQuery(query);
//        flightsDl.load();
//        //flightsDl.get
//        getScreenData().loadAll();
//    }
//    @Subscribe("clearFilterBtn")
//    public void onClearFilterBtnClick(Button.ClickEvent event) {
//        query = defaultQuery;
//        flightsDl.setQuery(defaultQuery);
//        airportField.setValue(null);
////        takeFrom.setValue(null);
////        takeTo.setValue(null);
//
//        flightsDl.removeParameter("airport");
//        flightsDl.removeParameter("dateStart");
//        flightsDl.removeParameter("dateEnd");
//
//        flightsDl.load();
//        getScreenData().loadAll();
//    }


}