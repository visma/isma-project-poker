package org.isma.poker.factory;

import org.isma.poker.game.Table;

public class TableFactory implements ITableFactory{
    @Override
    public Table buildTable() {
        return new Table();
    }
}
