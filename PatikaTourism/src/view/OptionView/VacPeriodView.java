package view.OptionView;

import business.PeriodManager;
import core.Helper;
import entity.Hotel;
import entity.Period;
import view.Layout;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class VacPeriodView extends Layout {
    private JPanel container;
    private JPanel pnl_period;
    private JPanel pnl_period_top;
    private JLabel lbl_period_winter1;
    private JFormattedTextField ftf_period_winterstartdate;
    private JFormattedTextField ftf_period_winterenddate;
    private JLabel lbl_period_summer1;
    private JFormattedTextField ftf_period_summerstartdate;
    private JFormattedTextField ftf_period_summerenddate;
    private JLabel lbl_period_winterstartdate;
    private JLabel lbl_period_winterenddate;
    private JLabel lbl_period_summerstartdate;
    private JLabel lbl_period_summerenddate;
    private JButton btn_period_update;

    private Hotel hotel;
    private Period period;
    private PeriodManager periodManager;;


    // Değerlendirme sorusu 11 : Acente personeli otel tatil dönemlerini sisteme ekleyebiliyor.
    public VacPeriodView(Hotel hotel) {
        this.add(container);
        this.hotel = hotel;

        this.periodManager = new PeriodManager();
        this.period = periodManager.SearchWithHotelId(hotel.getHotelID());

        this.initView(400, 350, " Tatil Dönemi Ayarları");

        displayPeriodData();
        initAccessButton();
    }

    public void displayPeriodData() {
        if (doesExist()) {
            Period period = this.periodManager.SearchWithHotelId(this.hotel.getHotelID());

            java.sql.Date winterStart = period.getWinterStart();
            java.sql.Date winterEnd = period.getWinterEnd();
            java.sql.Date summerStart = period.getSummerStart();
            java.sql.Date summerEnd = period.getSummerEnd();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            ftf_period_winterstartdate.setValue(formatter.format(winterStart));
            ftf_period_winterenddate.setValue(formatter.format(winterEnd));
            ftf_period_summerstartdate.setValue(formatter.format(summerStart));
            ftf_period_summerenddate.setValue(formatter.format(summerEnd));
        }
    }

    public void initAccessButton() {
        btn_period_update.addActionListener(e -> {
            ArrayList<java.sql.Date> dateList;

            try {
                checkDateValidity();
                dateList = Helper.parseDates(getTextFieldStrings());
            } catch (ParseException ignored) {
                Helper.showMsg("Geçersiz tarih");
                return;
            }

            if (doesExist()) {
                Period period = this.periodManager.SearchWithHotelId(this.hotel.getHotelID());
                if (periodManager.update(getPeriod(period.getPeriodID(), dateList.get(0), dateList.get(1), dateList.get(2), dateList.get(3))))
                    Helper.showMsg("success");
                else
                    Helper.showMsg("error");
            } else {
                if (periodManager.add(getPeriod(dateList.get(0), dateList.get(1), dateList.get(2), dateList.get(3))))
                    Helper.showMsg("success");
                else
                    Helper.showMsg("error");
            }

            dispose();
        });
    }

    private void checkDateValidity() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);

        ArrayList<String> tfList = getTextFieldStrings();

        for (int i = 0; i < tfList.size(); i++) {
            Date date = formatter.parse(tfList.get(i));
        }
    }

    private ArrayList<String> getTextFieldStrings() {
        ArrayList<String> list = new ArrayList<>();
        list.add(ftf_period_winterstartdate.getText().trim());
        list.add(ftf_period_summerenddate.getText().trim());
        list.add(ftf_period_summerstartdate.getText().trim());
        list.add(ftf_period_summerenddate.getText().trim());

        return list;
    }

    public Period getPeriod(java.sql.Date winterStart, java.sql.Date winterEnd, java.sql.Date summerStart, java.sql.Date summerEnd) {
        Period obj = new Period();

        obj.setHotelID(this.hotel.getHotelID());
        obj.setWinterStart(winterStart);
        obj.setWinterEnd(winterEnd);
        obj.setSummerStart(summerStart);
        obj.setSummerEnd(summerEnd);

        return obj;
    }

    public Period getPeriod(int id, java.sql.Date winterStart, java.sql.Date winterEnd, java.sql.Date summerStart, java.sql.Date summerEnd) {
        return new Period(id, this.hotel.getHotelID(), winterStart, winterEnd, summerStart, summerEnd);
    }

    public boolean doesExist() {
        return (periodManager.SearchWithHotelId(this.hotel.getHotelID())) != null;
    }

}
