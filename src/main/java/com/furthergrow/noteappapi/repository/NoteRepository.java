package com.furthergrow.noteappapi.repository;
import com.furthergrow.noteappapi.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByUserId(Long userId);
    List<Note> findNoteByIdAndUserId(Long id,Long userId);

}