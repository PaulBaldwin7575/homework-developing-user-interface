package com.sample.airtickets.screen.airline;

import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import com.sample.airtickets.entity.Airline;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Airline.browse")
@UiDescriptor("airline-browse.xml")
@LookupComponent("table")
public class AirlineBrowse extends MasterDetailScreen<Airline> {
    @Autowired
    private GroupTable<Airline> table;

    @Subscribe
    public void onInit(InitEvent event) {
        table.getColumn("iataCode").setFormatter(value -> value.toString().toUpperCase());
    }


}