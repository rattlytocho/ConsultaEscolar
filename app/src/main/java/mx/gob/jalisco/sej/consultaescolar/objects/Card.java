package mx.gob.jalisco.sej.consultaescolar.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 28/06/16.
 */
public class Card {

    private String url;
    private String text;
    private int image;
    public static List<Card> Card = new ArrayList<>();


    public Card(String url, String text, int image) {
        this.url = url;
        this.text = text;
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
