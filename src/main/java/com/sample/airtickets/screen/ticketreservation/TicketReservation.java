package com.sample.airtickets.screen.ticketreservation;

import com.sample.airtickets.app.TicketService;
import com.sample.airtickets.entity.Airport;
import com.sample.airtickets.entity.Flight;
import com.sample.airtickets.entity.Ticket;
import com.sample.airtickets.screen.ticket.TicketEdit;
import io.jmix.core.DataManager;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.Screens;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.BaseAction;
import io.jmix.ui.app.inputdialog.DialogOutcome;
import io.jmix.ui.app.inputdialog.InputDialog;
import io.jmix.ui.component.*;
import io.jmix.ui.component.impl.LinkButtonImpl;
import io.jmix.ui.executor.BackgroundTask;
import io.jmix.ui.executor.BackgroundTaskHandler;
import io.jmix.ui.executor.BackgroundWorker;
import io.jmix.ui.executor.TaskLifeCycle;
import io.jmix.ui.model.CollectionContainer;
import io.jmix.ui.model.InstanceContainer;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@UiController("TicketReservation")
@UiDescriptor("ticket-reservation.xml")
public class TicketReservation extends Screen {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected DataManager dataManager;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private CollectionContainer<Flight> flightsDc;
    @Autowired
    private EntityComboBox<Airport> airportFromFilterField;
    @Autowired
    private EntityComboBox<Airport> airportToFilterField;
    @Autowired
    private DateField<LocalDate> dateFilterField;
    @Autowired
    private Notifications notifications;
    @Autowired
    private ProgressBar searchProgressBar;
    @Autowired
    private BackgroundWorker backgroundWorker;
    @Autowired
    private InputDialogFacet inputDialog;
    @Autowired
    private InstanceContainer<Flight> flightDc;
    @Autowired
    private Screens screens;
    @Autowired
    private ScreenBuilders screenBuilders;

    @Subscribe("searchFilterBtn")
    public void onSearchFilterBtnClick(Button.ClickEvent event) {
        if (airportFromFilterField.getValue() == null
                && airportToFilterField.getValue() == null
                && dateFilterField.getValue() == null) {
            notifications.create()
                    .withType(Notifications.NotificationType.WARNING)
                    .withCaption("Please fill at least one filter field")
                    .show();
        } else {
            BackgroundTask<Integer, List<Flight>> task = new BackgroundTask<>(10) {
                @Override
                public List<Flight> run(TaskLifeCycle<Integer> taskLifeCycle) throws Exception {
                    taskLifeCycle.publish(1);
                    return ticketService.searchFlights(airportFromFilterField.getValue(),
                            airportToFilterField.getValue(), dateFilterField.getValue());
                }
                    @Override
                    public void progress(List<Integer> changes) {
                        searchProgressBar.setVisible(true);
                    }
                    @Override
                    public void done(List<Flight> result) {
                        searchProgressBar.setVisible(false);
                        super.done(result);
                        flightsDc.setItems(result);
                    }
            };

            BackgroundTaskHandler<List<Flight>> taskHandler = backgroundWorker.handle(task);
            taskHandler.execute();
        }
    }

    @Install(to = "flightsTable.actions", subject = "columnGenerator")
    private Component flightsTableActionsColumnGenerator(Flight flight) {
        LinkButton button = uiComponents.create(LinkButton.class);
        button.setCaption("Reserve");
        button.setId(flight.getId().toString());
        button.setAction(new BaseAction("reserveFlight")
                .withHandler(e -> {
                    flightDc.setItem(flight);
                    inputDialog.create().show();
                }));
        return button;
    }

    @Install(to = "inputDialog", subject = "dialogResultHandler")
    private void inputDialogDialogResultHandler(InputDialog.InputDialogResult inputDialogResult) {

        if (inputDialogResult.closedWith(DialogOutcome.CANCEL)){
            return;
        } else {
            Ticket ticket = dataManager.create(Ticket.class);
            ticket.setFlight(flightDc.getItem());
            ticket.setPassengerName(inputDialogResult.getValue("passengerNameField"));
            ticket.setPassportNumber(inputDialogResult.getValue("passportNumberField"));
            ticket.setTelephone(inputDialogResult.getValue("telephoneField"));
            ticketService.saveTicket(ticket);
            TicketEdit screen = screenBuilders.screen(this)
                    .withScreenClass(TicketEdit.class)
                    .withOpenMode(OpenMode.NEW_TAB).build();
            screen.setTicketDc(ticket);
            screen.show();
        }
    }
}