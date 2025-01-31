package org.team3082.chicken_planner.UIElements.Utilities;

import javafx.scene.text.Text;

public class TextUtil {

    /**
     * Creates a Text object with the specified content, ID, and style class.
     *
     * @param content the text content to display.
     * @param id the ID to assign to the text object for identification.
     * @return a Text object with the specified properties.
     */
    public static Text createText(String content, String id, String textClass) {
        Text text = new Text(content);
        text.setId(id);
        text.getStyleClass().add(textClass);
        return text;
    }
}
