package view.OptionView;

import business.*;
import core.CMBItem;
import core.Helper;
import entity.*;
import view.Layout;

import javax.swing.*;
import java.util.ArrayList;

public class RoomView extends Layout {
    private JPanel container;
    private JPanel pnl_room;
    private JLabel lbl_room_action;
    private JComboBox cmb_room_roomname;
    private JComboBox cmb_room_periodname;
    private JTextField tf_room_numberofbeds;
    private JTextField tf_room_squaremeters;
    private JTextField tf_room_adultprice;
    private JTextField tf_room_childprice;
    private JButton btn_room_action;
    private JLabel lbl_room_roomname;
    private JLabel lbl_room_numberofbeds;
    private JLabel lbl_room_squaremeters;
    private JComboBox cmb_room_hotelname;
    private JLabel lbl_room_hotelname;
    private JCheckBox cb_room_tv;
    private JCheckBox cb_room_minibar;
    private JCheckBox cb_room_console;
    private JCheckBox cb_room_safe;
    private JCheckBox cb_room_projector;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JTextField textField9;
    private JTextField textField10;
    private JTextField textField11;
    private JTextField textField12;
    private JTextField textField13;
    private JTextField textField14;
    private JPanel pnl_winter_ultraeverything;
    private JPanel winter;
    private JPanel summer;
    private JPanel pnl_winter_everything;
    private JPanel pnl_winter_roombreakfast;
    private JPanel pnl_winter_fulllodging;
    private JPanel pnl_winter_halflodging;
    private JPanel pnl_winter_onlybed;
    private JPanel pnl_winter_fullcredit;
    private JPanel pnl_summer_ultraeverything;
    private JPanel pnl_summer_everything;
    private JPanel pnl_summer_roombreakfast;
    private JPanel pnl_summer_fulllodging;
    private JPanel pnl_summer_halflodging;
    private JPanel pnl_summer_onlybed;
    private JPanel pnl_summer_fullcredit;
    private JTextField textField15;
    private JTextField textField16;
    private JTextField textField17;
    private JTextField textField18;
    private JTextField textField19;
    private JTextField textField20;
    private JTextField textField21;
    private JTextField textField22;
    private JTextField textField23;
    private JTextField textField24;
    private JTextField textField25;
    private JTextField textField26;
    private JTextField textField27;
    private JTextField textField28;
    private JTextField tf_room_stock;

    private RoomManager roomManager;
    private Room room;

    private StayManager stayManager;
    private HotelManager hotelManager;
    private PeriodManager periodManager;
    private PriceManager priceManager;

    private int hotelIndex;


    // Değerlendirme sorusu 13 : Acente personeli sisteme kayıtlı otellere oda ekleyebiliyor mu? Oda eklerken oda tipi (Single, Double, Junior Suite, Suite vb.), oda özellikleri (Yatak Sayısı, Televizyon, Minibar vb.) ve stok miktarı girişi dikkate alınıyor.
    // değerlendirme sorusu 15 : Acente çalışanı, tarih aralığına, otel ismine, şehire göre odaları başarılı bir şekilde arayabiliyor
    // değerlendirme sorusu 16 : Arama sonucuna uygun otellerin bilgileri (adres, yıldız, otel imkanları vb) ve odaların bilgileri (giriş, çıkış tarihi, yetişkin, çocuk sayısı, yatak sayısı, varsa, mini bar, TV vb) kullanıcıya gösteriliyor.
    // Değerlendirme sorusu 17 : Misafir bilgisi, kalınacak gece sayısı ve oda fiyatına göre konaklamaya ait fiyat başarılı bir şekilde hesaplanıyor
    public RoomView() {
        this.add(container);

        this.initView(1200, 600, "Oda Ekleme Ekranı");

        this.roomManager = RoomManager.getInstance();
        this.hotelManager = new HotelManager();
        this.stayManager = new StayManager();
        this.periodManager = new PeriodManager();
        this.priceManager = new PriceManager();






        loadHotelComboBox();

        initHotelComboBoxListener();

        initAddButton();



    }
    // Değerlendirme sorusu 14 :Sisteme yeni eklenen odaların fiyatları, sezon, pansiyon tipi ve konaklayanların yetişkin/çocuk olma durumuna göre belirleniyor.
    private void initAddButton() {
        lbl_room_action.setText("Oda Ekle");
        btn_room_action.setText("Ekle");

        btn_room_action.addActionListener(e -> {
            if (checkInputs()) {
                return;
            }

            if (!this.roomManager.add(getRoomFromTextFields(0))) {
                Helper.showMsg("error");
                return;
            }

            JTextField[] validInputs = getValidInputs();
            String[] validTypes = getValidTypes();

            boolean result = false;

            for (int i = 0; i < validInputs.length; i += 4) {

                if (this.stayManager.searchWithHotelIdTypeId(getHotelFromComboBox().getKey(), validTypes[i]) == null)
                    continue;

                int lodgingID = this.stayManager.searchWithHotelIdTypeId(getHotelFromComboBox().getKey(), validTypes[i]).getLodgingID();
                int roomID = this.roomManager.searchWithNameHotelId(getHotelFromComboBox().getKey(), cmb_room_roomname.getSelectedItem().toString()).getRoomID();

                double winterAdultPrice = Double.parseDouble(validInputs[i].getText());
                double winterChildPrice = Double.parseDouble(validInputs[i + 1].getText());
                double summerAdultPrice = Double.parseDouble(validInputs[i + 2].getText());
                double summerChildPrice = Double.parseDouble(validInputs[i + 3].getText());

                Price price = new Price(0, lodgingID, roomID, winterAdultPrice, winterChildPrice, summerAdultPrice, summerChildPrice);
                result = this.priceManager.add(price);
            }

            if (!result) {
                Helper.showMsg("error");
                return;
            }

            Helper.showMsg("success");
            dispose();
        });
    }




