package utils;

import javafx.scene.control.TextField;

public class validate {
    public static boolean isTextAllFilled(TextField... items) {
        for (TextField item : items) {
            if (!isTextFilled(item))
                return false;
        }
        return true;
    }

    public static boolean isTextFilled(TextField item) {
        return !item.getText().trim().isEmpty();
    }
}
