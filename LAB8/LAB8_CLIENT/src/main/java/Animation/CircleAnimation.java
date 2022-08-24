package Animation;
import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class CircleAnimation {

    private ScaleTransition tt;

    public CircleAnimation(Node node) {
        tt = new ScaleTransition(Duration.seconds(1), node);
        tt.setToX(5);
        tt.setToY(5);
    }

    //��������� ��������
    public void playAnimation() {
        tt.playFromStart();
    }
}
