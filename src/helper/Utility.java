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

    public static void alertUpcomingAppointments() {
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
        for (Appointment a : DBAppointment.getAllAppointmentsFromDb()) {
            apptStart = LocalDateTime.parse(a.getStartTime() + " " + a.getStartDate(), formatter);
            apptId = a.getAppointmentId();
            customer = Customer.getCustomerNameById(a.getCustomerId());
        }
        if (apptStart.getYear() == userZDT.getYear() && (apptStart.isBefore(LocalDateTime.of(userDate, fifteenMinutesFromNow))
        && apptStart.isAfter(LocalDateTime.of(userDate, userTime)))) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            System.out.println("apptStart: " + apptStart);
            System.out.println("userDate: " + userDate);
            System.out.println("fifteenMinutesFromNow: " + fifteenMinutesFromNow);
            System.out.println("LocalDateTime.of(userDate, fifteenMinutesFromNow): " + LocalDateTime.of(userDate, fifteenMinutesFromNow));
            alert.setTitle("Information");
            alert.setHeaderText("Appointment soon");
            alert.setContentText("Appointment (ID: " + apptId + ") with " + customer + " starts within the next 15 minutes.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("");
            alert.setContentText("No appointments are starting in the next 15 minutes.");
            alert.show();
        }
    }
}
