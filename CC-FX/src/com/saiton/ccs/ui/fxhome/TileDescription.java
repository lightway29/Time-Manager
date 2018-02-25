package com.saiton.ccs.ui.fxhome;

import java.util.Objects;

/**
 *
 * @author Saitonya
 */
public class TileDescription {

    private final String page;
    private final String title;

    public String getPage() {
        return page;
    }


    public String getTitle() {
        return title;
    }

    public TileDescription(String page, String title) {
        this.page = page;
        this.title = title;
    }



    @Override
    public int hashCode() {
        return title.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TileDescription other = (TileDescription) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }

}
