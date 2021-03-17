package com.example.testTask.Services;

import com.example.testTask.model.Note;

import java.io.*;
import java.util.ArrayList;

public class NotesContainer {
    private volatile ArrayList<Note> notesList;
    private File fileNotes;
    private String dirForFile="C:/Users/Professional/Desktop/тестовое задание/тестовое задание/Temp/Notes.bin";
    public NotesContainer(){
        ArrayList<Note> n = getNotesList();
            this.notesList= n;
        }

    private void createNewFile(){
        fileNotes= new File (dirForFile);

        try {
            fileNotes.createNewFile();
        }catch (IOException ex){
            System.out.println("Ошибка при создании папки");
        }
    }

    private File getFileNotes(){
        if(fileNotes==null){
            createNewFile();
        }
        return this.fileNotes;

    }
    synchronized private void saveInFile(){
        FileOutputStream fileOutputStream ;
        ObjectOutputStream objectOutputStream;
        try{
            fileOutputStream = new FileOutputStream(fileNotes);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this.notesList);
            objectOutputStream.flush();
            objectOutputStream.close();
        }catch(IOException ex){
            System.out.println("ошибка записи массива в файл" + ex);
            ex.printStackTrace();
        }
    }

    synchronized private ArrayList getNotesList(){
        getFileNotes();
        FileInputStream fileInputStream;
        ObjectInput objectInputStream;
        ArrayList<Note>result= new ArrayList<>();
        try {
            fileInputStream = new FileInputStream(this.fileNotes);
            int ch = 0;
            while(fileInputStream.read()!=-1){
                ch++;
            }
            fileInputStream.close();
            fileInputStream = new FileInputStream(this.fileNotes);
            if(ch>2) {
                InputStream bufferIn = new BufferedInputStream(fileInputStream);
                objectInputStream = new ObjectInputStream(bufferIn);
                try {
                    result = (ArrayList)objectInputStream.readObject();
                }catch (ClassNotFoundException ex){
                    System.out.println("ошибка чтения из Файла" + ex);
                }
                objectInputStream.close();
            }


        }catch(IOException ex){
            System.out.println("Ошибка в чтении из файла"+ ex);
        }
        return result;
    }
    public Note[] getNotes() {
        Note[] res=null;
        if(this.notesList!=null&&this.notesList.size()>0) {
            res = new Note[this.notesList.size()];
            for(int i=0;i<res.length;i++){
                res[i]= this.notesList.get(i);
            }
        }if (res == null) {
            res=new Note[1];
            res[0]= new Note();
            }
        return res;
    }
    public void  saveNotes(Note[] notes){
        this.notesList = new ArrayList<Note>();
        for (int i=0;i<notes.length;i++){
            notesList.add(notes[i]);
        }
        saveInFile();
    }


}
