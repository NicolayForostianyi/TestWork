package com.example.testTask.Services;


import com.example.testTask.model.Note;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.JsonAdapter;

public class NotesRepository {
    int greatStates = 10;
    Note[] notes;
    NotesContainer notesContainer;
    public NotesRepository(){
        notesContainer = new NotesContainer();
        notes = notesContainer.getNotes();
    }
    public boolean isItIsThereOne(String ID){
        Note n = findByID(ID);
        if(n==null){return false;}
        return true;
    }

    public Note findByID(String ID){
        for(int i=0;i<notes.length;i++){
            if(this.notes[i].getID().equals(ID)){
                return notes[i];
            }
        }
        return null;
    }
    public Note[] findAll(){

        return this.notes;
    }

    public void deleteNote(String ID){
        if(this.notes==null||this.notes.length<1){return ;}
        Note[] result = new Note[notes.length- 1];
        int ch=0;
        boolean idExist=false;
        for(int i=0;i<notes.length;i++ ){
            if(this.notes[i].getID().equals(ID)){
            ch = i;
            idExist=true;
            }
        }
        if(idExist) {
            int n = 0;
            for (int i = 0; i < notes.length; i++) {
                if (i != ch) {
                    result[n] = this.notes[i];
                    n++;
                }
            }
            this.notes = result;
            this.notesContainer.saveNotes(this.notes);
        }
    }
    public boolean editTitleNote(String ID, String title){
        boolean result = false;
        int ch=0;
        for(int i=0;i<this.notes.length ;i++){
            if(this.notes[i].getID().equals(ID)){
                result=true;
                ch=i;
            }
        }
            if(result){
                this.notes[ch].setTitle(title);
                this.notesContainer.saveNotes(this.notes);
                return result;
            }
            return result;

    }
    public boolean editContextNote(String ID, String text){
        boolean result = false;
        int ch = 0;
        for(int i =0 ;i<this.notes.length; i++){
            if(this.notes[i].getID().equals(ID)){
                result = true;
                ch=i;
            }
        }
        if(result){
            this.notes[ch].setContent(text);
            this.notesContainer.saveNotes(this.notes);
            return result;
        }
        return result;
    }
    public boolean itIsHaveID(String ID){
        boolean result=false;
        if(this.notes==null||this.notes.length<=1){
            return true;
        }
          for(int i=0;i<this.notes.length;i++){
                if(notes[i].getID().equals(ID)){
                    result=true;
                }
            }
          return result;
    }
    public String generateID(){
        if(this.notes==null||this.notes.length<=1){return "-";}
        String newId= "1";
        int num = 0;
        boolean result=true;
        while (result == true){
            num++;
            newId=String.valueOf(num);
            boolean t = this.itIsHaveID(newId);
            result = t;
        }
        return newId;
    }
    public Note addNote(String title, String content){
        Note note = new Note();
        String newID = this.generateID();
        note.setID(newID);
        note.setContent(content);
        note.setTitle(title);
        Note[] newNotes = new Note[this.notes.length+1];
        for(int i=0;i<this.notes.length;i++){
            newNotes[i] = notes[i];
        }
        newNotes[this.notes.length]= note;
        this.notes = newNotes;
        this.notesContainer.saveNotes(notes);
        return note;

    }
    public void save(){
        this.notesContainer.saveNotes(this.notes);
    }
    public Note findQuery(String query){
        Gson gson = new Gson();
        JsonArray array = new JsonArray();
        String[] masJsons = new String[this.notes.length];
        for(int i=0;i<this.notes.length;i++){
            masJsons[i] =gson.toJson(this.notes[i]) ;
        }
        return null ;

    }
    public Note[]findByContent(String content){
        Note[] result = null;
        int sizeNotes=0;
        for(int i=0;i<this.notes.length;i++){
            if(this.notes[i].getContent().equals(content)){
                sizeNotes++;
            }
        }
        if(sizeNotes!=0) {
            result = new Note[sizeNotes];
            int ch = 0;
            for(int i=0;i<this.notes.length;i++){
                if(this.notes[i].getContent().equals(content)){
                    result[ch]=this.notes[i];
                    ch++;
                }
            }
        }
        return result;
    }
    public Note[]findByTitle(String title){
        Note[] result = null;
        int sizeNotes=0;
        for(int i=0;i<this.notes.length;i++){
            if(this.notes[i].getTitle().equals(title)){
                sizeNotes++;
            }
        }
        if(sizeNotes!=0){
            result = new Note[sizeNotes];
            int ch=0;
            for(int i=0;i<this.notes.length;i++){
                if(this.notes[i].getTitle().equals(title)){
                    result[ch]=notes[i];
                    ch++;
                }
            }

        }
        return result;
    }
    public Note[] findByTitleAndContent(String title, String content){
        Note[] result= this.findByTitle(title);
        if(result==null){
            return null;
        }
        int sizeNotes=0;
        for(int i=0;i<result.length;i++){
            if(result[i].getContent().equals(content)){
                sizeNotes++;
            }
        }
        Note[] result2 = null;
        if(sizeNotes!=0) {
            result2 = new Note[sizeNotes];
            int ch = 0;
            for(int i=0;i<result2.length;i++){
                if(result[i].getContent().equals(content)){
                    result2[ch]=result[i];
                    ch++;
                }
            }
        }
        return result2;

    }
    public Note[] findByQuery(String query){
        int sizeNotes=0;
        for(int i=0;i<this.notes.length;i++) {
            if (this.notes[i].getTitle().contains(query)) {
                sizeNotes++;
            }
        }
        if(sizeNotes!=0){
            Note[] result = new Note[sizeNotes];
            int ch =0;
            for(int i=0;i<this.notes.length;i++){
                if(this.notes[i].getTitle().contains(query)){
                   result[ch]=this.notes[i];
                   ch++;
                }
            }
            return result;
         }
        sizeNotes=0;
        for(int i=0;i<this.notes.length;i++) {
            if (this.notes[i].getContent().contains(query)) {
                sizeNotes++;
            }
        }if(sizeNotes!=0){
            Note[] result = new Note[sizeNotes];
            int ch =0;
            for(int i=0;i<this.notes.length;i++){
                if(this.notes[i].getContent().contains(query)){
                    result[ch]=this.notes[i];
                    ch++;
                }
            }
            return result;
        }
            return null;
    }
    public static Note[] representNoteByQuery(Note[] notes,  int N){
        Note [] result=notes;
        for(int i=0;i<notes.length;i++){
                int numb = N;
                if(notes[i].getContent().length()<numb){numb = notes[i].getContent().length()-1;}
            if(notes[i].getTitle().equals("")||notes[i].getTitle().equals(" ")){
                result[i].setTitle(notes[i].getContent().substring(0, numb));
            }
            if(!notes[i].getTitle().equals("")||notes[i].getTitle().equals(" ")){
                result[i].setTitle(notes[i].getTitle());
            }
            result[i].setContent(notes[i].getContent());
        }
        return result;
    }


}
