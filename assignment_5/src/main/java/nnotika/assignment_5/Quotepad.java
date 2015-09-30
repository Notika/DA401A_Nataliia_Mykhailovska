package nnotika.assignment_5;

import java.io.Serializable;

/**
 * Created by Notika on 2015-09-28.
 */
public class Quotepad implements Serializable {

    private String quote;

    public Quotepad(String quote) {
        super();
        this.quote = quote;
    }

    public String getQuote() {
        return this.quote;
    }
    // get and set methods
}