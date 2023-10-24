package com.furthergrow.noteappapi.interfaces;


import com.furthergrow.noteappapi.entity.Note;
import com.furthergrow.noteappapi.repository.NoteRepository;
import com.furthergrow.noteappapi.services.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    @Override
    public Note createNote(Note note) {
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    @Override
    public Note getNoteById(Long id) {
        try {
            return noteRepository.findById(id).get();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    @Override
    public List<Note> getAllNotesByUserId(Long id) {
        return noteRepository.findAllByUserId(id);
    }

    @Override
    public Note getNoteByIdAndUserId(Long id, Long userId) {
        try {
            return noteRepository.findNoteByIdAndUserId(id,userId).get(0);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Note updateNote(Note note) {
        Note note1 = noteRepository.findById(note.getId()).get();
        note1.setUserId(1L);
        note1.setTitle(note.getTitle());
        note1.setContents(note.getContents());
        note.setUpdatedAt(LocalDateTime.now());
        Note noteUpdated = noteRepository.save(note1);
        return noteUpdated;
    }

    @Override
    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }
}