package com.furthergrow.noteappapi.services;

import com.furthergrow.noteappapi.entity.Note;

import java.util.List;

public interface NoteService {
    Note createNote(Note user);

    Note getNoteById(Long id);

    List<Note> getAllNotes();
    List<Note> getAllNotesByUserId(Long id);
    Note getNoteByIdAndUserId(Long id,Long userId);

    Note updateNote(Note note);

    void deleteNote(Long id);
}