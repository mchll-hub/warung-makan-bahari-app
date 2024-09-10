package com.enigmacamp.wmb.service.impl;

import com.enigmacamp.wmb.entity.Table;
import com.enigmacamp.wmb.repository.TableRepository;
import com.enigmacamp.wmb.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {

    private final TableRepository tableRepository;

    public Table createNew(Table table){
        try{
            return tableRepository.save(table);
        } catch (DataIntegrityViolationException e){
            throw new RuntimeException("Table naem already exist");
        }
    }

    public Table getByName(String name){
        return findByNameOrThrowNotFound(name);
    }

    public List<Table> getAll(){
        return tableRepository.findAll();
    }

    public Table update(Table table){
        try{
            findByNameOrThrowNotFound(table.getName());
            return tableRepository.save(table);
        } catch (DataIntegrityViolationException e){
            throw new RuntimeException("Table name already exist");
        }
    }

    public void deleteById(String id){
        Table table = findByNameOrThrowNotFound(id);
        tableRepository.delete(table);
//        tableRepository.deleteById(id);
    }

    private Table findByNameOrThrowNotFound(String name){
        return tableRepository.findByName(name).orElseThrow(() -> new RuntimeException("Table not found"));
    }

}
