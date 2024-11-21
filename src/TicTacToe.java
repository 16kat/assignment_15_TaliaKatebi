import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

public class TicTacToe extends Application {
    private static final int SIZE = 5;
    private char currentPlayer = 'X';
    private char[][] board = new char[SIZE][SIZE];

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                StackPane cell = createCell(row, col);
                grid.add(cell, col, row);
                board[row][col] = ' '; // Empty cell
            }
        }

        Scene scene = new Scene(grid, 500, 500);
        stage.setScene(scene);
        stage.setTitle("TicTacToe 5x5");
        stage.show();
    }

    private StackPane createCell(int row, int col) {
        StackPane cell = new StackPane();
        cell.setPrefSize(100, 100);
        cell.setStyle("-fx-border-color: black; -fx-background-color: white;");
        Text text = new Text();
        text.setFont(new Font(24));

        cell.getChildren().add(text);

        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if (text.getText().isEmpty()) {
                text.setText(String.valueOf(currentPlayer));
                board[row][col] = currentPlayer;

                if (checkWin(row, col)) {
                    showAlert("Player " + currentPlayer + " wins!");
                    resetBoard();
                } else if (isBoardFull()) {
                    showAlert("It's a draw!");
                    resetBoard();
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; 
                }
            }
        });

        return cell;
    }

    private boolean checkWin(int row, int col) {

        return checkDirection(row, col, 0, 1) ||
               checkDirection(row, col, 1, 0) ||
               checkDirection(row, col, 1, 1) ||
               checkDirection(row, col, 1, -1);
    }

    private boolean checkDirection(int row, int col, int dRow, int dCol) {
        int count = 1;
        count += countInDirection(row, col, dRow, dCol);
        count += countInDirection(row, col, -dRow, -dCol);
        return count >= 5;
    }

    private int countInDirection(int row, int col, int dRow, int dCol) {
        int count = 0;
        char target = board[row][col];

        for (int step = 1; step < SIZE; step++) {
            int newRow = row + step * dRow;
            int newCol = col + step * dCol;

            if (newRow < 0 || newRow >= SIZE || newCol < 0 || newCol >= SIZE || board[newRow][newCol] != target) {
                break;
            }
            count++;
        }

        return count;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void resetBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = ' ';
            }
        }
        start(new Stage());
    }
}
