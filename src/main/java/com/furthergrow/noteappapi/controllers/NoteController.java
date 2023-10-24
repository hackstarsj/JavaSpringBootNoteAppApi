package com.furthergrow.noteappapi.controllers;

import com.furthergrow.noteappapi.models.Notes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class NoteController {

    @GetMapping
    public String getHelloWorld(){
        return "Hello World";
    }

    @GetMapping("/helloJson")
    public Map<String,String> getHelloJSON(){
        Map<String,String> map=new HashMap<>();
        map.put("hello","world");
        map.put("name","sanjeev");
        return map;
    }

    @GetMapping("/getNoteList")
    public List<Notes> getNoteList(){
        List<Notes> notesList=new ArrayList<>();
        notesList.add(new Notes(1L,"Hello","World","1","2022-12-11 11:10:11"));
        notesList.add(new Notes(1L,"Hello","World","1","2022-12-11 11:10:11"));
        notesList.add(new Notes(1L,"Hello","World","1","2022-12-11 11:10:11"));
        notesList.add(new Notes(1L,"Hello","World","1","2022-12-11 11:10:11"));
        return notesList;
    }

    @GetMapping("/getUrlValueExample/{firstname}/{lastname}")
    public Map<String,String> getUrlValueExample(@PathVariable("firstname") String firstname,@PathVariable("lastname") String lastname){
        Map<String,String> map=new HashMap<>();
        map.put(firstname,lastname);
        return map;
    }

    @PostMapping("/getRequestDataWithModelExample")
    public Map<String,Object> getRequestWithOutModelExample(@RequestBody Map<String, Object> requestData){
        return requestData;
    }

    @PostMapping("/getRequestDataWithModelListExample")
    public List<Map<String, Object>> getRequestWithOutModelListExample(@RequestBody List<Map<String,Object>> requestDatalist){
        return requestDatalist;
    }

    @PostMapping("/getRequestDataWithUrlValue/{value}")
    public List<Map<String, Object>> getRequestDataWithUrlValue(@PathVariable("value") String value,@RequestBody List<Map<String,Object>> requestDatalist){
        Map<String,Object> map=new HashMap<>();
        map.put("value",value);
        requestDatalist.add(map);
        return requestDatalist;
    }


    @PostMapping("/helloJsonPost")
    public Notes postHelloJSON(@RequestBody Notes notes){
        return notes;
    }


}