    public RoomView(Room room) {
        this.add(container);
        this.room = room;

        this.initView(1200, 600, "Oda Güncelleme Ekranı");

        this.roomManager = RoomManager.getInstance();
        this.hotelManager = new HotelManager();
        this.stayManager = new StayManager();
        this.periodManager = new PeriodManager();
        this.priceManager = new PriceManager();


        loadHotelComboBox(this.room);
        displayRoomData();
        removeLodging();

        initHotelComboBoxListener();
        initUpdateButton();
    }
    private void displayRoomData() {
        cmb_room_hotelname.setSelectedIndex(hotelIndex);
        cmb_room_hotelname.setEnabled(false);
        cmb_room_roomname.setSelectedItem(this.room.getName());
        cmb_room_roomname.setEnabled(false);

        parseItems();

        tf_room_squaremeters.setText(this.room.getSquareMeter());
        tf_room_numberofbeds.setText(String.valueOf(this.room.getNumberOfBeds()));

        tf_room_stock.setText(String.valueOf(this.room.getStock()));

        JTextField[] validInputs = getValidInputs();
        String[] validTypes = getValidTypes();

        for (int i = 0; i < validInputs.length; i += 4) {

            if (this.stayManager.searchWithHotelIdTypeId(getHotelFromComboBox().getKey(), validTypes[i]) == null)
                continue;

            int lodgingID = this.stayManager.searchWithHotelIdTypeId(getHotelFromComboBox().getKey(), validTypes[i]).getLodgingID();
            int roomID = this.roomManager.searchWithNameHotelId(getHotelFromComboBox().getKey(), cmb_room_roomname.getSelectedItem().toString()).getRoomID();
            Price price = this.priceManager.searchStayIdRoomId(lodgingID, roomID);

            if (price == null) {
                continue;
            }

            String winterAdultPrice = String.valueOf(price.getWinterAdultPrice());
            String winterChildPrice = String.valueOf(price.getWinterChildPrice());
            String summerAdultPrice = String.valueOf(price.getSummerAdultPrice());
            String summerChildPrice = String.valueOf(price.getSummerChildPrice());

            validInputs[i].setText(winterAdultPrice);
            validInputs[i + 1].setText(winterChildPrice);
            validInputs[i + 2].setText(summerAdultPrice);
            validInputs[i + 3].setText(summerChildPrice);

        }
    }
    private void initUpdateButton() {
        lbl_room_action.setText("Oda Güncelle");
        btn_room_action.setText("Güncelle");

        btn_room_action.addActionListener(e -> {
            if (checkInputs()) {
                return;
            }

            if (!this.roomManager.update(this.getRoomFromTextFields(room.getRoomID()))) {
                Helper.showMsg("error");
                return;
            }

            JTextField[] validInputs = getValidInputs();
            String[] validTypes = getValidTypes();
            boolean result = false;

            for (int i = 0; i < validInputs.length; i += 4) {

                int lodgingID = this.stayManager.searchWithHotelIdTypeId(getHotelFromComboBox().getKey(), validTypes[i]).getLodgingID();
                int roomID = this.roomManager.searchWithNameHotelId(getHotelFromComboBox().getKey(), cmb_room_roomname.getSelectedItem().toString()).getRoomID();

                double winterAdultPrice = Double.parseDouble(validInputs[i].getText());
                double winterChildPrice = Double.parseDouble(validInputs[i + 1].getText());
                double summerAdultPrice = Double.parseDouble(validInputs[i + 2].getText());
                double summerChildPrice = Double.parseDouble(validInputs[i + 3].getText());

                if (this.priceManager.searchStayIdRoomId(lodgingID, roomID) == null) {
                    Price price = new Price(0, lodgingID, roomID, winterAdultPrice, winterChildPrice, summerAdultPrice, summerChildPrice);
                    result = this.priceManager.add(price);
                    continue;
                }

                Price price = this.priceManager.searchStayIdRoomId(lodgingID, roomID);
                price.setWinterAdultPrice(winterAdultPrice);
                price.setWinterChildPrice(winterChildPrice);
                price.setSummerAdultPrice(summerAdultPrice);
                price.setSummerChildPrice(summerChildPrice);

                result = this.priceManager.update(price);
            }

            if (!result) {
                Helper.showMsg("error");
                return;
            }

            Helper.showMsg("success");
            dispose();
        });
    }

