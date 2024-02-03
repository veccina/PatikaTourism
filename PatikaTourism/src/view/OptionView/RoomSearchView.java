package view.OptionView;

import business.HotelManager;
import business.StayManager;
import business.PeriodManager;
import business.RoomManager;
import core.Helper;
import entity.Room;
import view.Layout;

import javax.swing.*;
import java.util.ArrayList;
import java.sql.Date;

public class RoomSearchView extends Layout {
    private JPanel container, pnl_searchroom_guestinformation, pnl_searchroom_arrivaldeparture;
    private JTextField tf_searchroom_regioncityhotel, tf_searchroom_guestinformation, tf_searchroom_adult, tf_searchroom_children;
    private JFormattedTextField ftf_searchroom_arrival, ftf_searchroom_departure;
    private JButton btn_searchroom_search;
    private JLabel lbl_searchroom_regioncityhotel, lbl_searchroom_adult, lbl_searchroom_children, lbl_searchroom_arrivaldeparture, lbl_searchroom_arrival, lbl_searchroom_departure, lbl_searchroom_guestinformation,lbl_searchroom_searchforrooms;
    HotelManager hotelManager;
    RoomManager roomManager;
    PeriodManager periodManager;
    StayManager stayManager;

    ArrayList<Room> filteredRooms;
    ArrayList<String> guestInformation;

    public RoomSearchView() {
        this.add(container);
        this.initView(650, 200, "Oda Arama Ekranı");

        this.hotelManager = new HotelManager();
        this.roomManager = RoomManager.getInstance();
        this.periodManager = new PeriodManager();
        this.stayManager = new StayManager();

        this.filteredRooms = new ArrayList<>();
        this.guestInformation = new ArrayList<>();

        initSearchButton();
    }

    private void initSearchButton() {
        btn_searchroom_search.addActionListener(e -> {

            JTextField[] fieldList = getTextFields();

            if (Helper.isFieldListEmpty(fieldList)) {
                Helper.showMsg("fill");
                return;
            }

            ArrayList<Date> customerDateList;

            try {
                customerDateList = Helper.parseDates(getStringListOfDates());
            } catch (Exception ex) {
                Helper.showMsg("invalid date");
                return;
            }

            filteredRooms = this.roomManager.getListStayDates(customerDateList.get(0), customerDateList.get(1));

            for (Room room : filteredRooms)
                System.out.println(room.getRoomID());

            if (filteredRooms.isEmpty()) {
                Helper.showMsg("Oda bulunamadı. Lütfen tarih aralığını kontrol edin.");
            } else {
                new RoomFilterView(this.filteredRooms, this.guestInformation);
            }


            dispose();
        });
    }

    private JTextField[] getTextFields() {
        JTextField regionCityHotel = tf_searchroom_regioncityhotel;
        JTextField adults = tf_searchroom_adult;
        JTextField children = tf_searchroom_children;
        JTextField arrival = ftf_searchroom_arrival;
        JTextField departure = ftf_searchroom_departure;

        this.guestInformation.add(tf_searchroom_adult.getText());
        this.guestInformation.add(tf_searchroom_children.getText());
        this.guestInformation.add(ftf_searchroom_arrival.getText());
        this.guestInformation.add(ftf_searchroom_departure.getText());

        return new JTextField[]{regionCityHotel, adults, children, arrival, departure};
    }

    private ArrayList<String> getStringListOfDates() {
        ArrayList<String> list = new ArrayList<>();

        list.add(ftf_searchroom_arrival.getText());
        list.add(ftf_searchroom_departure.getText());

        return list;
    }
}
