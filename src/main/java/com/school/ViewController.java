package com.school;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class ViewController implements Initializable {

//<editor-fold defaultstate="collapsed" desc="FXML items">
    @FXML
            TableView table;
    @FXML
            TextField inputLastname;
    @FXML
            TextField inputFirstName;
    @FXML
            TextField inputEmail;
    @FXML
            Button addNewClassButton;
    @FXML
            TableView teachertable;
    @FXML
            TextField teacherInputLastname;
    @FXML
            TextField teacherInputFirstName;
    @FXML
            TextField teacherInputEmail;
    @FXML
            TextField teacherInputSubject;
    @FXML
            Button addNewTeacherButton;
    @FXML
            StackPane menuPane;
    @FXML
            Pane classPane;
    @FXML
            Pane teacherPane;
    @FXML
            Pane exportPane;
    @FXML
            SplitPane mainSplit;
    @FXML
            AnchorPane anchor;
    @FXML
            TextField inputExportName;
    @FXML
            Button exportTeacherButton;
    @FXML
            Button exportClassButton;
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="class variables">
    DB db = new DB();
    
    private final String MENU_CLASSES = "Osztályok";
    private final String MENU_TEACHERS = "Tanárok";
    private final String MENU_9A = "9.a";
    private final String MENU_10A = "10.a";
    private final String MENU_EXPORT = "Exportálás";
    private final String MENU_EXIT = "Kilépés";
    
    private final ObservableList<Person> data = FXCollections.observableArrayList();
    private final ObservableList<Teacher> dataTeacher = FXCollections.observableArrayList();
//</editor-fold>
    
    @FXML
    private void addStudent(ActionEvent event) {
        String email = inputEmail.getText();
        if (email.length() > 3 && email.contains("@") && email.contains(".")) {
            Person newPerson = new Person(inputLastname.getText(), inputFirstName.getText(), email);
            data.add(newPerson);
            db.addStudent(newPerson);
            inputLastname.clear();
            inputFirstName.clear();
            inputEmail.clear();
        }else{
            alert("Adj meg egy valódi e-mail címet!");
        }
    }

    @FXML
    private void addTeacher(ActionEvent event) {
        String email = teacherInputEmail.getText();
        if (email.length() > 3 && email.contains("@") && email.contains(".") ) {
            Teacher newTeacher = new Teacher(teacherInputLastname.getText(), teacherInputFirstName.getText(), email, teacherInputSubject.getText());
            dataTeacher.add(newTeacher);
            db.addTeacher(newTeacher);
            teacherInputLastname.clear();
            teacherInputFirstName.clear();
            teacherInputEmail.clear();
            teacherInputSubject.clear();
        }else{
            alert("Adj meg egy valódi e-mail címet!");
        }
    }
    
    @FXML
    private void exportTeacher(ActionEvent event) {
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            TeacherGeneration pdfCreator = new TeacherGeneration();
            pdfCreator.pdfGeneration(fileName, dataTeacher);
        }else{
            alert("Adj meg egy fájlnevet!");
        }
    }
    
    @FXML
    private void exportClass(ActionEvent event) {
        String fileName = inputExportName.getText();
        fileName = fileName.replaceAll("\\s+", "");
        if (fileName != null && !fileName.equals("")) {
            ClassGeneration pdfCreator = new ClassGeneration();
            pdfCreator.pdfGeneration(fileName, data);
        }else{
            alert("Adj meg egy fájlnevet!");
        }
    }

    public void setTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(130);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));

        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setLastName(t.getNewValue());
                db.updateClass(actualPerson);
            }
        }
        );

        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(130);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));

        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setFirstName(t.getNewValue());
                db.updateClass(actualPerson);
            }
        }
        );

        TableColumn emailCol = new TableColumn("Email cím");
        emailCol.setMinWidth(250);
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Person, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Person, String> t) {
                Person actualPerson = (Person) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualPerson.setEmail(t.getNewValue());
                db.updateClass(actualPerson);
            }
        }
        );

        TableColumn removeCol = new TableColumn( "Törlés" );
        emailCol.setMinWidth(100);

        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = 
                new Callback<TableColumn<Person, String>, TableCell<Person, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Person, String> param )
                    {
                        final TableCell<Person, String> cell = new TableCell<Person, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Person person = getTableView().getItems().get( getIndex() );
                                                data.remove(person);
                                                db.removeClass(person);
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
        
        table.getColumns().addAll(lastNameCol, firstNameCol, emailCol, removeCol);

        data.addAll(db.getAllClasses());

        table.setItems(data);
    }

    public void setTeacherTableData() {
        TableColumn lastNameCol = new TableColumn("Vezetéknév");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastName"));

        lastNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> t) {
                Teacher actualTeacher = (Teacher) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualTeacher.setLastName(t.getNewValue());
                db.updateTeacher(actualTeacher);
            }
        }
        );

        TableColumn firstNameCol = new TableColumn("Keresztnév");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("firstName"));

        firstNameCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> t) {
                Teacher actualTeacher = (Teacher) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualTeacher.setFirstName(t.getNewValue());
                db.updateTeacher(actualTeacher);
            }
        }
        );

        TableColumn emailCol = new TableColumn("Email cím");
        emailCol.setMinWidth(150);
        emailCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("email"));
        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

        emailCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> t) {
                Teacher actualTeacher = (Teacher) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualTeacher.setEmail(t.getNewValue());
                db.updateTeacher(actualTeacher);
            }
        }
        );
        
        TableColumn subjectCol = new TableColumn("Tantárgy");
        subjectCol.setMinWidth(150);
        subjectCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("subject"));
        subjectCol.setCellFactory(TextFieldTableCell.forTableColumn());

        subjectCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Teacher, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Teacher, String> t) {
                Teacher actualTeacher = (Teacher) t.getTableView().getItems().get(t.getTablePosition().getRow());
                actualTeacher.setSubject(t.getNewValue());
                db.updateTeacher(actualTeacher);
            }
        }
        );
        
        TableColumn removeCol = new TableColumn( "Törlés" );
        emailCol.setMinWidth(100);

        Callback<TableColumn<Teacher, String>, TableCell<Teacher, String>> cellFactory = 
                new Callback<TableColumn<Teacher, String>, TableCell<Teacher, String>>()
                {
                    @Override
                    public TableCell call( final TableColumn<Teacher, String> param )
                    {
                        final TableCell<Teacher, String> cell = new TableCell<Teacher, String>()
                        {   
                            final Button btn = new Button( "Törlés" );

                            @Override
                            public void updateItem( String item, boolean empty )
                            {
                                super.updateItem( item, empty );
                                if ( empty )
                                {
                                    setGraphic( null );
                                    setText( null );
                                }
                                else
                                {
                                    btn.setOnAction( ( ActionEvent event ) ->
                                            {
                                                Teacher teacher = getTableView().getItems().get( getIndex() );
                                                dataTeacher.remove(teacher);
                                                db.removeTeacher(teacher);
                                       } );
                                    setGraphic( btn );
                                    setText( null );
                                }
                            }
                        };
                        return cell;
                    }
                };

        removeCol.setCellFactory( cellFactory );
        
        teachertable.getColumns().addAll(lastNameCol, firstNameCol, emailCol, subjectCol, removeCol);

        dataTeacher.addAll(db.getAllTeachers());

        teachertable.setItems(dataTeacher);
    }
    
    private void setMenuData() {
        TreeItem<String> treeItemRoot1 = new TreeItem<>("Menü");
        TreeView<String> treeView = new TreeView<>(treeItemRoot1);
        treeView.setShowRoot(false);

        TreeItem<String> nodeItemA = new TreeItem<>(MENU_CLASSES);
        TreeItem<String> nodeItemB = new TreeItem<>(MENU_TEACHERS);
        TreeItem<String> nodeItemC = new TreeItem<>(MENU_EXPORT);
        TreeItem<String> nodeItemD = new TreeItem<>(MENU_EXIT);

        nodeItemA.setExpanded(true);

        Node class9aNode = new ImageView(new Image(getClass().getResourceAsStream("/9a.png")));
       // Node class10aNode = new ImageView(new Image(getClass().getResourceAsStream("/10a.png")));
        TreeItem<String> nodeItemA1 = new TreeItem<>(MENU_9A, class9aNode);
       // TreeItem<String> nodeItemA2 = new TreeItem<>(MENU_10A, class10aNode);

        nodeItemA.getChildren().addAll(nodeItemA1);  //nodeItemA2
        treeItemRoot1.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC, nodeItemD);

        menuPane.getChildren().add(treeView);

        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                TreeItem<String> selectedItem = (TreeItem<String>) newValue;
                String selectedMenu;
                selectedMenu = selectedItem.getValue();

                if (null != selectedMenu) {
                    switch (selectedMenu) {
                        case MENU_CLASSES:
                            selectedItem.setExpanded(true);
                            break;
                        case MENU_9A:
                            classPane.setVisible(true);
                            teacherPane.setVisible(false);
                            exportPane.setVisible(false);
                            break;
                            /*case MENU_10A:
                            classPane.setVisible(true);
                            teacherPane.setVisible(false);
                            exportPane.setVisible(false);
                            break;*/
                        case MENU_TEACHERS:
                            classPane.setVisible(false);
                            teacherPane.setVisible(true);
                            exportPane.setVisible(false);
                            break;    
                        case MENU_EXPORT:
                            classPane.setVisible(false);
                            teacherPane.setVisible(false);
                            exportPane.setVisible(true);
                            break;
                        case MENU_EXIT:
                            System.exit(0);
                            break;
                    }
                }

            }
        });

    }

    private void alert(String text) {
        mainSplit.setDisable(true);
        mainSplit.setOpacity(0.4);
        
        Label label = new Label(text);
        Button alertButton = new Button("OK");
        VBox vbox = new VBox(label, alertButton);
        vbox.setAlignment(Pos.CENTER);
        
        alertButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                mainSplit.setDisable(false);
                mainSplit.setOpacity(1);
                vbox.setVisible(false);
            }
        });
        
        anchor.getChildren().add(vbox);
        anchor.setTopAnchor(vbox, 300.0);
        anchor.setLeftAnchor(vbox, 300.0);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTableData();
        setTeacherTableData();
        setMenuData();
    }

}
