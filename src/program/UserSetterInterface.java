package program;

import javafx.collections.ObservableList;

import java.io.FileNotFoundException;

public interface UserSetterInterface {
    void setp(String lokasi) throws FileNotFoundException;
    void setBgMenu1();
    void setBgMenu2();
    void setUsername(String user);
}
