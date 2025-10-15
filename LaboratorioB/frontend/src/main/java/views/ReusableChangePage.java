public class ReusableChangePage {

    private AnchorPane MoveToPage(String fxmlPath, AnchorPane pane) {
        if (pane == null) {
        try {
            pane = new FXMLLoader(getClass().getResource(fxmlPath)).load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        return pane;
    }

    private AnchorPane ChangeToPage(String fxmlPath, AnchorPane pane) {
        if (pane == null) {
    }
        return pane;
    }

}
