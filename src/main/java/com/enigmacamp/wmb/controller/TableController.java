package com.enigmacamp.wmb.controller;

import com.enigmacamp.wmb.entity.Table;
import com.enigmacamp.wmb.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tables")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PostMapping
    public Table createNewTable(@RequestBody Table table){
        return tableService.createNew(table);
    }

    @GetMapping("/{name}")
    public Table getTableByName(@PathVariable String name){
        return tableService.getByName(name);
    }

    @GetMapping
    public List<Table> getAllTable(){
        return tableService.getAll();
    }

    @PutMapping
    public Table updateTable(@RequestBody Table table){
        return tableService.update(table);
    }

    @DeleteMapping("/{id}")
    public void deleteTable(@PathVariable String id){
        tableService.deleteById(id);
    }
}
