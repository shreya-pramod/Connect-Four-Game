package connectfour.gui;

import connectfour.model.ConnectFourBoard;
import connectfour.model.Observer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * The GUI for Connect Four game. This class encapsulates the View and Controller portions of the MVC architecture.
 *
 * @author  Shreya Pramod    sp3045@rit.edu
 */
public class ConnectFourGUI extends Application implements Observer {

    /** the rows in the model */
    private static final int ROW = ConnectFourBoard.ROWS;
    /** the columns in the Model */
    private static final int COL = ConnectFourBoard.COLS;
    /** the board is the Model */
    private ConnectFourBoard obj;
    /** empty disc image*/
    private Image empty = new Image(getClass().getResourceAsStream("empty.png"));
    /** black disc image*/
    private Image p1black = new Image(getClass().getResourceAsStream("p1black.png"));
    /** red disc image*/
    private Image p2red = new Image(getClass().getResourceAsStream("p2red.png"));
    /** the label to display number of moves */
    private Label moves;
    /** the label to display the current player*/
    private Label player;
    /** the label to display the status of the game */
    private Label status;

    /**
     * Initializing a new Connect Four Board.
     */
    public void init() {
        this.obj = new ConnectFourBoard();
        obj.addObserver(this);
        this.moves = new Label();
        this.player = new Label();
        this.status = new Label();
    }

    /**
     * The start method displays the board and the current statistics.
     */
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane borderPane = new BorderPane();

        this.moves.setText(obj.getMovesMade()+" moves made");
        this.player.setText("Current Player: "+obj.getCurrentPlayer());
        this.status.setText("Status: "+obj.getGameStatus());

        HBox hbox = new HBox();
        hbox.setSpacing(140);
        hbox.getChildren().addAll(moves, player, status);
        borderPane.setBottom(hbox);

        GridPane gridPane = new GridPane();

        gridPane = createBoard(gridPane);

        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setTitle("ConnectFourGUI");
        stage.show();
    }

    /*
     ******************* THE CONTROLLER SECTION *********************************
     */
    /**
     * A helper function that builds a grid of buttons to return.
     *
     * @return the grid pane
     */
    private GridPane createBoard(GridPane gridPane) {

        Button[][] connectButton = new Button[ROW][COL];

        for (int row=0; row<ROW;row++) {
            for (int col = 0; col < COL; col++) {
                //setting the buttons initially as empty
                connectButton[row][col] = new Button();
                connectButton[row][col].setGraphic(new ImageView(empty));
                connectButton[row][col].setId("e");
                gridPane.add(connectButton[row][col], col, row);
                int presentCol = col;

                // changing the button to the respective player when pressed
                connectButton[row][col].setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        for (int row = ROW - 1; row >= 0; --row) {
                            Button button = connectButton[row][presentCol];
                            if (obj.getCurrentPlayer() == ConnectFourBoard.Player.P1 && obj.getGameStatus() == ConnectFourBoard.Status.NOT_OVER && obj.isValidMove(presentCol) && button.getId().equals("e")) {
                                //updating the button details on the board
                                setButton(button);
                                obj.makeMove(GridPane.getColumnIndex(connectButton[row][presentCol]));
                                update(obj);
                                break;
                            }
                            else if (obj.getCurrentPlayer() == ConnectFourBoard.Player.P2 && obj.getGameStatus() == ConnectFourBoard.Status.NOT_OVER && obj.isValidMove(presentCol) && button.getId().equals("e")) {
                                //updating the button details on the board
                                setButton(button);
                                obj.makeMove(GridPane.getColumnIndex(connectButton[row][presentCol]));
                                update(obj);
                                break;
                            }
                        }
                    }
                });
            }
        }
        return gridPane;
    }

    /**
     * Sets the image and id for the called button on the board.
     *
     */
    private void setButton(Button button) {
        if (obj.getCurrentPlayer() == ConnectFourBoard.Player.P1) {
            button.setGraphic(new ImageView(p1black));
            button.setId("b");
        }
        else if((obj.getCurrentPlayer() == ConnectFourBoard.Player.P2)){
            button.setGraphic(new ImageView(p2red));
            button.setId("r");
        }
    }

    /*
     ******************* THE VIEW SECTION ***************************************
     */

    /**
     * The update for the GUI prints the current statistics onto the screen.
     */
    @Override
    public void update(Object o) {
        this.moves.setText(obj.getMovesMade()+" moves made");
        this.player.setText("Current Player: "+obj.getCurrentPlayer());
        this.status.setText("Status: "+obj.getGameStatus());
    }

    /**
     * The main routine which launches the ConnectFour Game.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args){
        ConnectFourGUI obj = new ConnectFourGUI();
        Application.launch(args);
    }
}
