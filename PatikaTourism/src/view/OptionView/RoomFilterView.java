package view.OptionView;

import business.*;
import core.Helper;
import entity.*;
import view.Layout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public class RoomFilterView extends Layout {

    private JPanel container;
    private JPanel pnl_filteredroom_list;
    private JScrollPane scrl_filteredroom_list;
    private JTable tbl_filteredroom_list;

    private DefaultTableModel mdl_filteredroom_list;
    private Object[] row_filteredroom_list;
    HotelManager hotelManager;
    RoomManager roomManager;
    PeriodManager periodManager;
    StayManager stayManager;
    PriceManager priceManager;
    ArrayList<Room> filteredRooms;
    ArrayList<String> guestInformation;

    JPopupMenu filteredRoomListPopup;
    public RoomFilterView(ArrayList<Room> filteredRooms, ArrayList<String> guestInformation) {
        this.add(container);
        this.initView(650, 400, "Oda Filtreleme Ekranı");

        this.hotelManager = new HotelManager();
        this.roomManager = RoomManager.getInstance();
        this.periodManager = new PeriodManager();
        this.stayManager = new StayManager();
        this.priceManager = new PriceManager();

        this.filteredRooms = filteredRooms;
        this.guestInformation = guestInformation;
        this.filteredRoomListPopup = new JPopupMenu();


        initFilteredRoomList();
        initRoomPopupMenu();

    }

    private void initFilteredRoomList() {
        mdl_filteredroom_list = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                if (column == 0 || column == 1 || column == 2 || column == 3 || column == 4 || column == 5 || column == 6)
                    return false;
                return super.isCellEditable(row, column);
            }
        };
        Object[] col_filteredroom_list  = {"Hotel Name", "City", "Region", "Address", "Telephone", "Star", "Facilities", "Room ID",
                "Room Name", "Lodging Type", "Number of Beds", "Items", "Nights", "Adults", "Children", "Total Price"};
        mdl_filteredroom_list.setColumnIdentifiers(col_filteredroom_list);
        row_filteredroom_list = new Object[col_filteredroom_list.length];

        tbl_filteredroom_list.setModel(mdl_filteredroom_list);
        tbl_filteredroom_list.getTableHeader().setReorderingAllowed(false);

        loadFilteredRoomModel(this.filteredRooms);
    }
    private void initRoomPopupMenu() {
        JMenuItem reserveRoom= new JMenuItem("Reserve This Room");

        this.filteredRoomListPopup.add(reserveRoom);



        createFilteredRoomMouseListener();

        createReserveRoomListener(reserveRoom);

    }
    private void createFilteredRoomMouseListener() {
        tbl_filteredroom_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int r = tbl_filteredroom_list.rowAtPoint(e.getPoint());
                if (r >= 0 && r < tbl_filteredroom_list.getRowCount()) {
                    tbl_filteredroom_list.setRowSelectionInterval(r, r);
                } else {
                    tbl_filteredroom_list.clearSelection();
                }

                int rowIndex = tbl_filteredroom_list.getSelectedRow();

                if (rowIndex < 0)
                    return;
                if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
                    filteredRoomListPopup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private void createReserveRoomListener(JMenuItem reserveRoom) {
        reserveRoom.addActionListener(e -> {

            int selectedID = Integer.parseInt(tbl_filteredroom_list.getValueAt(tbl_filteredroom_list.getSelectedRow() , 7).toString());
            Room room = this.roomManager.searchRoomId(selectedID);

            new BookingView(guestInformation, room);
        });
    }

    private void loadFilteredRoomModel(ArrayList<Room> roomList) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_filteredroom_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Room room : roomList) {
            ArrayList<Stay> stayList = this.stayManager.getListHotelId(room.getHotelId());

            for (Stay Stay : stayList) {
                i = 0;
                fillRow(room, Stay, i);
            }

        }
    }

    private void fillRow(Room room, Stay Stay, int i) {
        Hotel hotel = this.hotelManager.searchWithHotelId(room.getHotelId());
        Period period = this.periodManager.SearchWithHotelId(room.getHotelId());

        String arrival = this.guestInformation.get(2);
        String departure = this.guestInformation.get(3);

        ArrayList<String> stringList = new ArrayList<>();
        stringList.add(arrival);
        stringList.add(departure);

        ArrayList<Date> dateList;
        try {
            dateList = Helper.parseDates(stringList);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        int nights = Helper.getHotelStay(dateList.get(0), dateList.get(1));

        String winterOrSummer = Helper.whichPeriod(dateList, period);

        Price price = this.priceManager.searchStayIdRoomId(Stay.getLodgingID(), room.getRoomID());

        double totalPrice;

        int adultNumber = this.guestInformation.get(0).isEmpty() ? 0 : Integer.parseInt(this.guestInformation.get(0));
        int childNumber = this.guestInformation.get(1).isEmpty() ? 0 : Integer.parseInt(this.guestInformation.get(1));

        double winterAdultPrice = 0;
        double winterChildPrice = 0;
        double summerAdultPrice = 0;
        double summerChildPrice = 0;

        if (price != null) {
            winterAdultPrice = price.getWinterAdultPrice();
            winterChildPrice = price.getWinterChildPrice();
            summerAdultPrice = price.getSummerAdultPrice();
            summerChildPrice = price.getSummerChildPrice();
        }

        if ("winter".equals(winterOrSummer))
            totalPrice = ((adultNumber * winterAdultPrice) + (childNumber * winterChildPrice)) * nights;
        else
            totalPrice = ((adultNumber * summerAdultPrice) + (childNumber * summerChildPrice)) * nights;

        row_filteredroom_list[i++] = hotel.getName();
        row_filteredroom_list[i++] = hotel.getCity();
        row_filteredroom_list[i++] = hotel.getRegion();
        row_filteredroom_list[i++] = hotel.getAddress();
        row_filteredroom_list[i++] = hotel.getTelephone();
        row_filteredroom_list[i++] = hotel.getStar();
        row_filteredroom_list[i++] = Helper.parseFacility(hotel);
        row_filteredroom_list[i++] = room.getRoomID();
        row_filteredroom_list[i++] = room.getName();
        row_filteredroom_list[i++] = Stay.getType();
        row_filteredroom_list[i++] = room.getNumberOfBeds();
        row_filteredroom_list[i++] = room.getItem();
        row_filteredroom_list[i++] = nights;
        row_filteredroom_list[i++] = adultNumber;
        row_filteredroom_list[i++] = childNumber;
        row_filteredroom_list[i++] = totalPrice;

        mdl_filteredroom_list.addRow(row_filteredroom_list);
    }
}
