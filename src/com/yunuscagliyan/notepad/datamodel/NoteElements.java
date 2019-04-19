package com.yunuscagliyan.notepad.datamodel;

import java.time.LocalDate;

public class NoteElements {

    private String title;
    private String noteDetail;
    private LocalDate LastDate;

    public NoteElements(String title, String noteDetail, LocalDate lastDate) {
        this.title = title;
        this.noteDetail = noteDetail;
        LastDate = lastDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteDetail() {
        return noteDetail;
    }

    public void setNoteDetail(String noteDetail) {
        this.noteDetail = noteDetail;
    }

    public LocalDate getLastDate() {
        return LastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        LastDate = lastDate;
    }

    @Override
    public String toString() {
        return title;
    }
}
