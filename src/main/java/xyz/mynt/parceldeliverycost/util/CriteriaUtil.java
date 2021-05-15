package xyz.mynt.parceldeliverycost.util;

public class CriteriaUtil {

    private CriteriaUtil() {

    }

    public static boolean compareToCriteria(Double request, String operator, Double value) {
           switch(operator) {
            case ">":
                return request > value;
            case ">=":
                return request >= value;
            case "=":
                return request.equals(value);
            case "<=":
                return request <= value;
            case "<":
                return request < value;
            default:
                return false;
        }
    }
}
