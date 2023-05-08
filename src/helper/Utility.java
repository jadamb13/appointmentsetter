package helper;

import DBAccess.DBAppointment;
import javafx.scene.control.Alert;
import model.Appointment;
import model.Customer;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/* Class of utility methods. */
public class Utility {

    /**
     * Alerts the user upon login to any appointments within 15 minutes or alerts that there are no appointments within 15 minutes.
     *
     * */
    public static void alertUpcomingAppointments() {
        boolean apptFlag = false;
        // Get the current ZoneId, Date, and Time in the user's time zone and created ZonedDateTime
        ZoneId userZoneId = ZoneId.systemDefault();
        LocalDate userDate = LocalDate.now(userZoneId);
        LocalTime userTime = LocalTime.now(userZoneId);
        ZonedDateTime userZDT = ZonedDateTime.of(userDate, userTime, userZoneId).truncatedTo(ChronoUnit.SECONDS);

        LocalTime ltNow = userZDT.toLocalDateTime().toLocalTime();
        LocalTime fifteenMinutesFromNow = ltNow.plusMinutes(15).truncatedTo(ChronoUnit.MINUTES);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a MM-dd-yyyy");
        LocalDateTime apptStart = null;
        int apptId = 0;
        String customer = "";
        String apptDate = "";
        String apptTime = "";
        for (Appointment a : DBAppointment.getAllAppointmentsFromDb()) {

            apptStart = LocalDateTime.parse(a.getStartTime() + " " + a.getStartDate(), formatter);
            apptId = a.getAppointmentId();
            customer = Customer.getCustomerNameById(a.getCustomerId());
            apptDate = a.getStartDate();
            apptTime = a.getStartTime();
            if (apptStart.getYear() == userZDT.getYear() && (apptStart.isBefore(LocalDateTime.of(userDate, fifteenMinutesFromNow))
                    && apptStart.isAfter(LocalDateTime.of(userDate, userTime)))) {
                apptFlag = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Appointment soon");
                alert.setContentText("You have an upcoming appointment with " + customer + " on " + apptDate + " at " + apptTime + ".");
                alert.show();
            }
        }
        if(!apptFlag){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("");
            alert.setContentText("No appointments are starting in the next 15 minutes.");
            alert.show();
        }

    }
}
