package com.yunuscagliyan.notepad.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class NotData {

    private static NotData instance=new NotData();
    private String fileName="noteList.txt";
    private DateTimeFormatter formatter;
    private ObservableList<NoteElements> alNotes;

    public ObservableList<NoteElements> getAlNotes() {
        return alNotes;
    }

    public void setAlNotes(ObservableList<NoteElements> alNotes) {
        this.alNotes = alNotes;
    }

    private NotData(){

        formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");

    }
    public static  NotData getInstance(){
        return instance;
    }
    public void readNotesOnFile() throws IOException {

        alNotes=FXCollections.observableArrayList();

        Path filePath=Paths.get(fileName);
        BufferedReader bufferedReader=Files.newBufferedReader(filePath);

        String statement;

        try {
            while ((statement = bufferedReader.readLine()) != null) {
                String[] noteParts = statement.split("\t");
                String title = noteParts[0];
                String detail = noteParts[1];
                String lastDate = noteParts[2];

                LocalDate date = LocalDate.parse(lastDate, formatter);

                NoteElements note = new NoteElements(title, detail, date);

                alNotes.add(note);
            }
        }finally {
            if(bufferedReader !=null){
                bufferedReader.close();

            }
        }

    }
    public void addNotes(NoteElements e) throws IOException {
        alNotes.add(e);
    }
    public void removeNote(NoteElements remove){
        alNotes.remove(remove);


    }
    public void writeNotesOnFile() throws IOException {

        Path filePath=Paths.get(fileName);
        BufferedWriter bufferedWriter=Files.newBufferedWriter(filePath);

        Iterator<NoteElements> iterator=alNotes.iterator();

        try {
            while (iterator.hasNext()) {
                NoteElements note = iterator.next();
                bufferedWriter.write(String.format("%s\t%s\t%s", note.getTitle(), note.getNoteDetail(), note.getLastDate().format(formatter)));

                bufferedWriter.newLine();
            }
        }
        finally {
            if(bufferedWriter!=null){
                bufferedWriter.close();
            }
        }
    }
}
