package org.team3082.chicken_planner.UIElements.CustomNodes;

import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class WindowBarNode extends HBox {
    public WindowBarNode() {
        getChildren().addAll(addTitleText());
    }

    public Text addTitleText() {
        Text titleText = new Text(
            "Chicken Planner"
        );

        titleText.setId("TitleText");
        
        return titleText;
    }

}