    private void initHotelComboBoxListener() {
        cmb_room_hotelname.addActionListener(e -> {
            if (e.getSource() == cmb_room_hotelname) {
                removeLodging();
                cmb_room_hotelname.setEnabled(false);
            }
        });
    }

    public void removeLodging() {

        CMBItem itemH = (CMBItem) cmb_room_hotelname.getSelectedItem();

        if (itemH == null)
            return;

        Stay Stay = this.stayManager.searchWithHotelIdTypeId(itemH.getKey(), "Ultra Everything");

        if (Stay == null) {
            winter.remove(pnl_winter_ultraeverything);
            summer.remove(pnl_summer_ultraeverything);
        }

        Stay = this.stayManager.searchWithHotelIdTypeId(itemH.getKey(), "Everything");

        if (Stay == null) {
            winter.remove(pnl_winter_everything);
            summer.remove(pnl_summer_everything);
        }

        Stay = this.stayManager.searchWithHotelIdTypeId(itemH.getKey(), "Room Breakfast");

        if (Stay == null) {
            winter.remove(pnl_winter_roombreakfast);
            summer.remove(pnl_summer_roombreakfast);
        }

        Stay = this.stayManager.searchWithHotelIdTypeId(itemH.getKey(), "Full Lodging");

        if (Stay == null) {
            winter.remove(pnl_winter_fulllodging);
            summer.remove(pnl_summer_fulllodging);
        }

        Stay = this.stayManager.searchWithHotelIdTypeId(itemH.getKey(), "Half Lodging");

        if (Stay == null) {
            winter.remove(pnl_winter_halflodging);
            summer.remove(pnl_summer_halflodging);
        }

        Stay = this.stayManager.searchWithHotelIdTypeId(itemH.getKey(), "Only Bed");

        if (Stay == null) {
            winter.remove(pnl_winter_onlybed);
            summer.remove(pnl_summer_onlybed);
        }

        Stay = this.stayManager.searchWithHotelIdTypeId(itemH.getKey(), "Full Credit");

        if (Stay == null) {
            winter.remove(pnl_winter_fullcredit);
            summer.remove(pnl_summer_fullcredit);
        }
    }

    private Room getRoomFromTextFields(int roomID) {
        int numberOfBeds = Integer.parseInt(tf_room_numberofbeds.getText().trim());
        String items = getItems();
        String squareMeters = tf_room_squaremeters.getText().trim();
        int stock = Integer.parseInt(tf_room_stock.getText().trim());

        int hotelID = getHotelFromComboBox().getKey();
        Period period = this.periodManager.SearchWithHotelId(hotelID);
        if (period == null) {
            System.out.println("Tatil Periyodu Bulunamadı.");
            return null;
        }
        int periodID = period.getPeriodID();
        String name = cmb_room_roomname.getSelectedItem().toString().trim();

        return new Room(roomID, hotelID, periodID, name, numberOfBeds, items, squareMeters, stock);
    }
    private JTextField[] getTextFields() {
        return new JTextField[] {
            textField1, textField2, textField15, textField16,
            textField3, textField4, textField17,textField18,
            textField5, textField6, textField19, textField20,
            textField7, textField8,textField21, textField22,
            textField9, textField10,textField23, textField24,
            textField11, textField12,textField25, textField26,
            textField13,textField14,textField27, textField28
        };
    }

