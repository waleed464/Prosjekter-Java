import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.*;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.GridPane;
import javafx.collections.*;
import java.io.*;
import javafx.stage.FileChooser;
public class Hovedprogram extends Application {
    Labyrint l = null;
    int AapenFilTeller=0;
    int klikk_teller=0;
    @Override
    public void start(Stage hovedScene) throws Exception {

        try {
            StackPane rot = new StackPane();
            Scene scene = new Scene(rot, 600, 600);

            // setter tittel for scenen
            hovedScene.setTitle("Hjelp Til A Finne Ut- Labyrintloeseren");

            // oppretter en Filvelger
            FileChooser fil_velger = new FileChooser();

            // setter tittel
            fil_velger.setTitle("Velg Fil");

            // lager et etikett
            Label etikett = new Label("ingen fil valgt");
            Label etikett1 = new Label("");

            // oppretter en knapp
            Button button = new Button("Velg fil");

            //Oppretter en rutenett
            GridPane gridPane = new GridPane();

            //Innstiller størrelse for ruten
            gridPane.setMinSize(400, 200);

            //Innstilling av padding
            gridPane.setPadding(new Insets(10, 10, 10, 10));

            gridPane.setGridLinesVisible(true);

            //Innstilling av rutenettjustering
            gridPane.setAlignment(Pos.CENTER);

            ScrollPane scrollPane=new ScrollPane(gridPane);

            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

            // oppretter en VBox
            VBox vbox = new VBox(30, etikett, button,etikett1);

            // sett Justering
            vbox.setAlignment(Pos.CENTER);

            vbox.setPrefHeight(600);

            rot.getChildren().add(vbox);

            vbox.getChildren().add(scrollPane);

            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    File fil = fil_velger.showOpenDialog(hovedScene);
                  if(AapenFilTeller>0){
                       gridPane.getChildren().clear();
                      gridPane.setPadding(new Insets(10, 10, 10, 10));
                      gridPane.setGridLinesVisible(true);

                  }
                    AapenFilTeller++;
                    if (fil != null) {
                        etikett.setText(fil.getAbsolutePath()
                                + "  valgt");
                    }

                        try {
                            l = Labyrint.lesFraFil(fil);
                            for(int i=0; i<l.rader; i++) {
                                for (int j = 0; j < l.kolonner; j++) {

                                    if (l.rute[i][j] instanceof HvitRute || l.rute[i][j] instanceof Aapning) {

                                        gridPane.add(new Text(" . "), j ,i);


                                    } else if (l.rute[i][j] instanceof SortRute) {
                                        gridPane.add(new Text("#"), j, i);
                                        Pane pane=new Pane();
                                        pane.setStyle("-fx-background-color: #000000");
                                        gridPane.add(pane,j,i);

                                    }
                                }
                            }
                            scrollPane.setMaxWidth(600);
                            scrollPane.setMinWidth(200);
                            scrollPane.setMaxHeight(600);
                            scrollPane.setMinHeight(400);

                                gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                    @Override
                                    public void handle(MouseEvent mouseEvent) {

                                        Node klikketNode = mouseEvent.getPickResult().getIntersectedNode();
                                        int kildeRad = GridPane.getRowIndex(klikketNode);
                                        int kildeKol = GridPane.getColumnIndex(klikketNode);
                                        if (l.rute[kildeRad][kildeKol] instanceof HvitRute) {
                                            Liste<String> utveier = l.finnUtveiFra(kildeRad, kildeKol);
                                            System.out.print("klikket " + GridPane.getRowIndex(klikketNode) + " " + GridPane.getColumnIndex(klikketNode) + " \n");
                                            System.out.println("Antall utveier: " + Integer.toString(utveier.stoerrelse()));
                                            etikett1.setText("Antall utveier: : " + Integer.toString(utveier.stoerrelse()));

                                            if (utveier.stoerrelse() != 0) {
                                                for (String s : utveier) {
                                                    System.out.println(s);

                                                }

                                                boolean[][] utvei = losningStringTilTabell(utveier.hent(0), l.rader, l.kolonner);
                                                for (int i = 0; i < utvei.length; i++) {
                                                    for (int j = 0; j < utvei[0].length; j++) {
                                                        System.out.print(Boolean.toString(utvei[i][j]) + " ");
                                                        if (utvei[i][j]) {
                                                            Pane pane = new Pane();
                                                            if (klikk_teller > 0) {
                                                                System.out.print(klikk_teller);
                                                                if (klikk_teller % 2 == 0 && klikk_teller % 11 != 0 && klikk_teller % 7 != 0 && klikk_teller % 4 != 0 && klikk_teller % 3 != 0 && klikk_teller % 5 != 0) {
                                                                    pane.setStyle("-fx-background-color: #FF0000");
                                                                } else if (klikk_teller % 4 == 0) {
                                                                    pane.setStyle("-fx-background-color: #FFE400");
                                                                } else if (klikk_teller % 3 == 0) {
                                                                    pane.setStyle("-fx-background-color: #46FF00");
                                                                } else if (klikk_teller % 5 == 0) {
                                                                    pane.setStyle("-fx-background-color: #33F0E3");
                                                                } else if (klikk_teller % 7 == 0) {
                                                                    pane.setStyle("-fx-background-color: #00FFFF");
                                                                } else if (klikk_teller % 11 == 0) {
                                                                    pane.setStyle("-fx-background-color: #0055FF");
                                                                } else {
                                                                    pane.setStyle("-fx-background-color: #F700FF");
                                                                }

                                                            } else {
                                                                pane.setStyle("-fx-background-color: #FFFF33");

                                                            }
                                                            gridPane.add(pane, i, j);

                                                        }
                                                    }
                                                    System.out.print("\n");
                                                }
                                                klikk_teller++;
                                            } else {
                                                System.out.println("Ingen utvei.");
                                            }
                                            klikk_teller++;
                                            System.out.println();
                                        }
                                    }
                                });


                        }
                     catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
            });

            hovedScene.setTitle("Hjelp Til A Finne Ut- Labyrintloeseren");
            hovedScene.setScene(scene);
            hovedScene.show();

        }

        catch (Exception e) {

            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }


    public  boolean[][] losningStringTilTabell(String losningString, int bredde, int hoyde) {
        boolean[][] losning = new boolean[hoyde][bredde];
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
        java.util.regex.Matcher m = p.matcher(losningString.replaceAll("\\s",""));
        while (m.find()) {
            int x = Integer.parseInt(m.group(1));
            int y = Integer.parseInt(m.group(2));
            System.out.print("("+Integer.toString(x)+","+Integer.toString(y) +")");
            losning[y][x] = true;
        }
        return losning;
    }
}
