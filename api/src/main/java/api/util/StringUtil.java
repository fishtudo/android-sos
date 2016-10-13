package api.util;

import android.content.Context;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StringUtil {

    public static String formatCurrency(int integerValueToDivide) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        float value = ((float) integerValueToDivide) / 100;
        return decimalFormat.format(value);
    }

    public static String formatCurrencyWithLiteral(int integerValueToDivide) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
        float value = ((float) integerValueToDivide) / 100;
        return "R$" + decimalFormat.format(value);
    }

    public static int unFormatCurrency(String numberToUnmask) {
        return Integer.parseInt(0 + numberToUnmask.replaceAll("[^\\d]", "").replaceFirst("^0+(?!$)", ""));
    }

    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }


    public static String writeItemsSeparateByComa(List<String> stringList) {
        stringList = removeBlanksFromList(stringList);

        if (stringList.size() == 0)
            return "";

        if (stringList.size() == 1) {
            return stringList.get(0);
        }
        String result = stringList.get(0);

        for (int i = 1; i < stringList.size(); i++) {
            if (stringList.size() - 1 == i) {
                result += " e " + stringList.get(i);
            } else {
                result += ", " + stringList.get(i);
            }
        }

        return result;
    }

    public static String formatDate(Context context, Date date) {
        SimpleDateFormat dataFormatter = new SimpleDateFormat("dd/MM/yyyy");
        return dataFormatter.format(date);
    }

    public static List<String> removeBlanksFromList(List<String> stringList) {
        List<String> stringListWithoutBlanks = new ArrayList<>();

        for (String string : stringList) {
            if (!isEmpty(string)) {
                stringListWithoutBlanks.add(string);
            }
        }
        return stringListWithoutBlanks;
    }

    DecimalFormat df = new DecimalFormat("#.##");
}