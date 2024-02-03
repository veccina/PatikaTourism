package core;

import business.FacilityManager;
import entity.Facility;
import entity.Hotel;
import entity.Period;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.sql.Date;

public class Helper {
    public static void setTheme(String layoutName) {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (layoutName.equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static int getScreenCenter(String axis, Dimension size) {
        return switch (axis) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fieldList) {
        for (JTextField field : fieldList) {
            if (isFieldEmpty(field)) return true;
        }

        return false;
    }

    public static boolean isComboBoxEmpty(JComboBox cmb) {
        return cmb.getModel().getSelectedItem().equals("");
    }

    public static String querySearch(String table, String[] searchCriteria, String operator) {
        if (!operator.equals("AND") && !operator.equals("OR")) {
            throw new IllegalArgumentException("Operator must be 'AND' or 'OR'");
        }

        StringBuilder query = new StringBuilder("SELECT * FROM " + table + " WHERE ");
        boolean firstConditionAdded = false;

        for (int i = 0; i < searchCriteria.length; i += 2) {
            String field = searchCriteria[i];
            String value = searchCriteria[i + 1];

            if (!value.isEmpty()) {
                if (firstConditionAdded) {
                    query.append(" ").append(operator).append(" ");
                }
                query.append(field).append(" LIKE '%").append(value).append("%'");
                firstConditionAdded = true;
            }
        }

        if (!firstConditionAdded) {
            // Remove the WHERE clause if there are no conditions
            return query.substring(0, query.length() - " WHERE ".length());
        }

        return query.toString();
    }


    public static void showMsg(String str) {
        String message = "";
        String title = "";

        switch (str) {
            case "success" -> {
                message = "Başarılı";
                title = "Result";
            }
            case "invalid date" -> {
                message = "Hatalı gün seçimi !!";
                title = "Invalid Date";
            }
            case "fill" -> {
                message = "Lütfen tüm alanları doldurun !!";
                title = "Field Error";
            }
            case "notFound" -> {
                message = "Kullanıcı bulunamadı.";
                title = "Not Found";
            }
            case "error" -> {
                message = "Hata !!";
                title = "Error";
            }
            default -> message = str;
        }
        JOptionPane.showMessageDialog(
                null, message,
                title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String msgType) {
        optionPane();
        String message;

        switch (msgType) {
            case "sure":
                message = "Emin misiniz ?";
                break;
            default:
                message = msgType;
        }
        return JOptionPane.showConfirmDialog(null, message, "Onayla", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static void optionPane() {
        UIManager.put("OptionPane.yesButtonText", "YES");
        UIManager.put("OptionPane.noButtonText", "NO");
        UIManager.put("OptionPane.okButtonText", "OK");
    }

    public static ArrayList<Date> parseDates(ArrayList<String> dateList) throws ParseException {
        ArrayList<Date> formattedDates = new ArrayList<>();

        for (String str : dateList) {
            Date date = Date.valueOf(str.trim());
            formattedDates.add(date);
        }
        return formattedDates;
    }

    public static String parseFacility(Hotel hotel) {
        FacilityManager facilityManager = new FacilityManager();
        String output = "";

        for (Facility facility : facilityManager.getListHotelId(hotel.getHotelID())) {
            output += facility.getType() + ", ";

        }
        return output.trim();
    }

    public static int getHotelStay(Date strtDay, Date endDay) {
        LocalDate startDate = strtDay.toLocalDate();
        LocalDate endDate = endDay.toLocalDate();

        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static String whichPeriod(ArrayList<Date> dateList, Period period) {
        LocalDate winterStart = period.getWinterStart().toLocalDate();
        LocalDate winterEnd = period.getWinterEnd().toLocalDate();
        LocalDate summerStart = period.getSummerStart().toLocalDate();
        LocalDate summerEnd = period.getSummerEnd().toLocalDate();
        LocalDate arrival = dateList.get(0).toLocalDate();
        LocalDate departure = dateList.get(1).toLocalDate();

        if (arrival.isAfter(summerStart) && departure.isBefore(summerEnd))
            return "summer";
        if (arrival.isAfter(winterStart) && departure.isBefore(winterEnd)) {
            return "winter";
        }
        return null;
    }
}


