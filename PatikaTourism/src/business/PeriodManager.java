package business;

import dao.PeriodDao;
import entity.Period;

import java.util.ArrayList;

public class PeriodManager {
    private final PeriodDao periodDAO;

    public PeriodManager() {
        this.periodDAO = new PeriodDao();
    }

    public ArrayList<Period> getList() {
        return this.periodDAO.getList();
    }

    public boolean add(Period period) {
        if (period.getWinterStart().equals("") || period.getWinterEnd().equals("") || period.getSummerStart().equals("") || period.getSummerEnd().equals(""))
            return false;
        return this.periodDAO.add(period);
    }

    public boolean delete(int periodID) {
        return this.periodDAO.delete(periodID);
    }
    public boolean deleteWithHotelId(int hotelID) {
        return this.periodDAO.deleteWithHotelId(hotelID);
    }

    public boolean update(Period period) {
        if (period.getWinterStart().equals("") || period.getWinterEnd().equals("") || period.getSummerStart().equals("") || period.getSummerEnd().equals(""))
            return false;
        return this.periodDAO.update(period);
    }
    public Period searchWithPeriodId(int periodID) {
        return this.periodDAO.SearchWithPeriodId(periodID);
    }

    public Period SearchWithHotelId(int hotelID) {
        return this.periodDAO.searchWithHotelId(hotelID);
    }

}
