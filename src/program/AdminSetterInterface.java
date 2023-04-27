package program;

import javafx.collections.ObservableList;

import java.io.FileNotFoundException;

public interface AdminSetterInterface {
    void setp(String lokasi) throws FileNotFoundException;
    void setBgMenu1();
    void setBgMenu2();
    void setCbBentuk(ObservableList<String> bentuk);
    void setCbPanjang(ObservableList<String> panjang);
    void setUsername(String user);
    void settablesosis(String s);
    void settablenugget(String s);
}
