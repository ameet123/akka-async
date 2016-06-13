package org.ameet.app.model;

/**
 * Created by achaub001c on 6/13/2016.
 * Started using this since the one from Spring was not returning random quotes for some reason
 */
public class TechQuote {
    private String author;
    private int id;
    private String quote;
    private String permalink;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    @Override
    public String toString() {
        return "TechQuote{\n" +
                "\tauthor='" + author + '\'' +
                ", id=" + id +
                ",\n\tquote='" + quote + '\'' +
                ",\n\tpermalink='" + permalink + '\'' +
                '}';
    }
}
