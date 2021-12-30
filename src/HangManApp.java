import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HangManApp extends Application{
    final int TOTAL_LIVES = 6;
    int livesLeft;
    int livesUsed = 0;
    String lettersGuessed = "";
    ImageView img = new ImageView();
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException{
        
        //initializing borderPanes
        BorderPane pane1 = new BorderPane();
        BorderPane pane2 = new BorderPane();
        //initializing scenes
        Scene scene = new Scene(pane1);
        Scene scene2 = new Scene(pane2);
        // v box initializing
        VBox vBox1 = new VBox();
        VBox vBox2 = new VBox();
        
        //top of scene1
        Text title = new Text("Select a word!");
        title.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 40));
        //tf for scene1
        TextField tf = new TextField("");
        //btn for scene1
        Button btn = new Button();
        btn.setText("Select");
        //scene1 button action| gets length of word selected, validates word exsists and is a word that exsists in the words.txt file. Validates words.txt exsists.
        String[] userPick = new String[1];
        btn.setOnAction((ActionEvent event) -> {
            int length = tf.getLength();
            String word = getWord(tf.getText(), tf);
            userPick[0] = word;
            try {
                fileReader(word);
                primaryStage.setScene(scene2);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HangManApp.class.getName()).log(Level.SEVERE, null, ex);
            } catch (java.util.NoSuchElementException ex) {
                noSuchElementHandler();
            }
        });
        
        //vbox1 items
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(10);
        vBox1.setMargin(tf, new Insets(20,12,12,20));
        vBox1.getChildren().addAll(title, tf, btn);
        
        //vbox2 items
        Button button2 = new Button ("Return");
        Label label = new Label("Enter your guess (a letter)");
        Label label2 = new Label();
        Label livesLBL = new Label("Total Lives: " + TOTAL_LIVES);
        vBox2.setAlignment(Pos.CENTER);
        TextField tf2 = new TextField("");
        //add items to pane1 
        pane1.setCenter(vBox1);
        primaryStage.setTitle("Hang Man");
        primaryStage.setScene(scene);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setResizable(false);
        primaryStage.show();
        //add items to pane2
        vBox2.getChildren().addAll(livesLBL,setImage(livesUsed), label, tf2, button2, label2);
        vBox2.setSpacing(10);
        pane2.setCenter(vBox2);
        button2.setOnAction(e -> {
            String guessed = tf2.getText();
            
            if (userPick[0].contains(guessed) == false){
                livesUsed++;
                livesLeft = TOTAL_LIVES - livesUsed;
                livesLBL.setText("Lives Left: " + livesLeft);
            }
            tf2.setText("");
            lettersGuessed += guessed;
            label2.setText(fillTheWord(userPick[0], guessed));
            try{
            setImage(livesUsed);
            }catch(FileNotFoundException ex){
                System.out.println("you thought");
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    //imports text file.
    public static void fileReader(String w) throws FileNotFoundException{
        File file = new File("/Users/Joe/NetBeansProjects/SampleProjects/words.txt");
        boolean flag = false;
        try{
            Scanner scan = new Scanner(file);
            while (scan.hasNext() || (flag == false)){
               String line = scan.nextLine();
               if (line.equalsIgnoreCase(w)) {
                   flag = true;
               }
            }
    }catch (FileNotFoundException ex){
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText("File not found");
        errorAlert.showAndWait();
    
    }
    }
    //handles noSuchElement Exception
    public static void noSuchElementHandler(){
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setHeaderText("Input not valid");
        errorAlert.setContentText("Word not Found. Pick a new one ya turkey.");
        errorAlert.showAndWait();
    }
    //handles images by adding string to url
    public static InputStream imageHandler(String s) throws FileNotFoundException{
        InputStream stream = new FileInputStream("/Users/Joe/NetBeansProjects/SampleProjects/images/" + s);
        return stream;
}
    //fills label of letters guessed by user
    public String fillTheWord(String w, String letter){
        String rValue = "";
        for (char l: w.toCharArray()){
            if (lettersGuessed.contains(Character.toString(l))){
                rValue += l + "";
            }else{
                rValue += "_ ";
            }
        }
        return rValue;
    }
    //gets word from user input
    public static String getWord(String txt, TextField tf){
        String word = tf.getText();
        return word;
    }
    //loads images, changes images based on user lives
    public ImageView setImage(int i) throws FileNotFoundException{
        Image image1 = new Image(imageHandler("one.png"),150,150,false,false);
        Image image2 = new Image(imageHandler("two.png"),150,150,false,false);
        Image image3 = new Image(imageHandler("three.png"),150,150,false,false);
        Image image4 = new Image(imageHandler("four.png"),150,150,false,false);
        Image image5 = new Image(imageHandler("five.png"),150,150,false,false);
        Image image6 = new Image(imageHandler("six.png"),150,150,false,false);
        Image starter = new Image(imageHandler("post.jpg"),150,150,false,false);
      
      switch (i) {
                case 6: img.setImage(image6);
                        break;
                case 5: img.setImage(image5);
                        break;
                case 4: img.setImage(image4);
                        break;
                case 3: img.setImage(image3);
                        break;
                case 2: img.setImage(image2);
                        break;
                case 1: img.setImage(image1);
                        break;
                default: img.setImage(starter);
                        break;
        }
      return img;
    }
    
}