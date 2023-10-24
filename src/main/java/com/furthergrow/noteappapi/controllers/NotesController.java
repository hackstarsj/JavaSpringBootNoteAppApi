package com.furthergrow.noteappapi.controllers;

import com.furthergrow.noteappapi.config.JwtAuthenticationFilter;
import com.furthergrow.noteappapi.entity.Note;
import com.furthergrow.noteappapi.entity.User;
import com.furthergrow.noteappapi.services.NoteService;
import com.furthergrow.noteappapi.utils.Utils;
import eu.fraho.spring.securityJwt.dto.JwtUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("api/notes")
public class NotesController {
    private NoteService noteService;

    // build create note REST API
    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note){
        note.setUserId(Utils.AuthUser().getId());
        Note savedUser = noteService.createNote(note);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    // build get note by id REST API
    // http://localhost:8080/api/users/1
    @GetMapping("{id}")
    public Object getNoteById(@PathVariable("id") Long id){
        Note user = noteService.getNoteByIdAndUserId(id,Utils.AuthUser().getId());
        return Utils.responseHandler(user);
    }

    // Build Get All Note REST API
    // http://localhost:8080/api/users
    @GetMapping
    public Object getAllNote(){
//        List<Note> users = noteService.getAllNotes();
        List<Note> users = noteService.getAllNotesByUserId(Utils.AuthUser().getId());
        return Utils.responseHandler(users);
    }

    // Build Update Note REST API
    @PutMapping("{id}")
    // http://localhost:8080/api/users/1
    public ResponseEntity<Note> updateNote(@PathVariable("id") Long userId,
                                           @RequestBody Note user){
        user.setId(userId);
        Note updatedUser = noteService.updateNote(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    // Build Delete User REST API
    @DeleteMapping("{id}")
    public Map<String,Object> deleteNote(@PathVariable("id") Long userId){
        noteService.deleteNote(userId);
        Map<String, Object> stringMap=new HashMap<>();
        stringMap.put("error",false);
        stringMap.put("message","Deleted Successfully");
        return stringMap;
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @GetMapping("profile")
    public Object getUserProfile(){
        return Utils.AuthUser();
    }
}
