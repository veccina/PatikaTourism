package view.OptionView;

import business.StayManager;
import core.Helper;
import entity.Hotel;
import entity.Stay;
import view.Layout;

import javax.swing.*;
import java.util.HashMap;

public class StayView extends Layout {
    private JPanel container;
    private JPanel pnl_lodging;
    private JCheckBox cb_lodging_ultraeverything;
    private JLabel lbl_lodging_ultraeverything;
    private JLabel lbl_lodging_everything;
    private JLabel lbl_lodging_roombreakfast;
    private JLabel lbl_lodging_fulllodging;
    private JLabel lbl_lodging_halflodging;
    private JLabel lbl_lodging_onlybed;
    private JLabel lbl_lodging_fullcredit;
    private JCheckBox cb_lodging_everything;
    private JCheckBox cb_lodging_roombreakfast;
    private JCheckBox cb_lodging_fulllodging;
    private JCheckBox cb_lodging_halflodging;
    private JCheckBox cb_lodging_onlybed;
    private JCheckBox cb_lodging_fullcredit;
    private JLabel lbl_lodging;
    private JButton btn_lodging_update;

    private Hotel hotel;
    private StayManager stayManager;

    private boolean result;


    // Değerlendirme sorusu 12 : Otellerin sisteme eklenmesinde pansiyon tipi yönetimi yapılıyor.
    public StayView(Hotel hotel) {
        this.add(container);
        this.hotel = hotel;

        this.stayManager = new StayManager();

        this.initView(200, 250, " Otel Konaklama Tipleri");

        displayLodgingData();
        initAccessButton();
    }

    public void displayLodgingData() {
        cb_lodging_ultraeverything.setSelected(stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), "Ultra Everything") != null);
        cb_lodging_everything.setSelected(stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), "Everything") != null);
        cb_lodging_roombreakfast.setSelected(stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), "Room Breakfast") != null);
        cb_lodging_fulllodging.setSelected(stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), "Full Lodging") != null);
        cb_lodging_halflodging.setSelected(stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), "Half Lodging") != null);
        cb_lodging_onlybed.setSelected(stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), "Only Bed") != null);
        cb_lodging_fullcredit.setSelected(stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), "Full Credit") != null);
    }

    public void initAccessButton() {
        btn_lodging_update.addActionListener(e -> {
            HashMap<String, Boolean> typeHashMap = new HashMap<>(7);

            boolean ultraEverything = cb_lodging_ultraeverything.isSelected();
            boolean everything = cb_lodging_everything.isSelected();
            boolean roomBreakfast = cb_lodging_roombreakfast.isSelected();
            boolean fullLodging = cb_lodging_fulllodging.isSelected();
            boolean halfLodging = cb_lodging_halflodging.isSelected();
            boolean onlyBed = cb_lodging_onlybed.isSelected();
            boolean fullCredit = cb_lodging_fullcredit.isSelected();

            typeHashMap.put("Ultra Everything", ultraEverything);
            typeHashMap.put("Everything", everything);
            typeHashMap.put("Room Breakfast", roomBreakfast);
            typeHashMap.put("Full Lodging", fullLodging);
            typeHashMap.put("Half Lodging", halfLodging);
            typeHashMap.put("Only Bed", onlyBed);
            typeHashMap.put("Full Credit", fullCredit);

            typeHashMap.forEach(
                (key, value) -> {

                    if (!value) {

                        Stay Stay;

                        if ((Stay = stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), key)) != null) {

                            this.result = stayManager.delete(Stay.getLodgingID());

                        }


                    } else {

                        if (stayManager.searchWithHotelIdTypeId(hotel.getHotelID(), key) == null) {
                            Stay Stay = new Stay();
                            Stay.setHotelID(hotel.getHotelID());
                            Stay.setType(key);

                            this.result = stayManager.add(Stay);

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
}

