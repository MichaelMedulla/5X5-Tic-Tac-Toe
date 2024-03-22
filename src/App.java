
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Ellipse;

public class App extends Application
{
    private char who = 'X';

    private Cell[][] tile =  new Cell[5][5];

    private Label turn = new Label("X's turn to play");

    @Override
    public void start(Stage primaryStage)
    {
        GridPane grid = new GridPane(); 
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                grid.add(tile[i][j] = new Cell(), j, i);
            }
        }

        BorderPane border = new BorderPane();
        border.setCenter(grid);
        border.setBottom(turn);
        
        Scene scene = new Scene(border, 450, 450);
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public boolean used()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                if (tile[i][j].getToken() == ' ')
                {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean win(char token)
    {
        for (int i = 0; i < 5; i++)
        {
            if (tile[i][0].getToken() == token && tile[i][1].getToken() == token && tile[i][2].getToken() == token && tile[i][3].getToken() == token && tile[i][4].getToken() == token)
            {
                return true;
            }
        }
        
        for (int j = 0; j < 5; j++)
        {
            if (tile[0][j].getToken() ==  token && tile[1][j].getToken() == token && tile[2][j].getToken() == token && tile[3][j].getToken() == token && tile[4][j].getToken() == token)
            {
                return true;
            }

            if (tile[0][0].getToken() == token && tile[1][1].getToken() == token && tile[2][2].getToken() == token && tile[3][3].getToken() == token && tile[4][4].getToken() == token)
            {
                return true;
            }

            if (tile[0][4].getToken() == token && tile[1][3].getToken() == token && tile[2][2].getToken() == token && tile[3][1].getToken() == token && tile[4][0].getToken() == token)
            {
                return true;
            }
        }
        return false;
    }

    public class Cell extends Pane
    {
        private char token = ' ';

        public Cell()
        {
            setStyle("-fx-border-color: black"); 
            this.setPrefSize(800, 800);
            this.setOnMouseClicked(e -> click());
        }

        public char getToken()
        {
            return token;
        }

        public void setToken(char c)
        {
            token = c;
        
            if (token == 'X')
            {
                Line xL = new Line(10, 10, this.getWidth() - 10, this.getHeight() - 10);
                xL.endXProperty().bind(this.widthProperty().subtract(10));
                xL.endYProperty().bind(this.heightProperty().subtract(10));
                Line xR = new Line(10, this.getHeight() - 10, this.getWidth() - 10, 10);
                xR.startYProperty().bind(this.heightProperty().subtract(10));
                xR.endXProperty().bind(this.widthProperty().subtract(10));
                
                this.getChildren().addAll(xL, xR); 
            }
            else if (token == 'O')
            {
                Ellipse O = new Ellipse(this.getWidth() / 2, this.getHeight() / 2, this.getWidth() / 2 - 10, this.getHeight() / 2 - 10);
                O.centerXProperty().bind(this.widthProperty().divide(2));
                O.centerYProperty().bind(this.heightProperty().divide(2));
                O.radiusXProperty().bind(this.widthProperty().divide(2).subtract(10));        
                O.radiusYProperty().bind(this.heightProperty().divide(2).subtract(10));   
                O.setStroke(Color.BLACK);
                O.setFill(Color.WHITE);
                
                getChildren().add(O);
            }
        }

        private void click()
        {
            if (token == ' ' && who != ' ')
            {
                setToken(who);

                if (win(who))
                {
                    turn.setText(who + " won");
                    who = ' ';
                }
                else if (used())
                {
                    turn.setText("Draw");
                    who = ' ';
                }
                else
                {
                    who = (who == 'X') ? 'O' : 'X';
                    turn.setText(who + "'s turn");
                }
            }
        }
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}