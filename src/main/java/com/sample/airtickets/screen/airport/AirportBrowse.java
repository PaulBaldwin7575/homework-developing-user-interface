package com.sample.airtickets.screen.airport;

import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import com.sample.airtickets.entity.Airport;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Airport.browse")
@UiDescriptor("airport-browse.xml")
@LookupComponent("airportsTable")
public class AirportBrowse extends StandardLookup<Airport> {
    @Autowired
    private GroupTable<Airport> airportsTable;

    @Subscribe
    public void onInit(InitEvent event) {
        airportsTable.getColumn("code").setFormatter(value -> value.toString().toUpperCase());
    }


}