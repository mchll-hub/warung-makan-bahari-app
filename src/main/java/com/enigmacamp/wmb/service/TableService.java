package com.enigmacamp.wmb.service;

import com.enigmacamp.wmb.entity.Table;

import java.util.List;

public interface TableService {
    Table createNew(Table table);

    Table getByName(String name);

    List<Table> getAll();

    Table update (Table table);

    void deleteById(String id);
}
