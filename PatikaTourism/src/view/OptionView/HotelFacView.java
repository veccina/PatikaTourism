package view.OptionView;

import business.FacilityManager;
import core.Helper;
import entity.Facility;
import entity.Hotel;
import view.Layout;

import javax.swing.*;
import java.util.HashMap;

public class HotelFacView extends Layout {
    private JPanel container;
    private JCheckBox cb_facility_freeparking;
    private JLabel lbl_facility_freeparking;
    private JCheckBox cb_facility_freewifi;
    private JCheckBox cb_facility_swimmingpool;
    private JCheckBox cb_facility_fitnesscenter;
    private JCheckBox cb_facility_hotelconcierge;
    private JCheckBox cb_facility_spa;
    private JCheckBox cb_facility_roomservice;
    private JButton btn_facility_update;
    private JLabel lbl_facility_freewifi;
    private JLabel lbl_facility_swimmingpool;
    private JLabel lbl_facility_fitnesscenter;
    private JLabel lbl_facility_hotelconcierge;
    private JLabel lbl_facility_spa;
    private JLabel lbl_facility_roomservice;
    private JLabel lbl_facility_specs;
    private JPanel pnl_facility_specs;

    private Hotel hotel;
    private Facility facility;
    private FacilityManager facilityManager;

    private boolean result;


    public HotelFacView(Hotel hotel) {
        this.add(container);
        this.hotel = hotel;

        this.facilityManager = new FacilityManager();
        this.facility = facilityManager.searchWithHotelId(hotel.getHotelID());


        this.initView(200, 250, " Otel Özellikleri");

        displayFacilityData();
        initAccessButton();
    }

    public void displayFacilityData() {
        cb_facility_freeparking.setSelected(facilityManager.searchWithHotelTypeId(hotel.getHotelID(), "Free Parking") != null);
        cb_facility_freewifi.setSelected(facilityManager.searchWithHotelTypeId(hotel.getHotelID(), "Free Wifi") != null);
        cb_facility_swimmingpool.setSelected(facilityManager.searchWithHotelTypeId(hotel.getHotelID(), "Swimming Pool") != null);
        cb_facility_fitnesscenter.setSelected(facilityManager.searchWithHotelTypeId(hotel.getHotelID(), "Fitness Center") != null);
        cb_facility_hotelconcierge.setSelected(facilityManager.searchWithHotelTypeId(hotel.getHotelID(), "Hotel Concierge") != null);
        cb_facility_spa.setSelected(facilityManager.searchWithHotelTypeId(hotel.getHotelID(), "Spa") != null);
        cb_facility_roomservice.setSelected(facilityManager.searchWithHotelTypeId(hotel.getHotelID(), "Room Service") != null);
    }

    public void initAccessButton() {
        btn_facility_update.addActionListener(e -> {
            HashMap<String, Boolean> typeHashMap = getTypeHashMap();

            typeHashMap.forEach(
                (key, value) -> {
                    if (!value) {

                        if ((facility = facilityManager.searchWithHotelTypeId(hotel.getHotelID(), key)) != null) {

                            this.result = facilityManager.delete(facility.getFacilityID());

                        }

                    } else {

                        if (facilityManager.searchWithHotelTypeId(hotel.getHotelID(), key) == null) {
                            Facility facility = new Facility();
                            facility.setHotelID(hotel.getHotelID());
                            facility.setType(key);

                            this.result = facilityManager.add(facility);

                        }

                    }
                }
            );

            if (!result) {
                Helper.showMsg("error");
                return;
            }

            Helper.showMsg("success");
            dispose();
        });
    }

    private HashMap<String, Boolean> getTypeHashMap() {
        HashMap<String, Boolean> typeHashMap = new HashMap<>(7);

        boolean freeParking = cb_facility_freeparking.isSelected();
        boolean freeWifi = cb_facility_freewifi.isSelected();
        boolean swimmingPool = cb_facility_swimmingpool.isSelected();
        boolean fitnessCenter = cb_facility_fitnesscenter.isSelected();
        boolean hotelConcierge = cb_facility_hotelconcierge.isSelected();
        boolean spa = cb_facility_spa.isSelected();
        boolean roomService = cb_facility_roomservice.isSelected();

        typeHashMap.put("Free Parking", freeParking);
        typeHashMap.put("Free Wifi", freeWifi);
        typeHashMap.put("Swimming Pool", swimmingPool);
        typeHashMap.put("Fitness Center", fitnessCenter);
        typeHashMap.put("Hotel Concierge", hotelConcierge);
        typeHashMap.put("Spa", spa);
        typeHashMap.put("Room Service", roomService);
        return typeHashMap;
    }
}

