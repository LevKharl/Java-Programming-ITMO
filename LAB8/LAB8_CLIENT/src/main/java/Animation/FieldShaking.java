package Animation;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class FieldShaking {

    private TranslateTransition tt;
    public static String errorStyle = String.format("-fx-border-color: #FF033E;");
    public static String normalStyle = String.format("-fx-border-color: #A9A9A9;");

    public FieldShaking(Node node) {
        tt = new TranslateTransition(Duration.millis(70), node);
        tt.setFromX(0f);
        tt.setByX(20f);
        tt.setFromY(0f);
        tt.setByY(20f);
        tt.setCycleCount(4);
        tt.setAutoReverse(true);
    }

    //Включении анимации
    public void playAnimation() {
        tt.playFromStart();
    }
}
