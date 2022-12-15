package com.sample.airtickets.screen.ticket;

import com.google.common.collect.ImmutableMap;
import io.jmix.core.DataManager;
import io.jmix.ui.Screens;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.navigation.UrlIdSerializer;
import io.jmix.ui.navigation.UrlParamsChangedEvent;
import io.jmix.ui.navigation.UrlRouting;
import io.jmix.ui.screen.*;
import com.sample.airtickets.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;

@UiController("Ticket.edit")
@UiDescriptor("ticket-edit.xml")
@EditedEntityContainer("ticketDc")
@Route(value = "tickets/view", parentPrefix = "tickets")
public class TicketEdit extends Screen {
    @Autowired
    private UrlRouting urlRouting;
    @Autowired
    private InstanceContainer<Ticket> ticketDc;
    @Autowired
    private DataManager dataManager;

    public void setTicketDc(Ticket ticket) {
        ticketDc.setItem(ticket);
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (ticketDc != null) {
            String serializeId = UrlIdSerializer.serializeId(ticketDc.getItem().getReservationId());
            urlRouting.replaceState(this, ImmutableMap.of("id", serializeId));
        }
    }

    @Subscribe
    public void onUrlParamsChanged(UrlParamsChangedEvent event) {
        String serializedId = event.getParams().get("id");
        String id = (String) UrlIdSerializer.deserializeId(String.class, serializedId);
        Ticket ticket = dataManager.load(Ticket.class)
                .query("select t from Ticket t where t.reservationId = :id")
                .parameter("id", id)
                .one();
        ticketDc.setItem(ticket);
        getScreenData().loadAll();
    }
}