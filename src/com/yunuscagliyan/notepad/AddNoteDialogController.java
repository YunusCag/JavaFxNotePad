package com.yunuscagliyan.notepad;

import com.yunuscagliyan.notepad.datamodel.NotData;
import com.yunuscagliyan.notepad.datamodel.NoteElements;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

public class AddNoteDialogController {

    @FXML
    private TextField tfNoteTitle;
    @FXML
    private TextArea taNoteDetail;
    @FXML
    private DatePicker dpLastDate;

    public NoteElements addNote() throws IOException {
        String title=tfNoteTitle.getText();
        String detail=taNoteDetail.getText();
        LocalDate lastDate=dpLastDate.getValue();
        NoteElements note=new NoteElements(title,detail,lastDate);
        NotData.getInstance().addNotes(note);

        return note;


    }
}
