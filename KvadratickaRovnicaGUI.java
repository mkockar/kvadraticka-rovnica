import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Path;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.paint.Color; 
import java.util.Collections;
import javafx.scene.control.DialogPane; 

public class KvadratickaRovnicaGUI extends Application{
  private Label lbTitle;
  private TextField tfa;      
  private TextField tfb;
  private TextField tfc; 
  private Label lbx1;
  private Label lbx2;
  private Label lbdiskr;
  private Button btCalc; 
  
  public void start (Stage stage){
    lbTitle = new Label("KVADRATICKA ROVNICA");
    lbTitle.getStyleClass().add("title");
    lbTitle.setMinWidth(Double.MIN_VALUE);
    lbTitle.setMaxWidth(Double.MAX_VALUE);
    lbTitle.setAlignment(Pos.CENTER);
    
    tfa = new TextField();
    tfa.setPromptText("CLEN A");
    tfb = new TextField();
    tfb.setPromptText("CLEN B");
    tfc = new TextField();
    tfc.setPromptText("CLEN C");
    
    btCalc = new Button("POCITAJ");
    btCalc.setOnAction(new Klik());
    btCalc.getStyleClass().add("btn");
    
    lbx1 = new Label("X1 = "); 
    lbx2 = new Label("X2 = "); 
    lbdiskr = new Label("DISKRIMINANT = ");
    
    final NumberAxis osx = new NumberAxis(-40, 40, 1);
    final NumberAxis osy = new NumberAxis(-40, 40, 1);
    final LineChart<Number, Number> graf =  new LineChart<Number, Number>(osx, osy);
    graf.setTitle("GRAF KVADRATICKEJ FUNKCIE");
    graf.setLegendVisible(false);
    graf.setCreateSymbols(false);
    
    graf.getStyleClass().add("graf");
    graf.getData().add(series);
    series.getNode().lookup(".chart-series-line");
    
    VBox vbox = new VBox(lbTitle, tfa, tfb, tfc, lbx1, lbx2, lbdiskr, btCalc);
    //vbox.getChildren().add(btCalc);
    vbox.setPadding(new Insets(10, 10, 10, 10));
    vbox.setAlignment(Pos.CENTER);
    vbox.setSpacing(20);
    
    ToolBar toolbar = new ToolBar(vbox);
    toolbar.setMinHeight(Double.MIN_VALUE);
    toolbar.setMaxHeight(Double.MAX_VALUE);
    toolbar.getStyleClass().add("toolbar");
    
    BorderPane borderpane = new BorderPane();
    borderpane.setCenter(graf);
    borderpane.setAlignment(lbTitle, Pos.CENTER);
    borderpane.setMargin(lbTitle, new Insets(0, 0, 0, 0));
    borderpane.setLeft(toolbar);
    borderpane.setMargin(toolbar, new Insets(0, 15, 0, 0));
    
    Scene scene = new Scene(borderpane,1000,600);
    scene.getStylesheets().add("./css/style.css");
    stage.getIcons().add(new Image("./media/icon.png"));
    stage.setScene(scene);
    stage.setTitle("KVADRATICKA ROVNICA");
    
    stage.show();
  }
  
  XYChart.Series series = new XYChart.Series();
  
  public class Klik implements EventHandler{
    private boolean klik = false;
    public void handle(Event event){
      float a, b ,c;
      try {
        a = Float.parseFloat(tfa.getText());
        b = Float.parseFloat(tfb.getText());
        c = Float.parseFloat(tfc.getText());
        
        if(Vypocty.diskriminant(a,b,c) < 0 && klik == false){
          ImageView imgview =  new ImageView(new Image("./media/error.jpg"));
          imgview.setFitHeight(50);
          imgview.setFitWidth(50);
          
          Alert alertKomplex = new Alert(Alert.AlertType.ERROR);
          alertKomplex.setHeaderText("UNABLE TO CALCULATE");
          alertKomplex.setContentText("CANNOT CALCULATE WITH COMPLEX NUMBERS");
          alertKomplex.setGraphic(imgview);
          
          DialogPane dp = alertKomplex.getDialogPane();
          dp.getStylesheets().add(getClass().getResource("./css/alert.css").toExternalForm());
          dp.getStyleClass().add("alertKomplex");
          
          Stage stage = (Stage)alertKomplex.getDialogPane().getScene().getWindow();
          stage.getIcons().add(new Image("./media/error.jpg"));
          alertKomplex.showAndWait();
        }
        
        series.getData().clear();
        for(float i = -40; i <= 40 ; i += 0.5F){
          float x = Vypocty.vypocetX(a,b);
          float y = Vypocty.vypocetY(a,b,c);
          float z = (a * (float)Math.pow(i, 2));
          series.getData().add(new XYChart.Data(i + x, z + y));
        }
        
        lbx1.setText("X1 = " + Vypocty.kvadratickaRovnicaX1(a,b,c));
        lbx2.setText("X2 = " + Vypocty.kvadratickaRovnicaX2(a,b,c));
        lbdiskr.setText("DISKRIMINANT = " + Vypocty.diskriminant(a,b,c)); 
        btCalc.setText("CLEAR");
        
        if(klik == true){
          lbx1.setText("X1 = ");                                            
          lbx2.setText("X2 = ");
          lbdiskr.setText("DISKRIMINANT = ");
          btCalc.setText("POCITAJ");
          series.getData().clear();
          tfa.clear();
          tfb.clear();
          tfc.clear();
          klik = false;
        }else{
          klik = true;
        }
        
      }catch(Exception exception) {
        ImageView imgview =  new ImageView(new Image("./media/alert.png"));
        imgview.setFitHeight(50);
        imgview.setFitWidth(50);
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("WRONG INPUT"); 
        alert.setGraphic(imgview);
        alert.setContentText("INSERT NUMBERS");
        
        DialogPane dp = alert.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("./css/alert.css").toExternalForm());
        dp.getStyleClass().add("alert");
        
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("./media/alert.png"));
        
        btCalc.setText("CLEAR");
        
        alert.showAndWait();
        if(klik == true){
          lbx1.setText("X1 = ");                                            
          lbx2.setText("X2 = ");
          lbdiskr.setText("DISKRIMINANT = ");
          btCalc.setText("POCITAJ");
          series.getData().clear();
          tfa.clear();
          tfb.clear();
          tfc.clear();
          klik = false;
        }else{
          klik = true;
        }
      }      
    }
  }
}     