package notika.assignment_4;

/**
 * Created by Nataly on 2015-09-20.
 */
public class Treasure {

    String lat;
    String lng;
    String question;
    String alt_1;
    String alt_2;
    String alt_3;
    int answer;
    int icon;

    Treasure(String _lat, String _lng, String _question, String _alt_1, String _alt_2, String _alt_3, int _answer, int _icon) {
        lat = _lat;
        lng = _lng;
        question = _question;
        alt_1 = _alt_1;
        alt_2 = _alt_2;
        alt_3 = _alt_3;
        answer = _answer;
        icon = _icon;
    }
}