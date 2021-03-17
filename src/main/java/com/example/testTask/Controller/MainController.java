package com.example.testTask.Controller;
import com.example.testTask.ExceptionHanding.NoSuchNoteException;
import com.example.testTask.ExceptionHanding.NoteExceptionManager;
import com.example.testTask.Services.NotesRepository;
import com.example.testTask.model.Note;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("/api")
public class MainController {
    private static int N;
    boolean beWrite =false;
    @Value("${N}")
    public void setN(int Num){
        N=Num;
    }



    @GetMapping("/notes")
    public List<Note> allNotes(@RequestParam(required = false)String query, @RequestParam(required = false)String title,@RequestParam(required = false)String content)  {
       while(beWrite){}
        NotesRepository notesRepository = new NotesRepository();
        Note[] mass =notesRepository.findAll();
        List<Note> noteList = new ArrayList<>();
                    if(title!=null&&content!=null){
                        Note[] notes = notesRepository.findByTitleAndContent(title, content);
                        if(notes==null){
                            throw new NoSuchNoteException("There is no note with title = " + title +
                                    "and content  = " + content+ "in database ");
                        }
                        for(int i =0;i<notes.length;i++){
                            noteList.add(notes[i]);
                        }
                        return noteList;
                    }

                    if(content!=null){
                                Note[] notes = notesRepository.findByContent(content);
                                if(notes==null){
                                    throw new NoSuchNoteException("There is no note with content = " + content + "in database");
                                }
                                for(int i=0;i<notes.length;i++){
                                    noteList.add(notes[i]);
                                }
                                return noteList;
                    }
                    if(title!=null){
                        Note[] notes = notesRepository.findByTitle(title);
                        if(notes==null){
                            throw new NoSuchNoteException("There is no note with title =" + title + " in database");
                        }
                        for(int i=0;i<notes.length;i++){
                            noteList.add(notes[i]);
                        }
                        return noteList;
                    }
                    if(query!=null){
                        Note[] notes = notesRepository.findByQuery(query);
                        if(notes==null){
                            throw new NoSuchNoteException("There is no note with query = " + query+" in database");
                        }
                        notes = NotesRepository.representNoteByQuery( notes , N);
                        for(int i=0;i<notes.length;i++){
                            noteList.add(notes[i]);
                        }
                        return noteList;

                    }
        for(int i=1;i<mass.length;i++){
            noteList.add(mass[i]);
        }
        return noteList;
    }
    @GetMapping("/notes/{id}")
        public Note getNoteById(@PathVariable int id){
        while(beWrite){}
            NotesRepository notesRepository = new NotesRepository();
            Note note = notesRepository.findByID(String.valueOf(id));

            if(note==null){
                throw new NoSuchNoteException("There is no note with id =" + id + " in database");
            }
            HttpStatus.resolve(200);
            return note;
        }

    @ExceptionHandler
    public ResponseEntity <NoteExceptionManager>handleException(NoSuchNoteException ex){
        NoteExceptionManager data = new NoteExceptionManager();
        data.setInfo(ex.getMessage());

        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);

    }
    @ExceptionHandler
    public ResponseEntity <NoteExceptionManager>handleException(Exception ex){
        NoteExceptionManager data = new NoteExceptionManager();
        data.setInfo(ex.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);

    }
    @PostMapping("/notes")
    public Note addNote(@RequestBody Note newNote ){
        NotesRepository notesRepository = new NotesRepository();
        Note result = notesRepository.addNote(newNote.getTitle(), newNote.getContent());
        notesRepository.save();
        return result;
    }
    @PutMapping("/notes/{id}")
    public Note editNote(@PathVariable int id, @RequestBody Note newNote){
        synchronized (this){
            Note result = null;
        try {
            NotesRepository notesRepository = new NotesRepository();
            this.beWrite = true;
            notesRepository.editTitleNote(String.valueOf(id), newNote.getTitle());
            notesRepository.editContextNote(String.valueOf(id), newNote.getContent());
            this.beWrite = false;
            result = notesRepository.findByID(String.valueOf(id));

            if (result == null) {
                this.beWrite = false;
                throw new NoSuchNoteException("There is no note with id =" + id + " in database");
            }
        }catch(Exception ex){
            this.beWrite=false;
            System.out.println(ex.toString());
        }
        return result;
        }
    }
    @DeleteMapping("/notes/{id}")
    public String deleteNote(@PathVariable int id){
        synchronized (this) {

            NotesRepository notesRepository = new NotesRepository();
            Note result = notesRepository.findByID(String.valueOf(id));
            if (result == null) {
                this.beWrite=false;
                throw new NoSuchNoteException("There is no note with id =" + id + " in database");
            }
            try {
                this.beWrite = true;
                notesRepository.deleteNote(String.valueOf(id));
                this.beWrite = false;
            }catch(Exception ex){this.beWrite=false;}
            return "The note with id = " + id + "was been deleted";
        }
    }






}