    private String[] getLodgingTypes() {
        return new String[] {
            "Ultra Everything", "Ultra Everything", "Ultra Everything", "Ultra Everything",
            "Everything", "Everything", "Everything", "Everything",
            "Room Breakfast", "Room Breakfast", "Room Breakfast", "Room Breakfast",
            "Full Lodging", "Full Lodging", "Full Lodging", "Full Lodging",
            "Half Lodging", "Half Lodging", "Half Lodging", "Half Lodging",
            "Only Bed", "Only Bed", "Only Bed", "Only Bed",
            "Full Credit", "Full Credit", "Full Credit", "Full Credit",
        };
    }

    private String[] getValidTypes() {
        ArrayList<String> typeList = new ArrayList<>();

        CMBItem itemH = (CMBItem) cmb_room_hotelname.getSelectedItem();

        for (Stay Stay : this.stayManager.getListHotelId(itemH.getKey())) {
            typeList.add(Stay.getType());
        }

        String[] validTypes = new String[typeList.size() * 4];

        for (int i = 0; i < typeList.size(); i++) {
            validTypes[4 * i] = typeList.get(i);
            validTypes[4 * i + 1] = typeList.get(i);
            validTypes[4 * i + 2] = typeList.get(i);
            validTypes[4 * i + 3] = typeList.get(i);
        }

        return validTypes;
    }

    public JTextField[] getValidInputs() {
        ArrayList<JTextField> tfList = new ArrayList<>();

        JTextField[] textFieldArray = getTextFields();
        String[] lodgingTypeArray = getLodgingTypes();

        ArrayList<String> typeList = new ArrayList<>();

        CMBItem itemH = (CMBItem) cmb_room_hotelname.getSelectedItem();

        for (Stay Stay : this.stayManager.getListHotelId(itemH.getKey())) {
            typeList.add(Stay.getType());
        }

        for (int i = 0; i < textFieldArray.length; i++) {
            if (typeList.contains(lodgingTypeArray[i]))
                tfList.add(textFieldArray[i]);
        }

        JTextField[] inputArray = new JTextField[tfList.size()];
        for (int i = 0; i < tfList.size(); i++)
            inputArray[i] = tfList.get(i);

        return inputArray;
    }



    public boolean checkInputs() {
        JTextField[] checkTextField = {tf_room_numberofbeds};
        if (Helper.isFieldListEmpty(checkTextField)){
            Helper.showMsg("fill");
            return true;
        }

        if (Helper.isComboBoxEmpty(cmb_room_hotelname)) {
            Helper.showMsg("fill");
            return true;
        }

        if (Helper.isComboBoxEmpty(cmb_room_roomname)) {
            Helper.showMsg("fill");
            return true;
        }

        if (Helper.isFieldListEmpty(getValidInputs())) {
            Helper.showMsg("fill");
            return true;
        }

        return false;
    }



    private void loadHotelComboBox() {
        cmb_room_hotelname.removeAllItems();

        for (Hotel hotel : this.hotelManager.getList()) {
            cmb_room_hotelname.addItem(new CMBItem(hotel.getHotelID(), hotel.getName()));
        }

        cmb_room_hotelname.setSelectedItem(null);
    }

    private void loadHotelComboBox(Room room) {
        cmb_room_hotelname.removeAllItems();
        ArrayList<Hotel> hotelList = this.hotelManager.getList();

        Hotel hotel = this.hotelManager.searchWithHotelId(room.getHotelId());

        for (int i = 0; i < hotelList.size(); i++) {
            if (hotel.getName().equals(hotelList.get(i).getName()))
                this.hotelIndex = i;
            cmb_room_hotelname.addItem(new CMBItem(hotelList.get(i).getHotelID(), hotelList.get(i).getName()));
        }
    }

    private String getItems() {
        String output = "";

        if (cb_room_tv.isSelected())
            output += "TV, ";

        if (cb_room_minibar.isSelected())
            output += "Minibar, ";

        if (cb_room_console.isSelected())
            output += "Console, ";

        if (cb_room_safe.isSelected())
            output += "Safe, ";

        if (cb_room_projector.isSelected())
            output += "Projector, ";

        return output.trim();
    }

    private void parseItems() {
        String items = this.roomManager.searchWithNameHotelId(getHotelFromComboBox().getKey(), getRoomNameFromComboBox()).getItem();

        cb_room_tv.setSelected(items.contains("TV"));
        cb_room_minibar.setSelected(items.contains("Minibar"));
        cb_room_console.setSelected(items.contains("Console"));
        cb_room_safe.setSelected(items.contains("Safe"));
        cb_room_projector.setSelected(items.contains("Projector"));
    }

    private CMBItem getHotelFromComboBox() {
        return (CMBItem) cmb_room_hotelname.getSelectedItem();
    }

    private String getRoomNameFromComboBox() {
        return cmb_room_roomname.getSelectedItem().toString();
    }
}
