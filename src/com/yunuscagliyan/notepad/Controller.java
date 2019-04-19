package com.yunuscagliyan.notepad;

import com.sun.javafx.property.adapter.PropertyDescriptor;
import com.yunuscagliyan.notepad.datamodel.NotData;
import com.yunuscagliyan.notepad.datamodel.NoteElements;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {

    private ContextMenu lisViewContextMenu;

    @FXML
    private ListView lvNoteLists;

   // private ArrayList<NoteElements> noteLists;

    @FXML
    private TextArea taNoteDetails;

    @FXML
    private Label lbLastDate;
    @FXML
    private ToggleButton btnFilter;
    private FilteredList<NoteElements> filteredList;
    private Predicate<NoteElements>allNotes;
    private Predicate<NoteElements>todayNotes;
    @FXML
    public void initialize(){
        /*
        NoteElements note1=new NoteElements("Hediye Al","Anneler günü için alış verişe çıkmalısın",
                LocalDate.of(2018,10,20));
        NoteElements note2=new NoteElements("Spora git","Çok fazla kilo aldın spora gitmelisin",
                LocalDate.of(2018,11,30));
        NoteElements note3=new NoteElements("Markete git","Dolapta yiyecek bir şey kalmamış",
                LocalDate.of(2018,10,21));
        NoteElements note4=new NoteElements("Tatilini ayarla","Bütçene göre erkenden yaz tatilini ayarla",
                LocalDate.of(2019,03,13));

        noteLists=new ArrayList<>();
        noteLists.add(note1);
        noteLists.add(note2);
        noteLists.add(note3);
        noteLists.add(note4);
        */

        //lvNoteLists.getItems().addAll(NotData.getInstance().getAlNotes());


        //Tek seferlik dosyaya yazma
       // NotData.getInstance().setAlNotes(noteLists);
        lisViewContextMenu=new ContextMenu();
        MenuItem removeNote=new MenuItem("Remove note");
        removeNote.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                NoteElements remove= (NoteElements) lvNoteLists.getSelectionModel().getSelectedItem();
                removeNote(remove);
            }
        });
        lisViewContextMenu.getItems().setAll(removeNote);


        lvNoteLists.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(newValue !=null){
                    NoteElements selectedNote=(NoteElements)lvNoteLists.getSelectionModel().getSelectedItem();
                    taNoteDetails.setText(selectedNote.getNoteDetail());

                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    lbLastDate.setText(selectedNote.getLastDate().format(formatter));
                }

            }
        });
        allNotes= new Predicate<NoteElements>() {
            @Override
            public boolean test(NoteElements noteElements) {
                return true;
            }
        };
        todayNotes= new Predicate<NoteElements>() {
            @Override
            public boolean test(NoteElements noteElements) {
                return noteElements.getLastDate().equals(LocalDate.now());
            }
        };
        filteredList=new FilteredList<NoteElements>(NotData.getInstance().getAlNotes(),allNotes);
        SortedList<NoteElements> sortedNoteList=new SortedList<NoteElements>(filteredList, new Comparator<NoteElements>() {
            @Override
            public int compare(NoteElements t1, NoteElements t2) {
                return  t1.getLastDate().compareTo(t2.getLastDate());
            }
        });
        lvNoteLists.setItems(sortedNoteList);
        //lvNoteLists.setItems(NotData.getInstance().getAlNotes());//Listeye observableLis'den çekilen değerler yazılır...
        lvNoteLists.getSelectionModel().selectFirst();
        lvNoteLists.setCellFactory(new Callback<ListView, ListCell>() {
            @Override
            public ListCell call(ListView param) {
                ListCell<NoteElements> cell = new ListCell<NoteElements>() {
                    @Override
                    protected void updateItem(NoteElements item, boolean empty) {
                        super.updateItem(item,empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(item.toString());
                            if (item.getLastDate().equals(LocalDate.now())) {
                                setTextFill(Color.RED);

                            }else if(item.getLastDate().equals(LocalDate.now().plusDays(1))){
                                setTextFill(Color.BLUE);
                            }else if(item.getLastDate().isBefore(LocalDate.now())){
                                setTextFill(Color.DARKBLUE);
                            }
                            else{
                                setTextFill(Color.BLACK);
                            }
                        }
                    }
                };
                cell.setContextMenu(lisViewContextMenu);

                return cell;
            }
        });



    }

    private void removeNote(NoteElements remove) {
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove Note");
        alert.setHeaderText("will remove the note is "+remove.getTitle());
        alert.setContentText("Do you confirm ?");
       // alert.showAndWait();

        Optional<ButtonType> result=alert.showAndWait();
        if(result.get()==ButtonType.OK){
            NotData.getInstance().removeNote(remove);

        }


    }
    @FXML
    public void accessOnKeyBoard(KeyEvent event){

        NoteElements removeNote=(NoteElements) lvNoteLists.getSelectionModel().getSelectedItem();
        if(removeNote!=null){
            if(event.getCode().equals(KeyCode.DELETE)){
                removeNote(removeNote);
            }


        }
    }

    //@FXML
    /*
    public void noteSelected(){

     NoteElements selectedItems= (NoteElements)lvNoteLists.getSelectionModel().getSelectedItem();

     /*
     StringBuilder sb=new StringBuilder(selectedItems.getNoteDetail());
     sb.append("\n\n\n\n");
     sb.append(selectedItems.getLastDate().toString());
     taNoteDetails.setText(sb.toString());

     taNoteDetails.setText(selectedItems.getNoteDetail());
     lbLastDate.setText(selectedItems.getLastDate().toString());


    }

     */
    @FXML
    public void filterNotes(){
        if(btnFilter.isSelected()){

            filteredList.setPredicate(todayNotes);
            if(filteredList.isEmpty()){
                taNoteDetails.clear();
                lbLastDate.setText("");
            }

        }else{
            filteredList.setPredicate(allNotes);
        }

    }
    @FXML
    private BorderPane homeBorderPane;

    @FXML
    public void showAddNoteDialog() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(homeBorderPane.getScene().getWindow());

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("addNoteDialog.fxml"));
        dialog.setTitle("New Note");
        dialog.setHeaderText("Add new note");
        //Parent root=FXMLLoader.load(getClass().getResource("addNoteDialog.fxml"));
        dialog.getDialogPane().setContent(fxmlLoader.load());

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //dialog.show();

        Optional<ButtonType> result = dialog.showAndWait();

        if (result.get() == ButtonType.OK) {
            /*
            AddNoteDialogController addNoteDialogController=new AddNoteDialogController();
            addNoteDialogController.addNote();
            System.out.println("OK Basıldı...");
            */
            AddNoteDialogController controller = fxmlLoader.getController();
            NoteElements newNote = controller.addNote();
            //Yeni nottan sonra observableList kullandığımızdan listeyi güncellememiz gerekmiyor..
            //lvNoteLists.getItems().setAll(NotData.getInstance().getAlNotes());
            lvNoteLists.getSelectionModel().select(newNote);



        }
    }


}
